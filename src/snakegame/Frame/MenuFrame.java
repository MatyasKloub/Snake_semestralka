/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import snakegame.File.FileAction;
import snakegame.File.FileBinaryAction;
import snakegame.File.Score;
import snakegame.Input.InputControl;

/**
 * class representing Menu and it's functions (window - Frame)
 * @author matya
 */
public class MenuFrame extends JFrame implements ActionListener {
    
    private final JButton StartGame;
    private final JButton Exit;
    private final JButton ColorChooserButton;
    private final JButton SortByName;
    private final JButton Update;
    private final JTextArea Name;
    private final JTextArea Scores;
    private final JLabel label;
    private final JLabel hScores;
    private final JLabel controlLabel;
    private GameFrame game = new GameFrame(0);
    private final FileAction files = new FileAction();
    private final FileBinaryAction bFiles = new FileBinaryAction();
    private final JScrollPane scroll;
    

    private volatile boolean startGame = false;
    private volatile boolean exitApp = false;
    
    /**
     * initialization of menu frame
     */
    public MenuFrame()
    {
        files.Init();
        bFiles.InitBinary();
        
        this.setSize(450,360);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Game menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        StartGame = new JButton();
        StartGame.setText("Start Game");
        StartGame.addActionListener(this);
        StartGame.setLocation(60,60);
        StartGame.setSize(100,50);
        this.add(StartGame);
        
        controlLabel = new JLabel();
        controlLabel.setText("No special chararacters");
        controlLabel.setLocation(60,35);
        controlLabel.setSize(100,15);
        controlLabel.setForeground(Color.red);
        controlLabel.setVisible(false);
        this.add(controlLabel);
        
        Update = new JButton();
        Update.setText("Update table");
        Update.addActionListener(this);
        Update.setLocation(260, 260);
        Update.setSize(150,25);
        this.add(Update);
        
        Exit = new JButton();
        Exit.setText("Exit");
        Exit.addActionListener(this);
        Exit.setLocation(10,125);
        Exit.setSize(150,50);
        this.add(Exit);
        
        ColorChooserButton = new JButton();
        ColorChooserButton.setText("Pick snake color");
        ColorChooserButton.addActionListener(this);
        ColorChooserButton.setLocation(10,200);
        ColorChooserButton.setSize(150,50);
        this.add(ColorChooserButton);
        
        Name = new JTextArea();
        Name.setText("Player");
        Name.setLocation(60,15);
        Name.setSize(100,15);
        this.add(Name);
        
        label = new JLabel();
        label.setText("Name");
        label.setLocation(10,15);
        label.setSize(100,15);
        this.add(label);

        Scores = new JTextArea();
        Scores.setLocation(170, 35 );
        Scores.setSize(250,220);
        Scores.setEnabled(false);
        WriteHS(0);
        //this.add(Scores);
        
        scroll = new JScrollPane (Scores);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setSize(250,220);
        scroll.setLocation(170,35);
        this.add(scroll);
        
        hScores = new JLabel();
        hScores.setLocation(265,15);
        hScores.setSize(100,15);
        hScores.setText("High scores");
        this.add(hScores);
        
        SortByName = new JButton();
        SortByName.setText("Sort by name");
        SortByName.setSize(150, 25);
        SortByName.setLocation(100, 260);
        SortByName.addActionListener(this);
        this.add(SortByName);
               
        this.setVisible(true);
        
        
        // LocalDate Java API
        StringBuilder build = new StringBuilder();
        build.append("Application running, ").append("todays date ").append(LocalDate.now());
        System.out.println(build.toString());
        
    }
    
    /**
     * start of game
     * @return returns true if game was started, false otherwise
     */
    public boolean StartGame()
    {
        if (startGame)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Restart of game frame
     * @return Frame of game
     */
    public GameFrame GameStartFrame()
    {
        game.dispose();
        game = new GameFrame(Name.getText(),files);
        return game;
    }
    
    /**
     * if called, menu frame is shown
     */
    public void ShowMenu()
    {
        this.setVisible(true);
        this.remove(Update);
        files.Init();
        Scores.setText("");
        WriteHS(0);
        this.add(Update);
    }
    
    /**
     * when called game is exit
     */
    public void ExitGame()
    {
        startGame = false;
        
        game.dispose();
        game = new GameFrame(0);
        
    }
    
    /**
     * Writes highscore to table depending on @param
     * @param order 0 for descending order by score, 0 for descending order by names
     */
    public void WriteHS(int order)
    {
        if (order == 0)
        {
            List<Score> sc = files.sortByPoints();
            StringBuilder build = new StringBuilder();
            for (int i = sc.size()-1; i >= 0; i--) {
                build.append(sc.get(i).GetPoints()).append(" - ").append(sc.get(i).GetName()).append("\n");
            }
            Scores.setText(build.toString()); 
        }
        else
        {
            List<Score> sc = files.sortByName();
            StringBuilder build = new StringBuilder();
            for (int i = 0; i < sc.size(); i++) {
                 build.append(sc.get(i).GetPoints()).append(" - ").append(sc.get(i).GetName()).append("\n");
            }
            Scores.setText(build.toString());
            
        }
    }
    
    /**
     * continously checks if program is meant to be closed
     * @return true if program is closing, false otherwise
     */
    public boolean ExitApp()
    {
        if (exitApp)
        {
            return true;
        }
        return false;
    }
    
    /**
     * if program is meant to be closed, this is called, application closes
     */
    public void ExitAppFrame()
    {
        if (exitApp)
        {
            this.dispose();
        }
    }
    
    /**
     * after any action was performed (button click), check for button goes on
     * @param e JPanel ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == StartGame)
        {
            if (!InputControl.Input(Name.getText()))
            {
                controlLabel.setVisible(false);
                startGame = true;
                this.setVisible(false);
            }
            else 
            {
                controlLabel.setVisible(true);
            }
            
            
        }
        else if (e.getSource() == Exit)
        {
            game.dispose();
            exitApp = true;
        }
        else if (e.getSource() == ColorChooserButton)
        {
            Color initialcolor = Color.red;
            Color color= JColorChooser.showDialog(this,"Select a color of snake",initialcolor);  
            bFiles.SaveToBinary(color.getRed(), color.getGreen(), color.getBlue());
            ColorChooserButton.setBackground(color);
        }
        else if (e.getSource() == Update)
        {
            this.remove(Update);
            files.Init();
            Scores.setText("");
            WriteHS(0);
            this.add(Update);
            SwingUtilities.updateComponentTreeUI(this);
        }
        else if (e.getSource() == SortByName)
        {
            this.remove(Update);
            files.Init();
            Scores.setText("");
            WriteHS(1);
            this.add(Update);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
    
    
}
