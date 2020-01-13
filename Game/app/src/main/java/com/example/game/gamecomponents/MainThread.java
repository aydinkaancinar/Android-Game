package com.example.game.gamecomponents;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class MainThread extends Thread {
    private final int maxFps = 31;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private final GamePanel gamePanel;
    private boolean running = true;
    private Canvas canvas;

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / maxFps;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (gamePanel != null && canvas != null) {  //Need to revise this!
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0)
                    sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == maxFps) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("Current FPS: " + averageFPS);
                System.out.println(Constants.PLAYER_COLOR);
            }
        }

    }
}
