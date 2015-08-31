package testing;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Test {
	
	private String[] FileToArray() {
		
		
		return 
	}
	
    public String[] process() throws Exception {
        String[] ret = new String[20];
       
		Scanner s = new Scanner(new File("input.txt"), "UTF-8");

		ArrayList<String> list = new ArrayList<String>(50);
		
		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}
      
		s.close();
		
		System.out.println(list.size());
		String[] filearray = new String[list.size()]; 
		
		list.toArray(filearray);
		
		System.out.println(filearray[108]);
		
       // Get indexes maybe use in loop ? Integer[] indexes = this.getIndexes();

        // Set value of ret to output before return TO DO 
        
        return ret;
    }

	public static void main(String[] args) throws Exception {
		
		Test test1 = new Test();
		String[] var1 = test1.process();

	}

}
