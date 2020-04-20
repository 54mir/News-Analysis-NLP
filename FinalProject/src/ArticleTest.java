import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleTest extends Chart {

	ArrayList<Article> articles = super.getArticles();
	Article testArticle = articles.get(0);
	Article testArticle2 = articles.get(1);
	Article testArticle3 = articles.get(500);
	Article testArticle4 = articles.get(5);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	/**
	 * these methods are a bit hard to test. It might appear that we are testing the getter (which are public), but the
	 * variables that these getters get are created by other private methods within the article class.
	 * Really, we are testing these methods, not the getters. Calling the getters in this way is 
	 * just the easiest way to extract the variable as our methods create it, which we can then cross-check 
	 * by looking at the actual article.
	 * 
	 * (we've actually done in an manually computed some of these metrics for a sample article...a painstaking process!) 
	 */
	@Test
	void testDensityMethod() {
		double density = testArticle.getLexicalDensity();
		assertEquals(0.528132975101471, density);
	}
	
	
	@Test
	void testReadingLevelMethod() {
		double readingLevel = testArticle.getReadingLevel();
		assertEquals(9.677618026733398, readingLevel);
	}
	
	@Test
	void testCharacterCountMethod() {
		int charCount = testArticle.getCharacterCount();
		assertEquals(3310, charCount);
	}
	
	@Test
	void testPOSMethods() {
		int nounCount = testArticle.getNounCount();
		int verbCount = testArticle.getVerbCount();
		assertEquals(194, nounCount);
		assertEquals(100, verbCount);
	}
	
	@Test
	void testSentimentMethods() {
		int poscount = testArticle.getPositiveCount();
		assertEquals(4, poscount);
	}
	
	
	
	@Test
	void testMostWordsMethod() {
		ArrayList<String> mostWords = testArticle.getMostWords();
		String topWord = mostWords.get(0);
		System.out.println(topWord);
		assertEquals("yet", topWord);
	}


	@Test
	void testSentenceCountMethod() {
		int sentenceCount = testArticle.getSentenceCount();
		System.out.println(testArticle3.getTopPerson());
		assertEquals(32, sentenceCount, 5);
	}

	@Test
	void testTopPersonMethod() {
		String topPerson = testArticle3.getTopPerson();
		assertEquals("ben wallace", topPerson);
	}

	@Test
	void testCompareToMethod() {
		assertEquals(-1, testArticle.compareTo(testArticle2));
	}

	//Top Country tests CoreNLP's ability to detect the country most mentioned in an article. It does not
	//do a great job. We did not use it in any of our charts.
	@Test
	void testTopCountry() {
		assertEquals("france", testArticle2.getTopCountry());
	}

	@Test
	void testTopCountry2() {
		assertEquals("britain", testArticle3.getTopCountry());
	}

	@Test
	void testTopCountry3() {
		assertEquals("brazil", testArticle4.getTopCountry());
	}
	
	
	
	
	

}
