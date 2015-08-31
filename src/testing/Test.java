package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	
	
	/**
	 * 
	 * @param filename input file name string
	 * @param encoding specify file encoding string, e.g UTF-8
	 * @return Returns a String[] containing a line per element
	 * @throws FileNotFoundException
	 */
	private String[] FileToArray(String filename, String encoding) throws FileNotFoundException {

		Scanner s = new Scanner(new File(filename), encoding);

		ArrayList<String> list = new ArrayList<String>(50);

		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}

		s.close();

		System.out.println(list.size()); // For debugging
		String[] filearray = new String[list.size()];

		System.out.println(filearray[108]); // For debugging

		return list.toArray(filearray);
	}

	public String[] process() throws Exception {
		String[] ret = new String[20];

		String[] tobeprocessed = FileToArray("input.txt", "UTF-8"); 

		System.out.println(tobeprocessed[108]); // For debugging

		
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
