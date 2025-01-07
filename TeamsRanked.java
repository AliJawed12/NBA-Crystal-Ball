/*
TeamsRanked File
 */



import java.util.ArrayList;
import java.util.Comparator;

public class TeamsRanked implements Comparable<TeamsRanked> {

    private Team team;
    static ArrayList<Team> listOfTeams = new ArrayList<>();

    public TeamsRanked() {
        Team teamInstance = new Team();
        listOfTeams = teamInstance.compileData();
    }

    public TeamsRanked(Team teamIn) {
        team = teamIn;
    }

    @Override
    public int compareTo(TeamsRanked o) {
        Team team1 = this.team;
        Team team2 = o.getTeam();
        double team1WinPercent = (team1.getTeamWins() / (team1.getTeamWins() + team1.getTeamLosses())) * 100;
        double team2WinPercent = (team2.getTeamWins() / (team2.getTeamWins() + team2.getTeamLosses())) * 100;
        double team1Ranking = (team1.getTeamOffRating() * .20) - (team1.getTeamDefRating() * .20) + (team1.getNetRating() * .20) + (team1.getEfgPercent() * .15) + (team1.getTRebPercent() * .05) - (team1.getTovPercent() * .05) - (team1.getFtRateOpp() * .05) - (team1.getFtRate() * .05) - (team1.getPace() * .05);
        double team2Ranking = (team2.getTeamOffRating() * .20) - (team2.getTeamDefRating() * .20) + (team2.getNetRating() * .20) + (team2.getEfgPercent() * .15) + (team2.getTRebPercent() * .05) - (team2.getTovPercent() * .05) - (team2.getFtRateOpp() * .05) - (team2.getFtRate() * .05) - (team2.getPace() * .05);
        team1.setTeamsRankedRating(team1Ranking);
        team2.setTeamsRankedRating(team2Ranking);
        /*
        listOfTeams = VariousComparatorSorts.sortByNetRating(listOfTeams);

        for (int i = 0; i <= 10; i++) {
            if (listOfTeams.get(i).equals(team1)) {

            }
        }

         */


        return Double.compare(team2Ranking, team1Ranking);
    }

    public void setListOfTeams(ArrayList<Team> listOfTeamsIn) {
        listOfTeams = listOfTeamsIn;
    }

    public ArrayList<Team> getListOfTeams() {
        return listOfTeams;
    }

    public Team getTeam() {
        return team;
    }
}
