package com.company;
import java.util.*;
import java.io.*;
import java.awt.*;

/**
 * -------------------------------------
 * Class: CS 342, Fall 2016
 * System: OS X, IntelliJ IDE
 * Author Code Number: 879P
 * Program: 8 Tiles
 * Descr: An Object Oriented approach to an interactive version of solving an 8 Tile Puzzle
 */

/**
 * This is the driver for the rest of the program and contains main().
 * Use this to test the other classes as they are created.
 * The loop to play the game interactively (or automatically solve the puzzle) is inside this class.
 * Move validation is controlled from here.
 */
public class TilesDriver {
    public static void main(String[] args) {
        printHeader();
        //scanner for accepting user input from standard in
        Scanner scanner = new Scanner(System.in);

        //prompt for user input
        int userInput = promptUser(scanner);

        Board parentBoard;
        //create random board
        if(userInput == 1)
            parentBoard = new Board();
        //create custom board
        else
            parentBoard = new Board(getCustomBoard(scanner).toCharArray());

        parentBoard.printBoard(0);

        SearchTree stateSpaceSearch = new SearchTree(parentBoard);

        interactiveLoop(scanner, stateSpaceSearch, parentBoard);

        scanner.close();
    }//end main(...)

    //function to print header
    public static void printHeader(){
        System.out.println("" +
                "AuthorCode Number: 879P \n" +
                "Class: CS 342, Fall 2016 \n" +
                "Program: #3, 8 Tiles. \n" +
                "\n" +
                "\n" +
                "Welcome to the 8-tiles puzzle. \n" +
                "Place the tiles in ascending numerical order.  For each  \n" +
                "move enter the piece to be moved into the blank square, \n" +
                "or 0 to exit the program.\n");
    }//end printHeader()

    //function to prompt user for 0 or 1
    public static int promptUser(Scanner sc){
        int userInput = 0;
        boolean validInput = false;

        System.out.print("" +
                "Choose a game option: \n" +
                "  1. Start playing \n" +
                "  2. Set the starting configuration\n" +
                "Enter your choice --> ");

        //loop until a valid input is read in
        while(validInput == false) {
            //ask until an integer is found
            while (!sc.hasNextInt()) {
                System.out.println("\n\t***INVALID INPUT***\n");
                System.out.println("" +
                        "Choose a game option: \n" +
                        "  1. Start playing \n" +
                        "  2. Set the starting configuration\n" +
                        "Enter your choice --> ");
                sc.next();
            }

            userInput = sc.nextInt();

            //check to see if they entered a 1 or a 2
            if(userInput == 1 || userInput == 2)
                validInput = true;
            else{ //otherwise, re-prompt
                System.out.println("\n\t***INVALID INPUT***\n");
                System.out.println("" +
                        "Choose a game option: \n" +
                        "  1. Start playing \n" +
                        "  2. Set the starting configuration\n" +
                        "Enter your choice --> ");
                continue;
            }

        }

        return userInput;
    }//end promptUser

    //function to prompt user for a custom board
    public static String getCustomBoard(Scanner sc){
        String userInput = " ";
        boolean validInput = false;

        System.out.println("\nSome boards such as 728045163 are impossible.\n" +
                "Others such as 245386107 are possible.\n" +
                "Enter a string of 9 digits (including 0) for the board -->");

        while(validInput == false){
            while(!sc.hasNext()){
                System.out.println("\n\t*** INVALID INPUT: Not a string ***");
                System.out.println("Some boards such as 728045163 are impossible.\n" +
                        "Others such as 245386107 are possible.\n" +
                        "Enter a string of 9 digits (including 0) for the board -->");
                sc.next();
            }

            userInput = (sc.next()).toString();

            //check length
            if(userInput.length() != Constants.BOARD_SIZE){
                System.out.println("\n\t*** INVALID INPUT: String is not 9 digits ***");
                System.out.println("Some boards such as 728045163 are impossible.\n" +
                        "Others such as 245386107 are possible.\n" +
                        "Enter a string of 9 digits (including 0) for the board -->");
                continue;
            }

            if(checkUniqueness(userInput.toCharArray(), userInput.length()) == true){
                validInput = true;
            }
            else{
                System.out.println("\n\t*** INVALID INPUT: String is not unique in integers ***");
                System.out.println("Some boards such as 728045163 are impossible.\n" +
                        "Others such as 245386107 are possible.\n" +
                        "Enter a string of 9 digits (including 0) for the board -->");
                continue;
            }
        }

        return userInput;
    }//end getCustomBoard()

    //helper function for getCustomBoard to check for integer uniqueness
    public static boolean checkUniqueness(char[] input, int length){
        //check to see if they are all digits
        for(int i = 0; i < length; i++){
            if(Character.isDigit(input[i]) == false)
                return false;
        }

        //check for uniqueness
        for(int i = 0; i < length; i++){
            int numOccurrences = 0;
            for(int j = 0; j < length; j++){
                if(Character.getNumericValue(input[j]) == i){
                    numOccurrences++;
                    if(numOccurrences > 1)
                        return false;
                }
            }
        }

        return true;
    }//end checkUniqueness(...)

    //function to run interactive loop
    public static void interactiveLoop(Scanner sc, SearchTree beginningTree, Board parentBoard){
        int moveCounter = 1;
        boolean validInput = false;
        String userInput = " ";
        System.out.print("\nInitial board is:\n" + moveCounter + ".");

        parentBoard.printBoard(0);

        System.out.println("\nPiece to move: " + parentBoard.getShortestHeuristicPiece());

        /* ********* INCOMPLETE ********** */
        System.out.println("Enter \'s\' to automatically solve");
        while(validInput == false){
            while(!sc.hasNext()){
                System.out.println("\n\t*** INVALID INPUT: ***");
                sc.next();
            }

            userInput = (sc.next()).toString();

            if(userInput.equals("s")){
                System.out.println("Solving puzzle automatically...........................\n");
                beginningTree.algorithm();
                break;
            }
            else{
                System.out.println("\n\t*** INVALID INPUT: ***");
                continue;
            }
        }

    }//end interactiveLoop(...)

}//end TilesDriver class


