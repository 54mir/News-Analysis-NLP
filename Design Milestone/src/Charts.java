import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;

import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Theme;

public interface Charts {
	DataReader reader = new DataReader();
    ArrayList<Article> articles = reader.readArray("articleMetricsArray.ser");
//    String[] sources = {"Washington Post", "Guardian", "Buzzfeed", "Business Insider", "NPR", "Reuters",
//            "New York Post", "New York Times", "Atlantic", "CNN", "Fox News", "National Review", "Breitbart", "Vox"};

    ArrayList<String> sources = new ArrayList<String>(Arrays.asList("Washington Post", "Guardian", "Buzzfeed", "Business Insider", "NPR", "Reuters",
            "New York Post", "New York Times", "Atlantic", "CNN", "Fox News", "National Review", "Breitbart", "Vox"));

	  
	  
	
	
	
	
}
