
/**
 * This class
 * - creates a chart that displays the z-scores for the average reading level and lexical density by source
 * - creates a chart displaying reading levels by source
 * - creates a chart displaying lexical density by source
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.VectorGraphicsEncoder.VectorGraphicsFormat;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

public class LevelAndDensityCategoryChart  extends GenericChart  {

	// Instance Variables
	HashMap<String, Double[]> LevelsBySource;
	HashMap<String, Double[]> LevelsBySourceCOPY;
	HashMap<String, Double[]> Zdata;

	// Constructor
	public LevelAndDensityCategoryChart() {
		LevelsBySource = extractSourceLevelData();
		LevelsBySourceCOPY = extractSourceLevelData();
		Zdata = makeZs(LevelsBySourceCOPY);

	}

	/**
	 * Creates a HashMap with - the average reading level for each source - the
	 * average lexical densities for each source
	 * @return readingLevelBySource
	 */
	public HashMap<String, Double[]> extractSourceLevelData() {

		HashMap<String, Double[]> readingAndZBySource = new HashMap<>();

		// populating the hashmap
		for (Article article : GenericChart.getArticles()) {
			String source = article.getSource().trim();
			double readingLevel = article.getReadingLevel();
			double density = article.getLexicalDensity();

			if (readingAndZBySource.containsKey(source)) {
				Double[] levels = readingAndZBySource.get(source);
				levels[0] += readingLevel;
				levels[1] += density;
				readingAndZBySource.replace(source, levels);

			}

			else {
				Double[] levels = { readingLevel, density };
				readingAndZBySource.put(source, levels);

			}

		}

		// converting sums to averages
		for (String source : readingAndZBySource.keySet()) {
			Double[] levels = readingAndZBySource.get(source);
			levels[0] = levels[0] / 100;
			levels[1] = levels[1] / 100;
			readingAndZBySource.replace(source, levels);

		}

		return readingAndZBySource;
	}

	/**
	 * takes in a hashmap containing {average reading level, average density} by source
	 * converts averages (on a source level) into z-scores (on a corpus level) for
	 * reading level and lexical density for each source.
	 * 
	 * @param Zdata
	 * @return Zdata
	 */
	public HashMap<String, Double[]> makeZs(HashMap<String, Double[]> averagesMap) {

		double corpusReadingAvg = 0;
		double corpusReadingStDev = 0;
		double corpusDensityAvg = 0;
		double corpusDensityStDev = 0;
		//ArrayList<Double> reading = new ArrayList<>();
		//ArrayList<Double> density = new ArrayList<>();

		//  sum the reading levels and densities for each source
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			corpusReadingAvg += levels[0];
			corpusDensityAvg += levels[1];
			
		}

		// compute the average for reading Level and density by dividing by the total number of sources
		corpusReadingAvg = corpusReadingAvg / 14;
		corpusDensityAvg = corpusDensityAvg / 14;

		// compute the standard deviation for reading Level and density, for the corpus 
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			
			//get the source average - overall average squared, and sum it up 
			Double readingN = levels[0];
			Double densityN = levels[1];
			readingN = Math.pow((double) readingN - corpusReadingAvg, 2);
			corpusReadingStDev += readingN;
			densityN = Math.pow((double) densityN - corpusDensityAvg, 2);
			corpusDensityStDev += densityN;

		}
		
		//compute standard deviation by dividing by the number of sources and taking the square root
		corpusReadingStDev = Math.sqrt(corpusReadingStDev /14);
		corpusDensityStDev = Math.sqrt(corpusDensityStDev /14);
		
		
		// compute the z-score for each source
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			Double readingN = levels[0];
			Double densityN = levels[1];
			Double readingZ = (double) ((readingN - corpusReadingAvg) / corpusReadingStDev);
			Double densityZ = (double) ((densityN - corpusDensityAvg) / corpusDensityStDev);

			levels[0] = readingZ;
			levels[1] = densityZ;

			averagesMap.replace(source, levels);

		}

		return averagesMap;
	}

	/**
	 * creates the Z-scores graphxz
	 * 
	 * @return
	 */
	public CategoryChart makeZChart(String zType) {

		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600)
				.title( zType + " Z-Scores by Source").xAxisTitle("Source")
				.yAxisTitle("Z-Score").theme(ChartTheme.GGPlot2).build();

		// Series

		String[] xseries = Zdata.keySet().toArray(new String[0]);
		ArrayList<Double> y1 = new ArrayList<>();
		ArrayList<Double> y2 = new ArrayList<>();

		for (String source : Zdata.keySet()) {
			Double[] levels = Zdata.get(source);
			Double readingLevel = levels[0];
			Double density = levels[1];
			y1.add(readingLevel);
			y2.add(density);

		}

		if(zType.contains("Density")) {
			chart.addSeries("lexical density", new ArrayList<String>(Arrays.asList(xseries)), y2);	
		}
		
		else {
			chart.addSeries("reading level", new ArrayList<String>(Arrays.asList(xseries)), y1);	
		}
		
		
		chart.getStyler().setXAxisLabelRotation(45);
		return chart;
	}

	public CategoryChart makeAvgsChart(String avgType) {
		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title(avgType + " by Source")
				.xAxisTitle("Source").yAxisTitle("Level").theme(ChartTheme.GGPlot2).build();
		
		// Series
		String[] xseries = LevelsBySource.keySet().toArray(new String[0]);
		ArrayList<Double> y1 = new ArrayList<>();
		ArrayList<Double> y2 = new ArrayList<>();

		for (String source : LevelsBySource.keySet()) {
			Double[] levels = LevelsBySource.get(source);
			Double readingLevel = levels[0];
			Double density = levels[1];
			y1.add(readingLevel);
			y2.add(density);

		}

		if (avgType.contains("Density")) {
			chart.addSeries("lexical density", new ArrayList<String>(Arrays.asList(xseries)), y2);
		}

		else {
			chart.addSeries("reading level", new ArrayList<String>(Arrays.asList(xseries)), y1);

		}
		
		
		
		chart.getStyler().setXAxisLabelRotation(45);
		return chart;

	}

	public static void main(String[] args) {


	}

}
