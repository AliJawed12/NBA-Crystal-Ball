/*
CPS 240 Group Project
Ali Jawed, Brevin Ford, Justin Deines, Owen Robinson

Player File
 */


import java.io.*;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;

public class Player {

    private String name;
    private String team;
    private double ppg;
    private double apg;
    private double rpg;
    private double tov;
    private double spg;
    private double blk;
    private double playerScore; // Sum of ppg, rpg, assists
    private double offRating;
    private double defRating;
    private double trueShooting;
    private double mpg;
    private int playerAwards;

    public Player() {
        name = "xyz";
        team = "xyz";
        ppg = 0.0;
        apg = 0.0;
        rpg = 0.0;
        tov = 0.0;
        spg = 0.0;
        blk = 0.0;
        offRating = 0.0;
        defRating = 0.0;
        trueShooting = 0.0;
        playerScore = 0.0;
        mpg = 0.0;
        playerAwards = 0;
    }

    public Player(String nameIn, String teamIn, double ppgIn, double apgIn, double rpgIn,
                  double tovIn, double spgIn, double blkIn, double offRatingIn,
                  double defRatingIn, double trueShootingIn, double mpgIn, int playerAwardsIn) {
        name = nameIn;
        team = teamIn;
        ppg = ppgIn;
        apg = apgIn;
        rpg = rpgIn;
        tov = tovIn;
        spg = spgIn;
        blk = blkIn;
        offRating = offRatingIn;
        defRating = defRatingIn;
        trueShooting = trueShootingIn;
        mpg = mpgIn;

        // Calculate playerScore as the sum of ppg, rpg, and apg
        playerScore = Math.round((ppg + apg + spg) * 100) /100;

        DecimalFormat df = new DecimalFormat("#.##");
        playerScore = Double.parseDouble(df.format(playerScore));
        playerAwards = playerAwardsIn;

    }


    // Getter and Setter methods for each field
    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Team
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    // Points per Game (ppg)
    public double getPpg() {
        return ppg;
    }

    public void setPpg(double ppg) {
        this.ppg = ppg;
        updatePlayerScore(); // Update playerScore whenever ppg changes
    }

    // Assists per Game (apg)
    public double getApg() {
        return apg;
    }

    public void setApg(double apg) {
        this.apg = apg;
        updatePlayerScore(); // Update playerScore whenever apg changes
    }

    // Rebounds per Game (rpg)
    public double getRpg() {
        return rpg;
    }

    public void setRpg(double rpg) {
        this.rpg = rpg;
        updatePlayerScore(); // Update playerScore whenever rpg changes
    }

    // Turnovers (tov)
    public double getTov() {
        return tov;
    }

    public void setTov(double tov) {
        this.tov = tov;
    }

    // Steals per Game (spg)
    public double getSpg() {
        return spg;
    }

    public void setSpg(double spg) {
        this.spg = spg;
    }

    // Blocks per Game (blk)
    public double getBlk() {
        return blk;
    }

    public void setBlk(double blk) {
        this.blk = blk;
    }

    public void setPlayerAwards(int playerAwardsIn) {
        playerAwards = playerAwardsIn;
    }

    public int getPlayerAwards() {
        return playerAwards;
    }

    // Player Score (sum of ppg, rpg, apg)
    public double getPlayerScore() {
        return playerScore;
    }

    // Offense Rating
    public double getOffRating() {
        return offRating;
    }

    public void setOffRating(double offRating) {
        this.offRating = offRating;
    }

    // Defense Rating
    public double getDefRating() {
        return defRating;
    }

    public void setDefRating(double defRating) {
        this.defRating = defRating;
    }

    // True Shooting
    public double getTrueShooting() {
        return trueShooting;
    }

    public void setTrueShooting(double trueShooting) {
        this.trueShooting = trueShooting;
    }

    // Helper method to update playerScore whenever any of ppg, rpg, or apg changes
    public void updatePlayerScore() {
        playerScore = ppg + rpg + apg;

        DecimalFormat df = new DecimalFormat("#.##");
        playerScore = Double.parseDouble(df.format(playerScore));

    }

    public void setMpg(double mpgIn) {
        mpg = mpgIn;
    }

    public double getMpg() {
        return mpg;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", ppg=" + ppg +
                ", apg=" + apg +
                ", rpg=" + rpg +
                ", tov=" + tov +
                ", spg=" + spg +
                ", blk=" + blk +
                ", playerScore=" + playerScore +
                ", offRating=" + offRating +
                ", defRating=" + defRating +
                ", trueShooting=" + trueShooting +
                ", mpg=" + mpg +
                ", awardScore=" + playerAwards +
                '}';
    }



    // This method grabs all the data and turns into object
    // return type should be ArrayList<Player>
    public ArrayList<Player> compileData() {

        // Main List which stores all players
        ArrayList<Player> playerArrayList = new ArrayList<>();
        String tradedPlayerList = "C:\\Users\\Alija\\Desktop\\all_nba_trades_23_24.txt";
        // Helper Variables
        HashSet<String> playersChecked = new HashSet<>();
        ArrayList<String> basicAdvancedIncompletePlayers = new ArrayList<>();
        ArrayList<Player> basicOffDefIncompletePlayers = new ArrayList<>();
        HashSet<String> offDefHelper = new HashSet<>();
        ArrayList<Player> playersWNoTeam = new ArrayList<>();
        int offDefPlayerFound = 0;
        String[][] tradedPlayers = readPlayerTeams(tradedPlayerList);

        // Key Strings: CHANGE THESE DEPENDING ON THE YEAR YOUR LOOKING AT
        final String url1 = "https://www.basketball-reference.com/leagues/NBA_2025_per_game.html";
        BasicPlayerStats bps = new BasicPlayerStats(url1);
        final String url2 = "https://www.basketball-reference.com/leagues/NBA_2025_advanced.html";
        AdvancedPlayerStats aps = new AdvancedPlayerStats(url2);
        final String url3 = "https://www.basketball-reference.com/leagues/NBA_2025_per_poss.html";
        PlayerOffDefStats odr = new PlayerOffDefStats(url3);

        // Call the respective methods for each type of method
        String[][] basicPlayerStatsArray = BasicPlayerStats.getPlayerStats();
        String[][] advancedPlayerStatsArray = AdvancedPlayerStats.getAdvancedPlayerStats();
        String[][] playerOffDefStatsArray = PlayerOffDefStats.getOffDefRatings();

        int largestLength = 0;

        // Branch to grab the largest index, prevents arrayOutOfBoundsException and unneccesary loss of data
        if ((basicPlayerStatsArray.length >= advancedPlayerStatsArray.length) && (basicPlayerStatsArray.length >= playerOffDefStatsArray.length)) {
            largestLength = basicPlayerStatsArray.length;
            System.out.println("Largest is basicPlayerStatsArray: " + largestLength);
        }
        else if ((advancedPlayerStatsArray.length >= basicPlayerStatsArray.length) && (advancedPlayerStatsArray.length >= playerOffDefStatsArray.length)) {
            largestLength = advancedPlayerStatsArray.length;
            System.out.println("Largest is advancedPlayerStatsArray: " + largestLength);
        }
        else {
            largestLength = playerOffDefStatsArray.length;
            System.out.println("Largest is playerOffDefStatsArray: " + largestLength);
        }


        // Main outer loop, traverse until largest value reached
        for (int i = 0; i < largestLength - 1; i++) {

            // Create a temporary Player object
            Player player = new Player();

            // Data already prevents duplicate players, but this double checks, and prevents storing same player twice
            if (playersChecked.contains(basicPlayerStatsArray[i][0])) {
                // If player is already stored then go to next iteration
                continue;
            }
            else {
                // Add the player to the set allowing functionality of if branch if same player encountered again
                playersChecked.add(basicPlayerStatsArray[i][0]);

                // Set the players basic stats
                player.setName(basicPlayerStatsArray[i][0]);

                player.setPpg(parseDoubleOrDefault(basicPlayerStatsArray[i][1]));
                player.setRpg(parseDoubleOrDefault(basicPlayerStatsArray[i][2]));
                player.setApg(parseDoubleOrDefault(basicPlayerStatsArray[i][3]));
                player.setTov(parseDoubleOrDefault(basicPlayerStatsArray[i][4]));
                player.setBlk(parseDoubleOrDefault(basicPlayerStatsArray[i][5]));
                player.setSpg(parseDoubleOrDefault(basicPlayerStatsArray[i][6]));
                player.setMpg(parseDoubleOrDefault(basicPlayerStatsArray[i][7]));

                player.updatePlayerScore();

                // Now loop through advancedPlayerStatsArray to set true shooting
                for (int j = 0; j < advancedPlayerStatsArray.length - 1; j++) {

                    if (advancedPlayerStatsArray[j][0].equals(null)) {
                        continue;
                    }

                    //System.out.println("SEARCHING FOR PLAYER...");
                    // if the name in array equals the current player object
                    if (advancedPlayerStatsArray[j][0].equals(player.getName())) {
                        // if the index with true shooting is empty, then store in incompletePlayersList
                        if (advancedPlayerStatsArray[j][2].isEmpty()) {
                            basicAdvancedIncompletePlayers.add(advancedPlayerStatsArray[j][0]);
                        }
                        else {
                            // Otherwise set the true shooting
                            player.setTrueShooting(Double.parseDouble(advancedPlayerStatsArray[j][2]));
                            player.setPlayerAwards(Integer.parseInt(advancedPlayerStatsArray[j][3])); // LINE MAY CAUSE ERROR
                            //System.out.println("Player Found and Stored from AdvancedPlayerStatsArray");
                        }
                    }
                    else {
                        /*
                        System.out.println(advancedPlayerStatsArray[j][0] + " Does not equal: " + player.getName());
                        System.out.println("Player Not Found");

                         */
                    }




                }

                /*
                System.out.println("STORED");
                System.out.println("Uncomplete Players: " + basicAdvancedIncompletePlayers.size());

                System.out.println("SIZE OF PLAYER ARRAY LIST: " + playerArrayList.size());

                 */

                // Now loop through playerOffDefStatsArray
                for (int x = 0; x < playerOffDefStatsArray.length - 1; x++) {

                    // boolean offDefNotNull = true;


                    // if the array has a value in which there is null, then go to next iteration
                    if (playerOffDefStatsArray[x][0].equals(null)) {
                        continue;
                    }

                    // If the name in the array equals the current player object name then...
                    if (playerOffDefStatsArray[x][0].equals(player.getName())) {
                      //  System.out.println("Player Found");

                        // If offRating in array is empty or null then add to offDefincompletePlayersArray
                        // and add name to offDefHelperSet, lastly go to next iteration
                        if (playerOffDefStatsArray[x][2].isEmpty() || playerOffDefStatsArray[x][2] == null) {
                            basicOffDefIncompletePlayers.add(player);
                            offDefHelper.add(player.getName());
                            continue;
                        }
                        // if defRating in array is empty or null, then if offDefHelper set doesn't alrready have player name
                        // add it, lastly go to next iteration
                        if (playerOffDefStatsArray[x][3].isEmpty() || playerOffDefStatsArray[x][3] == null) {
                            if (!(offDefHelper.contains(player.getName()))) {
                                basicOffDefIncompletePlayers.add(player);
                            }
                            continue;
                        }


                        // index 2 of playerOffDefStats stores team name, if empty or null, then set team
                        // and add player object to playersWNoTeam list
                        // otherwise grab team name and set to player object
                        if (playerOffDefStatsArray[x][1].isEmpty() || playerOffDefStatsArray[x][1] == null) {
                            player.setTeam("");
                            playersWNoTeam.add(player);
                        }
                        else {
                            player.setTeam(playerOffDefStatsArray[x][1]);
                        }

                        // grab and setOffandDefRatings
                        player.setOffRating(Double.parseDouble(playerOffDefStatsArray[x][2]));
                        player.setDefRating(Double.parseDouble(playerOffDefStatsArray[x][3]));

                        // error checking should be around less than 600 if works properyly
                        offDefPlayerFound++;

                    }



                }


            }


            // Loop through tradedPlayers
            for (int n = 0; n < tradedPlayers.length - 1; n++) {
                //variable for checking
                boolean playerFound = false;

                // break out the loop so don't have to iterate over null variables
                if (tradedPlayers[n][0] == null) {
                    break;
                }

                // if playerFound break the loop, more efficeint traversing
                if (playerFound) {
                    break;
                }

                // if null or empty go to next iteration
                if (tradedPlayers[n][0] == null || tradedPlayers[n][0].isEmpty()) {
                    continue;
                }
                else {

                    // normalizeString is called because some names have non-english letters
                    // java would see o in spanish for example different as o in english
                    // Ex: Dennis Schröder is not the same as Dennis Schroder
                    //System.out.println("SEARCHING TRADED PLAYERS...");
                    if (normalizeString(tradedPlayers[n][0]).equals(normalizeString(player.getName()))) {
                        //System.out.println("The names are the same!");
                        player.setTeam(tradedPlayers[n][1]);

                        // remove the player from the playerWNoTeam array
                        if (playersWNoTeam.contains(player)) {
                            playersWNoTeam.remove(player);
                        }
                    } else {
                        //System.out.println("The names are different.");
                        //System.out.println(normalizeString(tradedPlayers[n][0]) + " DONT MATCH " + normalizeString(player.getName()));
                    }



                }

            }


            // Finally add the completed player to the playerArrayList
            playerArrayList.add(player);
        }





        /*
        for (int i = 0; i < playerArrayList.size() - 1; i++) {

            Player player = playerArrayList.get(i);

            if (!(player.getTeam().length() > 0)) {
                System.out.println(player);
            }

        }

         */

        System.out.println("_______________________________________________________________________________________");


        System.out.println("REMOVING THESE PLAYERS DUE TO INCOMPLETE DATA: Team");
        for (int i = 0; i < playerArrayList.size() - 1; i++) {

            Player player = playerArrayList.get(i);
            if (!(player.getTeam().length() > 0)) {
                System.out.println("REMOVING: " + player);
                playerArrayList.remove(i);
            }

        }


        System.out.println("Players with no team:" + playersWNoTeam.size());

        double dataValidityPercent = (double) (playerArrayList.size() / largestLength) * 100;
        System.out.println("VALID DATA AFTER DATA CLEANING: " + playerArrayList.size() + " out of " + largestLength);


        return playerArrayList;


    }


    private String[][] readPlayerTeams(String fileNameIn) {
        // Setting playerTeamArray to hold at most 500 nba trades in a season: THIS IS IMPOSSIBLE
        String[][] playerTeamArray = new String[500][2];
        File playerTeamFile = new File(fileNameIn);

        try {
            BufferedReader playerTeamBufferedReader = new BufferedReader(new FileReader(fileNameIn));

            String startPhrase = "Player Name,Team Abbreviation";
            boolean startReading = false;
            int storingPlayerCount = 0;


            String lineIn;
            while ((lineIn = playerTeamBufferedReader.readLine()) != null) {

                if (startReading) {
                    String[] splitLine = lineIn.split(",");
                    playerTeamArray[storingPlayerCount][0] = splitLine[0];
                    playerTeamArray[storingPlayerCount][1] = splitLine[1];
                    storingPlayerCount++;
                }

                // Check for the target phrase to start reading
                if (lineIn.trim().equals(startPhrase)) {
                    startReading = true; // Enable reading for subsequent lines
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return playerTeamArray;
    }

    public static String normalizeString(String name) {
        // Normalize the string to remove accents and special characters
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        // Remove diacritical marks (accents)
        normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
        return normalized;
    }

    public double parseDoubleOrDefault(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + value); // Optional: log invalid value
            }
        }
        return 0.0; // Default value when empty or invalid
    }
}
