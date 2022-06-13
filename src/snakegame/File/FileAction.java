/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Save, Load to CSV + Sorts of data
 * @author matya
 */
public class FileAction {

    private boolean message = false;
    
    private final List<Score> score = new ArrayList<>();
    
    private final String path;

    /**
     * Initiation of object, assigns path
     */
    public FileAction() {
        this.path = "data\\game_score.csv";
    }
     
    /**
     * checks if message was already written to console in order to avoid duplication of texts
     * @return true if message was already sent, false if not
     */
    public boolean messageWritten()
    {
        return message;
    }
    
    /**
     * Save into file that is specified inside, can be upgraded for multiple saves!
     * @param Score int number of score hit
     * @param Name string containing name of player
     * @return true if saving went successfuly, false if not
     */
    public boolean Save(int Score, String Name)
    {
        return SaveToCsv(Score, Name);
    }
    
    /**
     * Loads data from CSV into Score
     * @return returns true if data were successfuly loaded, false if not
     */
    private boolean LoadFromCsv()
    {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        StringBuilder build = new StringBuilder();
        
        try 
        {
            File Dir = new File("data");
            if (!Dir.exists())
            {
                Dir.mkdirs();
            }
            
            File myObj = new File(path);
            if (myObj.createNewFile()) 
            {
                build.append("File created at: ").append(s).append(File.separator).append(path);
                System.out.println(build.toString());
            } 
            else
            {
                build.append("File already exists at: ").append(s).append(File.separator).append(path);
                if (!message)
                {
                    System.out.println(build.toString());
                    message = true;
                }
                   
                
                if (myObj.length() != 0)
                {
                    Scanner sc = new Scanner(myObj);
                    sc.useDelimiter(";");
                    String[] parts;
                    String str = "";
                    Score scoreObj;
                    while(sc.hasNext())
                    {   
                        str = sc.next();
                        parts = str.split("#");
                        scoreObj = new Score(Integer.parseInt(parts[0]), parts[1]);
                        this.score.add(scoreObj);
                        
                    }
                    sc.close();
               
                }
                else
                {
                    //System.out.println("File is empty");
                } 
               
            }
            return true;
        }
        catch (IOException e)
        {
            // since name is set in code, this should never occur!
            System.out.println("Invalid name of file");
            return false;
        }
        catch (SecurityException e)
        {
            System.out.println("Application doesn't have rights to create save, run as administrator please.");
            return false;
        }
    }
    
    
    /**
     * Saves score data to csv in folder "data"
     * @param sc int score of player
     * @param nam name of player in string
     * @return true if saving went successfuly, false if not
     */
    private boolean SaveToCsv(int sc, String nam)
    {
        try 
        {
            Score scor = new Score(sc,nam);
            score.add(scor);
            
            StringBuilder build = new StringBuilder();
            // create a writer
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            
            for (int i = 0; i < score.size(); i++) {
                build.append(score.get(i).GetPoints()).append("#").append(score.get(i).GetName());
                if(i != score.size()-1)
                {
                    build.append(";");
                }
            }
            
         

            writer.write(build.toString());
            //close the writer
            writer.close();
            return true;

        }
        catch (IOException ex) 
        {
            // This error should never occur!! File is created in Init function!
            System.out.println("Access to file denied");
            return false;
        }

    }
    
    /**
     * Loads data from CSV into private score
     */
    public void Load()
    {
        LoadFromCsv();
    }
    
    /**
     * Initialization of score
     * @return true if load went successfuly, false if not
     */
    public boolean Init()
    {
        score.clear();
        return LoadFromCsv();
    }

    /**
     * sorts list in descending order by points
     * @return sorted list by points
     */
    public List<Score> sortByPoints() {
        List<Score> copy = new ArrayList<Score>(score);
        Collections.sort(copy, (Score s1, Score s2) ->
        Integer.compare(s1.GetPoints(), s2.GetPoints()));
        return copy;
    }
    
    /**
     * sorts list in descending order by name
     * @return sorted list by name
     */
    public List<Score> sortByName()
    {
        List<Score> copy = new ArrayList<Score>(score);
        Collections.sort(copy, (Score s1, Score s2) ->
        s1.compareTo(s2));
        return copy;
        
    }
  
}
 