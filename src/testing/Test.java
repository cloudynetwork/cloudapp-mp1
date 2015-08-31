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

	public String[] process() throws Exception {
		String[] ret = new String[20];

		String[] tobeprocessed = fileToArray("input.txt", "UTF-8"); 

		System.out.println(tobeprocessed[108]); // For debugging
		
		String[] extractedIndicies = extractMessageIndex(tobeprocessed, 10000);
		
		System.out.println(extractedIndicies[9999]);

		
		// Get indexes maybe use in loop ? Integer[] indexes =
		// this.getIndexes();

		// Set value of ret to output before return TO DO

		return ret;
	}
	
	

	public static void main(String[] args) throws Exception {

		Test test1 = new Test();
		String[] var1 = test1.process();

	}

}
