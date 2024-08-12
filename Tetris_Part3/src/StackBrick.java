/**
 * This is the subclass that is responsible for the Stack Brick
 * Dubem Akukwe
 * 4/8/2024
 */

public class StackBrick extends TetrisBrick{
    
    public StackBrick(int center_column){
        colorNum = 5;
    }
    
    public void initPosition(int center_column){
        position = new int[][] { {0, center_column-1}, 
                                {0,center_column},
                                {0,center_column+1},
                                {1, center_column}};
    }
    
    public void rotate(){
        int centralRow = position[1][0];
        int centralCol = position[1][1];

        for (int segDex = 0; segDex < numSegments; segDex++) {
            //find the difference
            int diffBtwRow = position[segDex][0] - centralRow;
            int diffBtwCol = position[segDex][1] - centralCol;
            
            // to Shift 
            int newRow = -diffBtwCol;
            int newCol = diffBtwRow;
            //Re-assign
            position[segDex][0] = centralRow - newRow;
            position[segDex][1] = centralCol - newCol;            
        }
    }
    
    public void unrotate(){
        int rotateTimes = 3;
        for (int cycle = 0; cycle < rotateTimes; cycle++) {
            rotate();
        }
    }
}
