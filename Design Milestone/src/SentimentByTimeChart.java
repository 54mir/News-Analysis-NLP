import org.knowm.xchart.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates a Scatter Plot of sentiment by source over time.
 */
public class SentimentByTimeChart {



        public static void main(String[] args) throws Exception {
            DataReader datareader = new DataReader();
            ArrayList<Article> data = datareader.readArray("articleMetricsArray.ser");
            ArrayList<Date> dateSeries = new ArrayList<>();
            ArrayList<Float> positiveSeries = new ArrayList<>();
            ArrayList<Float> neutralSeries = new ArrayList<>();
            ArrayList<Float> negativeSeries = new ArrayList<>();
            Date date = null;
            Float positiveValue, neutralValue, negativeValue;
            int i = 1;

            for (Article article: data) {
                if (article.getSource().trim().equals("New York Times")){
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
            XYChart chart = new XYChartBuilder().width(800).height(600).title("New York Times Sentiment over Time").xAxisTitle("X").yAxisTitle("Y").build();
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

            XYSeries series = chart.addSeries("Positive Sentiment Ratio", dateSeries, positiveSeries);
            chart.addSeries("Neutral", dateSeries, neutralSeries);
            chart.addSeries("Negative", dateSeries, negativeSeries);

            // Show it
            new SwingWrapper(chart).displayChart();

        }







}


