package uk.ac.aber.cs21120.knockout.interfaces;

/**
 * Each group consists of four teams, indexed 0-3.
 * There must be a constructor which takes an array of teams, e.g.
 *  Group(ITeam[] teams)
 *
 * Every team plays every other team in the group once. This will modify the
 * scores of the teams in the group:
 *  Winning team: +3 points
 *  Losing team: 0 points
 *  Draw: +1 point to each team
 */
public interface IGroup {

    /**
     * Return the next unplayed match, ready to have its result set.
     * If no matches are left, return null.
     * @return an unplayed match or null.
     */
    IGroupMatch getNextMatch();

    /**
     * Return the points for a given team.
     * @param team
     * @return number of points
     */
    int getPoints(ITeam team);


    /**
     * return an array of the teams in points order, with the team with
     * the highest points first.
     * @return array of teams
     * @throws IllegalStateException if any matches have not been played
     */
    ITeam[] getTable();

}
