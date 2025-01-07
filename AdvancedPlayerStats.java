/*
AdvancedPlayerStats File
 */



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

public class AdvancedPlayerStats {

    // Class-level variable to hold the 2D array of advanced player stats
    private static String[][] advancedPlayerStatsArray;

    public AdvancedPlayerStats(String urlIn) {
        fetchAdvancedPlayerStats(urlIn);
    }

    // Method to fetch advanced player stats and store them in a 2D array
    public static void fetchAdvancedPlayerStats(String url) {
        ArrayList<String[]> playerStatsList = new ArrayList<>(); // To store stats in a list
        Set<String> seenPlayers = new HashSet<>(); // To track players already added

        try {
            // Connect to the URL and parse the document (no user-agent specified)
            Document document = Jsoup.connect(url).get();
            System.out.println("Document fetched successfully.");

            // Select rows with the player data (using tr[data-row] inside tbody)
            Elements playerRows = document.select("tbody tr");

            System.out.println("Number of player rows found: " + playerRows.size());

            // Iterate through each row and extract player name, team, True Shooting %, and awards
            for (Element row : playerRows) {
                // Extract player name (the link inside <td data-stat="name_display">)
                String playerName = row.select("td[data-stat='name_display'] a").text();

                // Extract team abbreviation (the link inside <td data-stat="team_name_abbr">)
                String teamAbbreviation = row.select("td[data-stat='team_name_abbr'] a").text();

                // Extract True Shooting % (TS%) from the correct column
                String trueShooting = row.select("td[data-stat='ts_pct']").text();

                // Extract awards (if available)
                String awards = row.select("td[data-stat='awards']").text().trim();

                // Handle the case where team name is "TOT" (representing multiple teams)
                if (teamAbbreviation.equals("TOT")) {
                    teamAbbreviation = "N/A";
                }

                // Calculate award points
                int totalPoints = calculateAwardPoints(awards);

                // Check if the player has already been added
                if (!seenPlayers.contains(playerName)) {
                    // Add the player stats to the list if they haven't been added yet
                    playerStatsList.add(new String[]{playerName, teamAbbreviation, trueShooting, String.valueOf(totalPoints)});
                    seenPlayers.add(playerName); // Mark this player as added
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the List to a 2D array and store it in the class-level field
        if (playerStatsList.size() > 0) {
            advancedPlayerStatsArray = new String[playerStatsList.size()][4]; // 4 columns: name, team, TS%, award points
            for (int i = 0; i < playerStatsList.size(); i++) {
                advancedPlayerStatsArray[i] = playerStatsList.get(i);
            }
        } else {
            System.out.println("No player data found.");
        }
    }

    // Method to calculate award points based on the new rules
    private static int calculateAwardPoints(String awards) {
        int points = 0;

        // If awards string is empty or null, return 0 points
        if (awards == null || awards.isEmpty()) {
            return points;
        }

        // Split the awards string by commas to handle multiple awards
        String[] awardList = awards.split(",");

        for (String award : awardList) {
            award = award.trim(); // Remove leading/trailing spaces

            // Check for MVP awards
            if (award.contains("MVP")) {
                String[] awardParts = award.split("-");
                if (awardParts.length == 2) {  // Make sure the award is in the correct format (e.g., MVP-1)
                    String rank = awardParts[1];
                    if (rank.equals("1")) {
                        points += 5; // MVP-1 gets 6 points
                    } else if (rank.equals("2")) {
                        points += 4; // MVP-2 gets 4 points
                    } else if (rank.equals("3")) {
                        points += 3; // MVP-3 gets 3 points
                    }
                }
            }

            // Check for DPOY awards
            else if (award.contains("DPOY")) {
                String[] awardParts = award.split("-");
                if (awardParts.length == 2) {  // Make sure the award is in the correct format (e.g., DPOY-1)
                    String rank = awardParts[1];
                    int rankValue = Integer.parseInt(rank);
                    if (rankValue >= 1 && rankValue <= 3) {
                        points += 5; // DPOY-1, DPOY-2, DPOY-3 all get 6 points
                    } else if (rankValue == 4) {
                        points += 4; // DPOY-4 gets 5 points
                    } else if (rankValue == 5) {
                        points += 3; // DPOY-5 gets 4 points
                    } else if (rankValue == 6) {
                        points += 2; // DPOY-6 gets 3 points
                    } else if (rankValue == 7) {
                        points += 1; // DPOY-7 gets 2 points
                    }
                }
            }

            // Check for All-Star awards
            else if (award.equals("AS")) {
                points += 5; // All-Star gets 5 points
            }

            // Check for All-NBA teams
            else if (award.contains("NBA")) {
                String[] awardParts = award.split("-");
                if (awardParts.length == 2) {  // Make sure the award is in the correct format (e.g., NBA1)
                    String rank = awardParts[1];
                    if (rank.equals("1")) {
                        points += 3; // NBA1 gets 3 points
                    } else if (rank.equals("2")) {
                        points += 2; // NBA2 gets 2 points
                    } else if (rank.equals("3")) {
                        points += 1; // NBA3 gets 1 point
                    }
                }
            }
        }

        return points;
    }


    // Getter method to access the advanced player stats 2D array
    public static String[][] getAdvancedPlayerStats() {
        return advancedPlayerStatsArray;
    }

    public static void main(String[] args) {
        final String url = "https://www.basketball-reference.com/leagues/NBA_2024_advanced.html";
        AdvancedPlayerStats aps = new AdvancedPlayerStats(url);

        // Get the advanced player stats 2D array
        String[][] playerStats = AdvancedPlayerStats.getAdvancedPlayerStats();

        // Check if playerStats is not null and has data
        if (playerStats != null && playerStats.length > 0) {
            // Loop through the 2D array and print the player stats
            for (String[] playerStat : playerStats) {
                System.out.printf("Player: %s, Team: %s, TS%%: %s, Award Points: %s%n",
                        playerStat[0], playerStat[1], playerStat[2], playerStat[3]);
            }
        } else {
            System.out.println("No advanced player stats found.");
        }
    }
}
