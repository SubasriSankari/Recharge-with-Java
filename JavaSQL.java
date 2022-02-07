import java.sql.*; 
import java.lang.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Date;   
import java.util.Calendar; 
import java.util.Calendar.*;
import java.text.SimpleDateFormat;

public class JavaSQL{  
	static String name;
	static String simName;
	static String phone;
	static java.sql.Date lastRechargeDate;
	static java.sql.Date expiryDate;
	static int expiredOn;
	static String userPhone;
	static Connection con = null;
	static PreparedStatement ps;
	static String currDate;
	static String newDate;
	

	public static void otpConfirm(){
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
  		int number = 0;
  		for(int i = 0; i < 6; i++){
  			number = number*10 + random.nextInt(9);
  		}
		String otpStr = String.valueOf(number);
  		System.out.println("\nYour otp is " + otpStr);
		System.out.print("Please enter this otp to confirm...\n\n --->");
		String otp = sc.nextLine();
		System.out.println("\nProcessing..");
		if(!(otp.equals(otpStr)))
		{
			System.out.println("\nSorry:( You entered wrong otp....Please try again later!!");
			System.exit(1); 
		}
		
	}

	public static void newDate(int days){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		currDate = date.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, days);
		newDate = date.format(c.getTime());
		java.sql.Date currentDate = java.sql.Date.valueOf(currDate);
		java.sql.Date addedDate = java.sql.Date.valueOf(newDate);
		lastRechargeDate = currentDate;
		expiryDate = addedDate;
	}

	public static void changeDate(int days){
		otpConfirm();
		newDate(days);
		//System.out.println(lastRechargeDate + " " + expiryDate);
		
		try{
			ps = con.prepareStatement(  
				"UPDATE recharge_details SET last_recharge_date = ? , expiry_date = ?, expired_on = ? where name = ?");  
      	
			ps.setString(1,currDate);
			ps.setString(2,newDate);
			ps.setInt(3,0);
			ps.setString(4,name);
			ps.executeUpdate();
			System.out.println("\nCongratulations!!...You have Recharged Successfully:)\n\n");
		
		}catch(Exception e){
			System.out.println(e);
		} 
		
	}

	public static void choosePlan(int plan1[], int plan2[], int plan3[]){
		System.out.printf("Please choose your Plan\n\n");
		System.out.printf("************** %n");
		System.out.printf("1 -> For plan1%n");
		System.out.printf("************** %n");
		System.out.printf("cost Rs.%d/- %n%d Days %ndata %dGB per day%n%n%n", plan1[0], plan1[1], plan1[2]);
		System.out.printf("************** %n");
		System.out.printf("2 -> For plan2%n");
		System.out.printf("************** %n");
		System.out.printf("cost Rs.%d/- %n%d Days %ndata %dGB per day%n%n%n", plan2[0], plan2[1], plan2[2]);
		System.out.printf("************** %n");
		System.out.printf("3 -> For plan3%n");
		System.out.printf("************** %n");
		System.out.printf("cost Rs.%d/- %n%d Days %ndata %dGB per year%n%n -->", plan3[0], plan3[1], plan3[2]);
	
	}

	public static void planForAIRTEL(){
		Scanner sc = new Scanner(System.in);
		int plan1[] = {298, 28, 2};
		int plan2[] = {698, 84, 2};
		int plan3[] = {1498, 365, 24};
		System.out.printf("%n ------ Welcome to AIRTEL Recharge Offer -------%n%n");
		choosePlan(plan1,plan2,plan3);
		int plan = sc.nextInt();
		switch(plan){
			case 1:
				changeDate(28);
				break;
			case 2:
				changeDate(84);
				break;
			case 3:
				changeDate(365);
				break;
			default:
				System.out.println("%nYou entered wrong key:(%n");
		}
		
		
	}
	
	public static void planForJIO(){
		Scanner sc = new Scanner(System.in);
		int plan1[] = {298, 28, 2};
		int plan2[] = {599, 84, 2};
		int plan3[] = {2599, 365, 2};
		System.out.printf("\n ------ Welcome to JIO Recharge Offer -------\n\n");
		choosePlan(plan1,plan2,plan3);
		int plan = sc.nextInt();
		switch(plan){
			case 1:
				changeDate(28);
				break;
			case 2:
				changeDate(84);
				break;
			case 3:
				changeDate(365);
				break;
			default:
				System.out.println("\nYou entered wrong key:(\n");
		}
		
	}

	public static void planForVI(){
		Scanner sc = new Scanner(System.in);
		int plan1[] = {501, 28, 3};
		int plan2[] = {449, 56, 4};
		int plan3[] = {901, 84, 24};
		System.out.printf("\n ------ Welcome to VI Recharge Offer -------\n\n");
		choosePlan(plan1,plan2,plan3);
		int plan = sc.nextInt();
		switch(plan){
			case 1:
				changeDate(28);
				break;
			case 2:
				changeDate(56);
				break;
			case 3:
				changeDate(84);
				break;
			default:
				System.out.println("\nYou entered wrong key:(\n");
		}
	}

	public static void checkNumberValid(String phone){
		char character = phone.charAt(0);
		int number = character - '0';
		if(phone.length() != 10 || (number != 7 && number != 8 && number != 9) ){
			System.out.println("Invalid.... Please enter Valid mobile number!!");
			System.exit(1);
		}	
		
	}

	public static void display(){
		
		try{
			ps = con.prepareStatement(  
				"select * from recharge_details");  
      	
			ResultSet rs = ps.executeQuery();  
			while(rs.next()){
				name = rs.getString("name");
				simName = rs.getString("sim_name");
				phone = rs.getString("phone_no");
				lastRechargeDate = rs.getDate("last_recharge_date");
				expiryDate = rs.getDate("expiry_date");
				expiredOn = rs.getInt("expired_on");
				System.out.format("\nName..%s \nSim Name..%s \nMobile number..%s \nLast Recharge Date..%s \nExpiry Date..%s\n\n", 										name,simName,phone,lastRechargeDate,expiryDate);
			}
		
		}catch(Exception e){
			System.out.println(e);
		} 
	}

	public static void rechargeNow(){
		if(simName.equals("JIO")){
			planForJIO();
		}
		else if(simName.equals("AIRTEL")){
			planForAIRTEL();
		}
		else{
			planForVI();
		}
		
	}

	public static void existUser(){
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter Your Mobile number:"); 
		userPhone = sc.nextLine();
		checkNumberValid(userPhone);
		try{
			ps = con.prepareStatement(  
				"select * from recharge_details");  
      	
			ResultSet rs = ps.executeQuery();  
			int flag = 1;
			while(rs.next()){
				name = rs.getString("name");
				simName = rs.getString("sim_name");
				phone = rs.getString("phone_no");
				lastRechargeDate = rs.getDate("last_recharge_date");
				expiryDate = rs.getDate("expiry_date");
				expiredOn = rs.getInt("expired_on");
				//System.out.format("\n%s %s %s %s %s %s\n\n",name,simName,phone,lastRechargeDate,expiryDate,expiredOn);
				if(userPhone.equals(phone)){
					System.out.format("\nName..%s \nSim Name..%s \nMobile number..%s \nLast Recharge Date..%s \nExpiry Date..%s\n\n", 										name,simName,phone,lastRechargeDate,expiryDate);
					if(expiredOn == 1){
						System.out.println("Your Plan is Expired");
						System.out.println("Choose Your choice\n");
						System.out.println("1. Recharge Now");
						System.out.print("2. Not now...Exit\n\n-->");
						int choice = sc.nextInt();
						switch(choice)
						{
							case 1:
								rechargeNow();
								break;
							case 2:
								System.exit(0);
							default:
								System.out.println("You entered wrong key:(");
								System.exit(1);
						}
					}
					else{
						System.out.printf("Your Plan will expires on %s\n", expiryDate);
						System.exit(0);	
					}		
					flag = 0;
				}
			}
			if(flag == 1){
				System.out.println("\n----- You are new user -----\n");
				System.out.println("Please choose new user option\n");
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void chooseNetwork(){
		Scanner sc = new Scanner(System.in);
		System.out.println("1. JIO");
		System.out.println("2. AIRTEL");
		System.out.print("3. VI\n\n-->");
		int choice = sc.nextInt();
		switch(choice){
			case 1:
				simName = "JIO";
				break;
			case 2:
				simName = "AIRTEL";
				break;
			case 3:
				simName = "VI";
				break;
			default:
				System.out.println("You entered wrong key:(");
		}
	}
	
	public static void newUser(){
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter Your Mobile number:"); 
		userPhone = sc.nextLine();
		checkNumberValid(userPhone);
		System.out.println("\nPlease enter Your name:");
		name = sc.nextLine();
		System.out.println("\nPlease Choose Your network:");
		chooseNetwork();
		phone = userPhone;
		expiredOn = 0;
		rechargeNow();
		try{
			ps = con.prepareStatement(  
				"insert into recharge_details(name, sim_name, phone_no, last_recharge_date, expiry_date, expired_on)values(?,?,?,?,?,?)");
			ps.setString(1,name);  
      			ps.setString(2,simName);
			ps.setString(3,phone);
			ps.setDate(4,lastRechargeDate);
			ps.setDate(5,expiryDate);
			ps.setInt(6,expiredOn);
			ps.execute();  
		
		}catch(Exception e){
			System.out.println(e);
		} 		
		
	}

	public static void makeDBConnection(){
		try{
			con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/recharge_app","root","Suba@2000");
		}catch(Exception e) 
		{
			System.out.println(e);
		}   
	}

	public static void main(String[] args){ 

		Scanner sc = new Scanner(System.in);

		makeDBConnection();
		int choice;
		do{
			System.out.println("\nChoose Your Choice:\n");
			System.out.println("1.Existing User");
			System.out.println("2.New User");
			System.out.println("3.Display");
			System.out.print("0.Exit\n\n -->");
			choice = sc.nextInt();
			
			switch(choice){
				case 1:
					existUser();
					break;
				case 2:
					newUser();
					break;
				case 3:
					display();
					break;
				case 0:
					System.out.println("\nThank You:)\n");
					try{
						if(con!=null)
							con.close();
					}catch(Exception e){
						System.out.println("Failure to close the connection");
					}
					System.exit(0);
				default:
					System.out.println("\nYou entered Wrong key:(\n");
					System.exit(1);
					
			}

		}while(choice != 0);
        
	}  
}
