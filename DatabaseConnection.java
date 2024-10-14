import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/sample";
    private static final String USER = "postgres";
    private static final String PASSWORD = "zoho";
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect to the database.");
            }
        }
        return connection;
    }
   /* public static int executeUpdate(String query,Object[] values){
    	int row=0;
    	try{
    	     PreparedStatement pst=ConnectionDB.getConnection().prepareStatement(query);
    	     for(int i=0;i<values.length;i++){
    	     	pst.setObject(i+1,values[i]);
    	     }
    	     row=pst.executeUpdate();
    	     }catch(SQLException e){
    	     	System.out.println("Statement not Executed");
    	     }
    	  return row;   
    }
    public static ResultSet executeQuery(String Query,Object[] valeus)throws SQLException{
    	ResultSet rs=null;
    	try{
    		PreparedStatement pst=ConnectionDB.getConnection().prepareStatement(query);
    		for(int i=0;i<values.length;i++){
    			pst.setObject(i+1,values[i]);
    		}
    		rs=pst.executeQuery();
    	}catch(SQLException){
    		System.out.println("Statement not Executed");
    	}
    	return rs;
    }*/
    public static void closeConnection() {
        try{
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
