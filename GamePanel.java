package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 720;
    private static final int UNIT_SIZE = 40;
    private static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    private static final int DELAY = 75;

    //holding coordinates of the snake
    private final int[] xBodyParts = new int[GAME_UNITS];
    private final int[] yBodyParts = new int[GAME_UNITS];

    private int bodyParts = 6;
    private int applesEaten = 0;
    private int appleX;
    private int appleY;

    private char direction = 'R';
    private boolean running = false;

    private Timer timer;
    private Random random;
    public GamePanel (){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    private void draw(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                //draws the grid
                //first two coords are the starting point of the line, second two coords are the end points of the line
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.GREEN);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.fillRect(xBodyParts[i], yBodyParts[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.fillRect(xBodyParts[i], yBodyParts[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    private void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    private void move(){
        for(int i = bodyParts; i > 0 ; i--){
            xBodyParts[i] = xBodyParts[i-1];
            yBodyParts[i] = yBodyParts[i-1];
        }
        switch (direction){
            case 'U':
                yBodyParts[0] = yBodyParts[0]-UNIT_SIZE;
                break;
            case 'D':
                yBodyParts[0] = yBodyParts[0]+UNIT_SIZE;
                break;
            case 'L':
                xBodyParts[0] =xBodyParts[0]-UNIT_SIZE;
                break;
            case 'R':
                xBodyParts[0] =xBodyParts[0]+UNIT_SIZE;
                break;
            default:
                break;
        }
    }
    private void checkApple(){
        if((xBodyParts[0] == appleX && yBodyParts[0] == appleY)){
            bodyParts ++;
            applesEaten ++;
            newApple();
        }
    }
    private void checkCollisions(){
        if(xBodyParts[0] < 0 || xBodyParts[0]>SCREEN_WIDTH || yBodyParts[0] < 0 || yBodyParts[0] > SCREEN_HEIGHT){
            running = false;
        }
        for(int i = bodyParts; i > 0 ; i--){
            if((xBodyParts[0] == xBodyParts[i]) && (yBodyParts[0] == yBodyParts[i])){
                running = false;
            }
        }
        if(!running){
            timer.stop();
        }
    }
    private void gameOver(Graphics g){
        // game over text for the player to see
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
            }
        }
    }
}
