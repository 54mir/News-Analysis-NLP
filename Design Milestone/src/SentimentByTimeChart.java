import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchart.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

public class SentimentByTimeChart {



        public static void main(String[] args) throws Exception {

            DataReader datareader = new DataReader();
            ArrayList<Article> data = datareader.readArray("articleMetricsArray.ser");
            ArrayList<Date> dateSeries = new ArrayList<>();
            ArrayList<Float> positiveSeries = new ArrayList<>();
            ArrayList<Float> neutralSeries = new ArrayList<>();
            ArrayList<Float> negativeSeries = new ArrayList<>();
            TreeMap<Date, Float> tm = new TreeMap<>();

            Date date = null;
            Float positiveValue, neutralValue, negativeValue;


            int i = 1;
//            Collections.sort(data);
            for (Article article: data) {
                if (article.getSource().trim().equals("New York Times")){
//                    System.out.println(article.getDate());
                    date = Date.from(article.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    dateSeries.add(date);
                    positiveValue = ((float) article.getPositiveCount() / article.getSentenceCount());
                    neutralValue = ((float) article.getNeutralCount() / article.getSentenceCount());
                    negativeValue = ((float) article.getNegativeCount() / article.getSentenceCount());
                    positiveSeries.add(positiveValue);
                    neutralSeries.add(neutralValue);
                    negativeSeries.add(negativeValue);
//                    tm.put(date, value);
//                    System.out.println("anything");
                }
            }





            // Create Chart
            XYChart chart = new XYChartBuilder().width(800).height(600).title("Scatter").xAxisTitle("X").yAxisTitle("Y").build();
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

            XYSeries series = chart.addSeries("Positive Sentiment Ratio", dateSeries, positiveSeries);

            // Show it
            new SwingWrapper(chart).displayChart();

        }







}


