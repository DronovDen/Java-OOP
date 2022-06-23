package controller;

import model.Game;
import model.Player;

import java.io.*;
import java.util.ArrayList;

public class DataBase {
    public final static String USERS_PATH = "src/main/resources/data/users.txt";
    public final static String GAME_PATH = "src/main/resources/data/game.txt";
    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args) {
        DataBase.registerUser("Denis");
    }

    public static void saveGame(Game game) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_PATH));
            oos.writeObject(game);
            oos.close();
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(String nickname) {
        readUsers();
        Player usr = new Player(nickname);
        players.add(usr);
        saveUsers();
    }

    public static void readUsers() {
        try {
            InputStream file = new FileInputStream(USERS_PATH);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            players = (ArrayList<Player>) input.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private static void saveUsers() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_PATH));
            oos.writeObject(players);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateLogin(String nickNameToCheck) {
        readUsers();
        boolean correct = false;
        for (Player compare : players) {
            if (compare.getNickname().equals(nickNameToCheck)) {
                correct = true;
                break;
            }
        }
        return correct;
    }

}
