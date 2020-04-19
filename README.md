<p><strong>SET-UP INSTRUCTIONS:</strong></p>
<p>This is a computational text analysis on news articles. We use the following two external packages:</p>
<ul>
<li>StanfordCore NLP (more info here: <a href="https://stanfordnlp.github.io/CoreNLP/">https://stanfordnlp.github.io/CoreNLP/</a>)</li>
<li>XChart (<a href="https://knowm.org/open-source/xchart/">https://knowm.org/open-source/xchart/</a>)</li>
</ul>
<p>Our analysis is set up as a maven project, and therefore it is not necessary to download any .jar files. In order to run the analysis, you will just need to donwload all of the files in the DesignMilestone Folder, and set up the project as a maven project in your IDE of choice. (The pom.xml file is included.) More info on setting up Maven projects can be found here: <a href="https://knowm.org/open-source/xchart/">http://maven.apache.org/guides/getting-started/</a></p>
<p>&nbsp;</p>
<p><strong>THE NITTY-GRITTY - HOW THE ANALYSIS IS DESIGNED:</strong></p>
<p>//I'll put a more detailed breakdown of how things are structured here&nbsp;</p>
<p><br /> <br /><strong>THE ANALYSIS - AN OVERVIEW:</strong></p>
<p><span style="color: #00ff00;"><span style="color: #000000;">In order to run the analysis, you'll need to run the <span style="color: #800080;">ProjectRunnerClass</span>. In the console, you will be asked whether or not you would like to create an .ser file from a sample of the full dataset. We have included this option so that you (the user, the TA, etc) can see our <span style="color: #800080;">RawDocumentReader</span> class in action. When this class is run on the full data set, it takes around four hours.&nbsp; This is due to the computationally-intensive tasks of sentiment analysis and named-entity recognition.</span>&nbsp;</span></p>
<p>After the (optional) creation of the test .ser file, the full analysis will being to run. When it is complete, a bare-bones swing window will display the following graphs:</p>
<ul>
<li><em>Charts 1-4:</em>&nbsp;These charts display the average reading level and average lexical density for each news source in the corpus, as well as the z-scores for these averages. The goal of these charts was to give the user a sense of which sources might be written at a higher level, and which sources migth contain more information (ie: have a higher "lexical density" score.) We also chose to plot the z-scores of each of these etrics to give the user a sense of how much variance there was in these two metrics, across the different sources in the corpus. Ie - which sources really stand out in terms of being extra-dense or extra-hard, or alternatively,&nbsp; extra-fluffy or extra-easy.&nbsp;</li>
<li><em>Chart 5:</em> This chart displays, for each news source, what percent of the sentiment n articles from that source is negative, positive, or neutral. This graph, perhaps unsuprisingly, shows us that across the board for all sources, reporting tends to err on the side of negative sentiment.</li>
<li><em>Charts 6, 7, and 8:</em> In creating these charts, we took the normalized sums for each type of sentiment for every article in the corpus, and looked at what percent each source contributed to the total amont of each sentiment type. These charts show us that, for example, the Ney York Post contributes much more positive sentiment to our corpus that does, say, Reuters. Overall, however, it does seem that no sources really stick out as contributing an incredibly disproportionate amount of positive, negative, or neutral sentiment.</li>
<li><em>Charts 9, 10, and 11:</em></li>
<li><em>Chart 12:</em></li>
</ul>
<p>&nbsp;</p>
