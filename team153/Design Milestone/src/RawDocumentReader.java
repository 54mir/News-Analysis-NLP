import java.io.*;
import java.util.*;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


/**
 * Reads in articles and outputs file with relevant metrics.
 */
public class RawDocumentReader {
    File fileToOpen = new File("newsSourcesSAMPLED100.csv");
    File fileToWrite = new File ("articleMetricsArray.ser");
    ArrayList<String[]> fileArray = new ArrayList<>();
    String stopWordsPath = "stopWords.txt";
//    PrintWriter pw = new PrintWriter(fileToWrite);
    int idxTitle = 2, idxArticle = 3, idxSource = 0, idxDate = 4, idxAuthor = 1;
    Properties prop = new Properties();
    StanfordCoreNLP pipeline = new StanfordCoreNLP(property());

    /**
     * Set properties for CoreNLP
     * @return used to build nlp pipeline
     */
    private Properties property(){
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        return prop;
    }

    /**
     * Read articles from file into an array
     * @param file
     */
    private void constructFileArray(File file) {
        try {
            Scanner fileInput = new Scanner(file);
            fileInput.useDelimiter("(Z1Q\")|(Z1Q)");

            //File to Array
            int loopCounter = 0;
            while (fileInput.hasNext()) {
                String[] rawText = fileInput.next().split("(,Q1Z)|(,\"Q1Z)");
                fileArray.add(rawText);

            }
        } catch (FileNotFoundException e) {
            System.out.println("document not found");
        }

    }

    /**
     * Read stop words from file into arraylist.
     */
    private ArrayList<String> constructStopWordsArray(String fileName) {
        ArrayList<String> stopWords = new ArrayList<>();
        File file = new File(fileName);

        try(Scanner userFile = new Scanner(file)) {
            userFile.useDelimiter("\\n");
            while (userFile.hasNext()){
                stopWords.add(userFile.next().trim());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not find stop words file.");
        }

        return stopWords;
    }

    /**
     * Read articles from arraylist and produce metrics.
     */
    private void constructMetrics(){

    }



    /**
     * Output Arraylist to file for persistence.
     */
    private void storeArray(String outputFile){
        try{
            FileOutputStream writeData = new FileOutputStream(outputFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(fileArray);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing ArrayList to disk");
        }
    }


}
