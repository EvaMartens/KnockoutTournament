package uk.ac.aber.cs21120.knockout.interfaces;

/**
 * Interface for a single match between two teams. The match may or may not have been played.
 * Objects implementing this interface must have a constructor which takes two teams, e.g.
 *  GroupMatch(ITeam team1, ITeam team2)
 */

public interface IGroupMatch {
    /**
     * @return the first of the two teams in this match
     */
    ITeam getTeam1();

    /**
     * @return the second of the two teams in this match
     */
    ITeam getTeam2();

    /**
     * @return true if the match has been played, false otherwise
     */
    boolean isPlayed();

    /**
     * Set the score for this match. After this, isPlayed() should return true. Note that
     * once the scores are set we cannot retrieve or change them - we can only retrieve
     * the points.
     * @param team1Score the score for team 1
     * @param team2Score the score for team 2
     * @throws IllegalStateException if the match has already been played
     */
    void setScore(int team1Score, int team2Score);

    /**
     * Return the number of points team 1 has scored from this match. If team 1
     * scored more goals, they won the match and get 3 points. If the match was a draw,
     * both teams get 1 point. If team 2 scored more goals, team 1 gets 0 points.
     *
     * @return the number of points team 1 has scored from this match. Returns zero if the match has not been played.
     * @throws IllegalStateException if the match has not been played
     */
    int getTeam1Points();

    /**
     * Return the number of points team 2 has scored from this match. If team 2
     * scored more goals, they won the match and get 3 points. If the match was a draw,
     * both teams get 1 point. If team 1 scored more goals, team 2 gets 0 points.
     *
     * @return the number of points team 2 has scored from this match. Returns zero if the match has not been played.
     * @throws IllegalStateException if the match has not been played
     */
    int getTeam2Points();
}
