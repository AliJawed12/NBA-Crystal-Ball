/*
GUI File
 */


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {


    private ArrayList<Player> players;

    @Override
    public void start(Stage primaryStage) {


        Player playerData = new Player();




        // Ensure players is populated
        players = playerData.compileData();

        if (players == null || players.isEmpty()) {
            System.err.println("No players found. Check Player.compileData() implementation.");
            return;
        }

        // Create a Pane
        BorderPane root = new BorderPane();

        // Load the image
        //Image backgroundImage = new Image("");

        // Set the background image
        //BackgroundImage bgImage = new BackgroundImage(backgroundImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,
                //new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,false,true));

        //root.setBackground(new Background(bgImage));

        // Create buttons
        Button statsButton = new Button("Stats");
        Button teamMatchupButton = new Button("Team Matchup");
        Button championshipPredictorButton = new Button("Championship Predictor");

        // Set button sizes
        statsButton.setPrefSize(200, 50);
        teamMatchupButton.setPrefSize(200, 50);
        championshipPredictorButton.setPrefSize(200, 50);

        // Create a VBox to stack the buttons vertically
        VBox vbox = new VBox(10);  // 10 is the spacing between buttons
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, teamMatchupButton, championshipPredictorButton);

        // Create the initial scene
        Scene mainScene = new Scene(vbox, 400, 300);

        // Set event handlers for each button
        statsButton.setOnAction(e -> openStatsScene(primaryStage));
        teamMatchupButton.setOnAction(e -> openTeamMatchupScene(primaryStage));
        championshipPredictorButton.setOnAction(e -> openChampionshipPredictorScene(primaryStage));

        // Set up the primary stage
        primaryStage.setTitle("NBA Crystal Ball");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Method to open Stats Scene
    private void openStatsScene(Stage stage) {

        ArrayList<Player> playerList = new ArrayList<>();
        System.out.println("SIZE: " + playerList.size());
        Player player = new Player();
        playerList = player.compileData();



        ArrayList<PlayersRanked> rankedPlayers = new ArrayList<>();
        for (Player p : playerList) {
            rankedPlayers.add(new PlayersRanked(p)); // Wrapping Player in PlayerRanked
        }

        Collections.sort(rankedPlayers);

        openStatsScene(stage, rankedPlayers);


    }

    private void updatePlayerListView(ListView<String> playerListView) {
        if (players == null || players.isEmpty()) {
            System.err.println("Player list is empty. Cannot update ListView.");
            return;
        }

        playerListView.getItems().clear(); // Clear existing items
        for (Player player : players) {
            // Customize how player details are displayed
            playerListView.getItems().add(
                    String.format("Name: %s, Team: %s, PPG: %.2f", player.getName(), player.getTeam(), player.getPpg())
            );
        }
    }

    // Method to open Team Match up Scene
    private void openTeamMatchupScene(Stage stage) {

        Label titleLabel = new Label("Player Over-Under");
        Label againstlabel = new Label("against");
        Label resultLabel = new Label();

        // Rectangles for over under
        Rectangle ppgRect = new Rectangle(50,50);
        Rectangle rpgRect = new Rectangle(50,50);
        Rectangle apgRect = new Rectangle(50, 50);
        ppgRect.setVisible(false);
        rpgRect.setVisible(false);
        apgRect.setVisible(false);
        Label ppgLabel = new Label("Points");
        Label rpgLabel = new Label("Rebounds");
        Label apgLabel = new Label("Assists");
        ppgLabel.setVisible(false);
        rpgLabel.setVisible(false);
        apgLabel.setVisible(false);




        ArrayList<Player> playerList = new ArrayList<>();
        System.out.println("SIZE: " + playerList.size());
        Player player = new Player();
        playerList = player.compileData();

        ArrayList<PlayersRanked> rankedPlayers = new ArrayList<>();
        for (Player p : playerList) {
            rankedPlayers.add(new PlayersRanked(p)); // Wrapping Player in PlayerRanked
        }

        Collections.sort(rankedPlayers);

        // Create team combo box

        ArrayList<Team> teamList = new ArrayList<>();
        ArrayList<Team> tempTeamList = new ArrayList<>();
        Team team = new Team();
        teamList = team.compileData();
        tempTeamList = teamList;

        ArrayList<TeamsRanked> teamsRanked = new ArrayList<>();
        for (Team t : teamList) {
            teamsRanked.add(new TeamsRanked(t));
        }

        Collections.sort(teamsRanked);

        String[] nbaTeams = new String[teamList.size()];
        for (int i = 0; i < nbaTeams.length; i++) {
            Team tempTeam = teamList.get(i);
            nbaTeams[i] = tempTeam.getTeamName();
        }

        // Create a combo box
        ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(nbaTeams));

        TextField enteredPlayer = new TextField();
        enteredPlayer.setPromptText("Enter a Player");

        Button estimateButton = new Button("Estimate");

        estimateButton.setOnAction(e -> {
            if ((combo_box.getValue() == null) || enteredPlayer.getLength() <= 0) {
                resultLabel.setText("PLEASE ENTER ALL PARAMETERS");
            }
            else {
                resultLabel.setText("");

                boolean found = false;

                for (PlayersRanked pr : rankedPlayers) {
                    Player player1 = pr.getPlayer();

                    String normalizedPlayer1Name = Normalizer.normalize(player1.getName(), Normalizer.Form.NFD)
                            .replaceAll("\\p{M}", "") // Remove diacritical marks (accents)
                            .replaceAll("[^a-zA-Z ]", "") // Remove non-English characters but keep spaces
                            .toLowerCase();


                    if (enteredPlayer.getText().toLowerCase().equals(normalizedPlayer1Name)) {
                        found = true;
                        resultLabel.setText("PLAYER FOUND");
                        break;

                    }

                }

                String teamName = (String) combo_box.getValue();

                boolean under = overUnder(teamName, teamsRanked);

                if (under) {
                    ppgRect.setVisible(true);
                    ppgRect.setFill(Color.RED);

                    rpgRect.setVisible(true);
                    rpgRect.setFill(Color.RED);

                    apgRect.setVisible(true);
                    apgRect.setFill(Color.RED);

                    ppgLabel.setVisible(true);
                    rpgLabel.setVisible(true);
                    apgLabel.setVisible(true);

                }
                else {

                    ppgRect.setVisible(true);
                    ppgRect.setFill(Color.GREEN);

                    rpgRect.setVisible(true);
                    rpgRect.setFill(Color.GREEN);

                    apgRect.setVisible(true);
                    apgRect.setFill(Color.GREEN);

                    ppgLabel.setVisible(true);
                    rpgLabel.setVisible(true);
                    apgLabel.setVisible(true);
                }



                if (!found) {
                    resultLabel.setText("PLAYER NOT FOUND");

                }

            }
        });


        VBox pointsVBox = new VBox(10, ppgLabel, ppgRect);
        VBox reboundsVBox = new VBox(10, rpgLabel, rpgRect);
        VBox assistsVBox = new VBox(10, apgLabel, apgRect);

        HBox overUnderResults = new HBox(20, pointsVBox, reboundsVBox, assistsVBox);
        overUnderResults.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> stage.setScene(createMainScene(stage)));

        HBox overUndersPref = new HBox(20, enteredPlayer, againstlabel, combo_box, estimateButton);
        overUndersPref.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, backButton,titleLabel, overUndersPref, resultLabel, overUnderResults);
        mainLayout.setAlignment(Pos.CENTER);

        Scene teamMatchupScene = new Scene(mainLayout, 800, 600);
        stage.setScene(teamMatchupScene);
        stage.setTitle("Over Unders");
    }

    // Method to open Championship Predictor Scene
    private void openChampionshipPredictorScene(Stage stage) {



        // Get the list of teams and ensure they are compiled and sorted
        ArrayList<Team> teamList = new ArrayList<>();
        Team team = new Team();
        teamList = team.compileData();
        System.out.println("Team List Size: " + teamList.size());

        // Add teams to TeamsRanked list and sort them
        ArrayList<TeamsRanked> teamsRanked = new ArrayList<>();
        for (Team t : teamList) {
            teamsRanked.add(new TeamsRanked(t));
        }
        Collections.sort(teamsRanked);

        // Now calculate probabilities
        String[][] teamsWithProbabilities = calculateProbabilities(teamList);

        // Sort teams based on probabilities
        Arrays.sort(teamsWithProbabilities, (team1, team2) -> {
            double prob1 = Double.parseDouble(team1[1].replace("%", ""));
            double prob2 = Double.parseDouble(team2[1].replace("%", ""));
            return Double.compare(prob2, prob1);  // Sort descending
        });


        System.out.println("Teams and Probabilities:");
        for (String[] teamRow : teamsWithProbabilities) {
            System.out.printf("Team: %-25s Probability: %s\n", teamRow[0], teamRow[1]);
        }






        // Back button
        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> stage.setScene(createMainScene(stage)));

        // Create shapes for the championship predictor
        // Team 1 components
        Circle team2Logo = new Circle(50, Color.LIGHTGRAY);
        Text team1Name = new Text(teamsWithProbabilities[1][0] + "\n" + teamsWithProbabilities[1][1] + " Chance of Winning");
        System.out.println("Probalbity: " + teamsWithProbabilities[1][1]);
        Rectangle team1Bar = new Rectangle(100, 200, Color.SILVER);

        // Team 2 components
        Circle team1Logo = new Circle(50, Color.LIGHTGRAY);
        Text team2Name = new Text(teamsWithProbabilities[0][0] + "\n" + teamsWithProbabilities[0][1] + " Chance of Winning");
        Rectangle team2Bar = new Rectangle(100, 300, Color.SILVER); // Taller rectangle

        // Team 3 components
        Circle team3Logo = new Circle(50, Color.LIGHTGRAY);
        Text team3Name = new Text(teamsWithProbabilities[2][0] + "\n" + teamsWithProbabilities[2][1] + " Chance of Winning");
        Rectangle team3Bar = new Rectangle(100, 150, Color.SILVER);




        Image image1 = fetchTeamLogo(teamsWithProbabilities[1][0]);
        ImagePattern imagePattern1 = new ImagePattern(image1);
        team1Logo.setFill(imagePattern1);



        Image image2 = fetchTeamLogo(teamsWithProbabilities[0][0]);
        ImagePattern imagePattern2 = new ImagePattern(image2);
        team2Logo.setFill(imagePattern2);


        Image image3 = fetchTeamLogo(teamsWithProbabilities[2][0]);
        ImagePattern imagePattern3 = new ImagePattern(image3);
        team3Logo.setFill(imagePattern3);



        // Layout for each team (vertical arrangement of logo, bar, and text)
        VBox team1Layout = new VBox(10, team1Logo, team1Bar, team1Name);
        team1Layout.setAlignment(Pos.BOTTOM_CENTER);

        VBox team2Layout = new VBox(10, team2Logo, team2Bar, team2Name);
        team2Layout.setAlignment(Pos.BOTTOM_CENTER);

        VBox team3Layout = new VBox(10, team3Logo, team3Bar, team3Name);
        team3Layout.setAlignment(Pos.BOTTOM_CENTER);

        // Horizontal layout for all teams
        HBox teamsLayout = new HBox(20, team1Layout, team2Layout, team3Layout);
        teamsLayout.setAlignment(Pos.CENTER);

        // Main layout
        VBox mainLayout = new VBox(30, backButton, teamsLayout);
        mainLayout.setAlignment(Pos.CENTER);

        // Create and set the scene
        Scene championshipPredictorScene = new Scene(mainLayout, 800, 600);
        stage.setScene(championshipPredictorScene);
        stage.setTitle("Championship Odds");
    }

    // Method to create the main menu scene
    private Scene createMainScene(Stage stage) {
        Button statsButton = new Button("Stats");
        Button teamMatchupButton = new Button("Team Matchup");
        Button championshipPredictorButton = new Button("Championship Predictor");

        statsButton.setPrefSize(200, 50);
        teamMatchupButton.setPrefSize(200, 50);
        championshipPredictorButton.setPrefSize(200, 50);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, teamMatchupButton, championshipPredictorButton);

        statsButton.setOnAction(e -> openStatsScene(stage));
        teamMatchupButton.setOnAction(e -> openTeamMatchupScene(stage));
        championshipPredictorButton.setOnAction(e -> openChampionshipPredictorScene(stage));

        return new Scene(vbox, 400, 300);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Image fetchTeamLogo(String teamNameIn) {
        HashMap<String, Image> teamLogo = new HashMap<>();

        // Inserting by nba divsions

        teamLogo.put("Boston Celtics", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/8/8f/Boston_Celtics.svg/1200px-Boston_Celtics.svg.png"));
        teamLogo.put("Brooklyn Nets", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/4/40/Brooklyn_Nets_primary_icon_logo_2024.svg/1200px-Brooklyn_Nets_primary_icon_logo_2024.svg.png"));
        teamLogo.put("New York Knicks", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/2/25/New_York_Knicks_logo.svg/360px-New_York_Knicks_logo.svg.png"));
        teamLogo.put("Philadelphia 76ers", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/0/0e/Philadelphia_76ers_logo.svg/1200px-Philadelphia_76ers_logo.svg.png"));
        teamLogo.put("Toronto Raptors", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/3/36/Toronto_Raptors_logo.svg/300px-Toronto_Raptors_logo.svg.png"));

        teamLogo.put("Chicago Bulls", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/6/67/Chicago_Bulls_logo.svg/1200px-Chicago_Bulls_logo.svg.png"));
        teamLogo.put("Cleveland Cavaliers", new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Cleveland_Cavaliers_secondary_logo.svg/1200px-Cleveland_Cavaliers_secondary_logo.svg.png"));
        teamLogo.put("Indiana Pacers", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/1/1b/Indiana_Pacers.svg/1200px-Indiana_Pacers.svg.png"));
        teamLogo.put("Detroit Pistons" , new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Logo_of_the_Detroit_Pistons.svg/1200px-Logo_of_the_Detroit_Pistons.svg.png"));
        teamLogo.put("Milwaukee Bucks", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/4/4a/Milwaukee_Bucks_logo.svg/1200px-Milwaukee_Bucks_logo.svg.png"));

        teamLogo.put("Atlanta Hawks", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/2/24/Atlanta_Hawks_logo.svg/300px-Atlanta_Hawks_logo.svg.png"));
        teamLogo.put("Charlotte Hornets", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/c/c4/Charlotte_Hornets_%282014%29.svg/800px-Charlotte_Hornets_%282014%29.svg.png"));
        teamLogo.put("Miami Heat", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/f/fb/Miami_Heat_logo.svg/300px-Miami_Heat_logo.svg.png"));
        teamLogo.put("Orlando Magic", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/1/10/Orlando_Magic_logo.svg/1200px-Orlando_Magic_logo.svg.png"));
        teamLogo.put("Washington Wizards", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/0/02/Washington_Wizards_logo.svg/800px-Washington_Wizards_logo.svg.png"));

        teamLogo.put("Denver Nuggets", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/7/76/Denver_Nuggets.svg/300px-Denver_Nuggets.svg.png"));
        teamLogo.put("Minnesota Timberwolves", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/c/c2/Minnesota_Timberwolves_logo.svg/300px-Minnesota_Timberwolves_logo.svg.png"));
        teamLogo.put("Oklahoma City Thunder", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/5/5d/Oklahoma_City_Thunder.svg/300px-Oklahoma_City_Thunder.svg.png"));
        teamLogo.put("Portland Trailblazers", new Image("https://upload.wikimedia.org/wikipedia/sco/thumb/7/74/Portland_Trail_Blazers.svg/300px-Portland_Trail_Blazers.svg.png"));
        teamLogo.put("Utah Jazz", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/5/52/Utah_Jazz_logo_2022.svg/345px-Utah_Jazz_logo_2022.svg.png"));

        teamLogo.put("Golden State Warriors", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/0/01/Golden_State_Warriors_logo.svg/300px-Golden_State_Warriors_logo.svg.png"));
        teamLogo.put("Los Angeles Clippers", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/e/ed/Los_Angeles_Clippers_%282024%29.svg/330px-Los_Angeles_Clippers_%282024%29.svg.png"));
        teamLogo.put("Los Angeles Lakers", new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Los_Angeles_Lakers_logo.svg/437px-Los_Angeles_Lakers_logo.svg.png"));
        teamLogo.put("Phoenix Suns" , new Image("https://upload.wikimedia.org/wikipedia/en/thumb/d/dc/Phoenix_Suns_logo.svg/285px-Phoenix_Suns_logo.svg.png"));
        teamLogo.put("Sacramento Kings", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/c/c7/SacramentoKings.svg/270px-SacramentoKings.svg.png"));

        teamLogo.put("New Orleans Pelicans", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/0/0d/New_Orleans_Pelicans_logo.svg/375px-New_Orleans_Pelicans_logo.svg.png"));
        teamLogo.put("San Antonio Spurs", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/a/a2/San_Antonio_Spurs.svg/360px-San_Antonio_Spurs.svg.png"));
        teamLogo.put("Memphis Grizzlies", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/f/f1/Memphis_Grizzlies.svg/285px-Memphis_Grizzlies.svg.png"));
        teamLogo.put("Dallas Mavericks", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/9/97/Dallas_Mavericks_logo.svg/315px-Dallas_Mavericks_logo.svg.png"));
        teamLogo.put("Houston Rockets", new Image("https://upload.wikimedia.org/wikipedia/en/thumb/2/28/Houston_Rockets.svg/255px-Houston_Rockets.svg.png"));


        return teamLogo.get(teamNameIn);
    }


    public static String[][] calculateProbabilities(ArrayList<Team> teamList) {
        // Step 1: Calculate the sum of exponentials of all ratings
        double sumExp = 0.0;
        for (Team team : teamList) {
            sumExp += Math.exp(team.getTeamsRankedRating());  // Sum of exponentials of the ratings
        }

        // Step 2: Initialize a 2D array to store team names and their probabilities
        String[][] teamsWithProbabilities = new String[teamList.size()][2];

        // Step 3: Calculate the probability for each team
        for (int i = 0; i < teamList.size(); i++) {
            Team team = teamList.get(i);
            teamsWithProbabilities[i][0] = team.getTeamName(); // Team name
            double probability = Math.exp(team.getTeamsRankedRating()) / sumExp;  // Exponential probability
            teamsWithProbabilities[i][1] = String.format("%.2f%%", probability * 100); // Convert to percentage
            //teamsWithProbabilities[i][1] = String.valueOf(probability * 100);
        }

        return teamsWithProbabilities;
    }

    public boolean overUnder(String teamNameIn, ArrayList<TeamsRanked> rankedIn) {
        boolean under = false;

        for (int i = 0; i < 5; i++) {
            Team team1 = rankedIn.get(i).getTeam();

            if (teamNameIn.equals(team1.getTeamName())) {
                under = true;
            }

        }

        return under;
    }

    private void openStatsScene(Stage stage, ArrayList<PlayersRanked> playersRanked) {
        TableView<Player> table = new TableView<>();

        // Define the row number column
        TableColumn<Player, Number> indexCol = new TableColumn<>("#");
        indexCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(table.getItems().indexOf(cellData.getValue()) + 1));
        indexCol.setSortable(false); // Disable sorting on the index column

        // Define other table columns
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, Double> tovCol = new TableColumn<>("TOV");
        tovCol.setCellValueFactory(new PropertyValueFactory<>("tov"));

        TableColumn<Player, Double> stlCol = new TableColumn<>("STL");
        stlCol.setCellValueFactory(new PropertyValueFactory<>("spg"));

        TableColumn<Player, Double> blkCol = new TableColumn<>("BLK");
        blkCol.setCellValueFactory(new PropertyValueFactory<>("blk"));

        TableColumn<Player, Double> mpgCol = new TableColumn<>("MPG");
        mpgCol.setCellValueFactory(new PropertyValueFactory<>("mpg"));

        TableColumn<Player, Double> ppgCol = new TableColumn<>("PPG");
        ppgCol.setCellValueFactory(new PropertyValueFactory<>("ppg"));

        TableColumn<Player, Double> apgCol = new TableColumn<>("APG");
        apgCol.setCellValueFactory(new PropertyValueFactory<>("apg"));

        TableColumn<Player, Double> rpgCol = new TableColumn<>("RPG");
        rpgCol.setCellValueFactory(new PropertyValueFactory<>("rpg"));

        TableColumn<Player, Double> tsCol = new TableColumn<>("True Shooting");
        tsCol.setCellValueFactory(new PropertyValueFactory<>("trueShooting"));

        TableColumn<Player, Double> offRatingCol = new TableColumn<>("Off Rating");
        offRatingCol.setCellValueFactory(new PropertyValueFactory<>("offRating"));

        TableColumn<Player, Double> defRatingCol = new TableColumn<>("Def Rating");
        defRatingCol.setCellValueFactory(new PropertyValueFactory<>("defRating"));

        // Add columns to the table
        table.getColumns().addAll(
                indexCol, nameCol, mpgCol, ppgCol, apgCol, rpgCol, stlCol, blkCol, tovCol, tsCol, offRatingCol, defRatingCol
        );

        // Sort the PlayersRanked list
        Collections.sort(playersRanked);

        // Extract Player objects from PlayersRanked and add them to the TableView
        for (PlayersRanked rankedPlayer : playersRanked) {
            table.getItems().add(rankedPlayer.getPlayer());
        }

        // Layout
        HBox mainLayout = new HBox(table);
        Scene statsScene = new Scene(mainLayout, 800, 400); // Adjust width for more columns
        stage.setScene(statsScene);
        stage.setTitle("Player Stats");
    }


}
