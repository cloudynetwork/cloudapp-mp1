package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Test {
	
	Random generator;
	String userName = "0";
	String delimiters = " \t,;.?!-:@[](){}_*/";
	String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
	
	  void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
	        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
	        messageDigest.update(seed.toLowerCase().trim().getBytes());
	        byte[] seedMD5 = messageDigest.digest();

	        long longSeed = 0;
	        for (int i = 0; i < seedMD5.length; i++) {
	            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
	        }

	        this.generator = new Random(longSeed);
	    }

	    Integer[] getIndexes() throws NoSuchAlgorithmException {
	        Integer n = 10000;
	        Integer number_of_lines = 50000;
	        Integer[] ret = new Integer[n];
	        this.initialRandomGenerator(this.userName);
	        for (int i = 0; i < n; i++) {
	            ret[i] = generator.nextInt(number_of_lines);
	        }
	        return ret;
	        
	    }
	/**
	 * 
	 * @param filename input file name string
	 * @param encoding specify file 
	 * @return Returns a String[] containing a line per element
	 * @throws FileNotFoundException
	 */
	private String[] fileToArray(String filename, String encoding) throws FileNotFoundException {

		Scanner s = new Scanner(new File(filename), encoding);

		ArrayList<String> list = new ArrayList<String>(50);

		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}

		s.close();

//		System.out.println(list.size()); // For debugging
		String[] filearray = new String[list.size()];

		return list.toArray(filearray);
	}
	
	/**
	 * 
	 * @param arraytoprocess String[] object to process
	 * @param indexSize int of the index size to process
	 * @return a String array of only the indexes provided by getIndexes()
	 * @throws NoSuchAlgorithmException 
	 */
	private String[] extractMessageIndex(String[] arrayToProcess, int indexSize) throws NoSuchAlgorithmException {
		
		Integer[] processIndex = new Integer[indexSize];
		processIndex = getIndexes();
		
		ArrayList<String> list = new ArrayList<String>(5000);
		
		for (int o: processIndex) {
			
			list.add(arrayToProcess[o]);
		
		}
		
		String[] returnArray = new String[list.size()];

		return list.toArray(returnArray);
	}

	private String[] splitUsingDelimters(String[] arrayToProcess, String delims) {
		
		ArrayList<String> list = new ArrayList<String>(10000);
		
		for (String p : arrayToProcess) {
			
			String[] toList = p.split(delims);
			
			for (String q : toList) {
				list.add(q);
			}
			
		}
		
		String[] returnArray = new String[list.size()];
		return list.toArray(returnArray);
	}
	
	/**
	 * Makes lowercase, removes whitespace and returns String[]
	 * @param arrayToProcess
	 * @return cleaned up String[]
	 */
	private String[] cleanUpArray(String[] arrayToProcess) {
		
		ArrayList<String> list = new ArrayList<String>(10000);
		
		for (String str : arrayToProcess) {
			str = str.toLowerCase();
			str = str.replace(" ", "");
			list.add(str);
		}
		
		String[] returnArray = new String[list.size()];
		return list.toArray(returnArray);
	}
	
	private String[] removeStopWords(String[] arrayToProcess, String[] stopWords) {

		ArrayList<String> list = new ArrayList<String>(10000);

		for (String str : arrayToProcess) {
			list.add(str);
		}
		
		Integer[] indexesToRemove = new Integer[list.size()]; 
		
		int storeControl = 0; 
		
		for (int i = 0; i < list.size(); i++) {

			for (String word : stopWords) {

				if (word == list.get(i)) {
					indexesToRemove[storeControl] = i; 
					storeControl++;
				}

			}
		}
		
		int removalCounter = 0;
		
		for (int i = 0; i < indexesToRemove.length; i++) {
			
//			System.out.println(toRemove);
//			list.remove( (indexesToRemove[i-removalCounter]) );
			removalCounter++;
		
		}
		
//		System.out.println(indexesToRemove[1]);
		Integer removeIndex = indexesToRemove[0];
		list.remove(removeIndex.intValue());
		
		String[] returnArray = new String[list.size()];
		return list.toArray(returnArray);

	}
	
	public String[] process() throws Exception {
		String[] ret = new String[20];

		String[] tobeprocessed = fileToArray("input.txt", "UTF-8"); 

		String[] extractedIndicies = extractMessageIndex(tobeprocessed, 10000);
			
		String[] delimitedArray = splitUsingDelimters(extractedIndicies, "([\\t,;.\\?!\\-:@\\[\\]\\(\\){}_\\*\\/])" ); 
		
		String[] cleanedArray = cleanUpArray(delimitedArray);
		
		String[] removedWords = removeStopWords(cleanedArray, stopWordsArray);
		
		String[] wordTest = {"in", "melonhead", "because"};
		
		for (String out : wordTest) {
			System.out.println("Pre remove: " + out);
		}
		
		String[] removedWordsTest = removeStopWords(wordTest, stopWordsArray);
		
		for (String out : removedWordsTest) {
			System.out.println(out);
		}
		
		


		// Set value of ret to output before return TO DO

		return ret;
	}
	
	

	public static void main(String[] args) throws Exception {

		Test test1 = new Test();
		String[] var1 = test1.process();

	}

}
