package controller;

import client.ClientPlay;
import client.ClientLogin;
import view.MainAgario;
import model.Circle;
import model.Game;
import server.Server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {
    public final static int PORT = 8000; //tcp port
    // public final static String SERVER_ADDRESS = "172.30.174.251";

    public final static String SERVER_ADDRESS = "localHost";

    private Socket socket;
    private final Game game;
    private boolean correctLogin;
    private String nickName;
    private int id;
    private final MainAgario mainAgario;

    public Controller(MainAgario mainAgario) {

        this.mainAgario = mainAgario;
        this.correctLogin = false;
        this.game = new Game();

        try {
            this.socket = new Socket(SERVER_ADDRESS, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int portRequest(String request) {

        int answer = -1;
        DataOutputStream out;
        DataInputStream in;

        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF(request);
            String message = in.readUTF();
            answer = Integer.parseInt(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public void login(String nickName) {
        int x = portRequest(Server.LOGIN);
        ClientLogin cls = new ClientLogin(this, nickName, x);
    }

    /*public void register(String mail, String password, String nickname) {
        int x = portRequest(Server.LOGIN);
        Client_Login_Signin cls = new Client_Login_Signin(this, mail, nickname, x);
    }*/

    public void startGame() {
        int portNum = portRequest(Server.PLAY);
        ClientPlay clientPlayThread = new ClientPlay(this, portNum);
        clientPlayThread.start();
    }

    private ArrayList<Circle> readPlayers(String[] infoPlayers) {
        ArrayList<Circle> players = new ArrayList<Circle>();

        for (int i = 0; i < infoPlayers.length; i++) {

            String[] player = infoPlayers[i].split("/");
            int id = Integer.parseInt(player[0]);
            String nickname = player[1];
            double radius = Double.parseDouble(player[2]);
            double posX = Double.parseDouble(player[3]);
            double posY = Double.parseDouble(player[4]);
            int rgb = Integer.parseInt(player[5]);

            boolean alive = player[6].equals("true");

            if (this.id == id && !alive) {
                return null;
            }

            Circle playerCircle = new Circle(nickname, id);
            playerCircle.setColor(new Color(rgb));
            playerCircle.setPosX(posX);
            playerCircle.setPosY(posY);
            playerCircle.setRadius(radius);
            playerCircle.setAlive(alive);
            players.add(playerCircle);
        }

        return players;
    }

    private ArrayList<Circle> readFood(String[] infoBalls) {
        ArrayList<Circle> food = new ArrayList<Circle>();

        for (int i = 0; i < infoBalls.length; i++) {
            String[] ballInfo = infoBalls[i].split("/");
            int rgb = Integer.parseInt(ballInfo[0]);
            double posX = Double.parseDouble(ballInfo[1]);
            double posY = Double.parseDouble(ballInfo[2]);

            /*try {
                posX = Double.parseDouble(ballInfo[1]);
                posY = Double.parseDouble(ballInfo[2]);
            } catch (Exception e) {
            }*/

            Circle bl = new Circle();
            bl.setColor(new Color(rgb));
            bl.setPosX(posX);
            bl.setPosY(posY);
            food.add(bl);
        }
        return food;
    }

    public void updateWorld(String[] infoPlayers, String[] infoBalls) {
        ArrayList<Circle> players = readPlayers(infoPlayers);
        if (players != null) {
            game.updateWorld(players, readFood(infoBalls), this.id);
        }
    }

    public void initializeWorld(String[] infoPlayers, String[] infoBalls) {
        ArrayList<Circle> players = readPlayers(infoPlayers);
        if (players != null) {
            game.setRunning(true);
            game.initializeWorld(players, readFood(infoBalls));
        }
    }

    public Circle getAvatar() {
        return game.getAvatar(this.id);
    }

    public void startMoving() {
        ThreadMoving m = new ThreadMoving(id, this.game);
        m.start();
    }

    public Game getGame() {
        return game;
    }


    public boolean isCorrectLogin() {
        return correctLogin;
    }

    public void setCorrectLogin(boolean correctLogin) {
        this.correctLogin = correctLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void showLose() {
        String message = "";
        message = "You Lose!";
        mainAgario.showMessage(message);
        System.out.println("lose");
    }

    public void showWait() {
        String message = "Please Wait";
        mainAgario.showMessage(message);
    }

    public void showWin() {
        String message = "You Win!";
        System.out.println("win");
        mainAgario.showMessage(message);
    }

    public boolean youWin() {
        return (getGame().getAvatars().size() == 1 && getGame().getAvatars().get(0).getId() == id);
    }

    public void cleanMessage() {
        mainAgario.showMessage("");
    }

}
