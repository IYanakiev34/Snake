package com.company;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private final int HEIGHT = 720;
    private final int WIDTH = 1080;

    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new GamePanel());
        this.pack();
        this.setVisible(true);
    }
}
