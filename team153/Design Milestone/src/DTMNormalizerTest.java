import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DTMNormalizerTest {

	@BeforeEach
	void setUp() throws Exception {
		
		
	
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNormalizeDTM() {
		DTMNormalizer dtm = new DTMNormalizer("newsSourcesOut.csv");
		ArrayList <String[]> normalizedArticles = dtm.normalizeDTM();
		String[] article1 = normalizedArticles.get(0);
		assertTrue(article1[0].contains("New York Times"));
	

}

}
