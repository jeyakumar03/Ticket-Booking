import java.sql.SQLException;
import java.util.Scanner;

public class UserHelper {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDb userdb = new UserDb();
    public static void signup() throws SQLException {
        System.out.println("Enter the name:");
        String name = scanner.nextLine();
        System.out.println("Enter the username:");
        String username = scanner.nextLine();
        System.out.println("Enter the mobile number:");
        long mobile = scanner.nextLong();
        scanner.nextLine();  
        System.out.println("Enter the email id:");
        String email = scanner.nextLine();
        System.out.println("Enter the password:");
        String password = scanner.nextLine();
        User user = new User(name, username, mobile, email, password);
        userdb.insertUser(user);
    }
    public static int login() throws SQLException {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        int userId = userdb.login(username, password);
        if (userId != 0) {
            Booking.book(userId);  
        }
        return userId;
    }
    public static void updateUser(int id) {
        while (true) {
            System.out.println("-----------------------------------------------");
            System.out.println("Enter the choice for update:\n1.Name\n2.Mobile\n3.Mail\n4.Password\n5.Exit");
            System.out.println("-----------------------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) {
                case 1:
                    System.out.println("Enter the new name:");
                    String newName = scanner.nextLine();
                     userdb.updateUser("name", newName, id);
                    break;
                case 2:
                    System.out.println("Enter the new mobile number:");
                    long newMobile = scanner.nextLong();
                    scanner.nextLine();  
                    userdb.updateUser("mobile", String.valueOf(newMobile), id);
                    break;
                case 3:
                    System.out.println("Enter the new email:");
                    String newEmail = scanner.nextLine();
                     userdb.updateUser("mail", newEmail, id);
                    break;
                case 4:
                    System.out.println("Enter the new password:");
                    String newPassword = scanner.nextLine();
                    userdb.updateUserCredentials(id, newPassword);
                    break;
                case 5:
                    return; // Exit the loop
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

