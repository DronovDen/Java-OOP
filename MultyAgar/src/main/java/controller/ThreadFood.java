package controller;

import model.Circle;
import model.Game;

import javax.swing.*;


public class ThreadFood extends Thread {
    private final Game game;

    public ThreadFood(Game game) {
        this.game = game;
    }

    /**
     * Creates a new circle and adds it to the food ArrayList every 2.5 seconds. The
     * circle has a radius of 10 and a random color.
     */
    @Override
    public void run() {

        Timer timer = new Timer(1500, e -> addFood());
        timer.start();

        /*while (game.isRunning()) {
            game.getFood().add(new Avatar());
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/
    }

    private void addFood() {
        game.getFood().add(new Circle());
    }
}
