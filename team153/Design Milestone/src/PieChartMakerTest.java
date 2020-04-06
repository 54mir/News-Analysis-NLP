import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class PieChartMakerTest {

	@Test
	void testMakeSentSeriesArray() {
		DTMNormalizer dtm = new DTMNormalizer("newsSourcesOut.csv");
		ArrayList <String[]> normalizedDTM = dtm.normalizeDTM();
		PieChartMaker gm = new PieChartMaker(normalizedDTM);
		HashMap<String, Integer[]> series = gm.makeSentSeriesArray();
		int numSources = (int) series.size() ;
		System.out.println(numSources);
		assertTrue(numSources==15);
		//fail("Not yet implemented");
	}

}
