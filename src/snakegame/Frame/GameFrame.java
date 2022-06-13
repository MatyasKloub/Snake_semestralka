/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Frame;

import javax.swing.JFrame;
import snakegame.File.FileAction;

/**
 * Class representing Frame of game (window)
 * @author matya
 */
public class GameFrame extends JFrame {
    
    GamePanel gpanel;
   
    public GameFrame(int a) { }
    
    /**
     * initialization of game frame
     * @param pName player name
     * @param files FileAction files (loaded data)
     */
    public GameFrame(String pName, FileAction files)
    {
       gpanel = new GamePanel(pName,files);
      
       this.add(gpanel);
       this.setTitle("Snake");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       this.setResizable(false);
       this.pack();
       
       
       
       this.setVisible(true);
          
    }
    
    
    /**
     * Function checking if game ended and game window was requested to be shut down
     * @return true if yes, false if not
     */
    public boolean IsGameOver()
    {
        
        if (gpanel.ExitGame())
        {
            return true;
        }
        return false;
    }

 
}
