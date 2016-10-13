package com.example.kolas.mygame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by kolas on 07.11.2015.
 */
public class DrawGame extends Thread {
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap picture;
    public static Canvas canvas;
    GamePanel gamePanel;
    boolean pause;

    private long prevTime;

    public DrawGame(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {


        while (runFlag) {
            gamePanel.time += 0.5;//0.175666;
            while (pause)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            try {
                this.sleep(gamePanel.FSP);
            } catch (Exception e) {
            }


        }

    }
}

