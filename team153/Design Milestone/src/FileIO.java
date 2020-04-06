import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class handles reading and writing files 
 */
public class FileIO {
	
	String filename;
	String fileToWriteName;
	
	public FileIO(String file) {
		this.filename = file;
	}
	
	
	
	
	/*
	 * This methods reads in our document term matrix into an arraylist
	 */
	public ArrayList<String[]> DTMfileReader(String filename) {
		int count = 0;
		File f = new File(filename);
		ArrayList<String[]> lines = new ArrayList<String[]>();

		try {
			Scanner fileParser = new Scanner(f);
			String headers = fileParser.nextLine();
			System.out.println(headers);
			String[] headersRow = headers.split(",");
			lines.add(headersRow);
			// fileParser.useDelimiter("(Z1Q)");

			while (fileParser.hasNextLine()) {

				String thisLine = fileParser.nextLine();
				String[] thisRow = thisLine.split(",");
				lines.add(thisRow);

			}
			fileParser.close();

		}

		catch (FileNotFoundException e) {
			System.out.println("ERROR - file not found. Please try again.");

			e.printStackTrace();
		}
		System.out.println("file opened. ");
		
		return lines;

	}
	
	
	
	
	/*
	 * this method will write xChart output to a PDF
	 */
	public void ChartWriter() {
		
	}
	
	

	

}

