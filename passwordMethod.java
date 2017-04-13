public static boolean userLogin()
{
	//Setup for Method
	int userSelection, attempt = 3;
	String userName, userPassword;
	String userPrompt = "Please enter your username";
	String userNotFound = "Username not found, please try again.";
	String passwordPrompt = "Please enter your password.";
	String passwordWrong = "Incorrect password or username entered, please try again. Remainging attempts: ";			String passwordFailure = "Incorrect login attempted 3 times. Program is now terminating.";
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
	userScore = userInfo.get<userIndex>.get(2);
	System.out.println(userIndex + " " + userScore);
}
		//return completeValidation; set user score, user index