import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDb {
    

    public static int insertRecords(String name, String username, long mobile, String mail, String password) throws SQLException {
        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.insert_user);
            pst.setString(1, name);
            pst.setLong(2, mobile);
            pst.setString(3, mail);
            rs = pst.executeUpdate();
            if (rs > 0) {
                int userId = getId(mail);
                usercred(con, userId, username, password);
                rowsAffected = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user records.");
        }
        return rs;
    }

    public static void usercred(Connection con, int id, String username, String password) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(Queries.insert_usercred);
            pst.setInt(1, id);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting user credentials.");
        }
    }

    public static void update_user(String colname, String newvalue, int userid) {
        if (colname == null || newvalue == null) {
            System.out.println("Error: Missing required fields.");
            return;
        }
        if (!colname.equals("name") && !colname.equals("mobile") && !colname.equals("mail")) {
            System.out.println("Error: Invalid column name.");
            return;
        }

        String query = "UPDATE users SET " + colname + " = ? WHERE id = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConnection.getConnection();
            stmt = con.prepareStatement(query);
            if (colname.equals("mobile")) {
                try {
                    long newMobile = Long.parseLong(newvalue);
                    stmt.setLong(1, newMobile);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid mobile number format.");
                    return;
                }
            } else {
                stmt.setString(1, newvalue);
            }
            stmt.setInt(2, userid);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful: " + rowsAffected + " row(s) affected.");
            } else {
                System.out.println("No rows matched the condition. Update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during update operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void update_Usercred(String username, String newpassword) {
        String query = "UPDATE usercred SET password = ? WHERE username = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConnection.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, newpassword);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully for user: " + username);
            } else {
                System.out.println("No user found with the username: " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error during password update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getId(String email) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(Queries.select_id);
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
    
}

