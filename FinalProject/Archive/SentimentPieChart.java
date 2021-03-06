import java.awt.Color;
import java.util.HashMap;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.ChartTheme;

/*
 * This is a class that creates a pie chart. 
 * We will have several graphic classes with similar functions. 
 * We plan to change the methods to take inputs (ie:
 * allow us to specify if we want to make a chart for positive, 
 * negative, or neutral sentiment) 
 */
public class SentimentPieChart extends GenericChart {

	/**
	 * hashmap where each source is mapps to an array with counts
	 *  for the negative, neutral, and positive sentiment counts, 
	 *  as well as the total sum of sentiment count 
	 */
	HashMap<String, Integer[]> sentChartData = new HashMap<>();
	

	/*
	 * Constructs a PieChartMaker object
	 */
	public SentimentPieChart() {
		this.sentChartData = extractSentSourceData();
		
		
	}

	


	/*
	 * populates sentchartdata
	 */
	public HashMap<String, Integer[]> extractSentSourceData() {
		
	 for (Article article: super.getArticles()) {
		
		String source = article.getSource().trim();
		int posSent = article.getPositiveCount();
		int negSent = article.getNegativeCount();
		int neutSent = article.getNeutralCount();
		int totalSent = posSent + negSent + neutSent;
		
		if (sentChartData.containsKey(source)) {
			Integer[] counts = sentChartData.get(source);
			counts[0] += negSent;
			counts[1] += neutSent;
			counts[2] += posSent ;
			totalSent += totalSent;
			
			sentChartData.replace(source, counts);
		}
		
		else {
			Integer[] counts = {negSent, neutSent, posSent, totalSent};
			sentChartData.put(source, counts);
			
		}
		

		}
	 
	 
		return sentChartData;
	}

	/*
	 * makes a chart showing the percent of positive sentiment that each source
	 * contributes to the corpus.
	 */
	public PieChart makeChart(String sentiment) {
		PieChart chart = new PieChartBuilder().width(1000).height(600).title("Sentiment Chart").theme(ChartTheme.GGPlot2).build();
	
		Color[] sliceColors = new Color[] { new Color(139,0,0), new Color(255,99,71), new Color(255,165,0), new Color(255,215,0), new Color(154,205,50), new Color(34,139,34), new Color(0,255,127)
				,new Color(32,178,170), new Color	(47,79,79), new Color(100,149,237), new Color(25,25,112), new Color(138,43,226), new Color(221,160,221), new Color(255,192,203)};
	    chart.getStyler().setSeriesColors(sliceColors);
		
		Double sentAmount = 0.0;
		for (String source : sentChartData.keySet()) {
			Integer[] sents = sentChartData.get(source);
			double totalSents = sents[3];
			double pos = (double) sents[2];
			double neut = (double) sents[1];
			double neg = (double) sents[0];
			
			
			double sentTotal = pos + neut + neg;
		
			
			if (sentiment.contains("pos")) {
				sentAmount=  (double) pos / sentTotal;	
				chart.setTitle("Percent of Positive Sentiment that each Source Contributes to the Corpus as a Whole");
			}
			
			if(sentiment.contains("neut")){
				sentAmount= (double) neut / sentTotal;
				chart.setTitle("Percent of Neutral Sentiment that each Source Contributes to the Corpus as a Whole");
			}
			
			if(sentiment.contains("neg")){
				
				sentAmount= (double) neg / sentTotal;
				chart.setTitle("Percent of Negative Sentiment that each Source Contributes to the Corpus as a Whole");
			}
			
			chart.addSeries(source, sentAmount);

		}
		
		
		
		
		chart.getStyler().setChartPadding(4);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setAnnotationDistance(1.05);
		chart.getStyler().setAnnotationsRotation(45);
		
		return chart;
		

	}

	
	public static void main(String[] args) {
		SentimentPieChart gm = new SentimentPieChart();
		HashMap<String, Integer[]> series = gm.extractSentSourceData();
		gm.makeChart("pos");

	}

	
		
		
		
		
	}


