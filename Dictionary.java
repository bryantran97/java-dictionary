import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {

	private static final String FILENAME = "dictionary.txt";
	// ========================
	//      KEY VALUE PAIR
	// ========================
	public static class keyValue {
		int key = 0;
		String word = null;
		String value = null;
		String classification = null;
		
		public keyValue(int keyVal, String wordVal, String classVal, String defVal) {
			key = keyVal;
			word = wordVal;
			classification = classVal;
			value = defVal;
		}
	}
	// ========================
	//      Linear Probing
	// ========================
	public static class LinearProbing {
		int tableSize = 262147;
		int numElements = 0;
		keyValue[] lpArray = new keyValue[tableSize];
		
		// hash function
		public int hash(String word) {
			int hash = 0;
			for (int i = 0; i < word.length(); i++){
				hash = (hash << 5) - hash + word.charAt(i);
			}
			return Math.abs(hash % tableSize);
		}

		// return size
		public int size(){
			return tableSize;
		}
		
		// return lambda
		public double lambda(){
			double lambda = ((double)numElements) / tableSize;
			return lambda;
		}
		
		// insert
		public void insert(String word, String classification, String definition){
			int key = hash(word);	// retrieve hash key
			keyValue kvp = new keyValue(key, word, classification, definition); // create the object
			
			if(lpArray[key] == null){
				lpArray[key] = kvp;
			} else {
				while(lpArray[key] != null) {
					key = (key + 1) % tableSize;
				}
				lpArray[key] = kvp;
			}
			numElements++;
		}
	}
			
	// ========================
	//      MAIN FUNCTION
	// ========================
	public static void main(String[] args) {
		// Get the user input for dictionary word
		System.out.print("Hey you... put in a dictionary word: ");
		Scanner sc = new Scanner(System.in);
		String word = sc.nextLine();
		// Convert the word to match the dictionary.txt
		String converted_word = word.replaceAll(" ", "_").toLowerCase();;
		sc.close();
		
		// Linear probing Array size of 524287 (prime)
		LinearProbing lp = new LinearProbing();
		
		// Import dictionary
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
			String sCurrentLine;
			
			while((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(Pattern.quote("|"));
				lp.insert(parts[0], parts[1], parts[2]);
			}
			System.out.println(lp.tableSize + " " + lp.numElements + " " + lp.lambda());
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}