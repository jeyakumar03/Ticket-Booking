import java.util.Scanner;
import java.sql.SQLException;

public class Booking {
    private static Scanner scanner = new Scanner(System.in);

    public static void book(int userId) throws SQLException {
        boolean continueBooking = true;

        while (continueBooking) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(BookingDb.displayTheatre());
                    break;
                case 2:
                    System.out.println("Enter the theatre name:");
                    String theatreName = scanner.nextLine();
                    System.out.println(BookingDb.displayMoviesInTheatre(theatreName));
                    break;
                case 3:
                    BookingHelper.bookTicket(userId);
                    break;
                case 4:
                    UserHelper.updateUser(userId); 
                    break;
                case 5:
                    BookingHelper.displayUserBookings(userId);
                    break;
                case 6:
                    continueBooking = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("-------------------------------------------------------");
        System.out.println("1. Display Theatre and Movie List");
        System.out.println("2. Display Movies Running in a Theatre");
        System.out.println("3. Book a Ticket");
        System.out.println("4. Edit User Info");
        System.out.println("5. View Booking Details");
        System.out.println("6. Exit");
        System.out.println("-------------------------------------------------------");
        System.out.print("Enter your choice: ");
    }
}

