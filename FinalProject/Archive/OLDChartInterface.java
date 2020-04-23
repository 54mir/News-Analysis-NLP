package Archive;

import java.util.ArrayList;
import java.time.LocalDate;

import java.util.Arrays;

public interface OLDChartInterface {
	DataReader reader = new DataReader();
    ArrayList<Article> articles = reader.readArray("articleMetricsArray_hold.ser");
    LocalDate firstDate = articles.get(0).getDate();                         // First date in sorted array
    LocalDate lastDate = articles.get(articles.size() - 1).getDate();        // Last date in sorted array
    ArrayList<String> sources = new ArrayList<String>(Arrays.asList("Washington Post", "Guardian", "Buzzfeed",
            "Business Insider", "NPR", "Reuters", "New York Post", "New York Times", "Atlantic", "CNN", "Fox News",
            "National Review", "Breitbart", "Vox"));







}
