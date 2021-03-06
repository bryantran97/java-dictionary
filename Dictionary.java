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
	//      Linked List
	// ========================
	
	public static class Node {
		keyValue item;
		Node next;
		
		public Node(keyValue data){
			this.item = data;
		}
	}
	// ========================
	//      Linear Probing
	// ========================
	public static class LinearProbing {
		int tableSize = 330767;
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
		int tableSize = 319829;
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
		
		// insert
		public void insert(String word, String classification, String definition){
			int key = hash(word);	// retrieve hash key
			keyValue kvp = new keyValue(key, word, classification, definition); // create the object
			
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
	//      DOUBLE HASHING
	// ========================
	public static class DoubleHashing {
		int tableSize = 330767;
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
		
		// insert
		public void insert(String word, String classification, String definition){
			int key = hash1(word);	// retrieve hash key
			int key2 = hash2(word);
		
			int doubledhash = (key + key2) % tableSize;
			
			keyValue kvp = new keyValue(key, word, classification, definition); // create the object
			
			dhArray[doubledhash] = kvp;
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
	//    Separate Chaining
	// ========================
	public static class SeparateChaining {
		int tableSize = 330767;
		int numElements = 0;
		int bounce = 0;
		Node[] scArray = new Node[tableSize];
		
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
			Node newNode = new Node(kvp);
			
			if(scArray[key] == null){
				scArray[key] = newNode;
			} else {
				for(newNode = scArray[key]; newNode != null; newNode = newNode.next){
					if(key == newNode.item.key){
						newNode.item = kvp;
						return;
					}
				}
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
		
		// Probing
		LinearProbing lp = new LinearProbing();
		QuadraticProbing qp = new QuadraticProbing();
		DoubleHashing dh = new DoubleHashing();
		
		// Import dictionary
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
			String sCurrentLine;
			
			while((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(Pattern.quote("|"));
				lp.insert(parts[0], parts[1], parts[2]);
				qp.insert(parts[0], parts[1], parts[2]);
				dh.insert(parts[0], parts[1], parts[2]);
			}
			
			
			System.out.println(dh.find(converted_word).word + " " + dh.find(converted_word).classification + " " + dh.find(converted_word).value);
			System.out.println("linear probing | " + " tablesize: " + lp.tableSize + " " + "lambda: " + lp.lambda() + " " + "bounce: " + lp.bounce);
			System.out.println("quadratic probing | " + " tablesize: " + qp.tableSize + " " + "lambda: " + qp.lambda() + " " + "bounce: " + qp.bounce);
			System.out.println("double hashing | " + " tablesize: " + dh.tableSize + " " + "lambda: " + dh.lambda() + " " + "bounce: " + dh.bounce);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}