import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {

	private static final String FILENAME = "dictionary.txt";
	
	public static void main(String[] args) {
		// Get the user input for dictionary word
		System.out.print("Hey you... put in a dictionary word: ");
		Scanner sc = new Scanner(System.in);
		String word = sc.nextLine();
		// Convert the word to match the dictionary.txt
		String converted_word = word.replaceAll(" ", "_").toLowerCase();;
		
		System.out.println(word + " " + converted_word);
		sc.close();
		
		// Import dictionary
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
			String sCurrentLine;
			
			for(int i = 0; i < 10; i++) {
				if((sCurrentLine = br.readLine()) != null){
					String[] parts = sCurrentLine.split(Pattern.quote("|"));
					System.out.println(parts[0] + parts[1] + parts[2]);
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
