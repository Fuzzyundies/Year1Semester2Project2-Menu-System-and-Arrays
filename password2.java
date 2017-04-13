import javax.swing.*;

public class password2
{
	public static void main(String [] args)
	{
		JTextField userField = new JTextField();
		JPasswordField passField = new JPasswordField(); //hides user input
		JPanel panel = new JPanel(); //creates the box
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //aligns everything vertically
		panel.add(new JLabel("Username"));
		panel.add(userField);
		panel.add(new JLabel("Password:"));
		panel.add(passField);
		JOptionPane.showMessageDialog(null, panel, "Login", JOptionPane.QUESTION_MESSAGE);
		
		String userName = userField.getText();
		char [] userCharPassword = passField.getPassword();
		String userPassword = "";
		for (int i = 0; i < userCharPassword.length;i++)
		{
			userPassword += userCharPassword[i];
			userCharPassword[i] = '*';
		}
		//userCharPassword.remove;
		System.out.println(userName);
		System.out.println(userPassword);
	}
}