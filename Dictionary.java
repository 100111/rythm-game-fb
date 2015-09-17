import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Dictionary {
	private BinSNode data;
	private ArrayList<String[]> suggestions;
	
	public static void main(String[] args) {
		Dictionary dic = new Dictionary();
		dic.init();
	}
	
	public void init() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Loading dictionary data...");
		this.readFile("dictionary.txt");
		System.out.println("Data loaded.");
		
		this.data.deleteAtRoot("busybody");
		this.data.insert("busybody");
		
//		String input;
//		do {
//			System.out.print("Enter search word: ");
//			input = scanner.nextLine();
//			lookUpInput(input);
//			System.out.println();
//		}
//		while (input.compareToIgnoreCase("q") != 0);
		
		printStats();
		
		scanner.close();
		System.out.println("Exit.");
	}
	
	public void lookUpInput(String input) {
		if (this.data.searchFor(input)) {
			System.out.println("Word '" + input + "' found in dictionary.");
		} else {
			long startTime = System.currentTimeMillis();	
			
			ArrayList<String> foundWords = new ArrayList<String>();
			
			suggestions = new ArrayList<>(4);
			suggestions.add(similarOne(input));
			suggestions.add(similarTwo(input));
			suggestions.add(similarThree(input));
			suggestions.add(similarFour(input));
			
			for (String[] array : suggestions) {
				for (String word : array) {
					if(this.data.searchFor(word)){
						if (!foundWords.contains(word)) foundWords.add(word);
					}
				}
			}
			
			if(foundWords.size() == 0) System.out.println("No word found.");
			else {
				System.out.print("Sugest result: ");
				for (String word : foundWords) {
					System.out.print(word + ", ");
				}
			}
			
			System.out.println("");
			System.out.println("Total sugestions: " + foundWords.size());
			System.out.println("Total time for look up: "+ (System.currentTimeMillis() - startTime) + " ms");
			
		}
	}

	public void printStats() {
		System.out.println("***Stats****");
		
		int depth = this.data.depth();
		System.out.println("Depth: " + depth);
		
		for (int i = 0; i <= depth; i++) {
			System.out.println("Node count on depth " + i + ": " + this.data.nodeCountOnDepth(i));
		}
		
		System.out.println("Average depth: " + this.data.averageDepth());
		
		System.out.println("First word: " + this.data.minString());
		System.out.println("Last word: " + this.data.maxString());
	}

	void readFile(String fileName) {
		File file = new File(fileName);
		Scanner fileReader = null;
		
		try {
			fileReader = new Scanner(file);
			
			//Create the 1st node
			data = new BinSNode(fileReader.nextLine());
			
			while(fileReader.hasNextLine()) {
				data.insert(fileReader.nextLine());
			}
			 
			fileReader.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	
	///////////////////
	//Similar functions
	///////////////////
	public String[] similarOne(String word){
		char[] word_array = word.toCharArray();
		char[] tmp;
		String[] words = new String[word_array.length-1];
		for(int i = 0; i < word_array.length - 1; i++){
			tmp = word_array.clone();
			words[i] = swap(i, i+1, tmp);
		}
		return words;
	}
	
	public String swap(int a, int b, char[] word){
		char tmp = word[a];
		word[a] = word[b];
		word[b] = tmp;
		return new String(word);
	}
	
	public String[] similarTwo(String word) {
		char[] wordArray = word.toCharArray();
		char[] temp;
		String[] words = new String[wordArray.length * 26 - wordArray.length];
		
		int counter = 0;
		for(int i = 0; i < wordArray.length; i++){
			temp = wordArray.clone();
			for (int j = 97; j <= 122; j++) { // 'a' -> 'z' in ASCII
				if (wordArray[i] != j) {
					temp[i] = (char) j;
					words[counter] = new String(temp);
					counter++;
				}
			}
		}
		return words;
	}
	
	public String[] similarThree(String word) {
		String[] words = new String[(word.length() + 1) * 26];
		
		int counter = 0;
		for(int i = 0; i < word.length() +1; i++){
			for (int j = 97; j <= 122; j++) { // 'a' -> 'z' in ASCII
				words[counter] = new String (insertToPos(j, i, word));
				counter++;
			}
		}
		return words;
	}
	
	public char[] insertToPos(int charASCII, int pos, String word){
		char[] array = new char[word.length() + 1];
		
		for (int i = 0; i < array.length ; i++) {
			if (i < pos)  array[i] = word.charAt(i);
			else if (i > pos) array[i] = word.charAt(i-1);
			else array[i] = (char) charASCII;
		}
		
		return array;
	}
	
	public String[] similarFour(String word) {
		String[] words = new String[word.length()];
		
		int counter = 0;
		for(int i = 0; i < word.length(); i++){
			words[counter] = new String (removeFromPos(i, word));
			counter++;
		}
		return words;
	}
	
	public char[] removeFromPos(int pos, String word){
		char[] array = new char[word.length() - 1];
		
		for (int i = 0; i < array.length + 1 ; i++) {
			if (i < pos)  array[i] = word.charAt(i);
			else if (i > pos) array[i-1] = word.charAt(i);
		}
		
		return array;
	}
}
