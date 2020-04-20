import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Provides common fields and functionality to classes that create charts.
 */
public abstract class CommonMethods {
    private LocalDate firstDate;
    private LocalDate lastDate;
    private ArrayList<String> sources;
    private ArrayList<Article> articles;


    /**
     * Constructor: Initializes common fields
     */
    public CommonMethods() {
        articles = readArray("articleMetricsArray_hold.ser");
        firstDate = articles.get(0).getDate();                        // First date in sorted array
        lastDate = articles.get(articles.size() - 1).getDate();       // Last date in sorted array
        sources = new ArrayList<String>(Arrays.asList("Washington Post", "Guardian", "Buzzfeed",
                "Business Insider", "NPR", "Reuters", "New York Post", "New York Times", "Atlantic", "CNN", "Fox News",
                "National Review", "Breitbart", "Vox"));
    }


    /**
     * Converts LocalDate to Date format
     * @param dateIn date as LocalDate
     * @return date as Date
     */
    public Date convertDate(LocalDate dateIn){
        Date date = Date.from(dateIn.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    /**
     * Reads in ArrayList from disk into memory
     * @param arrayFileName path of SER file
     * @return ArrayList
     */
    private ArrayList<Article> readArray(String arrayFileName){
        ArrayList<Article> data = new ArrayList<>();
        try{
            FileInputStream readData = new FileInputStream(arrayFileName);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            data = (ArrayList<Article>) readStream.readObject();
            readStream.close();


        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading ArrayList into memory");
        }

        return data;

    }


    //Getters
    public LocalDate getFirstDate() {
        return firstDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
