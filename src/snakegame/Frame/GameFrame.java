/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Frame;

import javax.swing.JFrame;

/**
 *
 * @author matya
 */
public class GameFrame extends JFrame {
    public GameFrame()
    {
       this.add(new GamePanel());
       this.setTitle("Snake");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       this.setResizable(false);
       this.pack();
       this.setVisible(true);
        
    }
}
