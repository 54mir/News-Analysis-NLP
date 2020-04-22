import java.util.ArrayList;
import java.util.HashMap;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

<<<<<<< HEAD
/**
 * This class creates XY axis charts that explore the relationship between
 * article length, reading level, and lexical density.
 *
 */
public class LengthDensityAndLevelXYCharts extends GenericChart {

//instance variables 
	HashMap<String, ArrayList<Double[]>> data = new HashMap<>();

	/**
	 * Constructs a LengthDensityAndLevelXYCharts object, capable of create several
	 * different charts
	 */
	public LengthDensityAndLevelXYCharts() {
=======
public class LengthDensityAndLevelXYCharts extends GenericChart{
	
ArrayList<Double> readingLevels = new ArrayList<>();
ArrayList<Double> lengths = new ArrayList<>();
ArrayList<Double> densities = new ArrayList<>();
ArrayList<String> sources = new ArrayList<>();

HashMap<String, ArrayList<Double[]>> data = new HashMap<>();


public LengthDensityAndLevelXYCharts() {
	for (Article article : super.getArticles()) {
		Double length = (double) article.getCharacterCount();
		Double readingLevel = (double) article.getReadingLevel();
		Double density = (double) article.getLexicalDensity();
		lengths.add(length);
		readingLevels.add(readingLevel);
		densities.add(density);
		
		String source = article.getSource().trim();
		if (!sources.contains(source)) {
			sources.add(source);
			
		}
		
>>>>>>> 30620fb6b8e6577b9140d6b63bdbd77cfb7ee0f6
		this.data = populateData();
	}

	/**
	 * This method populates a hashmap with the data needed for chart construction,
	 * where the necessary metrics are keyed to their source.
	 * 
	 * In other words, each source in mapped to an arraylist of double[], where each
	 * double[] stores {length, readingLevel, density} for a single article.
	 * 
	 * @return data hashmap 
	 */
	public HashMap<String, ArrayList<Double[]>> populateData() {

<<<<<<< HEAD
		// loop through every article object in the corpus
		for (Article article : super.getArticles()) {

			// grab the metrics that we are interested in
			Double length = (double) article.getCharacterCount();
			Double readingLevel = (double) article.getReadingLevel();
			Double density = (double) article.getLexicalDensity();
			String source = article.getSource().trim();

			// add them to the hashmap
			if (data.containsKey(source)) {

				ArrayList<Double[]> levelsList = data.get(source);
				Double[] levels = { length, readingLevel, density };
				levelsList.add(levels);
				data.put(source, levelsList);

			}

			else {

				ArrayList<Double[]> levelsList = new ArrayList<>();
				Double levels[] = { length, readingLevel, density };
				levelsList.add(levels);
				data.put(source.trim(), levelsList);

			}
=======
public HashMap<String, ArrayList<Double[]>> populateData(){
	
	for (Article article : super.getArticles()) {
		Double length = (double) article.getCharacterCount();
		Double readingLevel = (double) article.getReadingLevel();
		Double density = (double) article.getLexicalDensity();
		String source = article.getSource().trim();
		
		if (data.containsKey(source)) {
			
			ArrayList<Double[]> levelsList = data.get(source);
			Double[] levels = {length, readingLevel, density};
			levelsList.add(levels);
			data.put(source, levelsList);
			
		}
		
		else {
			ArrayList<Double[]> levelsList = new ArrayList<>();
			Double levels[] = {length, readingLevel, density};
			data.put(source, levelsList);
			
>>>>>>> 30620fb6b8e6577b9140d6b63bdbd77cfb7ee0f6
		}

		return data;
	}

	public XYChart makeReadingandLengthChart(int x, int y, String xaxis, String yaxis) {

		// creating a new XYChart object, which we will fill with our data
		XYChart chart = new XYChartBuilder().width(800).height(600).title(xaxis + " by " + yaxis).xAxisTitle(xaxis)
				.yAxisTitle(yaxis).build();
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

		// it doesn't make sense to plot reading levels on a logarithmic scale,
		// because they fall into a pretty small range, so we wont. The other
		// graphs will get a log scale.
		if (!xaxis.contains("Reading Level")) {
			chart.getStyler().setXAxisLogarithmic(true);
		}

		// populating the chart series data for each source and adding it to the chart
		for (String source : data.keySet()) {

			source = source.trim();

			ArrayList<Double[]> levelsList = data.get(source);

			ArrayList<Double> xSeries = new ArrayList<>();
			ArrayList<Double> ySeries = new ArrayList<>();
			for (Double[] article : levelsList) {
				xSeries.add(article[x]);
				ySeries.add(article[y]);

			}

			XYSeries series = chart.addSeries(source, xSeries, ySeries);

		}

		return chart;

	}

}
