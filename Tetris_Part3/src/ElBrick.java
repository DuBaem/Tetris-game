/**
 * This is the subclass that is responsible for the El Brick
 * Dubem Akukwe
 * 4/8/2024
 */

public class ElBrick extends TetrisBrick {
    
    public ElBrick(int center_column){
        colorNum = 0;
    }
    
    public void initPosition(int center_column){
        position = new int[][] { {0, center_column-1}, 
                                {0,center_column},
                                {0,center_column+1},
                                {1, center_column-1}};
    }
    
    public void rotate(){
        int centralSegRow = position[1][0];
        int centralSegCol = position[1][1];

        for (int segDex = 0; segDex < numSegments; segDex++) {
            //find the difference
            int diffBtwRow = position[segDex][0] - centralSegRow;
            int diffBtwCol = position[segDex][1] - centralSegCol;
            // to Shift 
            int newRow = -diffBtwCol;
            int newCol = diffBtwRow;
            // Re-assign
            position[segDex][0] = centralSegRow - newRow;
            position[segDex][1] = centralSegCol - newCol;
        }
    }
    
    public void unrotate(){
        int rotateTimes = 3;
        for (int cycle = 0; cycle < rotateTimes; cycle++) {
            rotate();
        }
    }
}
