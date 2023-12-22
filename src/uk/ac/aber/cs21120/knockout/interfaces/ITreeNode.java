package uk.ac.aber.cs21120.knockout.interfaces;

/**
 * This interface must be implemented by classes representing nodes in the binary tree of teams
 * that makes up the competition. Implementing classes must also have constructor which takes
 * the left child node, the right child node, and a team to set (any of these can be null).
 * For example, TreeNode(ITree left, ITree right, ITeam t).
 * Note that these must be the *interfaces*, not the implementing classes.
 */

public interface ITreeNode {
    /**
     * return the left child node, or null if there isn't one
     * @return left child node or null
     */
    ITreeNode getLeft();

    /**
     * return the right child node, or null if there isn't one
     * @return right child node or null
     */
    ITreeNode getRight();

    /**
     * return the team which has been set, or null if no team has been set.
     * @return the team or null.
     */
    ITeam getTeam();

    /**
     * Set the team in the node.
     * @param t the team to set
     */
    void setTeam(ITeam t);

    /**
     * Return the score which has been set in this node (or zero if no score has been set)
     * @return the score
     */
    int getScore();

    /**
     * Set a score in this node.
     * @param s the score
     */
    void setScore(int s);
}
