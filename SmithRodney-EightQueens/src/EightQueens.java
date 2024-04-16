import java.util.*;

public class EightQueens {

    final private int [][] map = new int[8][8];
    final private int [][] testMap = new int[8][8];
    private int heuristic = 0;
    private int activeQueen = 0;
    private int restarts = 0;
    private int moves = 0;
    public boolean newMap = true;
    private int neighbors = 8;
    private static final int BOARD_SIZE = 8;


    public EightQueens() {
        initializeMap();
    }

    public void initializeMap() {         // Set the map to all zeros
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                map[i][j] = 0;
            }
        }
    }
    public void randomizeMap( ){  //randomize the map
        Random rand = new Random( );
        while(activeQueen < BOARD_SIZE){
            for(int i = 0; i < BOARD_SIZE; i++){
                map[rand.nextInt(7)][i] = 1;
                activeQueen++;
            }
        }
        heuristic = heuristic(map);
    }
    public boolean findRowConflict(int[][] test, int a){  //determines row conflicts
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (test[i][a] == 1) {
                count++;
            }
        }
        return count > 1;
    }
    public boolean findColConflict(int[][] test, int j){ //determines column conflicts
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (test[j][i] == 1) {
                count++;
            }
        }
        return count > 1;
    }
    public boolean findDiagonalConflict(int[][] test, int a, int b) { //determines column conflicts
        for (int i = 1; i < BOARD_SIZE; i++) {
            if ((a + i < BOARD_SIZE) && (b + i < BOARD_SIZE) && test[a + i][b + i] == 1)
                return true;
            if ((a - i >= 0) && (b - i >= 0) && test[a - i][b - i] == 1)
                return true;
            if ((a + i < BOARD_SIZE) && (b - i >= 0) && test[a + i][b - i] == 1)
                return true;
            if ((a - i >= 0) && (b + i < BOARD_SIZE) && test[a - i][b + i] == 1)
                return true;
        }
        return false;
    }
    public int heuristic(int[][] test){ // Counts the number of conflicts among the queens on the board
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (test[i][j] == 1) {
                    boolean rowEx = findRowConflict(test, j);
                    boolean colEx = findColConflict(test, i);
                    boolean diaEx = findDiagonalConflict(test, i, j);
                    if (rowEx || colEx || diaEx) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int findMinCol(int[][] test){ //finds column of minimum neighbor state
        int minCol = BOARD_SIZE;
        int minVal = BOARD_SIZE;
        int count = 0;

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(test[i][j] < minVal){
                    minVal = test[i][j];
                    minCol = j;
                }
                if(test[i][j] < heuristic){
                    count++;
                }
            }
        }
        neighbors = count;
        return minCol;
    }
    public int findMinRow(int[][] test){ //finds row of minimum neighbor state
        int minRow = BOARD_SIZE;
        int minVal = BOARD_SIZE;

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(test[i][j] < minVal){
                    minVal = test[i][j];
                    minRow = i;
                }
            }
        }
        return minRow;
    }
    public void moveQueen( ){  //moves a queen and determines to continue to a new state or restart or end.
        int[][] heuristicArray = new int[8][8];
        int colCount;
        int minCol;
        int minRow;
        int prevColQueen = 0;
        newMap = false;
        while (true) {
            colCount = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                System.arraycopy(map[i], 0, testMap[i], 0, 8);
            }
            while (colCount < BOARD_SIZE) {
                for (int i = 0; i < BOARD_SIZE; i++) {
                    testMap[i][colCount] = 0;
                }
                for (int i = 0; i < BOARD_SIZE; i++) {
                    if (map[i][colCount] == 1) {
                        prevColQueen = i;
                    }
                    testMap[i][colCount] = 1;
                    heuristicArray[i][colCount] = heuristic(testMap);
                    testMap[i][colCount] = 0;
                }
                testMap[prevColQueen][colCount] = 1;
                colCount++;
            }
            if (determineRestart(heuristicArray)) {
                activeQueen = 0;
                initializeMap();
                randomizeMap();
                System.out.println("RESTART");
                restarts++;
            }
            minCol = findMinCol(heuristicArray);
            minRow = findMinRow(heuristicArray);
            for (int i = 0; i < BOARD_SIZE; i++) {
                map[i][minCol] = 0;
            }
            map[minRow][minCol] = 1;
            moves++;
            heuristic = heuristic(map);
            if (heuristic(map) == 0) {
                System.out.println("\nCurrent State");
                for (int i = 0; i < BOARD_SIZE; i++) {
                    for (int j = 0; j < BOARD_SIZE; j++) {
                        System.out.print(map[i][j] + " ");
                    }
                    System.out.print("\n");
                }
                System.out.println("Solution Found!");
                System.out.println("State changes: " + moves);
                System.out.println("Restarts: " + restarts);
                break;
            }
            System.out.println("\n");
            System.out.println("Current h: " + heuristic);
            System.out.println("Current State");
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.print("\n");
            }
            System.out.println("Neighbors found with lower h: " + neighbors);
            System.out.println("Setting new current State");
        }
    }

    public boolean determineRestart(int [][] test){  // determines if restart is necessary
        int minVal = BOARD_SIZE;
        boolean restart = false;

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(test[i][j] < minVal){
                    minVal = test[i][j];
                }
            }
        }
        if(neighbors == 0){
            restart = true;
        }
        return restart;
    }
    public static void main(String[] args) {
        EightQueens one = new EightQueens( );
        one.randomizeMap();
        one.moveQueen();
    }
}