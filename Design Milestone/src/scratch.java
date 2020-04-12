import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.ArrayList;


class scratch {
    //Abigail, this is the method you'll want to copy.


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

    public static void main(String[] args)  {
        scratch reader = new scratch();


        //Playing around with parsing the date
        String inDate = "2008-05-04";
        String[] parseDate = inDate.split("-");

        LocalDate ld = LocalDate.of(2008,12,12);

        System.out.println(ld.getMonth());
        System.out.println(Integer.parseInt(parseDate[0].trim()));


        ArrayList<Article> articles = reader.readArray("articleMetricsArray.ser");
        int idx = 4;
        System.out.println(articles.get(idx).getSource());
        System.out.println(articles.get(idx).getWordCount());
        System.out.println(articles.get(idx).getSentenceCount());
        System.out.println(articles.get(idx).getCharacterCount());
        System.out.println(articles.get(idx).getVerbCount());
        System.out.println(articles.get(idx).getPositiveCount());
        System.out.println(articles.get(idx).getNeutralCount());
        System.out.println(articles.get(idx).getNegativeCount());
        System.out.println(articles.get(idx).getReadingLevel());
        System.out.println(articles.get(idx).getTopTitle());
        System.out.println(articles.get(idx).getTopCountry());
        System.out.println(articles.get(idx).getTopLocality());
        System.out.println(articles.get(idx).getTopPerson());
        System.out.println(articles.get(idx).getDate());




    }



}