/*

Team File
 */



import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Team {

    private String teamName;
    private int teamWins;
    private int teamLosses;
    private double teamOffRating;
    private double teamDefRating;
    private double strengthOfSchedule;
    private double netRating;
    private double efgPercent;
    private double tovPercent;
    private double oRebPercent;
    private double dRebPercent;
    private double tRebPercent;
    private double pace;
    private double ftRate;
    private double ftRateOpp;
    private double teamsRankedRating;

    public Team() {
        teamName = "";
        teamWins = 0;
        teamLosses = 0;
        teamOffRating = 0.0;
        teamDefRating = 0.0;
        strengthOfSchedule = 0.0;
    }

    public Team(String teamNameIn, int teamWinsIn, int teamLossesIn, double teamOffRatingIn, double teamDefRatingIn, double strengthOfScheduleIn) {
        teamName = teamNameIn;
        teamWins = teamWinsIn;
        teamLosses = teamLossesIn;
        teamOffRating = teamOffRatingIn;
        teamDefRating = teamDefRatingIn;
        strengthOfSchedule = strengthOfScheduleIn;
    }

    // Setters and getters for all abve data members
    // Getter and Setter for teamName
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    // Getter and Setter for teamWins
    public int getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(int teamWins) {
        this.teamWins = teamWins;
    }

    // Getter and Setter for teamLosses
    public int getTeamLosses() {
        return teamLosses;
    }

    public void setTeamLosses(int teamLosses) {
        this.teamLosses = teamLosses;
    }

    // Getter and Setter for teamOffRating
    public double getTeamOffRating() {
        return teamOffRating;
    }

    public void setTeamOffRating(double teamOffRating) {
        this.teamOffRating = teamOffRating;
    }

    // Getter and Setter for teamDefRating
    public double getTeamDefRating() {
        return teamDefRating;
    }

    public void setTeamDefRating(double teamDefRating) {
        this.teamDefRating = teamDefRating;
    }

    // Getter and Setter for strengthOfSchedule
    public double getStrengthOfSchedule() {
        return strengthOfSchedule;
    }

    public void setStrengthOfSchedule(double strengthOfSchedule) {
        this.strengthOfSchedule = strengthOfSchedule;
    }

    // Getter and Setter for netRating
    public double getNetRating() {
        return netRating;
    }

    public void setNetRating(double netRating) {
        this.netRating = netRating;
    }

    // Getter and Setter for efgPercent
    public double getEfgPercent() {
        return efgPercent;
    }

    public void setEfgPercent(double efgPercent) {
        this.efgPercent = efgPercent;
    }

    // Getter and Setter for tovPercent
    public double getTovPercent() {
        return tovPercent;
    }

    public void setTovPercent(double tovPercent) {
        this.tovPercent = tovPercent;
    }

    // Getter and Setter for oRebPercent
    public double getORebPercent() {
        return oRebPercent;
    }

    public void setORebPercent(double oRebPercent) {
        this.oRebPercent = oRebPercent;
    }

    // Getter and Setter for dRebPercent
    public double getDRebPercent() {
        return dRebPercent;
    }

    public void setDRebPercent(double dRebPercent) {
        this.dRebPercent = dRebPercent;
    }

    // Getter and Setter for tRebPercent
    public double getTRebPercent() {
        return tRebPercent;
    }

    public void setTRebPercent(double oRebPercent, double dRebPercent) {

        double rebounds = Math.round((oRebPercent + dRebPercent) / 2);
        DecimalFormat df = new DecimalFormat("#.##");
        rebounds = Double.parseDouble(df.format(rebounds));

        this.tRebPercent = rebounds;
    }

    // Getter and Setter for pace
    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    // Getter and Setter for ftRate
    public double getFtRate() {
        return ftRate;
    }

    public void setFtRate(double ftRate) {
        this.ftRate = ftRate;
    }

    // Getter and Setter for ftRateOpp
    public double getFtRateOpp() {
        return ftRateOpp;
    }

    public void setFtRateOpp(double ftRateOpp) {
        this.ftRateOpp = ftRateOpp;
    }

    public void setTeamsRankedRating (double ratingIn) {
        teamsRankedRating = ratingIn;
    }

    public double getTeamsRankedRating() {
        return teamsRankedRating;
    }

    public String toString() {
        /*
        return "Team: " + teamName + " Wins: " + teamWins + " Losses: " + teamLosses
                + " Offensive Rating: " + teamOffRating + " Defensive Rating: "
                + teamDefRating + " SOS: " + strengthOfSchedule;

         */

        return "Team: " + teamName + " Wins: " + teamWins + " Losses: " + teamLosses
                + " Offensive Rating: " + teamOffRating + " Defensive Rating: "
                + teamDefRating + " SOS: " + strengthOfSchedule
                + " Net Rating: " + netRating + " eFG%: " + efgPercent
                + " TOV%: " + tovPercent + " OREB%: " + oRebPercent
                + " DREB%: " + dRebPercent + " TREB%: " + tRebPercent
                + " Pace: " + pace + " FTR: " + ftRate + " Opponent FTR: " + ftRateOpp
                + " TeamsRankedRating: " + teamsRankedRating;
    }





    public ArrayList<Team> compileData() {
        final String teamStatsUrl = "https://www.basketball-reference.com/leagues/NBA_2025.html";
        ArrayList<Team> teamArrayList = new ArrayList<>();

        TeamBasicStats teamBasicStats = new TeamBasicStats(teamStatsUrl);
        String teamStats[][] = TeamBasicStats.getTeamStats();




        for (int i = 0; i < teamStats.length; i++) {
            Team team = new Team();

            if (teamStats[i][0].contains("*")) {
                teamStats[i][0] = teamStats[i][0].replace("*", "");
                team.setTeamName(teamStats[i][0]);
            }
            else {
                team.setTeamName(teamStats[i][0]);
            }

            team.setTeamWins(Integer.parseInt(teamStats[i][1]));
            team.setTeamLosses(Integer.parseInt(teamStats[i][2]));
            team.setTeamOffRating(Double.parseDouble(teamStats[i][3]));
            team.setTeamDefRating(Double.parseDouble(teamStats[i][4]));
            team.setStrengthOfSchedule(Double.parseDouble(teamStats[i][5]));
            team.setNetRating(Double.parseDouble(teamStats[i][6]));
            team.setEfgPercent(Double.parseDouble(teamStats[i][7]));
            team.setTovPercent(Double.parseDouble(teamStats[i][8]));
            team.setORebPercent(Double.parseDouble(teamStats[i][9]));
            team.setDRebPercent(Double.parseDouble(teamStats[i][10]));
            team.setPace(Double.parseDouble(teamStats[i][11]));
            team.setFtRate(Double.parseDouble(teamStats[i][12]));
            team.setFtRateOpp(Double.parseDouble(teamStats[i][13]));
            team.setTRebPercent(Double.parseDouble(teamStats[i][9]), Double.parseDouble(teamStats[i][10]));

            teamArrayList.add(team);
        }



        return teamArrayList;
    }


}
