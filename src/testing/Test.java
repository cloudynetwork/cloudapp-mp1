package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

	/**
	 * 
	 * @param arrayToProcess String[] with the elements including delimiters. 
	 * @param delims a String[] with the desired delimiters to be removed. 
	 * @return a String[] with the delimiters removed. 
	 */
	private String[] splitUsingDelimters(String[] arrayToProcess, String delims) {

		ArrayList<String> list = new ArrayList<String>(10000);

		for (String p : arrayToProcess) {

			String[] toList = p.split(delims);
			// Appears that default split does not remove leading trailing empty space

			for (String q : toList) {
				list.add(q);
			}

		}
		
		//Iterate through the ListArray and removes null and empty. 
		Iterator<String> i = list.iterator();
		while (i.hasNext()) {
			String s = i.next();
			if (s == null || s.isEmpty()) {
				i.remove();
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
	
	/**
	 * 
	 * @param arrayToProcess takes String[] array input of words to remove. 
	 * @param stopWords takes String[] input of words to be removed. 
	 * @return String[] with stopWords removed
	 */
	private String[] removeStopWords(String[] arrayToProcess, String[] stopWords) {

		ArrayList<String> list = new ArrayList<String>(10000);

		for (String str : arrayToProcess) {
			list.add(str);
		}

		ArrayList<String> stopWordsList = new ArrayList<String>(100);

		for (String str : stopWords) {
			stopWordsList.add(str);
		}

		if (list.removeAll(stopWordsList) != true) {
			System.out.println("removeStopWords Failed");
		}

		String[] returnArray = new String[list.size()];
		return list.toArray(returnArray);

	}
	
	private Map<String, Integer> countOccurrances(String[] arrayToCount) {

		Map<String, Integer> m = new HashMap<String, Integer>();

		for (String a : arrayToCount) {
			Integer freq = m.get(a);
			m.put(a, (freq == null ? 1 : freq + 1));

		}

//		System.out.println(m.size() + " distinct words:");
//		System.out.println(m);
		
		m = sortMap(m);

//		System.out.println(m.size() + " distinct words:");
//		System.out.println(m);
		
		return m;

	}
	
	private Map<String, Integer> sortMap(Map<String, Integer> m) {
		
		Map<String, Integer> sort = new TreeMap<String, Integer>(new MapComparator(m));
		sort.putAll(m);
		
//		System.out.println(sort.size() + " distinct words:");
//		System.out.println(sort);
		return sort; 
	}
	
	/**
	 * 
	 * @author 
	 *
	 */
	class MapComparator implements Comparator<String> {

		Map<String, Integer> m;

		public MapComparator(Map<String, Integer> m) {
			this.m = m;
		}

		public int compare(String a, String b) {
			
			Integer vala = m.get(a);
			Integer valb = m.get(b);
			
			int result = valb.compareTo(vala);
			
			if (result == 0)
				return a.compareTo(b);
			else
				return result;
		}
	}
	
	private String[] finalProcess(Map<String, Integer> inputMap) {

		ArrayList<String> outputList = new ArrayList<String>(100);

		for (String key : inputMap.keySet()) {
//			System.out.println(key);
			outputList.add(key);
		}

		String[] outputArray = new String[20];

		for (int i = 0; i < 20; i++) {
			outputArray[i] = outputList.get(i);
		}

		return outputArray;
	}

	public String[] process() throws Exception {
		String[] ret = new String[20];

		final String[] tobeprocessed = fileToArray("input.txt", "UTF-8"); 

		final String[] extractedIndicies = extractMessageIndex(tobeprocessed, 10000);
			
		final String[] delimitedArray = splitUsingDelimters(extractedIndicies, "([ \\t,;.?!\\-:@\\[\\](){}_*/])" ); 
	    
//		for (String key : delimitedArray) {
//			System.out.println(key);
//		}
		
		final String[] cleanedArray = cleanUpArray(delimitedArray);
		
		final String[] removedWords = removeStopWords(cleanedArray, stopWordsArray);
		
//		String[] testArray = {"valley","help","terrible","google","indium","varium","petrol","diesel","engine","iridium","elirium","gods","pest","famine","war","death","proof","darkness","valley","vb","vb","rainbow","rainbow","man","rainbow","valley","gods","valley","vb","vb","rainbow","rainbow","man","rainbow","valley","gods","valley","vb","vb","rainbow","rainbow","man","rainbow","valley","gods","valley","vb","vb","rainbow","rainbow","man","rainbow"};
		
		final Map<String, Integer> sortedMap = countOccurrances(removedWords);
		
		final String[] returnArray = finalProcess(sortedMap);

		// Set value of ret to output before return TO DO

		return returnArray;
	}
	
	public static void main(String[] args) throws Exception {

		Test test1 = new Test();
		String[] var1 = test1.process();
		
		for (String key : var1) {
			System.out.println(key);
		} 

	}

}
