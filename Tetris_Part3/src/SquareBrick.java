/**
 * This is the subclass that is responsible for the Square Brick
 * Dubem Akukwe
 * 4/8/2024
 */
public class SquareBrick extends TetrisBrick{
    
    public SquareBrick(int center_column){
        colorNum = 4;
    }
    
    public void initPosition(int center_column){
        position = new int[][] { {0, center_column-1}, 
                                {0,center_column},
                                {1,center_column-1},
                                {1, center_column}};
    }
    
    public void rotate(){
        
    }
    
    public void unrotate(){
        
    }
}
