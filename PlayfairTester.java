import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 
 * @authors Sanghoon Park and Adam Cancglin
 * @version 29 September 2018
 *
 */
public class PlayfairTester 
{
	public static void main(String[] args) throws IOException
	{
		//These are the file names that the program is going to read to encrypt and decrypt
		
		
		
		
		//These are the Strings that the text from the file is stored in.
		
		
		//This is the keyword.
		String keyword;
		
		//This boolean is what controls the while loop.
		boolean done = true;
		
		//This while loop is what allows the user to choose the encrypt or decrypt message.
		//Anything else will result in a line that tells the user to re-enter the word encrypt or decrypt only.
		while(done)
		{
			//This takes the next input from the user and stores it in the String s.
			System.out.println("Do you want to encrypt or decrypt a message?");
			Scanner in = new Scanner(System.in);
			String s = in.next();
			
			//If the user selects "encrypt", the user will then be told to select their keyword.
			//The while loop within will then take all text from the plaintext file and create one string to be
			//stored in String encryptString. The playfair encryption will occur and the overall while loop will be 
			//finished. All scanners are closed afterwards.
			if(s.equals("encrypt"))
			{
				//IMPORTANT: The Keyword for the cipher we did is playfair for encrypt and decrypt
				System.out.println("Please enter your keyword");
				keyword = in.next();
				File encryptFile = new File("plaintext.txt");
				FileWriter encryptWriter = new FileWriter("out1.txt", true);
				Scanner encryptFileScan = new Scanner(encryptFile);
				String encryptString = "";
				
				while(encryptFileScan.hasNextLine())
				{
					encryptString = encryptString + encryptFileScan.nextLine();
				}
				String encryptText = Playfair.encrypt(encryptString, keyword);
				done = false;
				encryptWriter.write(encryptText);
				in.close();
				encryptFileScan.close();
				encryptWriter.close();
				
			}
			//If the user selects "decrypt", the user will then be told to select their keyword.
			//The while loop within will then take all text from the ciphertext file and create one string to be
			//stored in String decryptString. The playfair decryption will occur and the overall while loop will be
			//finished. All scanners are closed afterwards.
			else if(s.equals("decrypt"))
			{
				System.out.println("Please enter your keyword");
				keyword = in.next();
				File decryptFile = new File("ciphertext.txt");
				FileWriter decryptWriter = new FileWriter("out2.txt", true); 
				Scanner decryptFileScan = new Scanner(decryptFile);
				String decryptString = "";
				
				while(decryptFileScan.hasNextLine())
				{
					decryptString = decryptString + decryptFileScan.nextLine();
				}
				
				String decryptText = Playfair.decrypt(decryptString, keyword);
				done = false;
				in.close();
				decryptWriter.write(decryptText);
				decryptFileScan.close();
				decryptWriter.close();
			}
			//This is a print statement that will occur continuously as long as the user doesn't select 
			//"encrypt" or "decrypt"
			else
			{
				System.out.println("Please enter encrypt or decrypt.");
			}
		}
	}
}

