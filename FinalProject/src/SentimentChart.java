import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Makes charts that based on sentiment data
 */
public class SentimentChart extends GenericChart {

    /**
     * Creates a Scanner Plot of Sentiment over Time of a given news sources
     *
     * @param source name of news source
     * @return chart object
     */
    public XYChart makeSentimentByTimeChart(String source) {
        ArrayList<Date> dateSeries = new ArrayList<>();
        ArrayList<Float> positiveSeries = new ArrayList<>();
        ArrayList<Float> neutralSeries = new ArrayList<>();
        ArrayList<Float> negativeSeries = new ArrayList<>();
        Date date = null;
        Float positiveValue, neutralValue, negativeValue;

        if (!getSources().contains(source)) {
            System.out.println("Source does not exist in dataset");
            XYChart chart = new XYChartBuilder().width(500).height(200).title("Source does not exist in dataset").xAxisTitle("X").yAxisTitle("Y").build();
            return chart;
        }

        for (Article article : getArticles()) {
            if (article.getSource().trim().equals(source)) {
                date = Date.from(article.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                dateSeries.add(date);
                positiveValue = ((float) article.getPositiveCount() / article.getSentenceCount());
                neutralValue = ((float) article.getNeutralCount() / article.getSentenceCount());
                negativeValue = ((float) article.getNegativeCount() / article.getSentenceCount());
                positiveSeries.add(positiveValue);
                neutralSeries.add(neutralValue);
                negativeSeries.add(negativeValue);
            }
        }

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(source + " Sentiment over Time").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        XYSeries series = chart.addSeries("Positive Sentiment Ratio", dateSeries, positiveSeries);
        chart.addSeries("Neutral", dateSeries, neutralSeries);
        chart.addSeries("Negative", dateSeries, negativeSeries);

        return chart;
    }

    /**
     * Creates bar chart of sentiment by sources.
     *
     * @return chart object
     */
    public CategoryChart makeSentimentBySourceChart() {
        ArrayList<String> sourcesSeries = new ArrayList<>();
        ArrayList<Double> positiveSeries = new ArrayList<>();
        ArrayList<Double> neutralSeries = new ArrayList<>();
        ArrayList<Double> negativeSeries = new ArrayList<>();
        HashMap<String, Integer[]> allData = extractSentSourceData();

        for (String source : allData.keySet()) {
            if (allData.get(source)[3] > 1) {
                sourcesSeries.add(source);
                positiveSeries.add((double) allData.get(source)[2] / allData.get(source)[3] * 100);  //positive
                neutralSeries.add((double) allData.get(source)[1] / allData.get(source)[3] * 100);  //neutral
                negativeSeries.add((double) allData.get(source)[0] / allData.get(source)[3] * 100);  //negative
            }
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("The Percentage Each Sentiment " +
                "Contributes to the Entire Output of a Source").
                xAxisTitle("Sources").yAxisTitle("Percent").build();
        chart.getStyler().setXAxisLabelRotation(45);

        chart.addSeries("Positive Sentiment Ratio", sourcesSeries, positiveSeries);
        chart.addSeries("Neutral", sourcesSeries, neutralSeries);
        chart.addSeries("Negative", sourcesSeries, negativeSeries);

        return chart;

    }

    /**
     * This method creates a pie chart for each sentiment based on
     * sentiment argument given. This method contributes three charts
     * to the overall matrix.
     *
     * @param sentiment takes pos, neut, or neg as arguments
     * @return a chart object
     */
    public PieChart makeSentimentPieChart(String sentiment) {
        /**
         * hashmap where each source is maps to an array with counts
         *  for the negative, neutral, and positive sentiment counts,
         *  as well as the total sum of sentiment count
         */
        HashMap<String, Integer[]> sentChartData = extractSentSourceData();

        PieChart chart = new PieChartBuilder().width(1000).height(600).title("Sentiment Chart").theme(Styler.ChartTheme.GGPlot2).build();

        Color[] sliceColors = new Color[]{new Color(139, 0, 0), new Color(255, 99, 71), new Color(255, 165, 0), new Color(255, 215, 0), new Color(154, 205, 50), new Color(34, 139, 34), new Color(0, 255, 127)
                , new Color(32, 178, 170), new Color(47, 79, 79), new Color(100, 149, 237), new Color(25, 25, 112), new Color(138, 43, 226), new Color(221, 160, 221), new Color(255, 192, 203)};
        chart.getStyler().setSeriesColors(sliceColors);

        Double sentAmount = 0.0;
        for (String source : sentChartData.keySet()) {
            Integer[] sents = sentChartData.get(source);
            double totalSents = sents[3];
            double pos = (double) sents[2];
            double neut = (double) sents[1];
            double neg = (double) sents[0];
            double sentTotal = pos + neut + neg;

            if (sentiment.contains("pos")) {
                sentAmount = (double) pos / sentTotal;
                chart.setTitle("Percent of Positive Sentiment that each Source Contributes to the Corpus as a Whole");
            }

            if (sentiment.contains("neut")) {
                sentAmount = (double) neut / sentTotal;
                chart.setTitle("Percent of Neutral Sentiment that each Source Contributes to the Corpus as a Whole");
            }

            if (sentiment.contains("neg")) {

                sentAmount = (double) neg / sentTotal;
                chart.setTitle("Percent of Negative Sentiment that each Source Contributes to the Corpus as a Whole");
            }

            chart.addSeries(source, sentAmount);

        }


        chart.getStyler().setChartPadding(4);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setAnnotationDistance(1.05);
        chart.getStyler().setAnnotationsRotation(45);

        return chart;


    }


}
