import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDb {
    public void insertUser(User user) throws SQLException {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(Queries.insert_user);
            pst.setString(1, user.getName());
            pst.setLong(2, user.getMobile());
            pst.setString(3, user.getEmail());
            pst.executeUpdate();
            int userId = getUserIdByEmail(user.getEmail());
            insertUserCredentials(userId, user.getUsername(), user.getPassword());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user records.");
        }
    }
    private void insertUserCredentials(int userId, String username, String password) throws SQLException {
        try {
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(Queries.insert_usercred); 

            pst.setInt(1, userId);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
        }
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user credentials.");
        }
    }

    public int login(String username, String password) throws SQLException {
        try{
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(Queries.login_query);

            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {

                System.out.println("Login successful! Welcome, " + username);
                return rs.getInt("id");
            } else {
                System.out.println("Invalid username or password.");
                return 0;
            }
        }
        catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return 0; 
    }

    public void updateUser(String column, String newValue, int userId) {
        String query = "UPDATE users SET " + column + " = ? WHERE id = ?";
        try{
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);

            if ("mobile".equals(column)) {
                pst.setLong(1, Long.parseLong(newValue));
            } else {
                pst.setString(1, newValue);
            }
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful: " + rowsAffected + " row(s) affected.");
            } else {
                System.out.println("No rows matched the condition. Update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }
    }

    public void updateUserCredentials(int userId, String newPassword) {
        String query = "UPDATE usercred SET password = ? WHERE id = ?";
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, newPassword);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully  " );
            } 
        } catch (SQLException e) {
            System.out.println("Error during password update: " + e.getMessage());
        }
    }

    private int getUserIdByEmail(String email) throws SQLException {
        try {
             Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(Queries.select_id);

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        
        return 0;  // User not found
    }
}

