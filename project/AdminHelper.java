import java.util.Scanner;
import java.sql.SQLException;
public class AdminHelper {
    private static Scanner ob = new Scanner(System.in);
    public static void addTheatre() throws SQLException {
        System.out.println("Enter the theatre name:");
        String name = ob.nextLine();
        System.out.println("Enter the theatre location:");
        String location = ob.nextLine();
        System.out.println("Enter the capacity of the theatre:");
        int capacity = ob.nextInt();
        ob.nextLine();
        System.out.println("Enter the theatre type(true(AC) & false(NON AC):");
        boolean theatretype = ob.nextBoolean();
        AdminDb.addTheatre(name, location, capacity, theatretype);
    }
    public static void addMovie() throws SQLException {
        System.out.println("Enter the movie name:");
        String moviename = ob.nextLine();
        System.out.println("Enter the language:");
        String language = ob.next();
        System.out.println("Enter the duration:");
        long duration = ob.nextLong();
        ob.nextLine();
        AdminDb.addMovie(moviename, language, duration);
    }
    public static void updateMovie() throws SQLException {
        System.out.println(AdminDb.displayTheatre());
        System.out.println("Enter the theatre id:");
        int updatetheatre = ob.nextInt();
        System.out.println(AdminDb.displayMovie());
        System.out.println("Enter the movie:");
        int updatemovie = ob.nextInt();
        AdminDb.updatemovie(updatetheatre, updatemovie);
    }
    public static void deleteTheatre() throws SQLException {
        AdminDb.displayTheatre();
        System.out.println("Enter the theatre id you want to delete:");
        int theatreid = ob.nextInt();
        AdminDb.deleteTheatre(theatreid);
    }
    public static void deleteMovie() throws SQLException {
        System.out.println(AdminDb.displayMovie());
        System.out.println("Enter the movie id to delete:");
        int id = ob.nextInt();
        AdminDb.deleteMovie(id);
    }
    public static void signin() throws SQLException {
        System.out.println("Enter the username:");
        String username = ob.nextLine();
        System.out.println("Enter the password:");
        String password = ob.nextLine();
        AdminDb.adminlogin(username, password);
    }
}

