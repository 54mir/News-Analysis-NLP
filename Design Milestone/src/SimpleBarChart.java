import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

public class SimpleBarChart implements Charts{
	
	HashMap<String, Double> readingLevelBySource;
	double[] xData;
    double[] yData;
    
    
    public SimpleBarChart() {
    	readingLevelBySource = extractSourceReadingLevelData();	
    	
    }
    
    
    public HashMap<String, Double> extractSourceReadingLevelData(){
    	
    	HashMap<String, Double> readingLevelBySource = new HashMap<>();
    	
    	 for (Article article: Charts.articles) {
    		 	String source = article.getSource().trim();
    			double readingLevel = article.getReadingLevel();

    			if (readingLevelBySource.containsKey(source)) {
    				double level = readingLevelBySource.get(source);
    				level += readingLevel;
    				readingLevelBySource.replace(source, level);
	
    				
    			}
    			
    			else {
    				readingLevelBySource.put(source, readingLevel);
    				
    			}		 
    		 
    	 }
    	 
    	 for (String source : readingLevelBySource.keySet()) {
    		double level =  readingLevelBySource.get(source) / 100;
    		readingLevelBySource.replace(source, level);
    		 
    	 }
    	

 		
    	return readingLevelBySource;
    }
    
    
    
    
    
    
    public CategoryChart makeChart() {
    	 
    	 // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Reading Level by Source").xAxisTitle("Source").yAxisTitle("ReadingLevel").theme(ChartTheme.GGPlot2).build();
     
     
        // Series
        
        String[] xseries = readingLevelBySource.keySet().toArray(new String[0]);
        Double[] yseries = readingLevelBySource.values().toArray(new Double[0]);
        
        chart.addSeries("reading level",  new ArrayList<String>(Arrays.asList(xseries)), new ArrayList<Double>(Arrays.asList(yseries)));
        new SwingWrapper<CategoryChart>(chart).displayChart();
        return chart;
      }
    
    
    public static void main(String[] args) {
		SimpleBarChart bm = new SimpleBarChart();
		bm.makeChart();

	}
    
    
}
