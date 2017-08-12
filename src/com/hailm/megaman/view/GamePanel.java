package com.hailm.megaman.view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends BasePanel implements Runnable, KeyListener {
    private Thread thread;

    private boolean isRunning;

    private InputManager inputManager;

    public GamePanel() {
        inputManager = new InputManager();
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
        setFocusable(true);
        addKeyListener(this);
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

}
