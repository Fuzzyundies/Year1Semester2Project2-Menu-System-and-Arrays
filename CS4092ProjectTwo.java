import java.io.*;
import java.util.*;
import javax.swing.*;

public class CS4092ProjectTwo
{
	//Declare variables and constructs that need to be accessible from several methods here. 
	public static ArrayList<ArrayList<String>> userInfo;
	public static ArrayList<ArrayList<String>> topics;
	public static ArrayList<ArrayList<String>> questions;
	public static int userIndex,userScore;
	public static File file1 = new File("users.txt");
	public static File file2 = new File("topics.txt");
	public static File file3 = new File("questions.txt");
	public static boolean stop = false;
	
	
	/*Method by Stephen Jameson
	Main method ties the methods together and keeps the user in the loop.*/
	public static void main(String []args) throws IOException
	{
		//Check if all files exist.
		if(file1.exists() && file2.exists() && file3.exists())
		{
			//Load the files initially and keep the user in the loop util they decide to exit.
			loadFiles();
			if(userLogin() == true)
				while(!stop)
				{
					userScore = Integer.parseInt(userInfo.get(2).get(userIndex));
					selectTopic();
					updateScore();
					loadFiles();
				}
		}
		else
			JOptionPane.showMessageDialog(null, "One or more files does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
	} 

	/*Method by Stephen Jameson
	This method reads the files and loads them into arrays used by other methods.
	*/
	public static void loadFiles() throws IOException
	{
		Scanner sc;
		String aLineOfText;
		
		userInfo = new ArrayList<ArrayList<String>>();
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		String fileElemets[];
		sc = new Scanner(file1);
		while(sc.hasNext())
		{
			fileElemets = sc.nextLine().split(";");
			userInfo.get(0).add(fileElemets[0]);
			userInfo.get(1).add(fileElemets[1]);
			userInfo.get(2).add(fileElemets[2]);
		}

		topics = new ArrayList<ArrayList<String>>();
		sc = new Scanner(file2);
		topics.add(new ArrayList<String>());
		topics.add(new ArrayList<String>());
		while(sc.hasNext())
		{
			fileElemets = sc.nextLine().split(";");
			topics.get(0).add(fileElemets[1]);
			topics.get(1).add(fileElemets[0]);
		}
		
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
		int userselectionIndex, attempt = 3;
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
			userselectionIndex = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			userName = userField.getText ();
			char [] userCharPassword = passField.getPassword();
			for (int i = 0; i < userCharPassword.length;i++)
			{
				userPassword += userCharPassword[i]; 
				userCharPassword[i] = '*';
			}
			userCharPassword = null;
			
			userIndex = userInfo.get(0).indexOf(userName);	
			if (userselectionIndex == JOptionPane.CANCEL_OPTION || userselectionIndex == JOptionPane.CLOSED_OPTION)
					attempt = 0;
			else
			{
				if (userIndex != -1 && userPassword.equals(userInfo.get(1).get(userIndex)))
				{
					completeValidation = true;
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
	
	public static void selectTopic() throws IOException
	{
		
		/*this method lets the user select a topic from a drop down menu which then leads to 10 or less questions from the chosen
		topic to be displayed one after the other in another drop down menu where the user can select an answer, if correct their
		score will increase, else it will display a statement explaining the correct answer.
		*/
		

		String[][] qList;
		ArrayList<String> splitQuestions = new ArrayList<String>() ;
		ArrayList<String> viableTopics = new ArrayList<String>() ;
		int score = 0, qAmount = 0, selectionIndex;
		String selection;
		
		
	//This part of the code displays the first drop down menue to select a topic while also passing an int to the variable
	//"selectionIndex" to be used to determine what topic has been selected.
		for(int i = 0 ; i < topics.get(1).size() ; i++)
			if(questions.get(i).size() > 0)
				viableTopics.add(topics.get(1).get(i));
		
		String topic [] = new String[viableTopics.size()];
		
		for(int i = 0; i < topic.length; i++)
			topic[i] = viableTopics.get(i);
		
		selection = (String)JOptionPane.showInputDialog(null, "Select a topic", "Topics", 3, null, topic, topic[0]);
		if(topics.get(1).contains(selection))
		{
			selectionIndex = topics.get(1).indexOf(selection);
		/*The following code determines if there are more than 10 questions in the selected topic and displays 10 random ones if
		there are more than 10, else it displays what is there. In both these cases we put the questions into a 2 dimensional array.
		*/
			int uniqueNumbers[];
			if(questions.get(selectionIndex).size() > 10)
			{
				uniqueNumbers = new int [10];
				for(int i = 0 ; i < 10 ;)
				{
					int rand = (int)(Math.random() * (questions.get(selectionIndex).size())) ;
					uniqueNumbers[i] = rand;
					int j;
					for(j = 0; uniqueNumbers[i] != uniqueNumbers[j]; j++);
					if(j == i)
							i++;
				}
				for(int i = 0; i < uniqueNumbers.length; i++)
					splitQuestions.add(questions.get(selectionIndex).get(uniqueNumbers[i]));
				qList = new String [10][8];
			}
			else
			{
				for(int i = 0 ; i < (questions.get(selectionIndex).size()) ; i++)
				{
					splitQuestions.add(questions.get(selectionIndex).get(i)) ;
				}
				qList = new String [questions.get(selectionIndex).size()][8];
			}
			
		/*This part of the code sorts the questions to be displayed depending on what topic was chosen. This is done by sorting the
		questions into an array then displaying them in a drop down menu. Also this updates the score when a correct answer is chosen.
		*/
		String temp [] = new String[splitQuestions.size()] ;
			for(int i = 0 ; i < splitQuestions.size() ; i++)
				 temp[i] = splitQuestions.get(i) ;
			 
			for(int i = 0 ; i < temp.length ; i++)
				qList[i] = temp[i].split(";") ;
			
			boolean stopTheLoop = false;
			for(int i = 0 ; i < qList.length && !stopTheLoop; i++)
			{

					String [] answers = new String [4] ;
					for(int j = 2 ; j <= 5 ; j++)
					{
						answers [j-2] = qList[i][j] ;
					}
					
					String correctAnswer = (String)JOptionPane.showInputDialog(null, qList[i][1], "Questions", 3, null, answers, answers[0]) ;
					if(correctAnswer != null)
					{
						if(correctAnswer.equals(answers[Integer.parseInt(qList[i][6])-1]))
							userScore++ ;
						else
						{
							JOptionPane.showMessageDialog(null, "The corrent answer was " + answers[Integer.parseInt(qList[i][6])-1]) ;
							JOptionPane.showMessageDialog(null, qList[i][7]) ;
						}
					}
					else
						stopTheLoop = true;
			}
			JOptionPane.showMessageDialog(null, "In this MCPT you have earned " + (userScore - Integer.parseInt(userInfo.get(2).get(userIndex))) + " points.\nYour overall score is " + userScore + " points.");
		}
		else 
			stop = true;
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
}