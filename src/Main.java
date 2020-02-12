/* Class: CISC 3130
 * Section: TY9
 * EmplId: ****8149
 * Name: Elena Tarasova
 */

import java.io.File;
import java.util.*;

//Main class shows output data to the executive and VIP client
public class Main {

    private static final String FILE_NAME = "data/regional-global-weekly-2020-01-17--2020-01-24.csv";

    public static void main(String[] args) throws Exception {
        //create Map to store name of artists and how many times they appear in chart
        Map<String, Integer> chart = createChart(FILE_NAME);

        //display chart by appearance artist in chart
        displayOrderedChart(chart);

        //display chart by artist name
        displayArtistInAlphabeticalOrder(chart);

    }

    //create chart from file
    private static Map<String, Integer> createChart(String fileName) throws Exception {
        Scanner scanner = new Scanner(new File(fileName));
        String description = scanner.nextLine();
        String title = scanner.nextLine();

        Map<String, Integer> chart = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(",");
            String artistName = tokens[2].trim();

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


    //display ordered chart
    private static void displayOrderedChart(Map<String, Integer> chart) {
        List<ArtistRating> artistRatings = new LinkedList<>();
        for (String artistName : chart.keySet()) {
            artistRatings.add(new ArtistRating(artistName, chart.get(artistName)));
        }

        artistRatings.sort(new ArtistRatingComparator());

        for (ArtistRating artistRating : artistRatings) {
            System.out.printf("%-35s %d%n", artistRating.getArtistName(), artistRating.getRating());
        }
    }

    //display artists in alphabetical order
    public static void displayArtistInAlphabeticalOrder(Map<String, Integer> chart) {
        //get artists names
        Set<String> artistsNames = chart.keySet();

        //sort artists names
        TreeSet<String> sortedArtistsNames =  new TreeSet<>(artistsNames);

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