/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package snakegame;
import snakegame.Frame.GameFrame;
import snakegame.Frame.MenuFrame;

/**
 * Creates application Frames
 * @author matya
 */
public class SnakeGame {


    public static void main(String[] args) {
        
        boolean gameloop = false;
  
        // create window with menu frame
        GameFrame game = new GameFrame(0);
        MenuFrame menu = new MenuFrame();

        while(true)
        {
            if (menu.StartGame())
            {
                game = menu.GameStartFrame();
                gameloop = true;

                while(gameloop)
                {            
                    if (game.IsGameOver())
                    {
                        menu.ExitGame();
                        menu.ShowMenu();
                        gameloop = false;
                    }
                }               
            }
            if (menu.ExitApp())
            {
                menu.ExitAppFrame();

                break;
            }
        } 
            
         
    }


    
}
