import java.io.*;
import java.util.*;
import javax.swing.*;

public class Project2Test
{
	public static ArrayList<ArrayList<String>> userInfo;
	public static ArrayList<ArrayList<String>> topics;
	public static ArrayList<ArrayList<String>> questions;
	public static int userIndex,userScore;
	public static File file1 = new File("users.txt");
	public static File file2 = new File("topics.txt");
	public static File file3 = new File("questions.txt");
	public static void main(String []args) throws IOException
	{

		if(file1.exists() && file2.exists() && file3.exists())
		{
			
			loadFiles();
			if (userLogin())
				System.out.println("Go into menu options from here, and stay in menu options");
			else
				System.out.println("userLogin returned false, so the else statement should end the program gracefully.");
			System.out.println(userIndex + " " + userScore);

			/*for(int i = 0; i <= 2 && successfulLogin != true && successfulLogin != null; i++)
				successfulLogin == logIn();
				if(successfulLogin == false && i < 2)
				{
					tries = 3-(i+1);
					JOptionPane.showMessageDialog(null, "You have " + tries + "tries to log in remaining.", "Login", JOptionPane.INFORMATION_MESSAGE);
				}
			if(successfulLogin == true)
			{
				do{
				//questions(selectTopic());
				//keep track of points	
				}while(run != null)
			}*/
		}
	}

	/*
		Written by Eric Lambert with assistance from Stephen Jameson
	
		This is a method that takes a dual input, a username, and a protected password field. The protected password field returns a Char array
		The method makes a string out of the Char array and then nulls the array.
		
		In total the user has 3 attempts to login. If the usernme or the password is wrong, the user loses an attempt. In total the user has 3 attempts
		to write in the correct username and password combination. If it fails, the third time, or the user hits the cancel option, esc key, or the 
		close window button, the program termintes smoothly. With each failed attempt, the user is notified, and is given the remaining atempts, however
		on the third failure, the user is notified that the program is closing.
		
		On a successful login, the global variables for userIndex and userScore is updated with the appropriate index in the array list, and the 
		corresponding score to be used by the main/other methods.

		Finally the method returns boolean true/false. True if valid input is entered, false if the user gets locked out or wants to exit the program.
	*/
	public static boolean userLogin()
	{
		//Setup for Method
		int userSelection, attempt = 3;
		String userName, userPassword;
		String userPrompt = "Please enter your username";
		String userNotFound = "Username not found, please try again.";
		String passwordPrompt = "Please enter your password.";
		String passwordWrong = "Incorrect password or username entered, please try again. Remainging attempts: ";
		String passwordFailure = "Incorrect login attempted 3 times. Program is now terminating.";
		boolean completeValidation = false;
		
		//Set up Panel
		JTextField userField = new JTextField();
		JPasswordField passField = new JPasswordField(); //hides user input
		JPanel panel = new JPanel(); //creates the box
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //aligns everything vertically
		panel.add(new JLabel("Username"));
		panel.add(userField);
		panel.add(new JLabel("Password:"));
		panel.add(passField);
		
		do
		{
			attempt --;
			userName = "";
			userPassword = "";
			//Replaces the char password
			userSelection = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			userName = userField.getText ();
			char [] userCharPassword = passField.getPassword();
			for (int i = 0; i < userCharPassword.length;i++)
			{
				userPassword += userCharPassword[i]; 
				userCharPassword[i] = '*';
			}
			userCharPassword = null;
			
			userIndex = userInfo.get(0).indexOf(userName);	
			if (userSelection == JOptionPane.CANCEL_OPTION || userSelection == JOptionPane.CLOSED_OPTION)
					attempt = 0;
			else
			{
				if (userIndex != -1 && userPassword.equals(userInfo.get(1).get(userIndex)))
				{
					completeValidation = true;
					userScore = Integer.parseInt(userInfo.get(2).get(userIndex));
				}
				else
				{
					if(attempt == 0)
						JOptionPane.showMessageDialog(null, passwordFailure, "Failed Login", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, passwordWrong + attempt, "Incorrect Username/Password", JOptionPane.ERROR_MESSAGE);
				}
			}
		}while((attempt != 0) && (completeValidation != true));
		return completeValidation;
	}
	
	/*
		Written by Eric Lambert with assistance from Stephen Jameson
		
		All this method does is updates the userScore for the correct userIndex, then rewrites the file with the updates.
	*/
	public static void updateScore() throws IOException
	{
		userInfo.get(2).set(userIndex, userScore + "");
		PrintWriter out = new PrintWriter(file1);
		for (int i = 0; i < userInfo.get(0).size(); i++)
		{
			out.println(userInfo.get(0).get(i)+";"+userInfo.get(1).get(i)+";"+userInfo.get(2).get(i));
		}
		out.close();
	}
	
	public static void loadFiles() throws IOException
	{
		Scanner sc;
		String aLineOfText;
		/*File file1 = new File("user.txt");
		File file2 = new File("topics.txt");
		File file3 = new File("questions.txt");*/
		
		userInfo = new ArrayList<ArrayList<String>>();
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		String fileElemets1[];
		sc = new Scanner(file1);
		while(sc.hasNext())
		{
			fileElemets1 = sc.nextLine().split(";");
			userInfo.get(0).add(fileElemets1[0]);
			userInfo.get(1).add(fileElemets1[1]);
			userInfo.get(2).add(fileElemets1[2]);
		}
		sc.close();
		/*System.out.println(userInfo.get(0));
		System.out.println(userInfo.get(1));
		System.out.println(userInfo.get(2));*/
		topics = new ArrayList<ArrayList<String>>();
		sc = new Scanner(file2);
		topics.add(new ArrayList<String>());
		topics.add(new ArrayList<String>());
		String fileElemets2[];
		while(sc.hasNext())
		{
			fileElemets2 = sc.nextLine().split(";");
			topics.get(0).add(fileElemets2[1]);
			topics.get(1).add(fileElemets2[0]);
		}
		sc.close();
		System.out.println(topics.get(0));
		System.out.println(topics.get(1));
		
		questions = new ArrayList<ArrayList<String>>();
		sc = new Scanner(file3);
		for(int i = 0; i < topics.get(0).size(); i++)
			questions.add(new ArrayList<String>());
		while(sc.hasNext())
		{
			aLineOfText = sc.nextLine();
			for(int i = 0; i < topics.get(0).size(); i++)
			{
				if(aLineOfText.startsWith(topics.get(0).get(i)))
					questions.get(i).add(aLineOfText);
			}
		}
		sc.close();
		System.out.println(questions.get(0));
		System.out.println(questions.get(1));
		System.out.println(questions.get(2));
	}
}