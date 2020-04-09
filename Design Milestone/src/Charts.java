import java.util.ArrayList;
import java.util.HashMap;

import org.knowm.xchart.internal.chartpart.Chart;

public interface Charts {
	
	DTMNormalizer normalizer = new DTMNormalizer("newsSourcesOut.csv");
	ArrayList<String[]> dtm = normalizer.getNormalizedDTM();
	ArrayList<Chart[]> ChartsArray = new ArrayList<Chart[]>();
	
	
	HashMap<String, Double[]> extractChartData();
	
	
	
}