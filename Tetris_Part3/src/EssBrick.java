/**
 * This is the subclass that is responsible for the Ess Brick
 * Dubem Akukwe
 * 4/8/2024
 */

public class EssBrick extends TetrisBrick{
    
    public EssBrick(int center_column){
        colorNum = 1;
    }
    
    public void initPosition(int center_column){
        position = new int[][] { {0, center_column}, 
                                {0,center_column+1},
                                {1,center_column-1},
                                {1, center_column}};
    }
    
    public void rotate(){
        int centralRow = position[0][0];
        int centralCol = position[0][1];

        for (int segDex = 0; segDex < numSegments; segDex++) {
            //find the difference
            int diffBtwRow = position[segDex][0] - centralRow;
            int diffBtwCol = position[segDex][1] - centralCol;
            // to Shift 
            int newRow = -diffBtwCol;
            int newCol = diffBtwRow;
            //Re-assign
            if (segDex != 0){
                position[segDex][0] = centralRow - newRow;
                position[segDex][1] = centralCol - newCol;
            }
        }
    }
    
    public void unrotate(){
        for (int cycle = 0; cycle < 2; cycle++) {
            rotate();
        }
    }
}
