import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public abstract class CommonMethods implements Charts{
    LocalDate firstDate, lastDate;
    ArrayList<String> sources;
    ArrayList<Article> articles;

    

    public CommonMethods() {
        articles = Charts.articles; 
        firstDate = Charts.firstDate;                         // First date in sorted array
        lastDate = Charts.lastDate;       // Last date in sorted array
        sources = Charts.sources;
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

    public ArrayList<Article> readArray(String arrayFileName){
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



}
