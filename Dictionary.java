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
		int bounce = 0;
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
		
		// resize
		public void resize() {
			keyValue[] doubledLPArray = new keyValue[tableSize*2];
			for(int i = 0; i < tableSize; i++){
				doubledLPArray[i] = lpArray[i];
			}
			
			tableSize = tableSize * 2;
			lpArray = doubledLPArray;
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
			
			if(numElements >= (tableSize/2)){
				resize();
			}
			
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
		
		// find word
		public keyValue find(String word) {
			int key = hash(word);
			if(lpArray[key].key == key){
				return lpArray[key];
			} else {
				while(lpArray[key].key != key) {
					key = (key + 1) % tableSize;
					bounce++;
				}
				return lpArray[key];
			}
		}
	}
	
	// ========================
	//      Quadratic Probing
	// ========================
	public static class QuadraticProbing {
		int tableSize = 13;
		int numElements = 0;
		int bounce = 0;
		keyValue[] qpArray = new keyValue[tableSize];
		
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
		
		// resize
		public void resize() {
			keyValue[] doubledQPArray = new keyValue[tableSize*2];
			for(int i = 0; i < tableSize; i++){
				doubledQPArray[i] = qpArray[i];
			}
			
			tableSize = tableSize * 2;
			qpArray = doubledQPArray;
		}
		
		// insert
		public void insert(String word, String classification, String definition){
			int key = hash(word);	// retrieve hash key
			keyValue kvp = new keyValue(key, word, classification, definition); // create the object
			
			if(numElements >= (tableSize/2)){
				resize();
			}
			
			if(qpArray[key] == null){
				qpArray[key] = kvp;
			} else {
				int count = 1;
				while(qpArray[key] != null) {
					key = (key + (count * count++)) % tableSize;
				}
				qpArray[key] = kvp;
			}
			numElements++;
		}
		
		// find word
		public keyValue find(String word) {
			int key = hash(word);
			if(qpArray[key].key == key){
				return qpArray[key];
			} else {
				int count = 1;
				while(qpArray[key].key != key) {
					key = (key + (count * count++)) % tableSize;
					bounce++;
				}
				return qpArray[key];
			}
		}
	}
	// ========================
	//     Separate Chaining
	// ========================
	public static class SeparateChaining {
		int tableSize = 13;
		int numElements = 0;
		int bounce = 0;
		keyValue[] scArray = new keyValue[tableSize];
		
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
		
		// resize
		public void resize() {
			keyValue[] doubledSCArray = new keyValue[tableSize*2];
			for(int i = 0; i < tableSize; i++){
				doubledSCArray[i] = scArray[i];
			}
			
			tableSize = tableSize * 2;
			scArray = doubledSCArray;
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
			
			if(numElements >= (tableSize/2)){
				resize();
			}
			
			if(scArray[key] == null){
				scArray[key] = kvp;
			} else {
				while(scArray[key] != null) {
					key = (key + 1) % tableSize;
				}
				scArray[key] = kvp;
			}
			numElements++;
		}
		
		// find word
		public keyValue find(String word) {
			int key = hash(word);
			if(scArray[key].key == key){
				return scArray[key];
			} else {
				while(scArray[key].key != key) {
					key = (key + 1) % tableSize;
					bounce++;
				}
				return scArray[key];
			}
		}
	}
	
	// ========================
	//      DOUBLE HASHING
	// ========================
	public static class DoubleHashing {
		int tableSize = 13;
		int numElements = 0;
		int bounce = 0;
		keyValue[] dhArray = new keyValue[tableSize];
		
		// hash function
		public int hash1(String word) {
			int hash = 0;
			for (int i = 0; i < word.length(); i++){
				hash = (hash << 5) - hash + word.charAt(i);
			}
			return Math.abs(hash % tableSize);
		}
		
		public int hash2(String word) {
			int hash = 0;
			for (int i = 0; i < word.length(); i++){
				hash = (hash << 5) - hash + (word.charAt(i))*hash;
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
		
		// resize
		public void resize() {
			keyValue[] doubledDHArray = new keyValue[tableSize*2];
			for(int i = 0; i < tableSize; i++){
				doubledDHArray[i] = dhArray[i];
			}
			
			tableSize = tableSize * 2;
			dhArray = doubledDHArray;
		}
		
		// insert
		public void insert(String word, String classification, String definition){
			int key = hash1(word);	// retrieve hash key
			int key2 = hash2(word);
			
			if(numElements >= (tableSize/2)){
				resize();
			}
			
			int doubledhash = (key + key2) % tableSize;
			
			keyValue kvp = new keyValue(key, word, classification, definition); // create the object
			
			dhArray[doubledhash] = kvp;
			numElements++;
		}
		
		// find word
		public keyValue find(String word) {
			int key = hash1(word);	// retrieve hash key
			int key2 = hash2(word);
			
			int doubledhash = (key + key2) % tableSize;
			
			return dhArray[doubledhash];
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
		
		// Probing
		LinearProbing lp = new LinearProbing();
		QuadraticProbing qp = new QuadraticProbing();
		// Double Hashing
		DoubleHashing dh = new DoubleHashing();
		// Chaining
		SeparateChaining sepc = new SeparateChaining();
		
		
		// Import dictionary
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
			String sCurrentLine;
			
			while((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(Pattern.quote("|"));
				lp.insert(parts[0], parts[1], parts[2]);
				qp.insert(parts[0], parts[1], parts[2]);
				dh.insert(parts[0], parts[1], parts[2]);
				sepc.insert(parts[0], parts[1], parts[2]);
			}
			
			System.out.println(dh.find(converted_word).word + " " + dh.find(converted_word).classification + " " + dh.find(converted_word).value);
			System.out.println("linear probing | " + " tablesize: " + lp.tableSize + " " + "lambda: " + lp.lambda() + " " + "bounce: " + lp.bounce);
			System.out.println("quadratic probing | " + " tablesize: " + qp.tableSize + " " + "lambda: " + qp.lambda() + " " + "bounce: " + qp.bounce);
			System.out.println("double hashing | " + " tablesize: " + dh.tableSize + " " + "lambda: " + dh.lambda() + " " + "bounce: " + dh.bounce);
			System.out.println("separate chaining | " + " tablesize: " + sepc.tableSize + " " + "lambda: " + sepc.lambda() + " " + "bounce: " + sepc.bounce);
			System.out.println(sepc.find(converted_word).value);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}