/*
CPS 240 Group Project
Ali Jawed, Brevin Ford, Justin Deines, Owen Robinson

TeamBasicStats FIle
 */



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TeamBasicStats {

    // Class-level variable to hold the 2D array of team stats
    private static String[][] teamStatsArray;

    public TeamBasicStats(String urlIn) {
        fetchTeamStats(urlIn);
    }

    // Method to fetch team stats and store them in a 2D array
    public static void fetchTeamStats(String url) {
        ArrayList<String[]> teamStatsList = new ArrayList<>(); // To store stats in a list
        Set<String> seenTeams = new HashSet<>(); // To track teams already added

        try {
            // Connect to the URL and parse the document
            Document document = Jsoup.connect(url).get();

            // Get all tbody tags from the document
            Elements tbodyElements = document.select("tbody");

            boolean tableFound = false;

            // Loop through each tbody to check for the correct table based on "sos" presence
            for (Element tbody : tbodyElements) {
                Elements rows = tbody.select("tr");

                // Check if this tbody has any row with a "sos" data-stat attribute
                if (rows.select("td[data-stat='sos']").size() > 0) {
                    tableFound = true;
                    System.out.println("Found the Advanced Stats Table");

                    // Loop through rows and extract relevant data
                    for (Element row : rows) {
                        String team = row.select("td[data-stat='team']").text();
                        String wins = row.select("td[data-stat='wins']").text();
                        String losses = row.select("td[data-stat='losses']").text();
                        String offRtg = row.select("td[data-stat='off_rtg']").text();
                        String defRtg = row.select("td[data-stat='def_rtg']").text();
                        String sos = row.select("td[data-stat='sos']").text();

                        // New stats
                        String netRtg = row.select("td[data-stat='net_rtg']").text();
                        String efgPct = row.select("td[data-stat='efg_pct']").text();
                        String tovPct = row.select("td[data-stat='tov_pct']").text();
                        String orbPct = row.select("td[data-stat='orb_pct']").text();
                        String drbPct = row.select("td[data-stat='drb_pct']").text();
                        String pace = row.select("td[data-stat='pace']").text();
                        String ftr = row.select("td[data-stat='fta_per_fga_pct']").text();
                        String oppFtr = row.select("td[data-stat='opp_ft_rate']").text(); // Opponent FTR

                        // Skip rows with empty team names and duplicates
                        if (!team.isEmpty() && !seenTeams.contains(team)) {
                            // Add the team stats to the list
                            teamStatsList.add(new String[]{
                                    team, wins, losses, offRtg, defRtg, sos, netRtg, efgPct, tovPct, orbPct, drbPct, pace, ftr, oppFtr
                            });
                            seenTeams.add(team); // Mark this team as added
                        }
                    }
                    break;
                }
            }

            if (!tableFound) {
                System.out.println("No table with 'sos' attribute found.");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the List to a 2D array and store it in the class-level field
        teamStatsArray = new String[teamStatsList.size()][14]; // 14 columns for all stats
        for (int i = 0; i < teamStatsList.size(); i++) {
            teamStatsArray[i] = teamStatsList.get(i);
        }
    }

    // Getter method to access the team stats 2D array
    public static String[][] getTeamStats() {
        return teamStatsArray;
    }

    public static void main(String[] args) {
        final String url = "https://www.basketball-reference.com/leagues/NBA_2024.html";
        TeamBasicStats tbs = new TeamBasicStats(url);

        // Get the team stats 2D array
        String[][] teamStats = TeamBasicStats.getTeamStats();

        // Check if teamStats is not null and has data
        if (teamStats != null && teamStats.length > 0) {
            // Loop through the 2D array and print the team stats
            for (String[] teamStat : teamStats) {
                System.out.printf(
                        "Team: %s, Wins: %s, Losses: %s, OffRtg: %s, DefRtg: %s, SOS: %s, NetRtg: %s, eFG%%: %s, TOV%%: %s, OREB%%: %s, DREB%%: %s, Pace: %s, FTR: %s, OppFTR: %s%n",
                        teamStat[0], teamStat[1], teamStat[2], teamStat[3], teamStat[4], teamStat[5],
                        teamStat[6], teamStat[7], teamStat[8], teamStat[9], teamStat[10], teamStat[11], teamStat[12], teamStat[13]
                );
            }
        } else {
            System.out.println("No team stats found.");
        }
    }
}
