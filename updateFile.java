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