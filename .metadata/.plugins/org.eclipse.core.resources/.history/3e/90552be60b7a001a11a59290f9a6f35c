import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/*
 * This class reads in the DocumentTermMatrix and and normalizes
 * wordcounts on an article by article basis. 
 */
public class DTMNormalizer {
	FileIO io;
	ArrayList<String[]> normalizedArticles;
	int posSent;
	int negSent;
	int neutSent;
	
	
	/*
	 * constructor for the class 
	 */
	public DTMNormalizer(String filename){
		this.io = new FileIO(filename);
		
	}
	
	
	/*
	 * Normalizes the DTM
	 */
	public ArrayList<String[]> normalizeArticles(ArrayList<String[]> lines){
		//looping through each line and creating a word count for the article, 
		// and then normalizing each cell based off of that 
		normalizedArticles = new ArrayList<String[]>();
		String[] headers = lines.get(0);
		System.out.println();
		for (String header: headers) {
			System.out.println(header);
		}
		lines.remove(0);
		for (String[] line : lines) {
			int wordCount = 0;
			for (int i = 4; i < line.length -1; i++) {
				wordCount += Integer.valueOf(line[i]);
			}
			for (int i = 4; i < line.length -1; i++) {
				double newValue = (Double.valueOf(line[i]) / (double) wordCount);
				line[i] = String.valueOf(newValue);
				//System.out.println(line[i]);
			}
			normalizedArticles.add(line);
			
		}
		
		
		return normalizedArticles;
	}
	

	
	
	
	public ArrayList<String[]> getNormalizedDTM() {
		
		ArrayList <String[]> lines = io.DTMfileReader("newsSourcesOut.csv");
		ArrayList <String[]> normalizedArticles = normalizeArticles(lines);
		return normalizedArticles;
		
	}
	
	
	
	public int getPositiveSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		posSent = Integer.valueOf(thisRow[3]);
		return posSent;	
	}
	
	public int getNeutralSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		neutSent = Integer.valueOf(thisRow[2]);
		return neutSent;	
		
	}
	
	public int getNegativeSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		negSent = Integer.valueOf(thisRow[2]);
		return negSent;	
		
	}
	
	
	
	
	//main method
	
	public static void main(String[] args) {
		DTMNormalizer dtm = new DTMNormalizer("newsSourcesOut.csv");
		ArrayList <String[]> normalizedArticles = dtm.getNormalizedDTM();
		System.out.println("done!");
		
	}
	
	
}
	
	
	
	
	
	
	



		
