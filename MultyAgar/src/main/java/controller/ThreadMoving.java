package controller;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class ThreadMoving extends Thread {
    
    private static final int INTERVALO = 100;

    private int id;
    private final Game game;
    private double xFinal, yFinal;

    public ThreadMoving(int id, Game game) {
        this.id = id;
        this.game = game;
        this.xFinal = 0;
        this.yFinal = 0;
    }

    @Override
    public void run() {
        Timer moveRate = new Timer(100, e->update());
        moveRate.start();

        /*while (game.getAvatar(id) != null && game.getAvatars().size() > 1 && game.isRunning()) {
                this.updateMousePosition();
                game.move(xFinal, yFinal, id);
            try {
                Thread.sleep(INTERVALO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void update(){
        if(game.getAvatar(id) != null && game.getAvatars().size() > 1 && game.isRunning()){
            this.updateMousePosition();
            game.move(xFinal, yFinal, id);
        }
    }

    private void updateMousePosition() {

        if (game.getAvatar(id) != null) {
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            this.xFinal = (mouse.x - game.getAvatar(id).getPosX()) / 250;
            this.yFinal = (mouse.y - game.getAvatar(id).getPosY()) / 250;

            if (this.xFinal < 0) {
                this.xFinal -= 4;
            }
            if (this.yFinal < 0) {
                this.yFinal -= 4;
            }
            if (this.yFinal > 1) {
                this.yFinal += 4;
            }
        }
    }
}
