import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;

import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Theme;

public interface OLDChartInterface {
	DataReader reader = new DataReader();
    ArrayList<Article> articles = reader.readArray("articleMetricsArray_hold.ser");
    LocalDate firstDate = articles.get(0).getDate();                         // First date in sorted array
    LocalDate lastDate = articles.get(articles.size() - 1).getDate();        // Last date in sorted array
    ArrayList<String> sources = new ArrayList<String>(Arrays.asList("Washington Post", "Guardian", "Buzzfeed",
            "Business Insider", "NPR", "Reuters", "New York Post", "New York Times", "Atlantic", "CNN", "Fox News",
            "National Review", "Breitbart", "Vox"));







}
