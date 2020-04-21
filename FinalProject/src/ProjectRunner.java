import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.internal.chartpart.Chart;


/*
 * In this class, we will run the project file.
 */
public class ProjectRunner extends JFrame {

	//Instance Variables
	ReadingLevelAndDensityChart bm = new ReadingLevelAndDensityChart();
		CategoryChart zChartReading = bm.makeZChart("Reading Level");
		CategoryChart zChartDensity = bm.makeZChart("Density");
		CategoryChart readingLevel = bm.makeAvgsChart("Reading Level");
		CategoryChart densityChart = bm.makeAvgsChart("Density");
	
	SentimentPieCharts pcm = new SentimentPieCharts();
		PieChart pos = pcm.makeChart("pos");
		PieChart neg = pcm.makeChart("neg");
		PieChart neut = pcm.makeChart("neut");
	
	LengthDensityAndLevelCharts ldl = new LengthDensityAndLevelCharts();
		XYChart lengthDensity = ldl.makeReadingandLengthChart(0, 2, "Article Length", "Density of Article");
		XYChart lengthReadingLevel = ldl.makeReadingandLengthChart(0, 1, "Article Length", "Reading Level of Article");
		XYChart readingLevelDensity = ldl.makeReadingandLengthChart(1, 2, "Reading Level of Article", "Density of Article");
		
	FrequencyCharts fc = new FrequencyCharts();
		XYChart personfreq = fc.makeFrequencyChart();
		
	SentimentChart sc = new SentimentChart();
		CategoryChart sentsource = sc.makeSentimentBySourceChart();
	
	
	ArrayList<Chart> charts = new ArrayList<>();



	public ProjectRunner() {

	}

	/**
	 * saves a matrix of the charts to a jpeg
	 */
	public void makeChartsMatrix() {
		charts.add(readingLevel);
		charts.add(zChartReading);
		charts.add(densityChart);
		charts.add(zChartDensity);
		
		charts.add(sentsource);
		charts.add(pos);
		charts.add(neg);
		charts.add(neut);
		
		charts.add(lengthDensity);
		charts.add(lengthReadingLevel);
		charts.add(readingLevelDensity);
		
		charts.add(personfreq);
		

		try {
			BitmapEncoder.saveBitmap(charts, 12,1, "chartsMatrix", BitmapFormat.GIF);
		} catch (IOException e) {
			System.out.println("Error message here");
			e.printStackTrace();
		}

	}


	/**
	 * displays that jpeg to a scrollable swing panel
	 */
	public void displayCharts() {
		ImageIcon ii = new ImageIcon("chartsMatrix.gif");
		JScrollPane jsp = new JScrollPane(new JLabel(ii));
		
		
		getContentPane().add(jsp);
		
		setSize(1300, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	
	

	




	public static void main(String[] args) {
		
		
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Hello! Before running the full data analysis, "
				+ "would you like to try \ncreating a corpus based on a small sample "
				+ "of the full dataset?");
		System.out.println("If so, type Y. (This is NOT necessary - "
				+ "we have already \ncreated and provided an ser for the full dataset.) ");
		
		String input = myScanner.nextLine().toString();
		
		if (input.contains("Y") || input.contains("y")) {
			System.out.println("Okay! Creating an .ser with 14 fully annotated article objects using the default NOP logger.");
			System.out.println("This will just take a few minutes.");
			RawDocumentReader rdr = new RawDocumentReader("newsSourcesSAMPLED1.csv");
			System.out.println("a test .ser has been created!");
		}
		
		
		
		
		System.out.println("Running the full data analysis... ");
		ProjectRunner pr = new ProjectRunner();
		System.out.println("Making a matrix of all of the charts to display..." );
		System.out.println("Please wait one moment - the charts will pop up in a JSwing window shortly.");
		pr.makeChartsMatrix();
		pr.displayCharts();
		




	}

}
