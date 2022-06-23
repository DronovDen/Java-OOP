package controller;

import model.Game;

public class ThreadInitialize extends Thread {
    private final Game game;
    private Long startTime;

    public ThreadInitialize(Game game) {
        this.game = game;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (!game.isRunning()) {

            System.out.println("Avatars size: " + game.getAvatars().size());
            if (game.getAvatars().size() == Game.MAX_PLAYERS) {
                System.out.println("MAX PLAYERS LIMIT REACHED!");
                game.startGame();
            }

            Long current = System.currentTimeMillis();
            long difference = current - startTime;

            if (difference >= Game.TIME_START) {
                if (game.getAvatars().size() >= Game.MIN_PLAYERS) {
                    System.out.println("WAITING TIME IS OVER!");
                    //System.out.println("Avatars size: " + game.getAvatars().size());
                    game.startGame();
                }
            }
        }

        System.out.println("*********** Game was Started ************");
        startTime = System.currentTimeMillis();
        while (game.isRunning()) {

            Long current = System.currentTimeMillis();
            long difference = current - startTime;
            if (difference >= Game.TIME_OUT) {
                game.setRunning(false);
            }
        }
    }
}
