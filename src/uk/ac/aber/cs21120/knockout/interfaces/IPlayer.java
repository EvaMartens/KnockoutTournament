package uk.ac.aber.cs21120.knockout.interfaces;

/**
 * This interface should be implemented by classes representing players in teams.
 * These classes should also provide a constructor which takes a name (a string) and
 * a position (an integer >=1).
 */
public interface IPlayer {
    /**
     * return the player's name
     * @return the name
     */
    String getName();

    /**
     * return the player's position
     * @return the position
     */
    int getPosition();
}
