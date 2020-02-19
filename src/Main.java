/* Class: CISC 3130
 * Section: TY9
 * EmplId: ****8149
 * Name: Elena Tarasova
 */

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Main class shows output data to the executive and VIP client
public class Main {

    private static final String FILE_NAME = "data/regional-global-weekly-2020-01-17--2020-01-24.csv";

    public static void main(String[] args) throws Exception {

        //create Map to store name of artists and how many times they appear in chart
        Map<String, Integer> chart = createChart(readCSVFile(FILE_NAME));

        //create List to get artists ratings
        List<ArtistRating> artistRatings = getArtistRatings(chart);

        //sort chart by artist ratings
        artistRatings.sort(new ArtistRatingComparator());

        //write sorted chart in file
        writeCSVFile("output/Artist_Ratings.csv", artistRatings);

        //sort chart by artist name
        artistRatings.sort(new ArtistNameComparator());

        //write sorted chart in file
        writeCSVFile("output/Artist_Sorted.csv", artistRatings);

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

    //write artist ratings to file
    private static void writeCSVFile(String filename, List<ArtistRating> artistRatings) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeNext(new String[]{"Artist Name", "Rating"});
            writer.writeAll(artistRatingsToRows(artistRatings));
        }
    }

    //convert artist ratings to rows
    private static List<String[]> artistRatingsToRows(List<ArtistRating> artistRatings) {
        // alternative implementation in Java 8
        // return artistRatings.stream().map(ArtistRating::toRow).collect(Collectors.toList());
        List<String[]> rows = new ArrayList<>(artistRatings.size());
        for (ArtistRating artistRating : artistRatings) {
            rows.add(artistRating.toRow());
        }
        return rows;
    }

    //create chart from file
    private static Map<String, Integer> createChart(List<String[]> rows) throws Exception {
        Map<String, Integer> chart = new HashMap<>();
        for (String[] columns : rows) {
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
        return artistRatings;
    }

    //display artist ratings
    private static void displayArtistRatings(List<ArtistRating> artistRatings) {
        // alternative implementation in Java 8
        //artistRatings.forEach(System.out::println);
        for (ArtistRating artistRating : artistRatings) {
            System.out.println(artistRating);
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

    String[] toRow() {
        // alternative implementation in Java 8
        //return new String[]{artistName, Integer.toString(rating)};
        String[] row = new String[2];
        row[0] = artistName;
        row[1] = Integer.toString(rating);
        return row;
    }

    public String toString() {
        return String.format("%-35s %d%n", getArtistName(), getRating());
    }
}

class ArtistNameComparator implements Comparator<ArtistRating> {
    @Override
    public int compare(ArtistRating artistRating1, ArtistRating artistRating2) {
        // sort by name
        int result = artistRating1.getArtistName().compareToIgnoreCase(artistRating2.getArtistName());
        // then sort by rating if name is the same
        if (result == 0) {
            result = -(artistRating1.getRating() - artistRating2.getRating());
        }
        return result;
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