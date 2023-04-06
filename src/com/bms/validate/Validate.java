package com.bms.validate;

public class Validate
{

	public String checkCredentials(String name, String username, String pwd)
	{

		if (name.trim().length() == 0 || username.trim().length() == 0 || pwd.trim().length() == 0)
		{
			return "Invalid entry ! Spaces not allowed in credentials";
		} else if (name.matches(".*\\d.*") || username.matches(".*\\d.*"))
		{
			return "Numbers are not allowed in name or username";
		} else if (pwd.length() < 4)
		{
			return "Password should have atleast 4 characters.";
		}
		return "Credentials approved";

	}

	public boolean checkNumber(String amount)
	{
		boolean isNum=true;
		for (int i = 0; i < amount.length(); i++)
		{
			if (!Character.isDigit(amount.charAt(i)))
			{
				isNum= false;

			}
			break;
		}
		return isNum;
	}

	public boolean checkNull(String amount){
		boolean isNotNull = true;
		if(amount==null){
			isNotNull= false;
		}
		return isNotNull;
	}

}





