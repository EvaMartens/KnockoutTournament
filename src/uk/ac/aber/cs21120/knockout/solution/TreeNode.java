package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;

//CS21120 Task 3.1 implementing ITreeNode Interface
public class TreeNode implements ITreeNode {
    private int score;
    private int level; //attribute which discribes how far away this node is from a leaf
    private ITeam team;
    private TreeNode leftChild;
    private TreeNode rightChild;

    private TreeNode parent;

    /**
     * Basic Tree Node Constructor sets the node attributes to null (or 0 in case of the score)
     */
    public TreeNode(){
        this.score = 0;
        this.level = 0;
        this.team = null;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public TreeNode(ITeam t){
        this.score = 0;
        this.level = 0;
        this.team = t;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public TreeNode(ITeam t, int s){
        this.score = s;
        this.level = 0;
        this.team = t;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public TreeNode(ITeam t, ITreeNode l, ITreeNode r){
        this.score = 0;
        this.level = 0;
        this.team = t;
        this.leftChild = (TreeNode) l;
        this.rightChild = (TreeNode) r;
        this.parent = null;
    }

    public TreeNode(ITeam t, ITreeNode l, ITreeNode r, int s){
        this.score = s;
        this.level = 0;
        this.team = t;
        this.leftChild = (TreeNode) l;
        this.rightChild = (TreeNode) r;
        this.parent = null;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    /**
     * return the left child node, or null if there isn't one
     * @return left child node or null
     */
    @Override
    public ITreeNode getLeft() {
        return leftChild;
    }

    /**
     * return the right child node, or null if there isn't one
     *
     * @return right child node or null
     */
    @Override
    public ITreeNode getRight() {
        return rightChild;
    }

    /**
     * return the team which has been set, or null if no team has been set.
     *
     * @return the team or null.
     */
    @Override
    public ITeam getTeam() {
        return team;
    }

    /**
     * Set the team in the node.
     *
     * @param t the team to set
     */
    @Override
    public void setTeam(ITeam t) {
        this.team = t;
    }

    /**
     * Return the score which has been set in this node (or zero if no score has been set)
     *
     * @return the score
     */
    @Override
    public int getScore() {
      return score;
    }

    /**
     * Set a score in this node.
     *
     * @param s the score
     */
    @Override
    public void setScore(int s) {
        if(s >= 0) {
            this.score = s;
        } else {
            throw new RuntimeException("The team score cannot be smaller than 0");
        }
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "score=" + score +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}
