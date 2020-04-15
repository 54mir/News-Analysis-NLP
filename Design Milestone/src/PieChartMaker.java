import java.util.ArrayList;
import java.util.HashMap;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Theme;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.VectorGraphicsEncoder;
/*
 * This is a class that creates a pie chart. 
 * We will have several graphic classes with similar functions. 
 * We plan to change the methods to take inputs (ie:
 * allow us to specify if we want to make a chart for positive, 
 * negative, or neutral sentiment) 
 */
public class PieChartMaker implements Charts {

	//DTMNormalizer dtm;
	//ArrayList<String[]> DTMdata;
	

	/*
	 * Constructs a PieChartMaker object
	 */
	public PieChartMaker() {
		
		
	}

	


	/*
	 * Simplifies the ser file into an array with the data needed for just this chart.
	 * This array can be used to investigate sentiment by source.
	 */
	public HashMap<String, Integer[]> extractSentSourceData() {
		HashMap<String, Integer[]> sentChartData = new HashMap<>();
	 for (Article article: Charts.articles) {
		
		String source = article.getSource().trim();
		int posSent = article.getPositiveCount();
		int negSent = article.getNegativeCount();
		int neutSent = article.getNeutralCount();
		
		if (sentChartData.containsKey(source)) {
			Integer[] counts = sentChartData.get(source);
			counts[0] += negSent;
			counts[1] += neutSent;
			counts[2] += posSent;
			sentChartData.replace(source, counts);
		}
		
		else {
			Integer[] counts = {negSent, neutSent, posSent};
			sentChartData.put(source, counts);
			
		}
		

		}
		return sentChartData;
	}

	/*
	 * makes a chart showing the percent of positive sentiment that each source
	 * contributes to the corpus.
	 */
	public PieChart makeChart(HashMap<String, Integer[]> series, String sentiment) {
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Sentiment Chart").theme(ChartTheme.GGPlot2).build();
	
		
		
		Integer sentAmount = 0;
		for (String source : series.keySet()) {
			Integer[] sents = series.get(source);
			
			if (sentiment.contains("pos")) {
				sentAmount= sents[2];	
				chart.setTitle("Positive Sentiment by Source");
			}
			
			if(sentiment.contains("neut")){
				sentAmount= sents[1];
				chart.setTitle("Neutral Sentiment by Source");
			}
			
			if(sentiment.contains("neg")){
				
				sentAmount= sents[0];
				chart.setTitle("Negative Sentiment by Source");
			}
			
			chart.addSeries(source, sentAmount);

		}

		new SwingWrapper<PieChart>(chart).displayChart();
		return chart;
		

	}

	
	public static void main(String[] args) {
		PieChartMaker gm = new PieChartMaker();
		HashMap<String, Integer[]> series = gm.extractSentSourceData();
		gm.makeChart(series, "pos");

	}

	
		
		
		
		
	}


