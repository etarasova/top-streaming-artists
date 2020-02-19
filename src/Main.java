/* Class: CISC 3130
 * Section: TY9
 * EmplId: ****8149
 * Name: Elena Tarasova
 */

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Main class shows output data to the executive and VIP client
public class Main {

    private static final String FILE_NAME = "data/regional-global-weekly-2020-01-17--2020-01-24.csv";

    public static void main(String[] args) throws Exception {

        //create Map to store name of artists and how many times they appear in chart
        Map<String, Integer> chart = createChart(readCSVFile(FILE_NAME));

        //display chart by artist ratings
        displayArtistRatings(getArtistRatings(chart));

        //display chart by artist name
        displaySortedArtists(getSortedArtists(chart), chart);
    }

    //read csv file using openCSV library
    private static List<String[]> readCSVFile(String filename) throws IOException, CsvException {
        //Start reading from line number 2 (line numbers start from zero)
        CSVReader reader = new CSVReaderBuilder(new FileReader(filename))
                .withSkipLines(2)
                .build();
        //Read all rows at once
        return reader.readAll();
    }

    //create chart from file
    private static Map<String, Integer> createChart(List<String[]> rows) throws Exception {
        Map<String, Integer> chart = new HashMap<>();
        for (String[] columns: rows) {
            String artistName = columns[2];
            //if the map contains the artistsName
            if (chart.containsKey(artistName)) {
                int count = chart.get(artistName);
                chart.put(artistName, count + 1);
            } else {
                chart.put(artistName, 1);
            }
        }
        return chart;
    }


    //get artist ratings
    private static List<ArtistRating> getArtistRatings(Map<String, Integer> chart) {
        List<ArtistRating> artistRatings = new LinkedList<>();
        for (String artistName : chart.keySet()) {
            artistRatings.add(new ArtistRating(artistName, chart.get(artistName)));
        }
        artistRatings.sort(new ArtistRatingComparator());
        return artistRatings;
    }

    //display artist ratings
    private static void displayArtistRatings(List<ArtistRating> artistRatings) {
        for (ArtistRating artistRating : artistRatings) {
            System.out.printf("%-35s %d%n", artistRating.getArtistName(), artistRating.getRating());
        }
    }

    // get sorted artists
    private static Set<String> getSortedArtists(Map<String, Integer> chart) {
        //sort artists names
        TreeSet<String> sortedArtistsNames =  new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        sortedArtistsNames.addAll(chart.keySet());
        return sortedArtistsNames;
    }

    //display artists in alphabetical order
    private static void displaySortedArtists(Set<String> sortedArtistsNames, Map<String, Integer> chart) {
        for (String artistName : sortedArtistsNames) {
            System.out.printf("%-35s %d%n", artistName, chart.get(artistName));
        }
    }
}

class ArtistRating {
    private String artistName;
    private int rating;

    ArtistRating(String artistName, int rating) {
        this.artistName = artistName.trim();
        this.rating = rating;
    }

    String getArtistName() {
        return artistName;
    }

    int getRating() {
        return rating;
    }
}

class ArtistRatingComparator implements Comparator<ArtistRating> {
    @Override
    public int compare(ArtistRating artistRating1, ArtistRating artistRating2) {
        // sort by rating first
        int result = -(artistRating1.getRating() - artistRating2.getRating());
        // then sort by name if rating is the same
        if (result == 0) {
            result = artistRating1.getArtistName().compareToIgnoreCase(artistRating2.getArtistName());
        }
        return result;
    }
}