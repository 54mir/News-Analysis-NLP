import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyCharts extends Chart {

    public XYChart makeFrequencyChart(){
        ArrayList<Date> dateSeries = new ArrayList<Date>();
        ArrayList<Integer> personSeries = new ArrayList<>();
        int years = Period.between(super.getFirstDate(), super.getLastDate()).getYears();
        int months = Period.between(super.getFirstDate(), super.getLastDate()).getMonths();
        int span = years * 12 + months + 1;
        HashMap<String, int[]> personMentions = new HashMap<>();

        //List of people and the number of times they were the main topic of an article in data set.
        HashMap<String, Integer> mentions = new HashMap<>();
        for (Article article : super.getArticles()) {
            if (article.getTopPerson() != null && mentions.containsKey(article.getTopPerson())){
                Integer x = mentions.get(article.getTopPerson()) + 1;
                mentions.put(article.getTopPerson(), x);
            } else {
                mentions.put(article.getTopPerson(), 1);
            }
        }

        //Only keep those mentioned more than x times
        int atLeastXTimes = 5;
        HashMap<String, Integer> shortList;
        shortList = createShortList(atLeastXTimes, mentions);

        int counter = 0;
        for (LocalDate date = super.getFirstDate(); date.isBefore(super.getLastDate()); date = date.plusMonths(1)) {
            dateSeries.add(super.convertDate(date));
            for (Article article : super.getArticles()) {
                if (article.getDate().equals(date) || (article.getDate().isAfter(date) && article.getDate().isBefore(date.plusMonths(1)))){

                    if (article.getTopPerson() != null  && shortList.containsKey(article.getTopPerson())) {
                        if (personMentions.containsKey(article.getTopPerson())){
                            personMentions.get(article.getTopPerson())[counter] += 1;

                        } else {
                            int[] arr = new int[span];
                            arr[counter] = 1;
                            personMentions.put(article.getTopPerson(), arr);
                        }
                    } else continue;
                }
            }
            counter++;
        }

        // Create Chart
        org.knowm.xchart.XYChart chart = new XYChartBuilder().width(800).height(600).
                title("Number of Articles about a person over Time").xAxisTitle("X").yAxisTitle("Y").build();

        //Add Series
        for (String name : personMentions.keySet()) {
            List<Integer> personList = Arrays.stream(personMentions.get(name)).boxed().collect(Collectors.toList());
            chart.addSeries(name, dateSeries, personList);
        }
        return chart;

    }


    public static void main(String[] args){
        FrequencyCharts fchart = new FrequencyCharts();
        new SwingWrapper(fchart.makeFrequencyChart()).displayChart();
    }
}
