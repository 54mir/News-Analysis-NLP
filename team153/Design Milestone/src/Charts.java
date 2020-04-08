import java.util.ArrayList;
import java.util.HashMap;

public interface Charts {
	
	DTMNormalizer normalizer = new DTMNormalizer("newsSourcesOut.csv");
	ArrayList<String[]> dtm = normalizer.getNormalizedDTM();
	
	
	HashMap<String, Integer[]> extractChartData();
	void makeChart();
	//void makeChart(HashMap<String, Integer[]> series);
	void writeChartToPDF();
}
