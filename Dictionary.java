import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {

	private static final String FILENAME = "dictionary.txt";
	
	// ========================
	//      Linear Probing
	// ========================
	public static class LinearProbing {
		private int currentSize, maxSize;
		private String[] keys;
		private String[] vals;
		
		// Constructor
		public LinearProbing(int capacity){
			currentSize = 0;
			maxSize = capacity;
			keys = new String[maxSize];
			vals = new String[maxSize];
		}
		
	    // Get the hash of a given key
	    private int hash(String key) 
	    {
	        return key.hashCode() % maxSize;
	    }
	    
	    public void insert(String key, String val){
	    	int tmp = hash(key);
	    	int i = tmp;
	    	do {
	    		if (keys[i] == null){
	    			keys[i] = key;
	    			vals[i] = val;
	    			currentSize++;
	    			return;
	    		}
	    		if (keys[i].equals(key)) {
	    			vals[i] = val;
	    			return;
	    		}
	    		i = (i + 1) % maxSize;
	    	} while (i != tmp);
	    }
	    
	    public String find(String key){
	    	int i = hash(key);
	    	while(keys[i] != null){
	    		if(keys[i].equals(key)){
	    			return vals[i];
	    		}
	    		i = (i + 1) % maxSize;
	    	}
	    	
	    	return null;
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
		
		System.out.println(word + " " + converted_word);
		sc.close();
		
		LinearProbing lp = new LinearProbing(500);
		
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