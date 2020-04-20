import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ChartTest extends GenericChart {


    @Test
    void getSentValues() {
        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("about");
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, ner, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        CoreDocument document = new CoreDocument("This movie was rather lame. It did not hold my " +
                "attention at all. I would rate it a D-. Yesterday's movie, on the other hand, was beautiful. I hope" +
                "it wins all the awards. Bravo to its phenomenal cast. Now I will drink tea and get ready" +
                "for bed. It is 10pm. The air is warm.");
        pipeline.annotate(document);
        Article articleTest = new Article("CNN", "Is News Trustable?", "Joe Briefcase", "2008-04-20", document, stopWords);
//        System.out.println(articleTest.getPositiveCount());
//        System.out.println(articleTest.getNeutralCount());
//        System.out.println(articleTest.getNegativeCount());
//        System.out.println(articleTest.getSentenceCount());

        //Sentiment analyzer is said to have around an 80% accuracy hence the delta
        assertEquals((double).333, getSentValues(articleTest)[0], .26); //normalized positive sentiment
        assertEquals((double).333, getSentValues(articleTest)[1], .26); //normalized neutral sentiment
        assertEquals((double).333, getSentValues(articleTest)[2], .26); //normalized negative sentiment

    }

    @Test
    void convertDate() {
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int monthExp = cal.get(Calendar.MONTH);

        LocalDate localDate = LocalDate.now();
        cal.setTime(super.convertDate(localDate));
        int monthTest = cal.get(Calendar.MONTH);

        assertEquals(monthExp, monthTest);
    }

    @Test
    void testCreateShortList() {
        HashMap<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String alpha = "abcdefghijklmnopqrstuvwxyz";
            hm.put(alpha.substring(i, i+1), i);
        }

        assertEquals(5, createShortList(15, hm).size());

    }

//    @Test
//    void getFirstDate() {
//    }
//
//    @Test
//    void getLastDate() {
//    }
//
//    @Test
//    void getSources() {
//    }
//
//    @Test
//    void getArticles() {
//    }
}