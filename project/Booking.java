import java.util.Scanner;
import java.sql.SQLException;
public class Booking {
    private static Scanner ob = new Scanner(System.in);
    public static void book(int id) throws SQLException {
        boolean s = true;
        while (s) {
            System.out.println("-------------------------------------------------------");
            System.out.println("1. Display The theatre and movie list");
            System.out.println("2. Display the movies which are running in the theatre");
            System.out.println("3. Book a ticket");
            System.out.println("4.Edit");
            System.out.println("5.Booking details");
            System.out.println("6.Exit");
            System.out.println("-------------------------------------------------------");
            System.out.println("Enter your choice:");
            int choice = ob.nextInt();
            System.out.println("-------------------------------------------------------");
            ob.nextLine();  
            switch (choice){
                case 1:
                    System.out.println(BookingDb.displayTheatre());
                    break;

                case 2:
                    System.out.println("Enter the theatre name:");
                    String theatrename = ob.nextLine();
                    BookingDb.displayMoviesInTheatre(theatrename);
                    break;
                case 3:
                    BookingHelper.book(id);
                    break;
                case 4:
		UserHelper.update_user(id);
		break;
	     case 5:
	     	BookingHelper.displayUserBookings(id);
	     	break;
                case 6:
                	s=false;
                	break;

                default:
                    System.out.println("Please enter a valid choice");
                    break;
            }
        }
    }
}

