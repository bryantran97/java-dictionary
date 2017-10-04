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
		int tableSize = 13;
		int numElements = 0;	
		// ~~~~~~~~~~~~~~ //
		//     Hashing    //
		// ~~~~~~~~~~~~~~ //
		public int hash(String word) {
			int hash = 0;
			for (int i = 0; i < word.length(); i++){
				hash = (hash << 5) - hash + word.charAt(i);
			}
			
			return Math.abs(hash % tableSize);
		}
		// ~~~~~~~~~~~~~~ //
		//      Array     //
		// ~~~~~~~~~~~~~~ //
		public keyValue[] getArray() {
			if(numElements >= (double)tableSize/2) {
				tableSize = resize(tableSize);
			}
			keyValue[] lpArray = new keyValue[tableSize];
			
			return lpArray;
		}
		// ~~~~~~~~~~~~~~ //
		//     Resize     //
		// ~~~~~~~~~~~~~~ //
		public int resize(int size){
			size = 2 * size;
			return size;
		}
		// ~~~~~~~~~~~~~~ //
		//      LAMBDA    //
		// ~~~~~~~~~~~~~~ //
		public double lambda(){
			double lambda = ((double) numElements)/tableSize;
			return lambda;
		}
		// ~~~~~~~~~~~~~~ //
		//     Insert     //
		// ~~~~~~~~~~~~~~ //
		public void insert(String word, String classification, String definition){
			// retrieve hash key
			int key = hash(word);
			// create the object
			keyValue kvp = new keyValue(key, word, classification, definition);
			// check if the number of elements is half the size of the table size
			
			keyValue[] lpArray = getArray();
			// check if the index is taken if not put in the index
			if(lpArray[key] == null){
				lpArray[key] = kvp;
			} else {
				// if it is, go and check all the next ones till it is null
				while(lpArray[key] != null) {
					// probe up one
					key = (key + 1) % tableSize;
				}
				lpArray[key] = kvp;
			}
			numElements++;
		}
		// ~~~~~~~~~~~~~~ //
		//      FIND      //
		// ~~~~~~~~~~~~~~ //
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
			
			System.out.println(lp.tableSize);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}