package com.hailm.megaman.view;

import java.awt.Color;

public class GamePanel extends BasePanel implements Runnable {
    private Thread thread;

    private boolean isRunning;

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
        // TODO Auto-generated method stub

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
            
            System.out.println("a= " + (a++));
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
