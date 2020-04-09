import java.util.ArrayList;
import java.util.HashMap;

import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.VectorGraphicsEncoder;
/*
 * In this class, we will run the project. 
 */
public class ProjectRunner {
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		PieChartMaker pcm = new PieChartMaker();
		HashMap<String, Double[]> series = pcm.extractChartData();
		PieChart POSpc = pcm.makeChart(series, "pos");
		PieChart NEGpc = pcm.makeChart(series, "neg");
		PieChart NEUTpc = pcm.makeChart(series, "neut");
		
		
	}

}