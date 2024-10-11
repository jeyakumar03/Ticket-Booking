import java.util.Scanner;
import java.sql.SQLException;
public class Admin {
    static String username = "jeyakumar";
    static String password = "jeyakumar@123";
    static Scanner ob = new Scanner(System.in);
    public static void show() throws SQLException{
    	     System.out.println("Admin Login");
                System.out.println("Enter username: ");
                String enteredUsername = ob.next();
                System.out.println("Enter password: ");
                String enteredPassword = ob.next();
                login(enteredUsername, enteredPassword); 
    }
    public static void login(String enteredUsername, String enteredPassword)throws SQLException {
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            System.out.println("Login successful!");
            ad();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    public static void ad()throws SQLException{
    boolean s=true;
    	while(s){
    	System.out.println("1.Add Admin\n2.Delete Admin\n3.Display  Admin");
    	int choice=ob.nextInt();
    	ob.nextLine();
    	
    		switch(choice){
    			case 1:
    			  System.out.println("Enter the admin name:");
    			  String name=ob.nextLine();
    			  System.out.println("Enter the admin username:");
    			  String username=ob.nextLine();
    			  System.out.println("Enter the mail id:");
    			  String mail=ob.nextLine();
    			  System.out.println("Enter the mobile:");
    			  long mobile=ob.nextLong();
    			  ob.nextLine();
    			  System.out.println("Enter the password:");
    			  String password=ob.nextLine();
    			  AdminDb.add_admin(name,username,mail,mobile,password);
    			  break;
    			case 2:
    			  System.out.println("Enter the admin mail for delete:");
    			  String adminmail=ob.nextLine();
    			  AdminDb.delete_Admin(adminmail);
    			  break;
    			case 3:
 			   System.out.println(AdminDb.display_Admin());
 			   break;
 			 default:
			   System.out.println("Enter the valid choice:");
			   break;			  
    		}
    		
    	}
    }
    public static void theatre()throws SQLException{
       boolean s=true;
       while(s){
    	System.out.println("-----------------------------------------------------------------------");
    	System.out.println("1.Add Theatre\n2.Display Theatre\n3.Add Movies\n4.Display Movies\n5.Update Movie to Theatre\n6.Delete Theatre\n7.Delete Movie\n8.Exit\nEnter your choice:");
        	System.out.println("------------------------------------------------------------------------");
    	int choice=ob.nextInt();
      	System.out.println("------------------------------------------------------------------------");
    	ob.nextLine();
    	switch(choice){
    		case 1:
    		      AdminHelper.addTheatre();
    		      break;
    		case 2:
    		      System.out.println(AdminDb.displayTheatre());
    		      break;
    		  case 3:
    		      AdminHelper.addMovie();
    		      break;
    		  case 4:
    		      System.out.println(AdminDb.displayMovie());
    		      break;
    		  case 5:

        		      AdminHelper.updateMovie();
    		      break;
    		  case 6:
    			AdminHelper.deleteTheatre();
    		  	break;
    		 case 7:
     		      	AdminHelper.deleteMovie();
     		      	break;
    		  	
    		  case 8:
    		      s=false;
    		      break;
    		  default:
    		      System.out.println("Enter the valid choice:");
    		      break;
    		   
    	}
    	}	
    }

    
}

