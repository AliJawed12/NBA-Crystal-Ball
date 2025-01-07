
# NBA Crystal Ball

## Overview

This project is a Java application designed to compile and analyze basketball player statistics from various sources. The application fetches data from online resources, processes it, and provides a comprehensive view of player performance metrics.

## Features

- Fetches player statistics from multiple online sources.
- Compiles basic, advanced, and offensive/defensive statistics.
- Handles traded players and aggregates their full-season stats.
- Provides a comprehensive view of player performance metrics.
- Outputs player data in a structured format.

## Project Structure

- `BasicPlayerStats.java`: Fetches and processes basic player statistics.
- `AdvancedPlayerStats.java`: Fetches and processes advanced player statistics.
- `PlayerOffDefStats.java`: Fetches and processes offensive and defensive player statistics.
- `Player.java`: Represents a player and their statistics, including methods to compile data from various sources.
- `GUI.java`: Manages the graphical user interface of the application.
- `Team.java`: Represents a basketball team.
- `TeamsRanked.java`: Represents a ranked team.
- `PlayersRanked.java`: Represents a ranked player.

## Usage

1. **Extract the ZIP file:**
   Extract the contents of the ZIP file to your desired location.

2. **Compile the Java files:**
   ```sh
   javac -cp .:jsoup-1.14.3.jar:javafx-sdk-17.0.1/lib/* SourceCode/*.java
   ```

3. **Run the application:**
   ```sh
   java -cp .:jsoup-1.14.3.jar:javafx-sdk-17.0.1/lib/* SourceCode.BasicPlayerStats
   ```

4. Hard-Coded File: Need to add all_nba_trade_23_24 file to Player Class
5. How to Look at different years:
  - Currently, data makes projections for last season. To make changes for desired year change the 3 URLs from classes in Player class to the year and change the year in the url in the Team Class 

## Dependencies

- [Jsoup](https://jsoup.org/): A Java library for working with real-world HTML.
- [JavaFX](https://openjfx.io/): A Java library for building rich client applications.


## Contact

For any questions or suggestions, please contact the project contributors.
