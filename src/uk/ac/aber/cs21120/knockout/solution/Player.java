package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;

//Task 1 of the assignment for the module CS21120 - Algorithms and data structures,
//implementing the Player Class

/**
 * This class implements the IPlayer interface and takes two parameters
 * Player.name and Player.position
 */
public class Player implements IPlayer {

    private String name;
    private int postion;

    /**
     * The constructor takes a String for the name or name and a position
     */
    public Player(String name){
        this.name = name;
    }

    public Player(String name, int position){
        this.name = name;
        if(position < 1){
            throw new RuntimeException("Player position can not be less than 1");
        } else {
            this.postion = position;
        }
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPosition() {
        return this.postion;
    }

}
