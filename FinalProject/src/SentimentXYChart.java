import org.knowm.xchart.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Makes charts that based on sentiment data
 */
public class SentimentXYChart extends GenericChart {

    /**
     * Creates a Scanner Plot of Sentiment over Time of a given news sources
     * @param source name of news source
     * @return chart object
     */
    public XYChart makeSentimentByTimeChart(String source){
        ArrayList<Date> dateSeries = new ArrayList<>();
        ArrayList<Float> positiveSeries = new ArrayList<>();
        ArrayList<Float> neutralSeries = new ArrayList<>();
        ArrayList<Float> negativeSeries = new ArrayList<>();
        Date date = null;
        Float positiveValue, neutralValue, negativeValue;

        if (!super.getSources().contains(source)) {
            System.out.println("Source does not exist in dataset");
            XYChart chart = new XYChartBuilder().width(500).height(200).title("Source does not exist in dataset").xAxisTitle("X").yAxisTitle("Y").build();
            return chart;
        }

        for (Article article: OLDChartInterface.articles) {
            if (article.getSource().trim().equals(source)){
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
     * @return chart object
     */
    public CategoryChart makeSentimentBySourceChart(){
        ArrayList<String> sourcesSeries = new ArrayList<>();
        ArrayList<Double> positiveSeries = new ArrayList<>();
        ArrayList<Double> neutralSeries = new ArrayList<>();
        ArrayList<Double> negativeSeries = new ArrayList<>();
        HashMap<String, double[]>  allData = new HashMap<>();

        for (Article article: OLDChartInterface.articles) {
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
            if (allData.get(str)[3] > 1) {
                sourcesSeries.add(str);
                positiveSeries.add(allData.get(str)[0] /= allData.get(str)[3]);  //positive
                neutralSeries.add(allData.get(str)[1] /= allData.get(str)[3]);  //neutral
                negativeSeries.add(allData.get(str)[2] /= allData.get(str)[3]);  //negative
            }
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Sentiment by Source").xAxisTitle("Sources").yAxisTitle("Y").build();
        chart.getStyler().setXAxisLabelRotation(45);

        chart.addSeries("Positive Sentiment Ratio", sourcesSeries, positiveSeries);
        chart.addSeries("Neutral", sourcesSeries, neutralSeries);
        chart.addSeries("Negative", sourcesSeries, negativeSeries);

        return chart;

    }

    public static void main(String[] args) {
        SentimentXYChart sbsChart = new SentimentXYChart();
        new SwingWrapper(sbsChart.makeSentimentBySourceChart()).displayChart();
        new SwingWrapper(sbsChart.makeSentimentByTimeChart("Business Insider")).displayChart();

    }



}
