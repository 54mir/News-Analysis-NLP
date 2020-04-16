import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.internal.chartpart.Chart;


/*
 * In this class, we will run the project. 
 */
public class ProjectRunner extends JFrame {

	//Instance Variables
	ReadingLevelAndDensityChart bm = new ReadingLevelAndDensityChart();
	CategoryChart zChart = bm.makeZChart();
	CategoryChart readingLevel = bm.makeAvgsChart("Reading Level ");
	CategoryChart densityChart = bm.makeAvgsChart("Density ");

	ArrayList<Chart> charts = new ArrayList<>();




	public ProjectRunner() {

	}

	/**
	 * saves a matric of the charts to a jpeg
	 */
	public void makeChartsMatrix() {

		charts.add(zChart);
		charts.add(readingLevel);
		charts.add(densityChart);
		charts.add(zChart);
		charts.add(readingLevel);
		charts.add(densityChart);

		try {
			BitmapEncoder.saveBitmap(charts, 6,1, "chartsMatrix", BitmapFormat.JPG);
		} catch (IOException e) {
			System.out.println("Error message here");
			e.printStackTrace();
		}

	}


	/**
	 * displays that jpeg to a scrollable swing panel
	 */
	public void displayCharts() {
		ImageIcon ii = new ImageIcon("chartsMatrix.jpg");
		JScrollPane jsp = new JScrollPane(new JLabel(ii));
		getContentPane().add(jsp);
		setSize(300, 250);
		setVisible(true);
	}





	public static void main(String[] args) {
		ProjectRunner pr = new ProjectRunner();
		pr.makeChartsMatrix();
		pr.displayCharts();




	}

}
