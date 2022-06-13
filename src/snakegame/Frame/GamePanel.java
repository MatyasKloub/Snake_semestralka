/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import snakegame.File.FileAction;
import snakegame.File.FileBinaryAction;
import snakegame.GameObject.GameObj;
import snakegame.GameObject.IMove;

/**
 * Class representing game of snake itself, full logic
 * @author matya
 */
public class GamePanel extends JPanel implements ActionListener, IMove {
    
  
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGTH = 600;
    private static final int UNIT_SIZE = 15; // random number used to make things smaller/bigger
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGTH)/UNIT_SIZE; // scaling of units on screen
    private static final int DELAY = 140; // slower, faster game - pace
    
    
    
    // snake parts
    private int x[] = new int[GAME_UNITS];
    private int y[] = new int[GAME_UNITS];
    private int countBodyParts = 3; // start body parts
    
    private int applesEaten;
    private GameObj apple = new GameObj();
    
    // direction snake is facing == will move that dir
    private char faceDirection = 'R'; // 'L', 'U'..
    
    // if game is supposed to run any more
    private volatile boolean running = false;
    
    // exit back to menu was requested
    private boolean requestExit = false;
    
    // counting time, not yet used
    private Timer timer;
    // used for random apple spawn
    private Random random;
    
    // color of snake
    private Color snakeCol = Color.GREEN;
    
    // object used for binary operations
    private FileBinaryAction fBin = new FileBinaryAction();
    
    private FileAction fAction;
    private boolean saved = false;
    
    // name of player
    private String name = "";
    
    /**
     * initialization of game
     * @param pName player name, if empty "Player" will be filled instead
     * @param files FileAction passed from menu
     */
    GamePanel(String pName, FileAction files)
    {  
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        fAction = files;
        snakeCol = fBin.LoadFromBinary();
        
        if (name.length() == 0)
        {
            name = "Player";
        }
        else
        {
            name = pName;
        } 
        startGame();
    }
    
    /**
     * Function initializating start of game
     */
    public void startGame()
    {
        spawnApple();
        running = true; 
        timer = new Timer(DELAY,this);   
        timer.start();
    }
    
    
    /**
     * function being called by JPanel, calls "draw" function consinstently, depending on timer from START
     * @param g Graphical component of JPanel
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    
    /**
     * function that draws "canvas" and game itself, drawing grid for better playability
     * @param g Graphics component of JPanel
     */
    public void draw(Graphics g)
    {
        if (running)
        {           
            // grid alike
            for (int i = 0; i < SCREEN_HEIGTH/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGTH);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            
            // create apple  
            g.setColor(Color.red);
            g.fillOval(apple.x, apple.y, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < countBodyParts; i++) {
                //head
                if (i == 0)
                {
                    g.setColor(new Color(0,94,42));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else
                {
                    g.setColor(snakeCol);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);           
                }
            }
            
        
        }
        else
        {
            gameOver(g);
        }
    }
    
    /**
     * Function that spawns apples at random places around the board
     */
    public void spawnApple()
    {
        apple.x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        apple.y = random.nextInt((int)(SCREEN_HEIGTH/UNIT_SIZE))*UNIT_SIZE;
    }
    
    /**
     * function that "moves" snake on grid by drawing parts ahead, starting with head
     */
    @Override
    public void move()
    {
        for (int i = countBodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];           
        }
        
        switch(faceDirection)
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    
    /**
     * function that checks if apple was eaten
     */
    public void checkApple()
    {
        if (x[0] == apple.x && y[0] == apple.y)
        {
            countBodyParts++;
            applesEaten++;
            spawnApple();
        }
    }
    
    /**
     * function checking if snake collided with itself or game borders
     */
    @Override
    public void checkCollisions()
    {
        // kolize s telem hada
        for (int i = countBodyParts; i > 0; i--) {
            if ((x[0] == x[i] && y[0] == y[i]))
            {
                running = false;
            }
        }
        
        // left border
        if (x[0] < 0)
        {
            running = false;
        }
        else if (x[0] > SCREEN_WIDTH)
        {
            // right
            running = false;
        }
        else if (y[0] < 0)
        {
            // top
            running = false;
        }
        else if (y[0] > SCREEN_HEIGTH)
        {
            // bottom
            running = false;
        }
        
        if (!running)
        {
            timer.stop();
        }
        
    }
    
    
    /**
     * When game is over, sets Game Over screen with score and menu or restart
     * @param g Graphics from JPanel
     */
    public void gameOver(Graphics g)
    {
        
        if (!saved)
        {
            fAction.Save(applesEaten,name);
            saved = true;
        }
        
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/4 + 45,SCREEN_HEIGTH/4 - 10);
        g.setColor(Color.blue);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/3 + 20,SCREEN_HEIGTH/3);
        
        g.setColor(Color.black);
        g.drawString("R - restart", (SCREEN_WIDTH - metrics.stringWidth("R - restart"))/2, SCREEN_HEIGTH/2);
        g.drawString("M - menu", (SCREEN_WIDTH - metrics.stringWidth("M - menu"))/2 - 14, SCREEN_HEIGTH/2 + 35);
        
        
    }
    
    /**
     * if game is running snake is automatically moving (performing action) in direction he is facing
     * @param e ActionEvent of JPanel
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       if (running)
       {
           move();
           checkApple();
           checkCollisions();
           
       }
       
       //update
       repaint(); 
    }
    
    
    /**
     * Class defining KeyAdapter, used for detecting keys pressed
     */
    public class MyKeyAdapter extends KeyAdapter
    {
        /**
         * if arrows, r, or m button were pressed something will happen depending if game is running or not
         * @param e KeyEvent, Component of KeyAdapter
         */
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if (!Character.toString(faceDirection).equals(String.valueOf(faceDir.R)))
                    {
                        faceDirection = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!Character.toString(faceDirection).equals(String.valueOf(faceDir.L)))
                    {
                        faceDirection = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!Character.toString(faceDirection).equals(String.valueOf(faceDir.D)))
                    {
                        faceDirection = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!Character.toString(faceDirection).equals(String.valueOf(faceDir.U)))
                    {   
                        faceDirection = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    if (!running)
                    {
                        // restart game
                        restartGamePanel();
                    }
                    break;
                case KeyEvent.VK_M:
                    if (!running)
                    {
                        requestExit = true;
                    }
                    break;
                       
                   
                       
                
            }
        }
    }
    
    
    /**
     * function checking if game ended and is requested shut down of window
     * @return true if yes, false if not
     */
    public boolean ExitGame ()
    {
 
        if (!running && requestExit)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Function restarting game
     */
    public void restartGamePanel()
    {
        
        running = true;
        countBodyParts = 3;
	applesEaten = 0;
        spawnApple();
        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];
	x[0] = 0;
	y[0] = 0;
	faceDirection = 'R';
	timer.restart();
	repaint();
        saved = false;
        
        this.remove(this);
    }
    
    
}
