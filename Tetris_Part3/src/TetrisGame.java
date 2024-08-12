/**
 * This class is responsible for the logic and decisions of the game 
 * Dubem Akukwe
 * 4/26/2024
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class TetrisGame {
    
    private TetrisBrick fallingBrick;
    private int [][] background;
    private int rows;
    private int cols;
    private int numBrickTypes;
    
    private int state;
    private int score;
    
    private Random randomGen;
    private LeaderBoard leadereBoard = new LeaderBoard();
    
    public TetrisGame(int rs, int cs){
        rows = rs;
        cols = cs;
        
        randomGen = new Random();
        background = new int [rows][cols];
        state = 1;
        initBoard();
        spawnBrick();
    }
    
   public String toString(){
        String fileInfo = "";
        int saveScore = getScore();
        fileInfo += saveScore+"\n";
        
        for( int row = 0; row < rows; row++){
            for( int col = 0; col < cols; col++){
                fileInfo += background[row][col]+" ";
            }
            fileInfo +="\n";
        }
        return fileInfo;
    }
    
    public void initBoard(){
        for (int rowDex= 0; rowDex < rows; rowDex++){
            for (int colDex= 0; colDex < cols; colDex++){
                background[rowDex][colDex] = -1;
            }
        } 
    }
    
    public void newGame(){
        initBoard(); 
        spawnBrick();
        score = 0;
        state = 1;
    }
    
    public int fetchBoardPosition(int row, int col){
        int filled = -1;
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return background[row][col];
        }
        else{
            return filled;
        }
    }
    
    public void transferColor(){
        for (int indexSeg = 0; indexSeg < fallingBrick.numSegments; indexSeg++){
            int segRow = fallingBrick.position[indexSeg][0]; // Get the actual row
            int segCol = fallingBrick.position[indexSeg][1];
            background[segRow][segCol] = fallingBrick.getColorNumber();
        }
    }
        
    private void spawnBrick(){
        int randomBrick = randomGen.nextInt(7);//check if it includes n
        int center_column = cols/2+1 ;
        if (cols % 2 == 0)
        {
            center_column = cols/2 ;
        }
        
        for (int col = 0; col < cols; col++){
            if (fetchBoardPosition(0, col) !=-1){
                state = 0;
            }
        }
        
        if (state ==1){
            if (randomBrick ==0){
                fallingBrick = new ElBrick(center_column);
            }
            else if(randomBrick ==1){
                fallingBrick = new EssBrick(center_column);
            }
            else if(randomBrick ==2){
                fallingBrick = new JayBrick(center_column);
            }
            else if(randomBrick ==3){
                fallingBrick = new LongBrick(center_column);
            }
            else if(randomBrick ==4){
                fallingBrick = new SquareBrick(center_column);
            }
            else if(randomBrick ==5){
                fallingBrick = new StackBrick(center_column);
            }
            else{
                fallingBrick = new ZeeBrick(center_column);
            }
            fallingBrick.initPosition(center_column);
        }
    }
    
    public void makeMove(char direction){
        
        if (direction == 'D'){
            fallingBrick.moveDown();
            if (validateMove() == false){
                fallingBrick.moveUp();
                transferColor();
                int minRow = findMinRow();
                int goneCount = 0;
                
                for (int startRow = findMaxRow(); startRow >= minRow; startRow-- ){
                    if (rowFull(startRow) == true){
                        copyAllRows(startRow);
                        startRow++;
                        minRow--;
                        goneCount +=1;
                    }
                }
                ScoreCount(goneCount);
                spawnBrick();
            }
        }

        else if (direction == 'T'){
            fallingBrick.rotate();
            if (validateMove() == false){
                fallingBrick.unrotate();
            }
        }

        else if (direction == 'L'){
            fallingBrick.moveLeft();
            if (validateMove() == false){
                fallingBrick.moveRight();
            }
        }
        
        else if (direction == 'R'){
            fallingBrick.moveRight();
            if (validateMove() == false){
                fallingBrick.moveLeft();
            }
        }        
    }
    
    private boolean validateMove(){
        for (int indexSeg = 0; indexSeg < fallingBrick.numSegments; indexSeg++){
            int segRow = fallingBrick.position[indexSeg][0]; // Get the actual row
            int segCol = fallingBrick.position[indexSeg][1];
            if (segCol < 0 || segCol >= cols){
                return false;
            }
            else if (segRow >= rows){
                return false;
            }
            else if (fetchBoardPosition(segRow,segCol) != -1){
                return false;
            }
        }
        return true;
    }
    
    public int getFallingBrickColor(){
        return fallingBrick.colorNum;
    }
    
    public int getNumberSegs(){
        return fallingBrick.numSegments;
    }
    
    public int getState() {
        return state;
    }

    public int getScore() {
        return score;
    }


    public int getSegRow(int segNum){
        return fallingBrick.position[segNum][0];
    }
    
    public int getSegCol(int segNum){
        return fallingBrick.position[segNum][1];
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getCols(){
        return cols;
    }
    
    public int findMaxRow(){
        int rowRange = getNumberSegs();
        int checkingRow = 0;
        int max = 0;
        for (int rowDex = 0; rowDex < rowRange; rowDex++ ){
            checkingRow = getSegRow(rowDex);
            if (checkingRow > max){
                max = checkingRow;
            }
        } 
        return max;
    }
    
    public int findMinRow(){
        int rowRange = getNumberSegs();
        int checkingRow = 0;
        int min = rows;
        for (int rowDex = 0; rowDex < rowRange; rowDex++ ){
            checkingRow = getSegRow(rowDex);
            if (checkingRow < min){
                min = checkingRow;
            }
        } 
        return min;
    }
    
    public boolean rowFull(int rowNumber){
              
        for (int col = 0; col < cols; col++) {
        if (background[rowNumber][col] == -1) {
            return false;
        }
    }
    return true;
    }
    
    public void checkFullRows() {
        for (int row = findMinRow(); row <= findMaxRow(); row++) {
            if (rowFull(row) == true) {
                copyRow(row);
                score+=1;
                row--;  // Decrement row index to check the same row index again after shift
            }
        }
    }

    private void copyRow(int row) {
        for (int col = 0; col < cols; col++) {
            background[row][col] = background[row-1][col];
        }
    }
    
    public void copyAllRows(int rowNumber){
        int count = 0;
        while (rowNumber > 0){
            copyRow(rowNumber);
            rowNumber--;
        }
    }
    
    public void ScoreCount(int goneCount){
        if (goneCount == 1){
            score += 100;
        }
        else if (goneCount ==2 ){
            score += 300;
        }
        else if (goneCount == 3){
            score += 600;
        }
        else if(goneCount ==4){
            score += 1200;
        }
        
    }

    public void SaveToFile(){
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showSaveDialog(null);
        
        if (selection == JFileChooser.APPROVE_OPTION){
            File takeFile = fileChooser.getSelectedFile();
            String fileName = takeFile.getAbsolutePath();
            
            if (!fileName.endsWith(".gam")){
                fileName +=".gam";
            }
            try{
                if (takeFile.exists() && takeFile.canWrite() == false){
                    System.err.println("Error: cannot make changes to file");
                    return;
                }
            
                FileWriter outWriter = new FileWriter(takeFile);
                outWriter.write(this.toString());
                outWriter.close();
                }
            catch(IOException ioe){
                    System.err.println("An error has occured. Failed to save file");
            }        
            
        }
    }
    
    public void RetrieveFromFile(){
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null);
        
        if (selection == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            try{
                Scanner scanFile = new Scanner(selectedFile);
                score = scanFile.nextInt();
                //int col = 0;
                if (scanFile.hasNextLine()){
                    for(int row = 0; row < rows; row++){
                        for(int col = 0; col < cols; col++){
                            background[row][col] = scanFile.nextInt();
                            System.out.println(background[row][col]);
                        }
                    }
                }
            }
            catch(IOException io){
                System.out.print("Trouble reading file");
                System.exit(0);
            }
        }
    } 
}