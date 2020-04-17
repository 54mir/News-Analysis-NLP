import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

import java.time.LocalDate;
import java.util.ArrayList;

public class FrequencyCharts implements Charts{


    public static void main(String[] args){
        LocalDate ld1 = LocalDate.of(2005,03,05);
        LocalDate ld2 = LocalDate.of(2006,05,13);
        LocalDate ld3 = LocalDate.of(2005,03,05);
        LocalDate ld4 = LocalDate.of(2005,02,05);

        System.out.println(ld1.isBefore(ld2));
        System.out.println(ld1.equals(ld3));
        System.out.println(ld1.isAfter(ld3));

//        ArrayList<Article> articles = Charts.articles;
//        articles.sort(null);
//        System.out.println(articles.get(0).getDate());
//        System.out.println(articles.get(1).getDate());
//        System.out.println(articles.get(2).getDate());
//        System.out.println(articles.get(3).getDate());
//        System.out.println(" ");
//
//        for (Article article: Charts.articles) {
//            System.out.println(article.getDate());
//        }

//        for (String str: Charts.articles.get(2).getTopPerson()) {
//            System.out.println(str);
//        }
//        System.out.println(Charts.articles.get(9).getTopPerson());




        // Create Chart
//        org.knowm.xchart.XYChart chart = new XYChartBuilder().width(800).height(600).title(source + " Sentiment over Time").xAxisTitle("X").yAxisTitle("Y").build();
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
//
//        XYSeries series = chart.addSeries("Positive Sentiment Ratio", dateSeries, positiveSeries);
//        chart.addSeries("Neutral", dateSeries, neutralSeries);
//        chart.addSeries("Negative", dateSeries, negativeSeries);
//
//        return chart;
    }
}
