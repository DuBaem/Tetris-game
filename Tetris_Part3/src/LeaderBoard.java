/*
 *This class is responsible for tracking the leaderboard of the game
 *Dubem Akukwe
 *4/26/2024
 */

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class LeaderBoard extends JFrame {
    private TetrisGame game;
    private int numScores = 10;
    private int[] scoreArray ;//new int[numScores];
    //ArrayList or Array to the scores
    //add Score method int parameter
        //call the sort method as soon as you add the 
    //and a sort score method no parameter
        //for loops that will check to see if the new score is higher than the other scores
    //get scores return arrayaList
    
    public LeaderBoard(){        
        scoreArray = new int [numScores];
    }
    
    public void sortScores(int newScore){
//        int newScore =  score;
        Arrays.sort(scoreArray);
        for (int num: scoreArray){
            if (newScore > num){
                scoreArray[0] = newScore;
                System.out.println("ScoreArray: "+scoreArray[0]);
            }
         }
        Arrays.sort(scoreArray);
//         for (int dex =0; dex< scoreArray.length; dex++) {
//            if (newScore > scoreArray[dex]) {
//                // Shift lower scores down to make room for the new score
//                for (int dexLower = scoreArray.length - 1; dexLower > dex; dexLower--) {
//                    scoreArray[dexLower] = scoreArray[dexLower- 1];
//                }
//                scoreArray[dex] = newScore;
//                break;
//            }
//        }
    }
    
    public String giveScores(){
        int numbering= 1;
        String  displayCount = " ";
        for (int num= scoreArray.length-1; num>=0; num--){
            if(scoreArray[num]== 0){
                displayCount += numbering+". __\n";
            }
            else{
                displayCount += numbering+". "+scoreArray[num]+"\n";
            }
            numbering++;
        }
        return displayCount;
    }
}

