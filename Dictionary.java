import java.util.Scanner;

public class Dictionary {

	public static void main(String[] args) {
		// Get the user input for dictionary word
		System.out.print("Hey you... put in a dictionary word: ");
		Scanner sc = new Scanner(System.in);
		String word = sc.nextLine();
		// Convert the word to match the dictionary.txt
		String converted_word = word.replaceAll(" ", "_").toLowerCase();;
		
		System.out.println(word + " " + converted_word);
		sc.close();
	}
}
