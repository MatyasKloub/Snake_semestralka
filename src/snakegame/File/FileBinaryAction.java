/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.File;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class working with binary files
 * @author matya
 */
public class FileBinaryAction {
    
    private final String path;

    /**
     * initialization of class
     */
    public FileBinaryAction() {
        this.path = "data/colors.bin";
    }
    
    // work with binary files
    
    /**
     * Loading color of snake from binary file
     * @return Color made from 3 binary saved numbers (RGB)
     */
    public Color LoadFromBinary()
    {
        int color[] = new int[3];
        try 
        {
            FileInputStream fis = new FileInputStream(path);
            DataInputStream is = new DataInputStream(fis);
            
            for (int i = 0; i < color.length; i++) {
                color[i] = is.readInt();
            }
            
            fis.close();
        }
        catch (IOException e)
        {
            // file not found. Should never happen unless user delets while program is running!!
            return null;
            
        }
        
        return new Color(color[0], color[1], color[2]);
    }
    
    /**
     * Function saving color into binary file
     * @param r color representing red from RGB spectre
     * @param g color representing green from RGB spectre
     * @param b color representing blue from RGB spectre
     */
    public void SaveToBinary(int r, int g, int b)
    {
        try {
            // create r writer
            FileOutputStream fos = new FileOutputStream(new File(path));
            DataOutputStream os = new DataOutputStream(fos);
            
            // write data to file
            os.writeInt(r);
            os.writeInt(g);
            os.writeInt(b);
            // close the writer
            fos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * initialization of binary file
     */
    public void InitBinary()
    {
        // init color is color.green - if file doesnt exist
        File f = new File(path);
        if(!f.exists() && !f.isDirectory()) 
        { 
            Color c = Color.GREEN;
            SaveToBinary(c.getRed(),c.getGreen(),c.getBlue());
        }
        
    } 
           
    
    
    
    
    
    
}
