import java.util.ArrayList;
import java.util.HashMap;
import org.knowm.xchart.*;
import org.knowm.xchart.SwingWrapper;

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
		HashMap<String, Integer[]> series = gm.extractChartData();
		
		gm.makeChart(series);

	}

	@Override
	public void writeChartToPDF() {
		// TODO Auto-generated method stub

	}

	/*
	 * Simplifies the large csv file into an array with just the necessary info.
	 * This array can be used to investigate sentiment by source.
	 */
	public HashMap<String, Integer[]> extractChartData() {
		HashMap<String, Integer[]> series = new HashMap<>();
		// we don't want to read in the the header to this, so let's remove it
		int count = 0;
		for (String[] line : DTMdata) {
			String source = line[0];
			System.out.println(source);
			int neg = dtm.getNegativeSentiment(count);
			int neut = dtm.getNeutralSentiment(count);
			int pos = dtm.getPositiveSentiment(count);
			count++;
			// add each individual source into the hashmap, storing the pos, neg, and
			// neutral counts
			if (!series.containsKey(source)) {
				Integer[] posNegNeut = { neg, neut, pos };
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
	public void makeChart(HashMap<String, Integer[]> series) {
		PieChart chart = new PieChartBuilder().width(800).height(600).title("simple chart").build();
		System.out.println("built chart object");

		for (String source : series.keySet()) {
			Integer[] sents = series.get(source);
			int pos = sents[2];
			chart.addSeries(source, pos);

		}

		new SwingWrapper<PieChart>(chart).displayChart();

	}

	@Override
	public void makeChart() {
		// TODO Auto-generated method stub

	}

}
