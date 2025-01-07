/*
PlayersRanked File
 */


public class PlayersRanked implements Comparable<PlayersRanked> {

    private Player player;

    public PlayersRanked() {

    }

    public PlayersRanked(Player playerIn) {
        player = playerIn;
    }

    @Override
    public int compareTo(PlayersRanked o) {
        Player player1 = this.player;
        Player player2 = o.getPlayer();

        double minMinutesThreshold = 10.0;

        double player1Score = (player1.getPpg() + player1.getRpg() + player1.getApg()) * (player1.getTrueShooting()) +
                (((.75 * player1.getOffRating()) - (.75 * player1.getDefRating())) * .25) + (.5 * player1.getSpg()) +
                (.5 * player1.getBlk()) - (.25 * player1.getTov()) + player1.getPlayerAwards();

        double player2Score = (player2.getPpg() + player2.getRpg() + player2.getApg()) * (player2.getTrueShooting()) +
                (((.75 * player2.getOffRating()) - (.75 * player2.getDefRating())) * .25) + (.5 * player2.getSpg()) +
                (.5 * player2.getBlk()) - (.25 * player2.getTov()) + player2.getPlayerAwards();

        if (player1.getMpg() < minMinutesThreshold) {
            player1Score *= 0.5;  // Apply a 50% penalty or adjust accordingly
        }

        if (player2.getMpg() < minMinutesThreshold) {
            player2Score *= 0.5;  // Apply a 50% penalty or adjust accordingly
        }

        return Double.compare(player2Score, player1Score);
    }

    public Player getPlayer() {
        return player;
    }

    public String toString() {
        Player p = this.player;
        // Calculate the score for the player
        double playerScore = (p.getPpg() + p.getRpg() + p.getApg()) * p.getTrueShooting() +
                (((.75 * p.getOffRating()) - (.75 * p.getDefRating())) * .25) + (.5 * p.getSpg()) +
                (.5 * p.getBlk()) - (.25 * p.getTov());

        // Return a string containing both the player's name and score
        return p.getName() + " - Score: " + playerScore;
    }


}
