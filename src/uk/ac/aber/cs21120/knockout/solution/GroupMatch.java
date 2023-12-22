package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

/**
 * GroupMatch implements IGroupMatch @author evm18 Eva Martens
 *
 * Attributes:
 *
 * ITeam teamA;
 * ITeam teamB
 * int scoreA;
 * int scoreB;
 * boolean isPlayed; (While I don't use this attribute in the group implementation, it is still set when the score is set.
 *                    This would allow to modify the application in the future and actually implment a search algorithm.
 *                    This could be used to randomise match order for example.)
 */


public class GroupMatch implements IGroupMatch {
    ITeam teamA;
    ITeam teamB;
    int scoreA;
    int scoreB;

    boolean isPlayed;


    public GroupMatch(ITeam team1, ITeam team2){
        this.teamA = team1;
        this.teamB = team2;
        this.isPlayed = false;
    }
    /**
     * @return the first of the two teams in this match
     */
    @Override
    public ITeam getTeam1() {
        return teamA;
    }

    /**
     * @return the second of the two teams in this match
     */
    @Override
    public ITeam getTeam2() {
        return teamB;
    }

    /**
     * @return true if the match has been played, false otherwise
     */
    @Override
    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * Set the score for this match. After this, isPlayed() should return true. Note that
     * once the scores are set we cannot retrieve or change them - we can only retrieve
     * the points.
     *
     * @param team1Score the score for team 1
     * @param team2Score the score for team 2
     *
     * Note: In my implementation I do not throw and IllegalStateException as required by the interface...
     */
    @Override
    public void setScore(int team1Score, int team2Score) {
        this.scoreA = team1Score;
        this.scoreB = team2Score;
        this.isPlayed = true;
    }

    /**
     * Return the number of points team 1 has scored from this match. If team 1
     * scored more goals, they won the match and get 3 points. If the match was a draw,
     * both teams get 1 point. If team 2 scored more goals, team 1 gets 0 points.
     *
     * @return the number of points team 1 has scored from this match. Returns zero if the match has not been played.
     * @throws IllegalStateException if the match has not been played
     */
    @Override
    public int getTeam1Points() {
        if(isPlayed == false){
            throw new IllegalStateException("This match has not been played");
        } else {
            if(scoreA > scoreB){
                return 3;
            } else if(scoreB > scoreA) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Return the number of points team 2 has scored from this match. If team 2
     * scored more goals, they won the match and get 3 points. If the match was a draw,
     * both teams get 1 point. If team 1 scored more goals, team 2 gets 0 points.
     *
     * @return the number of points team 2 has scored from this match. Returns zero if the match has not been played.
     * @throws IllegalStateException if the match has not been played
     */
    @Override
    public int getTeam2Points() {
        if(isPlayed == false){
            throw new IllegalStateException("This match has not been played");
        } else {
            if(scoreA > scoreB){
                return 0;
            } else if(scoreB > scoreA) {
                return 3;
            } else {
                return 1;
            }
        }
    }

    public ITeam getTeamA() {
        return teamA;
    }

    public ITeam getTeamB() {
        return teamB;
    }
}
