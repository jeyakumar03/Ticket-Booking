import java.util.Scanner;
import java.sql.SQLException;
public class Main{
	private static Scanner ob=new Scanner(System.in);
	public static void main(String[] args)throws SQLException{
	boolean s=true;
	while(s){
	           System.out.println("-------------------------------");
		System.out.println("1.SuperAdmin\n2.Admin \n 3.User");
                      System.out.println("-------------------------------");
		System.out.println("Enter your choice:");
		int choice=ob.nextInt();
		ob.nextLine();
		switch(choice){
			case 1:
			       Admin.show();
			       break;
			case 2:
			      AdminHelper.signin();
			      break;
			case 3:
			       UserInfo.show();
			       break;
			
			default:
			       System.out.println("Please enter the valid choice:");
			       break;
			}
			System.out.println("-------------------------------");
		}
	}
}
