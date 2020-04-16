import org.knowm.xchart.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SentimentBySourceChart {

    public static void main(String[] args) throws Exception {
        DataReader datareader = new DataReader();
        ArrayList<Article> data = datareader.readArray("articleMetricsArray.ser");
        ArrayList<String> sourcesSeries = new ArrayList<>();
        ArrayList<Double> positiveSeries = new ArrayList<>();
        ArrayList<Double> neutralSeries = new ArrayList<>();
        ArrayList<Double> negativeSeries = new ArrayList<>();
        HashMap<String, double[]>  allData = new HashMap<>();
        double positiveValue, neutralValue, negativeValue;
        int i = 1;

        for (Article article: data) {
            if (allData.containsKey(article.getSource())){
                double[] articleVals = getSentValues(article);
                allData.get(article.getSource())[0] += articleVals[0];  //positive
                allData.get(article.getSource())[1] += articleVals[1];  //neutral
                allData.get(article.getSource())[2] += articleVals[2];  //negative
                allData.get(article.getSource())[3] += 1;  //count

            } else {
                allData.put(article.getSource(), getSentValues(article));
            }

        }

        for (String str: allData.keySet()) {
            System.out.println(str + " count: " + allData.get(str)[3]);
            if (allData.get(str)[3] > 0) {
                sourcesSeries.add(str);
                positiveSeries.add(allData.get(str)[0] /= allData.get(str)[3]);  //positive
                neutralSeries.add(allData.get(str)[1] /= allData.get(str)[3]);  //positive
                negativeSeries.add(allData.get(str)[2] /= allData.get(str)[3]);  //positive
            }
        }





        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Sentiment by Source").xAxisTitle("Sources").yAxisTitle("Y").build();
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.);

        chart.addSeries("Positive Sentiment Ratio", sourcesSeries, positiveSeries);
        chart.addSeries("Neutral", sourcesSeries, neutralSeries);
        chart.addSeries("Negative", sourcesSeries, negativeSeries);

        // Show it
        new SwingWrapper(chart).displayChart();

    }

    private static double[] getSentValues(Article article) {
        double[] sentVals = new double[4];

        sentVals[0] = (double) article.getPositiveCount() / article.getSentenceCount();
        sentVals[1] = (double) article.getNeutralCount() / article.getSentenceCount();
        sentVals[2] = (double) article.getNegativeCount() / article.getSentenceCount();
        sentVals[3] = 0;

        return sentVals;

    }
}
