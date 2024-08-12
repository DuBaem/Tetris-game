/*
 *This is the executable class which sets up the window and instantiates both 
  TetrisGame and TetrisDisplay objects
 *Dubem Akukwe
 *4/26/2024
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class TetrisWindow extends JFrame {
    
    private TetrisGame game;
    private TetrisDisplay display;
    private LeaderBoard leaderBoard;
    private int win_width = 500;
    private int win_height = 500;
    private int game_rows = 20;
    private int game_cols = 12;
    
    public TetrisWindow() 
    {
        this.setTitle("My Tetris Game                    \tD.Akukwe");
        this.setSize(win_width,win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new TetrisGame(game_rows,game_cols);
        display = new TetrisDisplay(game);
        leaderBoard = new LeaderBoard();
        this.add(display);
        MenuItems();
        this.setVisible(true);
    }
    
    public void MenuItems(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
       
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save");
        JMenuItem resumeGame = new JMenuItem("Resume");
        JMenuItem exitGame = new JMenuItem("Exit");
        
        JMenu leaderBoardMenu = new JMenu("Highscores");
        JMenuItem highScore = new JMenuItem("high score");

        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                game.newGame();
                leaderBoard.giveScores();
                repaint();
            }
        });
        saveGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                game.SaveToFile();
                leaderBoard.sortScores(game.getScore());
            }
        });
        
        resumeGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                game.RetrieveFromFile();
            }
        });
        
        exitGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                System.exit(0);
            }
        });
        
        highScore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String scoreList = leaderBoard.giveScores();
                JOptionPane.showMessageDialog(null, "High Scores:\n" + scoreList, 
                   "Scoreboard",JOptionPane.PLAIN_MESSAGE);
            }
        });

        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(resumeGame);
        gameMenu.add(exitGame);
        leaderBoardMenu.add(highScore);
        menuBar.add(gameMenu);
        menuBar.add(leaderBoardMenu);
        setJMenuBar(menuBar); 
    }
    
    public static void main(String[] args)
    {
        TetrisWindow myWindow = new TetrisWindow();
    }
}
