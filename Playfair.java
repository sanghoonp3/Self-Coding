/**
 * 
 * @authors Sanghoon Park and Adam Cancglin 
 * @version 29 September 2018
 *
 */
public class Playfair 
{
	public static String[][] generateMatrix(String key)
	{
		//This clears the keyword of white text and makes the letters all lowercase.
		//This also changes all j's in the keyword to be i's.
		String keyword = key.toLowerCase().replaceAll("\\s+", "").replace('j', 'i').replaceAll("\\d", "").replaceAll("\\.", "");
		int row = 5;
		int column = 5;
		String [][] playfairMatrix = new String[row][column];
		//Left out the letter "j" because it's unnecessary and uncommon. All j's will become i's.
		String alphabet = "abcdefghiklmnopqrstuvwxyz";
		String[] array = new String [keyword.length()];
		
		//This sets the keyword without repeating characters to be added to the alphabet string
		for(int i = keyword.length(); i > 0; i--)
		{
			for(int j = 0; j < alphabet.length(); j++)
			{
				String keyLetter = keyword.substring(i - 1, i);
				//This checks if the letters are the same from the keyword and String alphabet
				if(keyLetter.equals(alphabet.substring(j, j + 1)))
				{
					//This recombines the substrings with the removed letter.
					String s1 = alphabet.substring(0, j);
					String s2 = alphabet.substring(j + 1, alphabet.length());
					alphabet = keyLetter + s1 + s2;
				}
			}
		}
		System.out.println("\t");

		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				//This fills the matrix with the String alphabet (which changes based off the key)
				playfairMatrix[i][j] = alphabet.substring(0, 1);
				alphabet = alphabet.substring(1, alphabet.length());
				//This prints out the matrix in a 5x5 format with spaces in between columns and tabs in between rows
				System.out.print(playfairMatrix[i][j] + "\t");
			}
			//This is the tab that creates the next line for the loop to follow
			System.out.println("\t");
		}
		return playfairMatrix;
	}
	//This checks for multiple letters that are the same and adds an "x" in between them.
	public static String checkPair(String text)
	{
		for(int i = 0; i < text.length() - 1; i = i + 2)
		{
			if(text.charAt(i) == text.charAt(i + 1))
			{
				//This if statement is if there is "xx" in the plaintext.
				//It will just skip over the double x as if normal rule for the text.
				//The encryption will make it so it always encrypt as if they are on the row.
				if(text.charAt(i) == 'x' && text.charAt(i + 1) == 'x')
				{
					i = i + 2;
				}
				else
				{
					String plains1 = text.substring(0, i + 1);
					String plains2 = text.substring(i + 1, text.length());
					text = plains1 + "x" + plains2;
				}
			}
		}
		return text;
	}
	
	public static String encrypt(String plaintext, String key)
	{
		String[][] pfMatrix = generateMatrix(key);
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		String ciphertext = "";
		//This replaces all white characters and numbers with nothing and all j's to i's.
		String plaintextcopy = plaintext.toLowerCase().replaceAll("\\s+", "").replace('j', 'i').replaceAll("\\d.", "").replaceAll("\\.", "");
		
		//Checks the initial plaintext for pairs of the same letters 
		String plaintextcopy2 = checkPair(plaintextcopy);
		
		//If the final plaintextcopy2 is odd, it adds 1 "x" at the end of the plaintextcopy2.
		if(plaintextcopy2.length() % 2 != 0)
		{
			plaintextcopy2 = plaintextcopy2 + "x";
		}
		
		//This checks every iteration of the matrix to see if the first 2 letters of the plaintext have a match in the matrix.
		//Then it stores their data as x1 and y1 for the first letter and x2 and y2 for the second letter.
		//It then checks each condition of whether or not they are in the same row, column, or neither.
		//After each check, it adds the corresponding letters to the String ciphertext.
		//It returns the ciphertext.
		while(plaintextcopy2.length() > 0)
		{
			for(int i = 0; i < pfMatrix.length; i++)
			{
				for(int j = 0; j < pfMatrix.length; j++)
				{					
					if(pfMatrix[i][j].equals(plaintextcopy2.substring(0,1)))
					{
						x1 = i;
						y1 = j;
					}
					if(pfMatrix[i][j].equals(plaintextcopy2.substring(1,2)))
					{
						x2 = i;
						y2 = j;
					}
				}
			}
			//This condition is if they are in the same row
			if(x1 == x2)
			{
				y1 = (y1 + 1) % pfMatrix.length;
				y2 = (y2 + 1) % pfMatrix.length;
				ciphertext = ciphertext + pfMatrix[x1][y1] + pfMatrix[x2][y2];
				plaintextcopy2 = plaintextcopy2.substring(2, plaintextcopy2.length());
			}
			//This condition is if they are in the same column
			else if(y1 == y2)
			{
				x1 = (x1 + 1) % pfMatrix.length;
				x2 = (x2 + 1) % pfMatrix.length;
				ciphertext = ciphertext + pfMatrix[x1][y1] + pfMatrix[x2][y2];
				plaintextcopy2 = plaintextcopy2.substring(2, plaintextcopy2.length());
			}
			//This is if they are neither in the same row nor in the same column
			else
			{
				ciphertext = ciphertext + pfMatrix[x1][y2] + pfMatrix[x2][y1];
				plaintextcopy2 = plaintextcopy2.substring(2, plaintextcopy2.length());
			}
		}
		return ciphertext;
	}

	public static String decrypt(String ciphertext, String key)
	{
		String[][] pfMatrix = generateMatrix(key);
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		String plaintext = "";
		//This replaces all white characters and numbers with nothing and all j's to i's.
		String ciphertextcopy = ciphertext.toLowerCase().replaceAll("\\s+", "").replace('j', 'i').replaceAll("\\d", "").replaceAll("\\.", "");
		
		//This checks every iteration of the matrix to see if the first 2 letters of the ciphertext have a match in the matrix.
		//Then it stores their data as x1 and y1 for the first letter and x2 and y2 for the second letter.
		//It then checks each condition of whether or not they are in the same row, column, or neither.
		//After each check, it adds the corresponding letters to the String plaintext.
		//It returns the plaintext.
		while(ciphertextcopy.length() > 0)
		{
			for(int i = 0; i < pfMatrix.length; i++)
			{
				for(int j = 0; j < pfMatrix.length; j++)
				{					
					if(pfMatrix[i][j].equals(ciphertextcopy.substring(0,1)))
					{
						x1 = i;
						y1 = j;
					}
					if(pfMatrix[i][j].equals(ciphertextcopy.substring(1,2)))
					{
						x2 = i;
						y2 = j;
					}
				}
			}
			//This condition is if they are in the same rows
			if(x1 == x2)
			{
				if(y1 - 1 < 0)
				{
					y1 = (y1 + pfMatrix.length) % pfMatrix.length;
				}
				if(y2 - 1 < 0)
				{
					y2 = (y2 + pfMatrix.length) % pfMatrix.length;
				}

				plaintext = plaintext + pfMatrix[x1][y1] + pfMatrix[x2][y2];
				ciphertextcopy = ciphertextcopy.substring(2, ciphertextcopy.length());
			}
			//This condition is if they are in the same columns
			if(y1 == y2)
			{
				if(x1 - 1 < 0)
				{
					x1 = (x1 + pfMatrix.length) % pfMatrix.length;
				}
				if(x2 - 1 < 0)
				{
					x2 = (x2 + pfMatrix.length) % pfMatrix.length;
				}
				
				plaintext = plaintext + pfMatrix[x1][y1] + pfMatrix[x2][y2];
				ciphertextcopy = ciphertextcopy.substring(2, ciphertextcopy.length());
			}
			//This is if they are neither in the same row nor in the same column
			if(x1 != x2 && y1 != y2)
			{
				plaintext = plaintext + pfMatrix[x1][y2] + pfMatrix[x2][y1];
				ciphertextcopy = ciphertextcopy.substring(2, ciphertextcopy.length());
			}
		}
		return plaintext;
	}
}
