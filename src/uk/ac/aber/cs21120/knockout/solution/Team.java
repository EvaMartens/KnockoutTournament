package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

import java.util.*;

//TASK 2 CS21120 Assignment

public class Team implements ITeam, Comparable<Team> {

    private HashMap<String, Integer> playerNames; //This HashMap stores the player names on the team and a pointer to the array of utilised positions
    private HashMap<Integer, Integer> positions; //contains the position as key value and a pointer to the names Hashmap
    private List<IPlayer> allPlayers;
    public String teamName;
    private int groupStagePoints;

    /**
     * Constructors for a Team can either take just a name or a name and List of Players
     */
    public Team(String name) {
        this.teamName = name;
        this.positions = new HashMap<Integer, Integer>();
        this.playerNames = new HashMap<String, Integer>();
        this.allPlayers = new ArrayList<IPlayer>();
        this.groupStagePoints = 0;
    }

    public Team(String name, List<IPlayer> teamList) {
        this.teamName = name;
        this.positions = new HashMap<>();
        this.playerNames = new HashMap<String, Integer>();
        this.allPlayers = new ArrayList<IPlayer>();
        for (IPlayer p : teamList) {
            this.addPlayer(p);
        }
        this.groupStagePoints = 0;
    }

    /**
     * If the player p does not already exist in the team,
     * and if the team does not already have the position filled
     *
     * @param p the player to add
     */
    @Override
    public void addPlayer(IPlayer p) {
        if (!playerNames.containsKey(p.getName()) && !positions.containsKey(p.getPosition())) {
            String pName = p.getName();
            int pPosition = p.getPosition();
            int arrayLoc = allPlayers.size();
            allPlayers.add(arrayLoc, p);
            playerNames.put(pName, arrayLoc);
            positions.put(pPosition, arrayLoc);
        }
    }

    public int getGroupStagePoints() {
        return groupStagePoints;
    }

    public void setGroupStagePoints(int add) {
        this.groupStagePoints = groupStagePoints + add;
    }

    /**
     * Get the player in a given position as Integer
     *
     * @param position the player position (integer >=1)
     * @return the player or null if none was found
     */
    @Override
    public IPlayer getPlayerInPosition(int position) {
        IPlayer p = null;
        if (positions.containsKey(position)) {
            int arrayPos = positions.get(position);
            return allPlayers.get(arrayPos);
        }
        return p;
    }

    /**
     * Get a list of all the players. Any implementation of List is permissible.
     *
     * @return the players as a list
     */
    @Override
    public List<IPlayer> getPlayers() {
        return allPlayers;
    }

    /**
     * Get the name of the team
     *
     * @return the name
     */
    @Override
    public String getName() {
        return teamName;
    }

    @Override
    public String toString() {
        return "Team[" +
                 teamName + " >> (Group-Stage Score: "+ groupStagePoints + ")" + '\'' +
                ']';
    }

    @Override
    public int compareTo(Team otherTeam) {
        if (this.groupStagePoints > otherTeam.groupStagePoints) {
            return 1;
        } else if (this.groupStagePoints < otherTeam.groupStagePoints) {
            return -1;
        } else {
            return 0;
        }
    }


}
