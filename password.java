import java.io.*;
import java.util.*;
import javax.swing.*;

public class password
{
	public static void main(String[] args) throws IOException
	{
		ArrayList<ArrayList<String>> userInfo = new ArrayList<ArrayList<String>>();
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		
		String [] tempArray;  
		File file1 = new File("users.txt");
		String userInput = ""; 
		int userScore, userNumber = 0, attempt = 0;
		String userPrompt = "Please enter your Username";
		String userNotFound = "Username not found, please try again.";
		String passwordPrompt = "Please enter your password.";
		String passwordWrong = "Incorrect password entered, please try again. You have " + (3 - attempt) + " attempts left.";
		String passwordFailure = "Incorrect password attempted 3 times. Program is now terminating.";
		boolean validPassword = false, validUser = false; 
		//boolean completeValidation = false used //used for method version of this
		FileReader aFileReader = new FileReader("users.txt");
		Scanner in = new Scanner(aFileReader);
		
		if (file1.exists())
		{

			while (in.hasNext())
			{
				tempArray = (in.nextLine()).split(";");
				userInfo.get(0).add(tempArray[0]);
				userInfo.get(1).add(tempArray[1]);
				userInfo.get(2).add(tempArray[2]);
			}
			in.close();
		}
		
		else
		{
			JOptionPane.showMessageDialog(null,"Error, user.txt is missing.");
		}
		
		while (userInput != null || !validUser)
		{
			userInput = JOptionPane.showInputDialog(null,userPrompt,"Login", JOptionPane.QUESTION_MESSAGE);
			userNumber = userInfo.get(0).indexOf(userInput);
			if (userNumber != -1)
				validUser = true;
			else
				JOptionPane.showMessageDialog(null,userNotFound,"Login Failed", JOptionPane.ERROR_MESSAGE);
		}
		
		while ((validUser && !validPassword) || userInput != null)
		{
			userInput = JOptionPane.showInputDialog(null,passwordPrompt,"Login Password", JOptionPane.QUESTION_MESSAGE);
			if (userInput.matches(userInfo.get(1).get(userNumber)))
				validPassword = true;
			else
			{
				attempt += 1;
				JOptionPane.showMessageDialog(null,passwordWrong,"Incorrect Password", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (attempt == 3)
			JOptionPane.showMessageDialog(null,passwordFailure,"Locked Out", JOptionPane.ERROR_MESSAGE);
		else if (validPassword && validUser)
			System.out.println("true");
			//completeValidation = true;
		//return completeValidation;
	}
}