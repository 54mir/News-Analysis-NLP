<p><strong>SET-UP INSTRUCTIONS:</strong></p>
<p>This is a computational text analysis on news articles. We use the following two external packages:</p>
<ul>
<li>StanfordCore NLP (more info here:&nbsp;<a href="https://stanfordnlp.github.io/CoreNLP/">https://stanfordnlp.github.io/CoreNLP/</a>)</li>
<li>XChart (<a href="https://knowm.org/open-source/xchart/">https://knowm.org/open-source/xchart/</a>)</li>
</ul>
<p>Our analysis is set up as a maven project, and therefore it is not necessary to download any .jar files. In order to run the analysis, you will just need to either clone this repository or download all of the files in the FinalProject folder, and set up the project as a maven project in your IDE of choice. (The pom.xml file is included.) More info on setting up Maven projects can be found here:&nbsp;<a href="https://knowm.org/open-source/xchart/">http://maven.apache.org/guides/getting-started/</a>&nbsp;</p>
<p>A note if you choose not to clone the repo: The src folder (within the FinalProject folder) contains all of our code. The other essential non-java files are articleMetricsArray_hold.ser and newSourcesSAMPLE1.csv.</p>
<p><strong>RUNNING THE ANALYSIS:</strong></p>
<p>Once the project is set up, open ProjectRunner.java and run the main method. Console prompts will guide you through the rest.</p>
<p>As you will see below, we have processed the data on 1400 articles and saved this data to disk. Our analysis runs on this stored data as opposed to processing the data each time the project is run. However, if you would like to see the processing part of the project, the console prompt will allow you to run through a small subset of the data. If you proceed with the analysis, the project will generate charts and present them in a window.</p>
<p><strong>THE NITTY-GRITTY - HOW THE ANALYSIS IS DESIGNED:</strong></p>
<p><em>Part 1 &ndash; Creating a usable dataset, and Building Charts</em></p>
<p>We begin with a .csv file that contains 1400 articles from 14 sources, as well as metadata for the author, source, date of publication, etc. The<strong> RawDocumentReader</strong> class reads in this .csv file, makes the necessary CoreNLP annotations, and creates an <strong>Article</strong> object for each article. (The <strong>Article </strong>class computes and stores various metrics, which we use later on in our data analysis.) The articles are read into a .ser file, where they can be stored and shared to others who might be interested in running a similar analysis. The abstract <strong>GenericChart </strong>class reads in the .ser file and stores it into memory. The <strong>GenericChart </strong>superclass is extended by the following child classes:</p>
<ul>
<li><strong>LevelAndDensityCategoryChart</strong> (creates charts 1-4)</li>
<li><strong>SentimentXYChart </strong>(creates chart 5)</li>
<li><strong>SentimentPieChart </strong>(creates charts 6-8)</li>
<li><strong>LengthDensityAndLevelXYChart </strong>(creates charts 9-11)</li>
<li><strong>FrequencyChart</strong> (creates chart 12)</li>
</ul>
<p>The <strong>ProjectRunner </strong>class servs to create these chart objects and display them in a simple swing window. It also gives users the option to see a demo implementation of the <strong>RawDocumentReader</strong> class. This demo creates a mini .ser file with a small sample of the full dataset. &nbsp;<br /> </p>
<p><em>Part 2 - Running the Analysis and Displaying the Charts</em></p>
<p><strong>AN OVERVIEW:</strong></p>
<p>In order to run the analysis, you'll need to run the&nbsp;ProjectRunnerClass. In the console, you will be asked whether or not you would like to create an .ser file from a sample of the full dataset. We have included this option so that you (the user, the TA, etc) can see our&nbsp;RawDocumentReader&nbsp;class in action. When this class is run on the full data set, it takes around four hours.&nbsp; This is due to the computationally-intensive tasks of sentiment analysis and named-entity recognition.&nbsp;</p>
<p>After the (optional) creation of the test .ser file, the full analysis will being to run. When it is complete, a bare-bones swing window will display the following graphs:</p>
<ul>
<li><em>Charts 1-4:</em>&nbsp;These charts display the average reading level and average lexical density for each news source in the corpus, as well as the z-scores for these averages. The goal of these charts was to give the user a sense of which sources might be written at a higher level, and which sources migth contain more information (ie: have a higher "lexical density" score.) We also chose to plot the z-scores of each of these etrics to give the user a sense of how much variance there was in these two metrics, across the different sources in the corpus. Ie - which sources really stand out in terms of being extra-dense or extra-hard, or alternatively,&nbsp; extra-fluffy or extra-easy.&nbsp;</li>
<li><em>Chart 5:</em>&nbsp;This chart displays, for each news source, what percent of the sentiment n articles from that source is negative, positive, or neutral. This graph, perhaps unsuprisingly, shows us that across the board for all sources, reporting tends to err on the side of negative sentiment.</li>
<li><em>Charts 6, 7, and 8:</em>&nbsp;In creating these charts, we took the normalized sums for each type of sentiment for every article in the corpus, and looked at what percent each source contributed to the total amont of each sentiment type. These charts show us that, for example, the New York Post contributes much more positive sentiment to our corpus than does, say, Reuters. Overall, however, it does seem that no sources really stick out as contributing an incredibly disproportionate amount of positive, negative, or neutral sentiment.</li>
<li><em>Charts 9, 10, and 11: </em>These charts display the various relationships between an article&rsquo;s length, reding level, and lexical density. They also allow the viewer to see how different sources (and certain outlier articles) tend to be written, and how tightly &ldquo;clumped&rdquo; they are in accordance with these parameters. For example, we can see that most Atlantic articles send to be very clumped in regards to their reading level and density, however, these articles are spread over a relatively wide spread of lengths.</li>
<li><em>Chart 12: </em>This chart shows the trend, over time, in the mentions of a variety of politicians. Perhaps unsurprisingly, Obama and Trump/Donald Trump mentions have the largest spikes.</li>
</ul>
