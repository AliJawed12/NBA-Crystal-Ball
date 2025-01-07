/*
CPS 240 Group Project
Ali Jawed, Brevin Ford, Justin Deines, Owen Robinson

BasicPlayerStats File
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicPlayerStats {

    // Class-level variable to hold the 2D array of player stats
    private static String[][] playerBasicStatsArray;

    public BasicPlayerStats(String urlIn) {
        fetchPlayerStats(urlIn);
    }

    // Method to fetch player stats and store them in a 2D array
    public static void fetchPlayerStats(String url) {
        List<String[]> playerStatsList = new ArrayList<>(); // To store stats in a list
        Set<String> seenPlayers = new HashSet<>(); // To track players already added

        try {
            // Connect to the URL and parse the document
            Document document = Jsoup.connect(url).get();

            // Select the rows in tbody, excluding those with "thead"
            Elements rows = document.select("tbody tr:not(.thead)");

            // Full season stats map for traded players
            List<String[]> fullSeasonStats = new ArrayList<>(); // To store full-season stats

            // First pass: Collect full-season stats for traded players
            for (Element row : rows) {
                String playerName = row.select("td[data-stat=name_display]").text();
                String pointsPerGame = row.select("td[data-stat=pts_per_g]").text();
                String rebounds = row.select("td[data-stat=trb_per_g]").text();
                String assists = row.select("td[data-stat=ast_per_g]").text();
                String turnovers = row.select("td[data-stat=tov_per_g]").text();
                String blocks = row.select("td[data-stat=blk_per_g]").text();
                String steals = row.select("td[data-stat=stl_per_g]").text();
                String minutesPerGame = row.select("td[data-stat=mp_per_g]").text(); // Extract minutes per game
                String teamAbbreviation = row.select("td[data-stat=team_name_abbr]").text();

                // Check if it's a full-season dataset (2TM, 3TM, etc.)
                if (teamAbbreviation.endsWith("TM")) {
                    fullSeasonStats.add(new String[]{playerName, pointsPerGame, rebounds, assists, turnovers, blocks, steals, minutesPerGame}); // Store full-season stats with minutes
                }
            }

            // Second pass: Collect stats for all players
            for (Element row : rows) {
                String playerName = row.select("td[data-stat=name_display]").text();
                String pointsPerGame = row.select("td[data-stat=pts_per_g]").text();
                String rebounds = row.select("td[data-stat=trb_per_g]").text();
                String assists = row.select("td[data-stat=ast_per_g]").text();
                String turnovers = row.select("td[data-stat=tov_per_g]").text();
                String blocks = row.select("td[data-stat=blk_per_g]").text();
                String steals = row.select("td[data-stat=stl_per_g]").text();
                String minutesPerGame = row.select("td[data-stat=mp_per_g]").text(); // Extract minutes per game
                String teamAbbreviation = row.select("td[data-stat=team_name_abbr]").text();

                // If the player is a traded player, add their full-season stats
                for (String[] fullStat : fullSeasonStats) {
                    if (fullStat[0].equals(playerName) && !seenPlayers.contains(playerName)) {
                        playerStatsList.add(fullStat); // Add full season stats for traded players
                        seenPlayers.add(playerName); // Mark the player as added
                        break;
                    }
                }

                // Otherwise, add the current season stats for non-traded players
                if (!teamAbbreviation.endsWith("TM") && !seenPlayers.contains(playerName)) {
                    playerStatsList.add(new String[]{playerName, pointsPerGame, rebounds, assists, turnovers, blocks, steals, minutesPerGame}); // Include minutes per game
                    seenPlayers.add(playerName); // Mark the player as added
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the List to a 2D array and store it in the class-level field
        playerBasicStatsArray = new String[playerStatsList.size()][8]; // 8 columns: name + 7 stats
        for (int i = 0; i < playerStatsList.size(); i++) {
            playerBasicStatsArray[i] = playerStatsList.get(i);
        }
    }

    // Getter method to access the player stats 2D array
    public static String[][] getPlayerStats() {
        return playerBasicStatsArray;
    }

    public static void main (String[] args) {
        final String url = "https://www.basketball-reference.com/leagues/NBA_2024_per_game.html";
        BasicPlayerStats bps = new BasicPlayerStats(url);

        // Get the player stats 2D array
        String[][] playerStats = BasicPlayerStats.getPlayerStats();

        // Check if playerStats is not null and has data
        if (playerStats != null && playerStats.length > 0) {
            // Loop through the 2D array and print the player stats
            for (String[] playerStat : playerStats) {
                System.out.printf("Player: %s, PPG: %s, RPG: %s, APG: %s, TOV: %s, BLK: %s, STL: %s, MPG: %s%n",
                        playerStat[0], playerStat[1], playerStat[2], playerStat[3], playerStat[4], playerStat[5], playerStat[6], playerStat[7]);
            }
        } else {
            System.out.println("No player stats found.");
        }
    }
}
