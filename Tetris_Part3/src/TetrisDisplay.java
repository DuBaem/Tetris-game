/**
 * This class creates the graphics of the game
 * Dubem Akukwe
 * 4/26/2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {
    
    private TetrisGame game;
    private int start_x = 180;
    private int start_y = 100;
    private int cell_size = 10;
    private int delay = 200; 
    private boolean pause = false;
    private Timer timer;
    private Color[] colors = {Color.red,Color.GREEN,new Color(226,234, 244),
                                Color.BLUE, Color.YELLOW, 
                                new Color(242, 16, 200), Color.CYAN};
    
    public TetrisDisplay(TetrisGame gam){
        game = gam;
        
        timer = new Timer(delay, new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            } 
        });
        
        addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                translateKey(ke); 
            }
        });  
       
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g)
    {
        super. paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        drawWell(g);
        drawBackground(g);
        drawFallingBrick(g);
        drawScore(g);
        
        if(game.getState() == 0){
            drawGameOver(g);
        }
    }
        
    private void drawFallingBrick(Graphics g){
        
        for (int dex = 0; dex < game.getNumberSegs(); dex++){
            //draw colored brick
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(start_x + (game.getSegCol(dex) * cell_size),
                    start_y + game.getSegRow(dex) * cell_size,
                cell_size, cell_size);
            
            //outline of brick
            g.setColor(Color.BLACK);
            g.drawRect(start_x + (game.getSegCol(dex) * cell_size),
                    start_y + game.getSegRow(dex) * cell_size,
                cell_size, cell_size);
        }
    }
      
    private void drawWell(Graphics g){
        int backgroundWid = cell_size * (game.getCols());
        int wellHei = cell_size * game.getRows();
        
        //Draw Background
        g.setColor(Color.WHITE);//white
        g.fillRect(start_x,start_y,
                backgroundWid, wellHei);
        
        g.setColor(Color.BLACK);//black
        //Draw left
        g.fillRect(start_x - cell_size, start_y, cell_size, 
              wellHei);
        //Draw right
        g.fillRect(start_x + backgroundWid, start_y, cell_size, 
              wellHei);
        //Draw bottom
        g.fillRect(start_x - cell_size, start_y +(wellHei),backgroundWid+(2*cell_size), 
              cell_size);  
    }
    
    private void drawBackground(Graphics g){
        for (int row = 0; row < game.getRows(); row++) 
        {
            for (int col = 0; col < game.getCols(); col++) 
            {
                int cellColStart = start_x + (col * cell_size);
                int cellRowStart = start_y + (row * cell_size);
                int colorIndex = game.fetchBoardPosition(row, col);
                if ( colorIndex != -1) 
                { 
                    g.setColor(colors[colorIndex]);
                    g.fillRect(cellColStart, cellRowStart, cell_size, cell_size);
                    g.setColor(Color.BLACK); // Draw border for each cell
                    g.drawRect(cellColStart, cellRowStart, cell_size, cell_size);
                }
            }
        }
    }
    
    private void drawScore(Graphics g){
        int xStart = 35;
        int yStart = 50;
                
        int font_Size = 30;
        Font bigFont = new Font ("Arial", 1, font_Size);
        g.setFont(bigFont);
        g.drawString("Score: "+ game.getScore(), xStart,yStart);
        //lab.setFont(bigFont);        
    }
    
    private void drawGameOver(Graphics g){
        int boxHei = 75;
        int boxWid = 180;
        
        int xStart = 150;
        int yStart = 150;
        
        int stringX = 150;
        int stringY = 190;
        
        int font_Size = 30;
        Font bigFont = new Font ("Arial", 1, font_Size);
        g.setFont(bigFont);
        
        g.setColor(Color.RED); // Draw border for each cell
        g.fillRect(xStart, yStart , boxWid, boxHei);
        g.setColor(Color.BLACK);
        g.drawString("Game Over!", stringX,stringY);
    }
    
    private void translateKey(KeyEvent ke){
        int code = ke.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SPACE:
                pause = !pause;
                break;
            case KeyEvent.VK_UP:
                game.makeMove('T');
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('L');
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove('R');
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;
            case KeyEvent.VK_I:
                delay = delay/2;
                timer.setDelay(delay);
                break;
            case KeyEvent.VK_R:
                delay = delay*2;
                timer.setDelay(delay);
                break;                
        }
        repaint();
    }
      
    private void cycleMove()
    {
        if (pause != true){
            game.makeMove('D'); 
            repaint();
        }
    }
}