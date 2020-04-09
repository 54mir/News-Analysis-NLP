import java.util.ArrayList;
import java.util.HashMap;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.VectorGraphicsEncoder;
/*
 * This is a class that creates a pie chart. 
 * We will have several graphic classes with similar functions. 
 * We plan to change the methods to take inputs (ie:
 * allow us to specify if we want to make a chart for positive, 
 * negative, or neutral sentiment) 
 */
public class PieChartMaker implements Charts {

	DTMNormalizer dtm;
	ArrayList<String[]> DTMdata;

	/*
	 * Constructs a PieChartMaker object
	 */
	public PieChartMaker() {
		this.DTMdata = Charts.dtm;
		this.dtm = Charts.normalizer;
	}

	public static void main(String[] args) {
		PieChartMaker gm = new PieChartMaker();
		HashMap<String, Double[]> series = gm.extractChartData();
		gm.makeChart(series, "pos");

	}



	/*
	 * Simplifies the large csv file into an array with just the necessary info.
	 * This array can be used to investigate sentiment by source.
	 */
	public HashMap<String, Double[]> extractChartData() {
		HashMap<String, Double[]> series = new HashMap<>();
		// we don't want to read in the the header to this, so let's remove it
		int count = 0;
		for (String[] line : DTMdata) {
			String source = line[0];
			System.out.println(source);
			double neg = dtm.getNegativeSentiment(count);
			double neut = dtm.getNeutralSentiment(count);
			double pos = dtm.getPositiveSentiment(count);
			count++;
			// add each individual source into the hashmap, storing the pos, neg, and
			// neutral counts
			if (!series.containsKey(source)) {
				Double[] posNegNeut = { neg, neut, pos };
				series.put(source, posNegNeut);
				System.out.println("added " + source + " to series hashmap");

			}

		}
		return series;
	}

	/*
	 * makes a chart showing the percent of positive sentiment that each source
	 * contributes to the corpus.
	 */
	public PieChart makeChart(HashMap<String, Double[]> series, String sentiment) {
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Sentiment Chart").build();
		
		Double sentAmount = 0.0;
		for (String source : series.keySet()) {
			Double[] sents = series.get(source);
			
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

	
	
	
		
		
		
		
	}

