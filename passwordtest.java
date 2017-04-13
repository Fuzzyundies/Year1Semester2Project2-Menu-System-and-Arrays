import javax.swing.*;
import java.io.*;
import java.util.*;

public class passwordtest
{
	public static void main(String [] args) throws IOException
	{
		File file1 = new File("users.txt");
		if (file1.exists())
		{
			//need to assign score to userScore (int)
			int userScore, userIndex, userSelection;
			String userName;
			String userPassword = "";
			int attempt = 3;
			//need to assign value to userIndex (int)
			
			
			//Setup for Read File
			ArrayList<ArrayList<String>> userInfo = new ArrayList<ArrayList<String>>(); //2 Dimensional Array List
			userInfo.add(new ArrayList<String>()); //Adds row
			userInfo.add(new ArrayList<String>());
			userInfo.add(new ArrayList<String>());
			String [] tempArray;
			//Loads the file reader and input
			FileReader aFileReader = new FileReader(file1); 
			Scanner in = new Scanner(aFileReader);			
			
			//Setup for Method
			String userPrompt = "Please enter your username";
			String userNotFound = "Username not found, please try again.";
			String passwordPrompt = "Please enter your password.";
			String passwordWrong = "Incorrect password or username entered, please try again. Remainging attempts: ";	String passwordFailure = "Incorrect login attempted 3 times. Program is now terminating.";
			boolean validPassword = false, validUser = false; 
			boolean completeValidation = false; //used for method version of this
			
			//Username and Password
			JTextField userField = new JTextField();
			JPasswordField passField = new JPasswordField(); //hides user input
			JPanel panel = new JPanel(); //creates the box
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //aligns everything vertically
			panel.add(new JLabel("Username"));
			panel.add(userField);
			panel.add(new JLabel("Password:"));
			panel.add(passField);
			//JOptionPane.showMessageDialog(null, panel, "Login", JOptionPane.QUESTION_MESSAGE);
			
			//Read File
			while(in.hasNext())
			{
				tempArray = (in.nextLine()).split(";");
				userInfo.get(0).add(tempArray[0]);
				userInfo.get(1).add(tempArray[1]);
				userInfo.get(2).add(tempArray[2]);
			}
			in.close();
			
			do
			{
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
					if (userIndex == -1)
					{
						attempt --;
						JOptionPane.showMessageDialog(null, passwordWrong + attempt, "Incorrect Username/Password", JOptionPane.ERROR_MESSAGE);
					}
					
					else
					{
						if (userPassword.equals(userInfo.get(1).get(userIndex)))
							completeValidation = true;
						else
						{
							attempt --;
							if(attempt == 0)
								JOptionPane.showMessageDialog(null, passwordFailure, "Failed Login", JOptionPane.ERROR_MESSAGE);
							else
								JOptionPane.showMessageDialog(null, passwordWrong + attempt, "Incorrect Username/Password", JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}while((attempt != 0) && (completeValidation != true));
		System.out.println(completeValidation);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Error, users.txt is missing.");
		}

		//return completeValidation;
	}
}