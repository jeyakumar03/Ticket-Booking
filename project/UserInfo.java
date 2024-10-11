import java.util.Scanner;
import java.sql.SQLException;
public class UserInfo{
       private static Scanner ob=new Scanner(System.in);
	public static void show()throws SQLException{
	    boolean b=true;
	    while(b){
	      System.out.println("1.Sign in\n2.Sign up\n3.Exit\nEnter your choice:\n");
	      int ch=ob.nextInt();
	      switch(ch){
	           case 1:
		   UserHelper.login();
		   break;
		case 2:
		   UserHelper.signup();
		   break;
		case 3:
		   return;
		default:
		   System.out.println("Please enter a valid choice");
		   break;
	        }
	     }	
	}

}
