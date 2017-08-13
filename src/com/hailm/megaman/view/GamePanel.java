package com.hailm.megaman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import com.hailm.megaman.manager.ImageStore;

public class GamePanel extends BasePanel implements Runnable {
    private Thread thread;

    private KeyListener keyListener;

    private boolean isRunning;

    private InputManager inputManager;
    
    private BufferedImage image;
    public GamePanel() {
        inputManager = new InputManager();
        image = ImageStore.IMG_BG.getSubimage(2,5,30,100);
    }

    @Override
    public void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    @Override
    public void addComponents() {
        startGame();
    }

    @Override
    public void registerListener() {
        keyListener = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.processKeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.processKeyReleased(e.getKeyCode());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

        };
        setFocusable(true);
        addKeyListener(keyListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics2d.drawImage(image , 10, 10, this);
    }

    public void startGame() {
        if (thread == null) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {

        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;

        long period = 1000000000 / 80;
        int a = 1;
        while (isRunning) {

            // System.out.println("a= " + (a++));
            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try {

                if (sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else
                    Thread.sleep(period / 2000000);

            } catch (Exception e) {
            }

            previousTime = System.nanoTime();
        }

    }
}
