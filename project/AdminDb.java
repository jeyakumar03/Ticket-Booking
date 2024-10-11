import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class AdminDb{
    public static void addTheatre(String name, String location, int capacity, boolean isAc) throws SQLException {
        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.add_theatre);
            pst.setString(1, name);
            pst.setString(2, location);
            pst.setInt(3, capacity);
            pst.setBoolean(4, isAc);
            rowsAffected = pst.executeUpdate();  
            if (rowsAffected > 0) {
                System.out.println("Theatre added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } 
    }

    public static String displayTheatre() throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();  
        try {
            String Query = "select * from theatre";
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Query);
            rs = pst.executeQuery();
            while (rs.next()) {
                sb.append("----------------------------------\n");
                sb.append("Id         : ").append(rs.getInt(1)).append("\n");
                sb.append("Name       : ").append(rs.getString(2)).append("\n");
                sb.append("Location   : ").append(rs.getString(3)).append("\n");
                sb.append("Capacity   : ").append(rs.getInt(4)).append("\n");
                sb.append("Type       : ").append(rs.getBoolean(5) ? "AC" : "Non-AC").append("\n");
                sb.append("-----------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return sb.toString(); 
    }

    public static void addMovie(String name, String language, long duration) throws SQLException {
        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.add_movie);
            pst.setString(1, name);
            pst.setString(2, language);
            pst.setLong(3, duration);
            rowsAffected = pst.executeUpdate();  
            if (rowsAffected > 0) {
                System.out.println("Movie added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } 
    }

    public static String displayMovie() throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder(); 
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.display_movie);  
            rs = pst.executeQuery(); 
            while (rs.next()) {
                sb.append("------------------------------------------\n");
                sb.append(String.format("| %-16s : %-20d |\n", "Movie Id         ", rs.getInt("id")));
                sb.append(String.format("| %-16s : %-20s |\n", "Movie Name       ", rs.getString("name")));
                sb.append(String.format("| %-16s : %-20s |\n", "Language         ",  rs.getString("language")));
                sb.append(String.format("| %-16s : %-20d |\n", "Duration(in mins)", rs.getLong("duration")));
                sb.append("------------------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return sb.toString(); 
    }
    public static void displaySession() throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM session";  
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(query);  
            rs = pst.executeQuery(); 
            System.out.println("------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-16s : %-20d |\n", "Id", rs.getInt("id"));
                String sessionTime = rs.getString("name");  
                System.out.printf("| %-16s : %-20s |\n", "Session Time", sessionTime);
                System.out.println("------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while displaying sessions: " + e.getMessage());
        }
    }
    public static void updatemovie(int theatreid, int newMovieid) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.getMovieQuery);
            pst.setInt(1, newMovieid);
            rs = pst.executeQuery();
            if (rs.next()) {
                pst = con.prepareStatement(Queries.updateMovieQuery);
                pst.setInt(1, newMovieid);
                pst.setInt(2, theatreid);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Movie updated successfully in the theatre.");
                } else {
                    System.out.println("Error: No theatre found with the given ID.");
                }
            } else {
                System.out.println("Error: Movie not found in the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    public static void deleteTheatre(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.checkQuery);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                pst = con.prepareStatement(Queries.deleteQuery);
                pst.setInt(1, id);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Theatre with ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete the theatre. Please try again.");
                }
            } else {
                System.out.println("Theatre with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the theatre: " + e.getMessage());
        }
    }
    public static void deleteMovie(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.checkQuery_movie);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                pst = con.prepareStatement(Queries.deleteQuery);
                pst.setInt(1, id);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Movie with ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete the movie. Please try again.");
                }
            } else {
                System.out.println("Movie with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the movie: " + e.getMessage());
        }
    }
    public static int add_admin(String name, String username, String mail, Long mobile, String password) throws SQLException {
        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.insert_admin);
            pst.setString(1, name);
            pst.setLong(2, mobile);
            pst.setString(3, mail);   
            rs = pst.executeUpdate();
            if (rs > 0) {
                int userId = getId(mail);  
                admincred(con, userId, username, password);  
                rowsAffected = 1;  
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user records.");
        } 
        return rs;
    }

    public static void admincred(Connection con, int id, String username, String password) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(Queries.insert_admincred);
            pst.setInt(1, id);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user credentials.");
        } 
    }
    public static int getId(String email) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.select_adminid);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  
            } else {
                throw new SQLException("No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error during fetching ID.");
        }     
    }
    public static void delete_Admin(String mail) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.delete_admin);
            pst.setString(1, mail);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin with email " + mail + " deleted successfully.");
            } else {
                System.out.println("No admin found with the provided email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting admin.");
        } 
    }
    public static String display_Admin() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();  
        try {
            String Query = "select * from admin";
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Query);
            rs = pst.executeQuery();
            while (rs.next()) {
                sb.append("----------------------------------\n");
                sb.append("Id         : ").append(rs.getInt(1)).append("\n");
                sb.append("Name       : ").append(rs.getString(2)).append("\n");
                sb.append("Mail       : ").append(rs.getString(3)).append("\n");
                sb.append("Mobile     : ").append(rs.getLong(4)).append("\n");
                sb.append("-----------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return sb.toString(); 
    }
    public static int adminlogin(String username, String password) throws SQLException {
        int userId = -1; 
        String query = "SELECT id FROM admincred WHERE username = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection(); 
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);  
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id"); 
                System.out.println("Login successful! User ID: " + userId);
                Admin.theatre();
            } else {
                System.out.println("Invalid username or password.");
            }
        }
        return userId;
    }
}
