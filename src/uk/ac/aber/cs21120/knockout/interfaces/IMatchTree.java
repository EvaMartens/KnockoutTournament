package uk.ac.aber.cs21120.knockout.interfaces;

/**
 * This interface describes how other code interacts with a set of matches
 * in an elimination competition.
 * Implementing classes must also provide a constructor taking an array of ITeam objects.
 */
public interface IMatchTree {
    /**
     * return the root node of the tree. This really shouldn't be necessary, and it breaks encapsulation -
     * but it does allow us to print the entire tree.
     *
     * @return the root node
     */
    ITreeNode getRoot();

    /**
     * Return the node which represents the next node to be played. This is a node which:
     * - does not have its Team set (i.e. calling getTeam() will return null for that node)
     * - has two child nodes
     * - both child nodes have their Teams set
     *
     * @return the next match to be played
     */
    ITreeNode getNextMatch();

    /**
     * Set the score of the last node found with getNextMatch(). If there are no matches to be played,
     * or getNextMatch() has never been called, it should throw a RuntimeException.
     *
     * @param leftScore score of team in left child node
     * @param rightScore score of team in right child node
     */
    void setScore(int leftScore, int rightScore);
}