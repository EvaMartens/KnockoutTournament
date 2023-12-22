package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.*;
import uk.ac.aber.cs21120.knockout.tests.TreePrinter;

import java.util.*;

public class MatchTreeApplication {

    public static void main(String[] args) {
        intro();
        boolean loop = true;
        List<ITeam> teamsParticipating = new ArrayList<ITeam>();
        do {
            printMainMenu();
            int choice = getInputFromUserForMainMenue();

            if (choice == 1 || choice == 2) {
                teamsParticipating.addAll(buildTreeStartUpMenue()); //allow the user to add teams and if they want, add players to those teams
                ITeam teams[] = teamsParticipating.toArray(new ITeam[teamsParticipating.size()]);
                List<ITeam> winners = new ArrayList(); //only really needed if choice is 2
                System.out.println("-----------------------------");
                System.out.println("All Teams added to tournament");
                System.out.println("-----------------------------");
                System.out.println("");
                if (choice == 2) { //make groups for groupstages
                    if (teamsParticipating.size() < 3) {
                        throw new IllegalStateException("Tried making Group Matches with less than 3 teams in Tournament. Nope. We don't do that here.");
                        //why not you ask? because I think that is silly
                    } else {
                        System.out.println("Please select number of groups you would like");
                        int numOfGroups = howMany(teamsParticipating.size());
                        List<Group> groupList = new LinkedList<>();
                        groupList.addAll(makeGroups(numOfGroups, teamsParticipating)); //make as many groups as the user requested and split teams evenly between them
                        for (Group g : groupList) {
                            System.out.println("-----------------------------");
                            System.out.println(g.toString());
                            System.out.println("-----------------------------");
                            System.out.println();
                            int groupmatchesInGroup = g.numberOfMatches();
                            g.playMatches(); //this methode plays all matches in the group and allows user to type in scores
                            winners.add(g.getWinner1());
                        }
                        for (Group g : groupList) {
                            winners.add(g.getWinner2());
                        }
                    }
                }

                ITeam[] teamsInKnockout = rightArray(choice, winners, teams);
                IMatchTree applicationTree = new MatchTree(teamsInKnockout);
                TreePrinter printer = new TreePrinter();
                if (choice == 2) {
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("The Winners of the Groupstage are:");
                    int halfWinnersSize = winners.size() / 2;
                    for (int i = 0; i < halfWinnersSize; i++) { //this loop prints the winners in order of groups
                        System.out.println(winners.get(i));
                        System.out.println(winners.get(i + halfWinnersSize));
                    }
                    System.out.println("-----------------------------------------------------------------");
                }
                System.out.println("");
                printer.print(applicationTree);
                while (applicationTree.getRoot().getTeam() == null) {
                    printPlayTreeMenue(applicationTree);
                    System.out.println("-----------------------------------------------------------------");
                    printer.print(applicationTree);
                }

                ITeam t = applicationTree.getRoot().getTeam();
                System.out.println("");
                System.out.println("-----------------------------------------------------------------");
                System.out.println("-------------------------THE WINNER IS---------------------------");
                System.out.println("-----------------------------------------------------------------");
                System.out.println("");
                System.out.println(t.getName());
                System.out.println("");
                System.out.println("--------------------------  HOORAY!  ----------------------------");
            } else if (choice == 3) {
                demo();
            }
            loop = exitPrompt();
        } while(loop);

    }

    public static boolean exitPrompt(){
        Scanner s = new Scanner(System.in);
        boolean loopEnd = true;
        boolean loop = true;
        System.out.println("Would you like to Exit the program? (y/n)");
        do {
            String answer = s.nextLine();
            if (answer.equals("y")) {
                loop = false;
                loopEnd = false;
                break;
            } else if (answer.equals("n")) {
                loop = false;
                break;
            } else {
                System.out.println("Please select either -- y -- (EXIT) or -- n -- (CONTINUE) !");
            }
        } while (loop);
        return loopEnd;
    }

    private static void demo(){
        System.out.println("First we create as many Teams as we like");
        ITeam[] demoTeams = new ITeam[10];
        ITeam t1 = new Team("Your_favourite_Team");
        demoTeams[0] = t1;
        ITeam t2 = new Team("Tottenham");
        demoTeams[1] = t2;
        ITeam t3 = new Team("Bayern_Munich");
        demoTeams[2] = t3;
        ITeam t4 = new Team("BVB_Dortmund");
        demoTeams[3] = t4;
        ITeam t5 = new Team("Frankfurt");
        demoTeams[4] = t5;
        ITeam t6 = new Team("Arsenal_boo!");
        demoTeams[5] = t6;
        ITeam t7 = new Team("Manchester_United");
        demoTeams[6] = t7;
        ITeam t8 = new Team("Liverpool");
        demoTeams[7] = t8;
        ITeam t9 = new Team("Aberystwyth_Town_FC");
        demoTeams[8] = t9;
        ITeam t10 = new Team("Just_Cristiano_Ronaldo");
        demoTeams[9] = t10;
        System.out.println("In this tournament we have 10 Teams:");
        System.out.println(demoTeams);
        System.out.println("You can create a groupstage if you like");
        System.out.println("Or add players to teams when creating them");
        System.out.println("");
        System.out.println("In this case we are creating the match tree immediately");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("");

        IMatchTree applicationTree = new MatchTree(demoTeams);
        TreePrinter printer = new TreePrinter();
        printer.print(applicationTree);

        while (applicationTree.getRoot().getTeam() == null) {
            int score1 = (int)(10 * Math.random());
            int score2 = (int)(10 * Math.random());
            applicationTree.setScore(score1, score2);
            System.out.println("-----------------------------------------------------------------");
            printer.print(applicationTree);
        }

        ITeam t = applicationTree.getRoot().getTeam();
        System.out.println("");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------THE DEMO WAS WON BY-----------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("");
        System.out.println(t.getName());
        System.out.println("");
        System.out.println("--------------------------  HOORAY!  ----------------------------");

    }

    private static ITeam[] rightArray(int c, List<ITeam> w, ITeam[] t) {
        if (c == 1) {
            return t;
        } else {
            ITeam[] t2 = w.toArray(new ITeam[w.size()]);
            return t2;
        }
    }

    private static int howMany(int teamNum) {
        Scanner s = new Scanner(System.in);
        int ret = -1;
        boolean loop = true;

        do {
            System.out.println("How many teams? Choose between 2 and >half of however many teams you added< up to 16");
            //It is worth noting I misread the instructions for task 5 and overcomplicated this.
            //Task 5 requires 16 teams to be split into 4 groups of 4
            //I made the user choose the amount of groups instead and evenly distribute teams in between the groups
            String input = s.nextLine();
            if (isNumeric(input)) {
                ret = Integer.parseInt(input);
                if ((ret >= 2) && (ret <= (teamNum/2) && (ret <= 16))) { //max teams is capped by number of teams and 16. This value (16) could be changed to any other number. Just a design choice
                    loop = false;
                    break;
                } else {
                    System.out.println("Any number between 2 and 16, or half the number of teams you created.");
                    System.out.println("If you created 8 teams for example, you can only create 4 groups max.");
                    continue;
                }
            } else {
                System.out.println("Choose between 2 and >half of however many teams you added< or 16");
            }
        } while (loop);

        return ret;
    }

    private static List<Group> makeGroups(int numOfGroups, List<ITeam> teams) {
        List<Group> groups = new LinkedList<>();
        for (int i = 0; i < numOfGroups; i++) { //fill the list groups with Group Objects
            Group aGroup = new Group();
            groups.add(aGroup);
        }

        for (int i = 0; i < teams.size(); i += 0) { //iterate over team[] and fill groups evenly
            for (int j = 0; j < numOfGroups; j++) {
                if (i < teams.size()) {
                    groups.get(j).addTeam(teams.get(i));
                    i++;
                }
                //if randomisation is wanted the easiest way of implementing this would be changing the input        LinkedHashSet<ITeam> teamsBasedOnInput -> HashSet<ITeam> teamsBasedOnInput
                //which randomises the order the teams are accessed in the code. Please view                         buildTreeStartUpMenue()
            }
        }
        for (Group g : groups) { //make all the matches inside the groups
            g.addMatches();
        }
        return groups;
    }

    private static void printPlayTreeMenue(IMatchTree tree) {
        Scanner s = new Scanner(System.in);
        IMatchTree applicationTreeT = tree;
        ITreeNode n = applicationTreeT.getNextMatch();

        ITeam team1 = n.getLeft().getTeam();
        ITeam team2 = n.getRight().getTeam();

        System.out.println("");
        System.out.println("Please enter the scores for this match");
        System.out.println("");
        System.out.print(team1.getName());
        System.out.print(" vs ");
        System.out.println(team2.getName());

        int score1 = -1;
        int score2 = -1;
        boolean loop = true;

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

        applicationTreeT.setScore(score1, score2); //tbh this could be replaced by using the n.getLeft().setScore(score1) and n.getRight().setScore(score2)
        //which would mean that getNextMatch() does not get called twice, saving loads of operations
        //but I wanted to implement the setScore methode that was required by the interface
    }

    private static int getInputFromUserForMainMenue() {
        Scanner s = new Scanner(System.in);
        int userInput = -1;
        boolean loop = true;

        do {
            String input = s.nextLine();
            if (isNumeric(input)) {
                userInput = Integer.parseInt(input);
            } else {
                System.out.println("Please select either 1 or 2 or 3");
            }
            //System.out.println(userInput);
            if (userInput == 1 || userInput == 2 || userInput == 3) {
                loop = false;
                break;
            }
        } while (loop);

        if (userInput == 1) {
            System.out.println("---------You have selected to build a new Match tree-------------");
            System.out.println("-----------------------------------------------------------------");
        } else if (userInput == 2) {
            System.out.println("---------You have selected to build a new Match tree-------------");
            System.out.println("-------------------with a group Stage----------------------------");
        } else { //or 3
            System.out.println("-----------You have selected to demonstrate the app--------------");
            System.out.println("-----------------------------------------------------------------");
        }

        return userInput;
    }

    //-------------------------------------------------------------------------
    //the following methode has been taken from:
    //https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the,Double.parseDouble(String)
    //evm18 - adapted the isNumeric methode to sanitise User Input

    public static boolean isNumeric(String strNum) {
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

    private static void printMainMenu() {
        System.out.println("Please select what you would like to do");
        System.out.println("1 - Build a new match tree");
        System.out.println("2 - Build a new match tree with a group stage");
        System.out.println("3 - demonstrate what a Matchtree looks like");

    }

    private static void intro() {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("----------------MATCH TREE BUILDER APPLICATION-------------------");
        System.out.println("-----------------written by evm18 for CS21120--------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
    }

    private static Set<ITeam> buildTreeStartUpMenue() {
        Scanner team = new Scanner(System.in);
        Set<ITeam> teamsBasedOnInput = new LinkedHashSet<>(); //unique values only

        System.out.println("");
        System.out.println("Please type in the names of the teams in your tournament");
        System.out.println("When you are done type -------------- x - to EXIT ---------------");
        System.out.println("");
        System.out.println("Note that Team Names do not allow for spaces or special signs");
        System.out.println("");

        String input;
        int countTeams = 1;
        do {
            input = team.nextLine();
            if (input.equals("x")) {
                break;
            }
            input = input.replaceAll(" ", "_");//replace all spaces with underscore
            input = input.replaceAll("[^0-9A-Za-z_]", ""); //no special signs or spaces allowed but underscore in this string

            if (isNumeric(input)) { //if it is just numbers, we skip the team creation and repeat the loop
                System.out.println("Team cannot be called by just numbers");
                System.out.println("Please enter valid team name");
                continue;
            }

            ITeam newTeam = makeTeam(input);
            if (!teamsBasedOnInput.contains(newTeam)) {
                teamsBasedOnInput.add(newTeam);
                System.out.print("Your team no. ");
                System.out.print(countTeams);
                System.out.print(" is called: ");
                System.out.println(input);
                countTeams = teamsBasedOnInput.size() + 1;
                System.out.println("Please add the next team or press  x  to EXIT");
                //System.out.println(teamsBasedOnInput);
            } else {
                System.out.println("A Team of that name already exists and cannot participate twice!");
            }

        } while (!input.equals("x"));
        return teamsBasedOnInput;
    }

    private static ITeam makeTeam(String teamName) {
        Scanner teamEditor = new Scanner(System.in);
        ITeam newTeam = new Team(teamName);
        String editTeam;
        System.out.println("Would you like to edit the team and add players? (y/n)");
        boolean loop = true;

        do {
            editTeam = teamEditor.nextLine();
            if (editTeam.equals("y")) {
                loop = false;
                editTeam(newTeam);
                break;
            } else if (editTeam.equals("n")) {
                loop = false;
                break;
            } else {
                System.out.println("Please select either --y-- (Yes) or --n-- (No) !");
            }
        } while (loop);

        return newTeam;
    }

    //same pattern of code as in buildTree() methode, the input could be generalsised by writing an input methode that takes a prompt as input
    private static void editTeam(ITeam currentTeam) {
        Scanner s = new Scanner(System.in);
        ITeam t = currentTeam;

        String input;
        Set<IPlayer> players = new HashSet<>();
        int count = 1;

        do {
            System.out.println("Please add  valid player name");
            System.out.println("If you are done adding Players type  x  to EXIT");
            input = s.nextLine();
            if (input.equals("x")) {
                break;
            }
            input = input.replaceAll(" ", "_");//replace all spaces with underscore
            input = input.replaceAll("[^0-9A-Za-z-_]", ""); //no special signs allowed, but hyphens and underscore

            if (isNumeric(input)) { //if it is just numbers, we skip the team creation and repeat the loop
                System.out.println("Player cannot be called by just numbers");
                System.out.println("Please enter valid player name");
                continue;
            }

            int position = inputPosition();

            IPlayer p = new Player(input, position);
            if (!players.contains(p)) {
                players.add(p);
                System.out.print("Your player no. ");
                System.out.print(count);
                System.out.print(" is called: ");
                System.out.println(input);
                count = players.size() + 1;
            } else {
                System.out.println("A Player of that name  and position already exists and cannot participate twice!");
            }

        } while (!input.equals("x"));
    }

    private static int inputPosition() {
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        int position = 0;
        do {
            String pos;
            System.out.print("Please enter a valid position between  1  and  50");
            pos = s.nextLine();
            if (isNumeric(pos)) {
                position = Integer.parseInt(pos);
                if (position > 50 || position < 1) {
                    continue;
                } else {
                    loop = false;
                    break;
                }
            } else {
                continue;
            }
        } while (loop);
        return position;
    }
}
