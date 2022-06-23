package controller;

import model.Game;

import javax.swing.*;

public class ThreadCollision extends Thread {

    private final Game game;

    public ThreadCollision(Game game) {
        this.game = game;
    }

    public void run() {
        Timer timer = new Timer(100, e -> checkCollisions());
        timer.start();

        /*while (game.isRunning()) {
            try {
                checkCollisions();
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    public void checkCollisions() {
        if (game.isRunning()) {
            for (int i = 0; i < game.getAvatars().size(); i++) {
                for (int j = 0; j < game.getFood().size(); j++) {
                    if (game.getFood().get(j) != null) {
                        game.getAvatars().get(i).checkCollision(game.getFood().get(j));
                    }
                }
            }
            for (int i = 0; i < game.getAvatars().size(); i++) {
                for (int j = 0; j < game.getAvatars().size(); j++) {
                    if (game.getAvatars().get(j) != null && game.getAvatars().get(i) != null) {
                        game.getAvatars().get(i).checkCollision(game.getAvatars().get(j));
                    }
                }
            }

            game.deleteAvatars();
            game.deleteFood();
        }
    }
}
