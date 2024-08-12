/**
 * This is an abstract class and the superclass for the classes of the seven 
   brick types
 * Dubem Akukwe
 * 4/26/2024
 */

import java.awt.*;

public abstract class TetrisBrick {
    
    protected int numSegments = 4;
    protected int[][] position; 
    protected Color color;
    protected int colorNum;
    
    public TetrisBrick(){
         
    }
      
    public int getColorNumber(){
        return colorNum;
    }
    
    public void moveLeft(){
        for (int colDex = 0; colDex <position.length; colDex++){
            position[colDex][1]-=1;
        }
    }
    
    public void moveRight(){
        for (int colDex = 0; colDex <position.length; colDex++){
            position[colDex][1]+=1;
        }
    }
    
    public void moveDown(){
        for (int rowDex = 0; rowDex <position.length; rowDex++){
            position[rowDex][0]+=1;
        }
    }
    
    public void moveUp(){
        for (int rowDex = 0; rowDex <position.length; rowDex++){
            position[rowDex][0] -=1;
        }
    }
    
    public abstract void initPosition(int center_column);
    
    public abstract void rotate();
    
    public abstract void unrotate();
    
}
