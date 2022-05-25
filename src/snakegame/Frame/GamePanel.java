/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import snakegame.GameObject.GameObj;
import snakegame.GameObject.IMove;
/**
 *
 * @author matya
 */
public class GamePanel extends JPanel implements ActionListener, IMove {
    
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGTH = 600;
    private static final int UNIT_SIZE = 15; // random number used to make things smaller/bigger
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGTH)/UNIT_SIZE; // scaling of units on screen
    private static final int DELAY = 140; // slower, faster game - pace
    
    
    
    // snake parts
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private int countBodyParts = 3; // start body parts
    
    private int applesEaten;
    private GameObj apple = new GameObj();
    
    // direction snake is facing == will move that dir
    private char faceDirection = 'R'; // 'L', 'U'..
    
    // if game is supposed to run any more
    private boolean running = false;
    
    // counting time, not yet used
    private Timer timer;
    // used for random apple spawn
    private Random random;
    
    
    
    GamePanel()
    {
       
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    public void startGame()
    {
        spawnApple();
        running = true; 
        timer = new Timer(DELAY,this);   
        timer.start();
    }
    
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    
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
                    g.setColor(Color.MAGENTA);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);           
                }
            }
            
        
        }
        else
        {
            gameOver(g);
        }
    }
    
    public void spawnApple()
    {
        apple.x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        apple.y = random.nextInt((int)(SCREEN_HEIGTH/UNIT_SIZE))*UNIT_SIZE;
    }
    
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
    
    public void checkApple()
    {
        if (x[0] == apple.x && y[0] == apple.y)
        {
            countBodyParts++;
            applesEaten++;
            spawnApple();
        }
    }
    
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
    
    public void gameOver(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGTH/2);
    }
    
    
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
    
    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if (faceDirection != 'R')
                    {
                        faceDirection = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (faceDirection != 'L')
                    {
                        faceDirection = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (faceDirection != 'D')
                    {
                        faceDirection = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (faceDirection != 'U')
                    {
                        faceDirection = 'D';
                    }
                    break;
                
            }
        }
    }
    
    
}
