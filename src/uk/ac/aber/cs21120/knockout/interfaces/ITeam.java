package uk.ac.aber.cs21120.knockout.interfaces;

import java.util.List;

/**
 * This interface must be implemented by any class representing a team of players.
 * Any class which implements this interface must also provide a constructor which
 * takes the team name (which will then be returned by getName).
 */
public interface ITeam {
    /**
     * Add a player to a team. It should only be possible to add each player once,
     * and each position should have only one player.
     *
     * @param p the player to add
     */
    void addPlayer(IPlayer p);

    /**
     * Get the player in a given position
     *
     * @param position the player position (integer >=1)
     * @return the player or null if none was found
     */
    IPlayer getPlayerInPosition(int position);

    /**
     * Get a list of all the players. Any implementation of List is permissible.
     * @return the players as a list
     */
    List<IPlayer> getPlayers();

    /**
     * Get the name of the team
     * @return the name
     */
    String getName();
}
