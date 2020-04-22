import java.util.ArrayList;
import java.util.HashMap;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

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