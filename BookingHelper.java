import java.util.Scanner;
import java.sql.SQLException;

public class BookingHelper {
    private static Scanner ob = new Scanner(System.in);
    public static void displayMovieInTheatre() {
        System.out.println("Enter the theatre name to see the movies running there:");
        String name = ob.nextLine();
        BookingDb.displayMoviesInTheatre(name);
    }
    public static void bookTicket(int id) throws SQLException {
        boolean s = true;
        String Location_name = null;
        while (s) {
            System.out.println("1. Sivakasi\n2. Sriviliputhur\n3. Virudhunagar\nSelect your Location:");
            int location = ob.nextInt();
            switch (location) {
                case 1:
                    Location_name = "Sivakasi";
                    if(BookingDb.displayMovie(Location_name) == 0) {
                        System.out.println("No theatres found in " + Location_name);
                        return;
                    }
                    s = false;
                    break;
                case 2:
                    Location_name = "Srivi";
                    if (BookingDb.displayMovie(Location_name) == 0) {
                        System.out.println("No theatres found in " + Location_name);
                        return;
                    }
                    s = false;
                    break;
                case 3:
                    Location_name = "Virudhunagar";
                    if (BookingDb.displayMovie(Location_name) == 0) {
                        System.out.println("No theatres found in " + Location_name);
                        return;
                    }
                    s = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        System.out.println("Enter the theatre id:");
        int theatreId = ob.nextInt();
        System.out.println(BookingDb.displaySession());
        System.out.println("Enter the session:");
        int sessionid = ob.nextInt();
        int theatreCapacity = BookingDb.seatCapacity(theatreId);
        int booked_seat = BookingDb.available_seat(theatreId, sessionid);
        int available_seat = theatreCapacity - booked_seat;
        System.out.println("Available Seats: " + available_seat);
        System.out.println("Enter the number of seats you want to book:");
        int seats = ob.nextInt();
        if (available_seat >= seats) {
            BookingDb.bookTicket(id, theatreId, seats, sessionid);
        } else {
            System.out.println("Not enough seats available. Only " + available_seat + " seats available.");
        }
    }
    public static void displayUserBookings(int id) throws SQLException {
        System.out.println(BookingDb.displayBookings(id));
    }
}

