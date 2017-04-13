import javax.swing.*;
import java.io.*;
import java.util.*;

public class WriteFile
{
	public static void main(String [] args) throws IOException
	{
		ArrayList<ArrayList<String>> userInfo = new ArrayList<ArrayList<String>>(); 
		userInfo.add(new ArrayList<String>()); //Adds row
		userInfo.add(new ArrayList<String>());
		userInfo.add(new ArrayList<String>());
		File file1 = new File("users.txt");
		FileReader aFileReader = new FileReader("users.txt");
		Scanner in = new Scanner(aFileReader);
		int userScore = 69, userIndex = 0;
		String tempArray [];
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
			
			System.out.println(userInfo.get(2).get(userIndex));
			
			userInfo.get(2).set(userIndex, userScore + "");
			
			System.out.println(userInfo.get(2).get(userIndex));
			
			//File file1 = new File("users.txt");
			PrintWriter out = new PrintWriter(file1);
			
			for (int i = 0; i < userInfo.get(0).size(); i++)
			{
				out.println(userInfo.get(0).get(i)+";"+userInfo.get(1).get(i)+";"+userInfo.get(2).get(i));
			}
			//file1.close();
			out.close();
		}
		
	}
}