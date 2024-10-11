import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class BookingDb { 
    public static String displayTheatre() throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.display_theatre);
            rs = pst.executeQuery();
            while (rs.next()) {
                sb.append("-------------------------------------------------\n");
                sb.append("Theatre Name       : ").append(rs.getString("Theatre")).append("\n");
                sb.append("Theatre Location   : ").append(rs.getString("Location")).append("\n");
                sb.append("Movie              : ").append(rs.getString("Movie")).append("\n");
                boolean isAc = rs.getBoolean("Type");
                sb.append("Type               : ").append(isAc ? "AC" : "Non-AC").append("\n");
                sb.append("-------------------------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return sb.toString();
    }

    public static int displayTheatresForMovie(int movieid) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.displaytheatre);
            pst.setInt(1, movieid);
            rs = pst.executeQuery();
            boolean theatreFound = false;
            while (rs.next()) {
                theatreFound = true;
                System.out.println("-------------------------------------------------");
                System.out.println("Theatre ID     : " + rs.getInt("id"));
                System.out.println("Theatre Name   : " + rs.getString("name"));
                System.out.println("Location       : " + rs.getString("location"));
                System.out.println("Capacity       : " + rs.getInt("capacity"));
                boolean isAc = rs.getBoolean("isac");
                System.out.println("Type           : " + (isAc ? "AC" : "Non-AC"));
                System.out.println("-------------------------------------------------");
            }
            if (!theatreFound) {
                System.out.println("No theatres available for the selected movie with enough seats.");
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return 1;
    }

    public static void displayMoviesInTheatre(String theatrename) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.displayMoviesInTheatre);
            pst.setString(1, theatrename);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("-------------------------------------------");
                System.out.println("Movie        : " + rs.getString("Movie"));
                System.out.println("Language     : " + rs.getString("Language"));
                System.out.println("Duration     : " + rs.getLong("Duration") + " minutes");
                System.out.println("------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public static void bookTicket(int userId, int theatreid, int count, int sessionid) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int movieid = movie(theatreid);
        try {
            LocalDate today = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(today);
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.movieQuery);
            pst.setInt(1, movieid);
            rs = pst.executeQuery();
            if (!rs.next()) {
                System.out.println("Movie not found.");
                return;
            }
            pst = con.prepareStatement(Queries.theatreQuery);
            pst.setInt(1, theatreid);
            pst.setInt(2, movieid);
            rs = pst.executeQuery();
            if (!rs.next()) {
                System.out.println("Theatre not found, or the selected movie is not showing at this session.");
                return;
            }
            int theatreCapacity = rs.getInt("capacity");
            int bookedSeats = rs.getInt("booked_seats");
            int remainingSeats = theatreCapacity - bookedSeats;
            if (remainingSeats < count) {
                System.out.println("Not enough seats available in the theatre.");
                return;
            }
            pst = con.prepareStatement(Queries.insertBookingQuery);
            pst.setInt(1, userId);
            pst.setInt(2, movieid);
            pst.setInt(3, theatreid);
            pst.setInt(4, count);
            pst.setInt(5, sessionid);
            pst.setDate(6, sqlDate);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                pst = con.prepareStatement("UPDATE theatre SET booked_seats = ? WHERE id = ? AND session_id = ?");
                pst.setInt(1, bookedSeats + count);
                pst.setInt(2, theatreid);
                pst.setInt(3, sessionid);
                pst.executeUpdate();
                pst = con.prepareStatement(Queries.updateRemainingSeatsQuery);
                pst.setInt(1, remainingSeats - count);
                pst.setInt(2, theatreid);
                pst.setInt(3, sessionid);
                pst.executeUpdate();

                System.out.println("Booking successful! Tickets booked: " + count);
            } else {
                System.out.println("Error: Booking could not be processed.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while booking tickets: " + e.getMessage());
        }
    }

    public static void displayBookings(int userId) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.display_Bookings);
            pst.setInt(1, userId);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("-------------------------------------------------");
                System.out.println("Booking ID        : " + rs.getInt("id"));
                System.out.println("Movie Name        : " + rs.getString("movie_name"));
                System.out.println("Theatre Name      : " + rs.getString("theatre_name"));
                System.out.println("Theatre Location  : " + rs.getString("theatre_location"));
                System.out.println("Tickets Booked    : " + rs.getInt("tickets_booked"));
                System.out.println("Session Timing    : " + rs.getString("timing"));
                System.out.println("Booking Date      : " + rs.getDate("booking_date"));
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving bookings: " + e.getMessage());
        }
    }

    public static int login(String username, String password) {
        int userId = -1;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.login_query);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
                System.out.println("Login successful! Welcome, " + username);
            } else {
                System.out.println("Invalid username or password.");
                return userId;
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return userId;
    }

    public static int seatCapacity(int id) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.seatCapacity);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacity");
            } else {
                throw new SQLException("No theatre found:");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error during fetching ID.");
        }
    }

    public static int available_seat(int theatre_id, int session_id) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.current_seat_available);
            pst.setInt(1, theatre_id);
            pst.setInt(2, session_id);
            rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error during fetching available seats.");
        }
    }

    public static int displayMovie(String location) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            if (location == null || location.trim().isEmpty()) {
                System.out.println("Location is invalid.");
                return 0; 
            }
            String query = "SELECT theatre.id, theatre.name, theatre.capacity, theatre.location, theatre.isac, movie.name as movie " +
                           "FROM theatre " +
                           "JOIN movie ON theatre.movie = movie.id " +
                           "WHERE theatre.location = ?";
            con = DatabaseConnection.getConnection();
            if (con == null) {
                System.out.println("Error: Unable to connect to the database.");
                return 0; 
            }

            pst = con.prepareStatement(query);
            pst.setString(1, location);
            rs = pst.executeQuery();
            boolean theatre = false;
            while (rs.next()) {
                theatre = true;
                System.out.println("-------------------------------------------------");
                System.out.println("Theatre ID     : " + rs.getInt("id"));
                System.out.println("Theatre Name   : " + rs.getString("name"));
                System.out.println("Movie          : " + rs.getString("movie"));
                System.out.println("Location       : " + rs.getString("location"));
                System.out.println("Capacity       : " + rs.getInt("capacity"));
                boolean isAc = rs.getBoolean("isac");
                System.out.println("Type           : " + (isAc ? "AC" : "Non-AC"));
                System.out.println("-------------------------------------------------");
            }

            if (!theatre) {
                System.out.println("No theatres available for the selected location.");
                return 0; 
            }

        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public static int movie(int theatreid) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String query = "SELECT movie FROM theatre WHERE id=?";
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, theatreid);
            rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("movie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error during fetching ID.");
        }
        return 0;
    }
}

