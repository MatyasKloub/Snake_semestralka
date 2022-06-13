/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.File;

/**
 * Class representing score of player, containing Points and Name
 * @author matya
 */
public class Score implements Comparable<Score>{
    
    private Integer points;
    
    private String name;
    
    public Score() { }
    
    /**
     * initialization of object
     * @param points score of player
     * @param name name of player
     */
    public Score(int points, String name)
    {
        this.points = points;
        this.name = name;
    }
    
    /**
     * Getter for points
     * @return score
     */
    public int GetPoints()
    {
        int points = this.points;
        return points;         
    }
    
    /**
     * Getter for name
     * @return name
     */
    public String GetName()
    {
        String name = this.name;
        return name;
    }
    
    /**
     * Function comparing names
     * @param o Object we want to compare with
     * @return 0 if both are same, higher than 0 if string has more chars, lesser than 0 if string has less chars
     */
    @Override
    public int compareTo(Score o) {
        return this.name.compareTo(o.GetName());
    }
    
    
    
    
}
