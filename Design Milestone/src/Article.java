import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An article with its metrics. Can compute its own metrics based on the document passed in.
 */
public class Article implements Serializable, Comparable {

    private String source, title, author;
    private int sentenceCount, positiveCount, neutralCount, negativeCount, wordCount, characterCount,
            verbCount, nounCount, adjectiveCount, adverbCount, prepositionCount, interjectionCount;
    private float readingLevel, lexicalDensity;
    private HashMap<String, String> NERmetrics = new HashMap<>();
    private String topPerson, topTitle, topCountry, topLocality;
    private LocalDate date;
    private ArrayList<String> entityTypeList = new ArrayList<>();
    private ArrayList<String> mostWords = new ArrayList<>();
  


    public Article(String source, String title, String author, String date, CoreDocument document, ArrayList stopWords) {
        this.source = source.trim();
        this.title = title.trim();
        this.author = author.trim();
        //this.stopWords = stopWords;
        this.date = makeDate(date);
        sentenceCount = document.sentences().size();
        wordCount = document.tokens().size();
        characterCount = characterCount(document);
        readingLevel = computeReadingLevel(document);
        sentimentCount(document);

        //Parts of Speech counts
        HashMap<String, Integer> posMap = makePOSMap(document);
        String[] verbCodes = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
        String[] nounCodes = {"NN", "NNS", "NNP", "NNPS"};
        String[] adjectiveCodes = {"JJ", "JJR", "JJS"};
        String[] adverbCodes = {"RB", "RBR", "RBS"};
        verbCount = posCounter(posMap, verbCodes);
        nounCount = posCounter(posMap, nounCodes);
        adjectiveCount = posCounter(posMap, adjectiveCodes);
        adverbCount = posCounter(posMap, adverbCodes);
        interjectionCount = posMap.getOrDefault("UH", 0);
        prepositionCount = posMap.getOrDefault("IN", 0);
        lexicalDensity = computeLexicalDensity(verbCount, nounCount, adjectiveCount, adverbCount, wordCount);
        
        //NER Metrics
        entityTypeList.add("PERSON");
        entityTypeList.add("TITLE");
        entityTypeList.add("COUNTRY");
        entityTypeList.add("STATE_OR_PROVINCE");
        HashMap<String, ArrayList<String>> NERMap = makeNERMap(document, stopWords);
        NERmetrics = makeNERMetrics(NERMap);
        topPerson = NERmetrics.get("PERSON");
        topTitle = NERmetrics.get("TITLE");
        topCountry = NERmetrics.get("COUNTRY");
        topLocality = NERmetrics.get("STATE_OR_PROVINCE");
        
        //Word Counts
        mostWords = computeMostWords(document, stopWords);

    }

    /**
     * Lexical Density is a measure of how much information a text tries to convey.
     * http://www.analyzemywriting.com/lexical_density.html
     * @param verbCount # of verbs in article
     * @param nounCount # of nouns in article
     * @param adjectiveCount # of adjs in article
     * @param adverbCount # of advs in article
     * @param wordCount # of words in article
     * @return Sum of meaning carrying words divided by total words.
     */
    private float computeLexicalDensity(int verbCount, int nounCount, int adjectiveCount, int adverbCount, int wordCount) {
        float x = verbCount + nounCount + adjectiveCount + adverbCount;
        x = (wordCount != 0) ? (x / wordCount) : 0;
        return x;
    }

    /**
     * Parses publication date from a string into a LocalDate object
     * @param date "yyyy-MM-dd"
     * @return date of publication as LocalDate
     */
    public LocalDate makeDate(String date) {
        try {
            String[] parseDate = date.split("-");
            int year = Integer.parseInt(parseDate[0].trim());
            int month = Integer.parseInt(parseDate[1].trim());
            int dom = Integer.parseInt(parseDate[2].trim());
            LocalDate ld = LocalDate.of(year,month,dom);
            return ld;
        } catch (NumberFormatException e){
            System.out.println("Date not in correct format: " + date);
        }

        return (LocalDate.of(1900,1,1));

    }


    /**
     * returns an array of the artcle's top 5 words
     * @param document
     * @return mostWords
     */
    private ArrayList<String> computeMostWords(CoreDocument document, ArrayList stopWords){
    	ArrayList<String> mostWords = new ArrayList<>();
    	HashMap<String, Integer> wordCounts = new HashMap<>();
    	 for (int i = 0; i < document.tokens().size(); i++) {
    		 String mostWord;
    		 String word = document.tokens().get(i).lemma().toLowerCase().trim();
    		 word = word.replaceAll("[^a-zA-Z]", "");
    		 //System.out.println("word is " + word);
    		 if (!stopWords.contains(word)) {
    			if (wordCounts.containsKey(word)) {
    				int count = wordCounts.get(word);
    				count++;
    				wordCounts.replace(word, count);
    			}
    			else {
    				wordCounts.put(word, 1);
    			}	 
    		 }
    	 }
    	 
    	 if (wordCounts.size() > 5) {
    	 while(mostWords.size() < 5) {
    		 String maxWord = Collections.max(wordCounts.keySet());
    		 mostWords.add(maxWord);
    		 wordCounts.remove(maxWord);
    	 }}
    	return mostWords;
    }
    


    /**
     * computes reading level based off of the automated readability index algorithm, 
     * which is given by:
     * https://en.wikipedia.org/wiki/Automated_readability_index 
     * 4.71 * (chars/words) + .5 (words/sentences) - 21.43
     * @param document
     * @return
     */
    private float computeReadingLevel(CoreDocument document) {
    	double avgWordLength =  (double) characterCount / (double) wordCount;
    	double avgSentenceLength = (double) wordCount / (double) sentenceCount;
    	float readingLevel =  ((float) (4.71 * avgWordLength + 0.5 * avgSentenceLength - 21.43));
    	return readingLevel;
    }
    
    
    /**
     * makes a MAP containing all NER types and their entities 
     */
    private HashMap makeNERMap(CoreDocument document, ArrayList stopWords) {
    	HashMap<String, ArrayList<String>> NERMap = new HashMap<>();
    	
    	CoreLabel coreLabel;
    	String word;
    	String entityType;
    	for (CoreEntityMention em : document.entityMentions()) {
    		word = em.text();
    		//System.out.println("word is: " + word);
    		entityType = em.entityType();
    		//System.out.println(word + "is entity: " + entityType);
    		
    		if (!stopWords.contains(word)) {
    		if (entityTypeList.contains(entityType)) {
    		//add word to the list of words of that entity type
    		if (NERMap.containsKey(entityType)){
    			 ArrayList<String> thisWordList = NERMap.get(entityType);
    			 thisWordList.add(word);
    			 NERMap.put(entityType, thisWordList);
    		}
    		else {	
    			ArrayList<String> thisWordList = new ArrayList<>();
    			thisWordList.add(word);
   			 	NERMap.put(entityType, thisWordList);		
    		}
    	}
    	}
    	}
    	return NERMap;
    }


    /**
     * uses the NER Map to create a hashmap with the 
     * NERmetricsq`	1
     * @param NERMap
     * @return NERMetrics
     */
    private HashMap<String, String> makeNERMetrics(HashMap<String, ArrayList<String>> NERMap){
    	for (String entityType: NERMap.keySet()) {
    		//System.out.println("this entiy type is: " +  entityType);
    		ArrayList<String> wordsOfThisType = NERMap.get(entityType);
    		int count = 0;
    		HashMap<String, Integer> countsMap = new HashMap<>();
    		for (String word: wordsOfThisType) {
    			//System.out.println(word);
    			if (countsMap.containsKey(word)) {
    				count = countsMap.get(word);
    				count++;
    				countsMap.put(word, count);
    			}
    			else {
    				countsMap.put(word, 1);
    			}	
    		}
    	String topWord = Collections.max(countsMap.keySet());  
    	NERmetrics.put(entityType, topWord);
    	}
		return NERmetrics;
    }


    /**
     * Goes through each word and catalogs the part of speech of each word
     * @param document annotated document
     * @return Hashmap containing counts of up to 36 different parts of speech labels
     */
    private HashMap makePOSMap(CoreDocument document) {
        HashMap<String, Integer> posMap = new HashMap<>();
        int count = 0;
        String tag;
        for (int i = 0; i < document.tokens().size(); i++) {
            tag = document.tokens().get(i).tag();
            if (posMap.containsKey(tag)){
                count = (int) posMap.get(tag) + 1;
                posMap.put(tag, count);
            }
            else {
                posMap.put(document.tokens().get(i).tag(), 0);
            }
        }
        return posMap;
    }


    /**
     * Sums the counts of a collection of part of speech codes.
     * @param posMap HashMap of parts of speech counts
     * @param posCodes Array of parts of speech codes that you want counted
     * @return sum of the counts of the specified codes
     */
    private int posCounter(HashMap<String, Integer> posMap, String[] posCodes) {
        int count = 0;
        for (String code : posCodes) {
            if (posMap.containsKey(code)) count += (int) posMap.get(code);
        }
        return count;
    }


    /**
     * Counts the number of [a-zA-Z] characters in article
     * @param document annotated article
     * @return character count
     */
    private int characterCount(CoreDocument document) {
        Pattern p = Pattern.compile("(\\w)");
        Matcher match = p.matcher(document.text());
        int counter = 0;

        while (match.find()){
            if (match.group().length() != 0) counter++;
        }
        return counter;
    }

    /**
     * Counts the number of sentences of a given sentiment
     * @param document annotated article
     * @return sentiment count
     */
    private void sentimentCount(CoreDocument document) {
        positiveCount = 0;
        neutralCount = 0;
        negativeCount = 0;
        for (int i = 0; i < document.sentences().size(); i++) {
            try {
                String sent = document.sentences().get(i).sentiment();
                if (sent.equals("Positive")) positiveCount++;
                else if (sent.equals("Neutral")) neutralCount++;
                else if (sent.equals("Negative")) negativeCount++;
            } catch (Exception e) {
                System.out.println("could not get sentiment");
            }
        }
    }




    //Getters for Article Metrics
    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public int getNeutralCount() {
        return neutralCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public int getVerbCount() {
        return verbCount;
    }

    public int getNounCount() {
        return nounCount;
    }

    public int getAdjectiveCount() {
        return adjectiveCount;
    }

    public int getAdverbCount() {
        return adverbCount;
    }

    public int getPrepositionCount() {
        return prepositionCount;
    }

    public int getInterjectionCount() {
        return interjectionCount;
    }

    public float getReadingLevel() {
        return readingLevel;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public String getTopCountry() {
        return topCountry;
    }

    public String getTopLocality() {
        return topLocality;
    }

    public String getTopPerson() {
        return topPerson;
    }

    public  ArrayList<String> getMostWords(){
        return mostWords;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getLexicalDensity() { return lexicalDensity; }

    @Override
    public int compareTo(Object o) {
        Article other = (Article) o;
        if (date.equals(other.getDate())) return 0;
        if (date.isAfter(other.getDate())) return 1;
        return -1;
    }
}
