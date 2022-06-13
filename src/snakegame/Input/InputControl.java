/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used for input control
 * @author matya
 */
public final class InputControl {
    
    /**
     * Control of @param
     * @param Input if @param contains special characters
     * @return returns true if yes, false otherwise
     */
    public static boolean Input(String Input)
    {
        
        Pattern pat = Pattern.compile("[^A-Za-z0-9]");  
        Matcher matcher = pat.matcher(Input);
        boolean containsSpecial = matcher.find();
        return containsSpecial;                
    }
    
}
