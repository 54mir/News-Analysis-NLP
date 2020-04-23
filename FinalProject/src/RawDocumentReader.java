import java.io.*;
import java.util.*;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


/**
 * Reads in articles and outputs file with relevant metrics.
 */
public class RawDocumentReader {
    File fileToOpen;
    File fileToWrite = new File ("articleMetricsArray.ser");
  
    String stopWordsPath = "stopWords.txt";
    ArrayList<String[]> fileArray = new ArrayList<>();
    int idxTitle = 2, idxArticle = 3, idxSource = 0, idxDate = 4, idxAuthor = 1;
    Properties prop = new Properties();
    StanfordCoreNLP pipeline = new StanfordCoreNLP(property());

    public RawDocumentReader(String FileName) {
    	File rawFile = new File(FileName);
    	this.fileToOpen = rawFile;
        constructFileArray(fileToOpen);
        constructMetrics();

    }

  

	/**
     * Set properties for CoreNLP
     * @return used to build nlp pipeline
     */
    private Properties property(){
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, ner, sentiment");
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
            while (fileInput.hasNext()) {
                String[] rawText = fileInput.next().split("(,Q1Z)|(,\"Q1Z)");
                fileArray.add(rawText);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Document not found. Please ensure that your CSV file is located in the correct directors\n "
            		+ "and that you are inputting the correct file and path into the constructor. ");
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
            System.out.println("Could not find stop words file. Please make sure that the file exists. ");
        }

        return stopWords;
    }



    /**
     * Read articles from arraylist and produce metrics.
     */
    private void constructMetrics() {
        ArrayList<Article> arrayOfArticles = new ArrayList<>();
        ArrayList<String> stopWords = constructStopWordsArray(stopWordsPath);
        int loopCounter = 1;
        for (String[] row : fileArray) {
//            row[idxArticle] = cleanArticle(row[idxArticle]);
            CoreDocument document = null;
            try {
                row[idxArticle] = row[idxArticle].toLowerCase();
                document = new CoreDocument(row[idxArticle]);
            } catch (Exception e) {
                System.out.println("There is not a " + loopCounter + "th row to process. The .csv has been fully read.");
                continue;
            }
            pipeline.annotate(document);
            
            System.out.println("Working on Article: " + loopCounter);
//            if (loopCounter > 5) break;
            loopCounter++;

            Article article = new Article(row[idxSource], row[idxTitle], row[idxAuthor], row[idxDate], document, stopWords);
            arrayOfArticles.add(article);
        }

        arrayOfArticles.sort(null);
        storeArray(arrayOfArticles, fileToWrite);
       
    }




	/**
     * Output Arraylist to file for persistence.
     */
    private void storeArray(ArrayList<Article> arrayToWrite, File outputFile){
        try{
            FileOutputStream writeData = new FileOutputStream(outputFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(arrayToWrite);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing ArrayList to disk");
        }
    }

    /**
     * Read stored ArrayList back into memory
     */
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

    public static void main(String[] args) {
        RawDocumentReader rdr = new RawDocumentReader("newsSourcesSAMPLED100.csv");
        
    }
}
