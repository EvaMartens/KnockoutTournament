package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IGroup;
import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

import java.util.*;

/**
 * Group implements IGroup @author evm18 Eva Martens
 *
 * Attributes:
 *
 * LinkedList<Team> teamsPlaying; - sorted ascending by groupPoints when all matches are played
 * List<GroupMatch> matches; - all Matches that need to be played in the group
 * ITeam winner1; - for easy access to winners
 * ITeam winner2; - for easy access to winners
 *
 * int counter - a variable that saves searching time by keeping track of the last played match
 */

public class Group implements IGroup {

    private ITeam winner1;
    private ITeam winner2;
    private LinkedList<Team> teamsPlaying;
    private List<GroupMatch> matches = new LinkedList<>();
    int counter; //saves us searching for the next unplayed match in array

    public Group() {
        this.teamsPlaying = new LinkedList<>();
        this.matches = new LinkedList<>();
        this.counter = 0;
        winner1 = null;
        winner2 = null;
    }

    public Group(ITeam[] teams) {
        this.teamsPlaying = new LinkedList<>();
        for (int i = 0; i < teams.length; i++) {
            teamsPlaying.add((Team) teams[i]);
        }
        this.matches.addAll(makeMatches(teams));
        this.counter = 0;
        winner1 = null;
        winner2 = null;
    }
    public ITeam getWinner1() {
        return winner1;
    }

    //after sorting the List in "playMatches()", the last two positions are the winner of the groups
    public void setWinner1() {
        this.winner1 = teamsPlaying.get(teamsPlaying.size()-1);
    }

    public ITeam getWinner2() {
        return winner2;
    }

    //after sorting the List in "playMatches()", the last two positions are the winner of the groups
    public void setWinner2() {
        this.winner2 = teamsPlaying.get(teamsPlaying.size()-2);
    }

    public int numberOfMatches(){
        return matches.size();
    }

    public void addTeam(ITeam t){
        teamsPlaying.add((Team) t);
    }

    public void addMatches(){
        ITeam teams[] = teamsPlaying.toArray(new ITeam[teamsPlaying.size()]); //cast to array because makeMatches needs that as input
        this.matches.addAll(makeMatches(teams));
    }

    /**
     * Private helper methode called by Group constructor to make a list of all possible matches
     *
     * @param teams which is an array containing all teams in the group
     * @return LinkedList which contains all matches
     */
    private List<GroupMatch> makeMatches(ITeam[] teams) {
        List<GroupMatch> g = new LinkedList<>();
        int pointer = 1;
        for (int i = 0; i < teams.length; i++) {
            ITeam teamA = teams[i];
            for (int j = pointer; j < teams.length; j++) {
                ITeam teamB = teams[j];
                GroupMatch newMatch = new GroupMatch(teamA, teamB);
                g.add(newMatch);
            }
            pointer++;
        }
        return g;
    }

    /**
     * Return the next unplayed match, ready to have its result set.
     * If no matches are left, return null.
     * <p>
     * Iterates counter each time it is used which saves us time searching.
     *
     * @return an unplayed match or null.
     */
    @Override
    public IGroupMatch getNextMatch() {

        if (counter == matches.size()) {
            return null;
        }
        GroupMatch game = matches.get(counter);
        return game;
    }

    /**
     * Return the points for a given team.
     *
     * @param team
     * @return number of points
     */

    @Override
    public int getPoints(ITeam team) {
        Team t = (Team) team; //casting to team bc ITeam does not possess getGroupStagePoints() methode
        int points = t.getGroupStagePoints();
        return points;
    }

    /**
     * return an array of the teams in points order, with the team with
     * the highest points first.
     *
     * @return array of teams
     * @throws IllegalStateException if any matches have not been played
     */
    @Override
    public ITeam[] getTable() {
        if(counter == matches.size()) {
            return (ITeam[]) teamsPlaying.toArray();
        } else {
            throw new IllegalStateException("Matches still need to be played!!");
        }
    }

    /**
     * This methode plays all the matches in match list by looping over the list and itterating counter
     * At the end it sorts the teamsPlaying list ascendingly by points won and sets winners
     */

    public void playMatches(){
        Scanner s = new Scanner(System.in);

        for(int i = 0; i < matches.size(); i++) {
            IGroupMatch match = getNextMatch();
            Team team1 = (Team) match.getTeam1();
            Team team2 = (Team) match.getTeam2();

            int score1 = -1;
            int score2 = -1;
            boolean loop = true;

            System.out.println("Please set the score for " + team1 + " vs " + team2);
            System.out.println("");
            //get input for team1 score1
            do {
                System.out.println("");
                System.out.print("Enter score for ");
                System.out.println(team1.getName());
                String input = s.nextLine();
                if (isNumeric(input)) { //if the input is a number, and also greater than 0 break out of while loop, else repeat
                    score1 = Integer.parseInt(input);
                    if (score1 < 0) {
                        System.out.println("");
                        System.out.println("Enter a valid score");
                        continue;
                    } else {
                        loop = false;
                        System.out.print("Score was set to ");
                        System.out.println(score1);
                        break;
                    }
                } else {
                    System.out.println("Enter a valid score");
                }
            } while (loop);

            //Get input for team2 score2
            loop = true;
            do {
                System.out.println("");
                System.out.print("Enter score for ");
                System.out.println(team2.getName());
                String input = s.nextLine();
                if (isNumeric(input)) { //if the input is a number, and also greater than 0 break out of while loop, else repeat
                    score2 = Integer.parseInt(input);
                    if (score2 < 0) {
                        System.out.println("");
                        System.out.println("Enter a valid score");
                        continue;
                    } else {
                        loop = false;
                        System.out.print("Score was set to ");
                        System.out.println(score2);
                        break;
                    }
                } else {
                    System.out.println("Enter a valid score");
                }
            } while (loop);

            //A team wins 3 points for every match won
            //1 point for every match drawn
            //0 points for every match lost
            if (score1 > score2) {
                team1.setGroupStagePoints(3);
                team2.setGroupStagePoints(0);
            } else if (score1 < score2) {
                team2.setGroupStagePoints(3);
                team1.setGroupStagePoints(0);
            } else {
                team1.setGroupStagePoints(1);
                team2.setGroupStagePoints(1);
            }

            //the cheat that saves us searching:
            counter++;
        }

        Collections.sort(teamsPlaying); //https://www.geeksforgeeks.org/java-program-to-sort-linkedlist-using-comparable/ says time complexity is O(n*log(n))
        //System.out.println("this group List is");
        //System.out.println(this);
        //System.out.println("");
        setWinner1();
        setWinner2();
    }

    //-------------------------------------------------------------------------
    //the following methode has been taken from:
    //https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the,Double.parseDouble(String)
    //evm18 - adapted the isNumeric methode to sanitise User Input

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException notNum) {
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------

    @Override
    public String toString() {
        return "The current group contains [" + teamsPlaying +
                ']';
    }
}
