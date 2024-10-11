import java.util.Scanner;
import java.sql.SQLException;

public class UserHelper {
    private static Scanner ob = new Scanner(System.in);

    public static void signup() throws SQLException {
        System.out.println("Enter the name:");
        String name = ob.nextLine();
        System.out.println("Enter the username:");
        String username = ob.nextLine();
        System.out.println("Enter the mobile number:");
        long mobile = ob.nextLong();
        ob.nextLine();  
        System.out.println("Enter the email id:");
        String mail = ob.nextLine();
        System.out.println("Enter the password:");
        String password = ob.nextLine();
        UserDb.insertRecords(name, username, mobile, mail, password);
    }
    public static int login()throws SQLException{
        System.out.print("Enter your username: ");
        String username = ob.nextLine();
        System.out.print("Enter your password: ");
        String password = ob.nextLine();
        int id = BookingDb.login(username, password);
        if(id!=0){
        	Booking.book(id);
        }
        return 0;
    }

    public static void update_user(int id) {
        while (true) {
            String oldvalue, newvalue;
            System.out.println("Enter the choice for update:\n1.Name\n2.Mobile\n3.Mail\n4.Password\n5.Exit");
            int choice = ob.nextInt();
            ob.nextLine();  
            
            switch (choice) {
                case 1:
                    System.out.println("Enter the new value:");
                    newvalue=ob.nextLine();
                    UserDb.update_user("name",  newvalue,id);
                    break;
                case 2:
                    
                    System.out.println("Enter the new mobile number:");
                    long newMobile = ob.nextLong();
                    ob.nextLine();  
                    UserDb.update_user("mobile", String.valueOf(newMobile),id);
                    break;
                case 3:
                    
                    System.out.println("Enter the new email:");
                    newvalue = ob.nextLine();
                    UserDb.update_user("mail",newvalue,id);
                    break;
                case 4:
                    System.out.println("Enter the username:");
                    String username = ob.nextLine();
                    System.out.println("Enter the new password:");
                    newvalue = ob.nextLine();
                    UserDb.update_Usercred(username,newvalue);
                    break;
                case 5:
                    return;

                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
            
        }
    }
    
}

