package com.evermos.onlinestore;

import java.util.Random;
import java.util.Scanner;

public class TreasureHunt {
    private static int row = 6, column = 8;
    public static String arr [][] = new String[row][column];
    public static int treasureRow = 1, treasureColumn = 1;
    public static int userRow = 1, userColumn = 1;
    public static int stepUp, stepRight, stepDown, stepLeft;
    public static void main(String[] args) {
        boolean isValid = Boolean.FALSE;

        //build map
        buildMap();

        //user location
        setUser();

        while (!isValid){

            //treasure location
            setTreasure();

            displayBoard(arr);

            //input step
            inputStep();

            //step up
            if (userRow-stepUp < 0){
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (!arr[userRow-stepUp][userColumn].equals("#")) arr[userRow-stepUp][userColumn] = "X";
            else{
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (stepUp>0) arr[userRow][userColumn] = ".";
            userRow = userRow-stepUp;

            //step right
            if (userColumn+stepRight > column){
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (!arr[userRow][userColumn+stepRight].equals("#")) arr[userRow][userColumn+stepRight] = "X";
            else{
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (stepRight>0) arr[userRow][userColumn] = ".";
            userColumn = userColumn+stepRight;

            //step down
            if (userRow+stepDown > row){
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (!arr[userRow+stepDown][userColumn].equals("#")) arr[userRow+stepDown][userColumn] = "X";
            else{
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (stepDown>0) arr[userRow][userColumn] = ".";
            userRow = userRow+stepDown;

            //step left
            if (userColumn-stepLeft < 0){
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (!arr[userRow][userColumn-stepLeft].equals("#")) arr[userRow][userColumn-stepLeft] = "X";
            else{
                System.err.println("you break the wall, you lose..!!");
                break;
            }
            if (stepLeft>0) arr[userRow][userColumn] = ".";
            userColumn = userColumn-stepLeft;

            //find the treasure
            if (userRow==treasureRow && userColumn==treasureColumn){
                displayBoard(arr);
                System.err.println("you find the treasure..!!");
                isValid = Boolean.TRUE;
            }
        }

    }

    private static void inputStep() {
        System.out.print( "Step Up: " );
        Scanner in = new Scanner( System.in );
        stepUp = in.nextInt();

        System.out.print( "Step Right: " );
        in = new Scanner( System.in );
        stepRight = in.nextInt();

        System.out.print( "Step Down: " );
        in = new Scanner( System.in );
        stepDown = in.nextInt();

        System.out.print( "Step Left: " );
        in = new Scanner( System.in );
        stepLeft = in.nextInt();
        System.err.println("");
    }

    private static void setUser() {
        userRow = new Random().nextInt(row-2)+1;
        userColumn = new Random().nextInt(column-2)+1;
        if (arr[userRow][userColumn].equals("#")) setUser();
        else arr[userRow][userColumn] = "X";
    }

    private static void setTreasure() {
        treasureRow = new Random().nextInt(row-2)+1;
        treasureColumn = new Random().nextInt(column-2)+1;
        if (arr[treasureRow][treasureColumn].equals("#")) setTreasure();
        else arr[treasureRow][treasureColumn] = ".";
    }

    private static void buildMap() {
        for (int i=0; i<arr.length; i++){
            for (int j=0; j<arr[0].length; j++){
                if (i==0 && j<arr[0].length) arr[i][j] = "#";
                else if (i<arr.length && j==0) arr[i][j] = "#";
                else if (i<arr.length && j==arr[0].length-1) arr[i][j] = "#";
                else if (i==arr.length-1 && j<arr[0].length) arr[i][j] = "#";
                else arr[i][j] = getSaltString();
            }
        }
        addObstacle();
    }

    private static void addObstacle() {
        arr[2][2] = "#";
        arr[2][3] = "#";
        arr[2][4] = "#";
        arr[3][4] = "#";
        arr[3][6] = "#";
        arr[4][2] = "#";
    }

    private static void displayBoard(String[][] arr) {
        for (int i=0; i<arr.length; i++){
            for (int j=0; j<arr[0].length; j++){
                System.err.print(" " +arr[i][j]);
            }
            System.err.println();
        }
    }

    public static String getSaltString() {
        String SALTCHARS = ".";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 1) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
