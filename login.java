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