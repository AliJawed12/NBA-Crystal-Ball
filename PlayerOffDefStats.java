/*
PlayerOffDefStats File
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlayerOffDefStats {

    // Class-level variable to hold the 2D array of offensive and defensive ratings
    private static String[][] offDefRatingArray;

    public PlayerOffDefStats(String urlIn) {
        fetchOffDefRatings(urlIn);
    }

    // Method to fetch offensive and defensive ratings and store them in a 2D array
    public static void fetchOffDefRatings(String url) {
        ArrayList<String[]> playerStatsList = new ArrayList<>(); // To store stats in a list
        Set<String> seenPlayers = new HashSet<>(); // To track players already added

        try {
            // Connect to the URL and parse the document
            Document document = Jsoup.connect(url).get();

            // Locate the stats table
            Element table = document.select("div#all_per_poss table.stats_table").first();
            if (table == null) {
                System.out.println("Table not found!");
                return;
            }

            // Select all rows in tbody
            Elements playerRows = table.select("tbody tr");

            // Iterate through each row and extract stats
            for (Element row : playerRows) {
                // Skip rows with the class 'partial_table'
                if (row.hasClass("partial_table")) {
                    continue;  // Skip this row
                }

                // Extract player name, team, offensive rating, and defensive rating
                String playerName = row.select("td[data-stat='name_display'] a").text();
                String teamAbbreviation = row.select("td[data-stat='team_name_abbr'] a").text();
                String offensiveRating = row.select("td[data-stat='off_rtg']").text();
                String defensiveRating = row.select("td[data-stat='def_rtg']").text();

                // Skip empty player names and duplicate players
                if (!playerName.isEmpty() && !seenPlayers.contains(playerName)) {
                    // Add the player stats to the list
                    playerStatsList.add(new String[]{playerName, teamAbbreviation, offensiveRating, defensiveRating});
                    seenPlayers.add(playerName); // Mark this player as added
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the List to a 2D array and store it in the class-level field
        offDefRatingArray = new String[playerStatsList.size()][4]; // 4 columns: name, team, offensive rating, defensive rating
        for (int i = 0; i < playerStatsList.size(); i++) {
            offDefRatingArray[i] = playerStatsList.get(i);
        }
    }

    // Getter method to access the offensive and defensive ratings 2D array
    public static String[][] getOffDefRatings() {
        return offDefRatingArray;
    }

    public static void main(String[] args) {
        final String url = "https://www.basketball-reference.com/leagues/NBA_2024_per_poss.html";
        PlayerOffDefStats odr = new PlayerOffDefStats(url);

        // Get the offensive and defensive ratings 2D array
        String[][] playerStats = PlayerOffDefStats.getOffDefRatings();

        // Check if playerStats is not null and has data
        if (playerStats != null && playerStats.length > 0) {
            // Loop through the 2D array and print the player stats
            for (String[] playerStat : playerStats) {
                System.out.printf("Player: %s, Team: %s, Offensive Rating: %s, Defensive Rating: %s%n",
                        playerStat[0], playerStat[1], playerStat[2], playerStat[3]);
            }
        } else {
            System.out.println("No offensive/defensive ratings found.");
        }
    }
}
