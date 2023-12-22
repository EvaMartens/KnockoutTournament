package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IMatchTree;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;

import java.util.LinkedList;
import java.util.List;


/**
 * Written by evm18 - Eva Martens based on the IMatchTree Interface provided by James Finnis
 *
 *     A comment on the design for Task 4
 *
 *     Source for design idea https://www.youtube.com/watch?v=vV10FTdXnLg, "Google Coding Interview Question" deepest node
 *     The second solution in the video suggests comparing all nodes by level. I have adapted this to return a list of all matches from the tree.
 *     The current algorithm can be improved several ways:
 *     1) I could use extra space to track matches of each level by making a queue or stack of next matches in the match tree and storing the solution
 *        as an attribute of the tree. This would mean a huge reduction in operations, as checkNodes would only need to be called each time the
 *        stack of next matches hits 0. This would make the runtimemultiplication for checkNode() at worst linear, as we are only searching each node once to add
 *        to the stack. This comes closer to the solution suggested in the video source above.
 *     2) I could use the level attribute of the Nodes to itteratively only fetch the nodes of that level and then either store them
 *        on a stack (linear time, extra space) or check all the Nodes of the current level each time checkNode() is called (which results
 *        in ∑ (n, 1) but saves space)
 *     The current algorithm of getNextMatch() and checkNode() results in extra space of half n and n log(n) tree searches and
 *     at most 1/2 n  comparisons against all other possible matches at any one time.
 *      **/

public class MatchTree implements IMatchTree {

    ITreeNode rootNode;
    int depth;
    int teamNum;

    /**
     * The constructor takes a list of competitors
     * With esufficient enough teams it will first calculate the depth of the tree and how many matches are on the last (deepest) level of the tree.
     * It then makes the bottom nodes first by taking two teams for every last level match, and one Team for every node higher up.
     * Then it recursively connects them and creates a root for the match tree.
     *
     * @param competitors is a list of teams that compete in the tournament
     */
    public MatchTree(ITeam[] competitors){
        this.teamNum = competitors.length;
        if(teamNum > 1){
            //find out how deep the tree needs to be
            this.depth = calculateTreeSize(competitors.length); //O(1)
            int preliminaryMatches = (nodesOnLastLevel(teamNum, depth));//O(1)
            //The following code will always result in a List with an even number of items
            List<ITreeNode> level = new LinkedList<>();
            int positionInList = 0;
            if(preliminaryMatches == teamNum){ /**To do: refactor code to avoid code repetitionmake -> ParentNode(ITeam a, ITeam b) methode**/
                for (int i = 0; i < preliminaryMatches; i += 2) { //this works because of how the methode "nodesOnLastLevel" is defined
                    //take two teams and add them to a node, then add that to the list
                    TreeNode lowestLevel = new TreeNode();
                    lowestLevel.setLevel(1);

                    TreeNode leftChild = new TreeNode(competitors[i]);
                    leftChild.setLevel(0);
                    leftChild.setParent(lowestLevel);
                    lowestLevel.setLeftChild(leftChild);

                    TreeNode rightChild = new TreeNode(competitors[i + 1]);
                    rightChild.setLevel(0);
                    rightChild.setParent(lowestLevel);
                    lowestLevel.setRightChild(rightChild);

                    lowestLevel.setLevel(rightChild.getLevel() + 1);
                    level.add(lowestLevel);
                    positionInList += 2;
                } //O(n)
                this.rootNode = buildTree(level); //O(log(n))
            } else { /**To do: refactor code to avoid code repetition -> makeParentNode(ITeam a, ITeam b) methode **/
                for (int i = 0; i < preliminaryMatches * 2; i += 2) {
                    //take two teams and add them to a node, then add that to the list
                    TreeNode lowestLevel = new TreeNode();
                    lowestLevel.setLevel(1);

                    TreeNode leftChild = new TreeNode(competitors[i]);
                    leftChild.setLevel(0);
                    leftChild.setParent(lowestLevel);
                    lowestLevel.setLeftChild(leftChild);

                    TreeNode rightChild = new TreeNode(competitors[i + 1]);
                    rightChild.setLevel(0);
                    rightChild.setParent(lowestLevel);
                    lowestLevel.setRightChild(rightChild);

                    lowestLevel.setLevel(rightChild.getLevel() + 1);
                    level.add(lowestLevel);
                    positionInList += 2;
                }//O(n)
                for (int i = positionInList; i < competitors.length; i++) {
                    //for each remaining team make one node where the team is the parent node
                    TreeNode remaining = new TreeNode(competitors[i]);
                    remaining.setLevel(2);
                    level.add(remaining);
                }//O(n)
                //now the lowest level of nodes should be in the list "level" with an even number of nodes
                //we can now build the remaining tree bottom up by taking two nodes and linking them with another one
                this.rootNode = buildTree(level); //O(log(n))
            }//O(n*log(n)) based on accessing the input list of teams once per team and building the nodes in log(n) time
        } else if (competitors.length == 0){
            this.rootNode = null;
        } else if (competitors.length == 1){
            this.rootNode = new TreeNode(competitors[0]);
        }
    }

    /**
     * Takes a List of nodes and recursively connects them until the root is found
     * Olog(n) of the input of a list of teams in the match tree constructor
     *
     * call stack is equal to the depth of the tree
     *
     * @param lowest is the list of the lowest level nodes and then in recursive calls contains a list of nodes that connect the previous calls list of nodes
     * @return a node which is the root of the tree
     */

    private ITreeNode buildTree(List<ITreeNode> lowest){
       List<ITreeNode> l = new LinkedList<>();
       //base-case
       if(lowest.size() == 1){
           return lowest.get(0);
       } else {
           //take two nodes and make a parent
           for (int i = 0; i < lowest.size(); i=i+2) {

               TreeNode newParent = new TreeNode();
               TreeNode leftChild = (TreeNode) lowest.get(i);
               TreeNode rightChild = (TreeNode) lowest.get(i + 1);

               leftChild.setParent(newParent);
               rightChild.setParent(newParent);
               newParent.setLeftChild(leftChild);
               newParent.setRightChild(rightChild);
               newParent.setLevel(leftChild.getLevel() + 1);

               l.add(newParent);
           }
           //now recursively repeat step with halfed list
           //until base-case is reached
           return buildTree(l);
       }
    }

    /**
     * This methode finds the depth of the match tree
     * @param numberOfTeams is number of input list for the matchtree constructor
     * @return rounds i.E. how deep the tree is at it's furthest leafs from the root
     */
    private int calculateTreeSize(int numberOfTeams){
        int leafs = 2;
        int rounds = 1;
        if(leafs == teamNum){
            return rounds;
        } else {
            while (leafs < numberOfTeams) {
                rounds++;
                leafs = (int) Math.pow(2, rounds);
            }
        }
        return rounds;
    }

    /**
     * Each level on the tree has 2^level nodes
     * This level returns the amount of preliminary matches, unless the last level is fully filled with teams,
     * in which case it returns the team number.
     *
     * @param rounds referring to the depth of the tree
     * @return preliminaries
     */
    private int nodesOnLastLevel(int teamNum, int rounds){
        int preliminaries = 0;
        if(teamNum == (Math.pow(2, rounds))){
            //this means the last level is fully filled
            preliminaries = teamNum;
        } else {
            //otherwise 2^rounds will always be larger, see previous methode
            //so we find difference between teamNum and the first fully filled level of the tree
            preliminaries = (int) (teamNum - (Math.pow(2, (rounds - 1))));
        }
        return preliminaries;
    }


    /**
     * return the root node of the tree. This really shouldn't be necessary, and it breaks encapsulation -
     * but it does allow us to print the entire tree.
     *
     * @return the root node
     */
    @Override
    public ITreeNode getRoot() {
        return rootNode;
    }

    /**
     * Return the node which represents the next node to be played. This is a node which:
     * - does not have its Team set (i.e. calling getTeam() will return null for that node)
     * - has two child nodes
     * - both child nodes have their Teams set
     *
     * @return the next match to be played
     */

    @Override
    public ITreeNode getNextMatch() {
        if(rootNode == null){ //no tree
            return null;
        } else if(this.teamNum == 1){ // only one team in tournament
            return null;
        } else if (rootNode.getTeam() != null){
                throw new RuntimeException("All matches have been played, buddy. Sorry, but the season is over.");
            } else if(isMatch(rootNode)){
                return rootNode;
            } else {
            List<TreeNode> potentialMatches = new LinkedList<>();
            potentialMatches.addAll(checkNode((TreeNode) rootNode, potentialMatches));
            TreeNode compare = potentialMatches.get(0); //Source for design idea https://www.youtube.com/watch?v=vV10FTdXnLg, "Google Coding Interview Question"
            //the above methode checknode() runs in O(n^2) for the input of the current list of matches and is not very efficient in terms of time complexity
            //the input for checknode() is at most [1/2 of the size of the teams in the match tree]
            if(potentialMatches.size() == 1 ){
                return compare;
            }
            for(int i = 1; i < potentialMatches.size(); i++){
                if(potentialMatches.get(i).getLevel() < compare.getLevel()){
                    compare = potentialMatches.get(i);
                }
            }
            return compare;
           }
    }

    /**
     * @param m an ITreeNode
     * @return true if the team of said node is null, the score is 0 and there are a left and right child with a team
     */

    private boolean isMatch(ITreeNode m){
        if((m.getTeam() == null) && (m.getScore() == 0) && (m.getRight().getTeam() != null) && (m.getLeft().getTeam() != null)){
            return true;
        }
        return false;
    }

    /**
     * Recursively traverses the tree and finds all matches any time it is called.
     * The methode runs in O(n) for the input of TeamList in the MatchTree Constructor and is called log(n) times at least
     * Which results in a worst case runtime of at least O(n^2), but I believe could be O(n(log(n)), but I am not intirely sure about this.
     * @param current is the first checked node of each recursive call
     * @param allMatches is the List of playable matches the methode finds
     * @return a List of Tree nodes which are currently matches
     */

    private List<TreeNode> checkNode(TreeNode current, List<TreeNode> allMatches){ //Source for design idea https://www.youtube.com/watch?v=vV10FTdXnLg, "Google Coding Interview Question"
        List<TreeNode> matches = allMatches;
        //base case
        if(isMatch(current)){
            matches.add(current);
            return matches;
        }

        if(current.getTeam() == null){ //if the current node has a team but isn't a match just return matches

            if (current.getLeft() != null) { //otherwise proceed by getting all teams from the left
                if (isMatch(current.getLeft())) {
                    matches.add((TreeNode) current.getLeft());
                } else {
                    checkNode((TreeNode) current.getLeft(), matches);
                }
            }

            if (current.getRight() != null) { //and from the right
                if (isMatch(current.getRight())) {
                    matches.add((TreeNode) current.getRight());
                } else {
                    checkNode((TreeNode) current.getRight(), matches);
                }
            }
        }

        return matches; //return the list you have
    }

    /**
     * Set the score of the last node found with getNextMatch(). If there are no matches to be played,
     * or getNextMatch() has never been called, it should throw a RuntimeException.
     *
     * @param leftScore  score of team in left child node
     * @param rightScore score of team in right child node
     */
    @Override
    public void setScore(int leftScore, int rightScore) {
        ITreeNode nextMatch = getNextMatch();
        nextMatch.getLeft().setScore(leftScore);
        nextMatch.getRight().setScore(rightScore);
        if(leftScore > rightScore){
            nextMatch.setTeam(nextMatch.getLeft().getTeam());
        } else if (rightScore > leftScore){
            nextMatch.setTeam(nextMatch.getRight().getTeam());
        } //needs equal case
    }
}
