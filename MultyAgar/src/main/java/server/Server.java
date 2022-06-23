package server;

import controller.DataBase;
import controller.ThreadInitialize;
import model.Circle;
import model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final static String LOGIN = "LOGIN";
    public final static String SIGN_IN = "SIGN_IN";
    public final static String PLAY = "PLAY";
    public final static String OBSERVE = "OBSERVE";

    // Answers
    public final static String LOGIN_OK = "LOGIN_OK";
    public final static String LOGIN_INCORRECT = "LOGIN_INCORRECT";

    // Ports
    public static int PORT_CONNECTION = 8000;// tcp
    public static int PORT_LOGIN = 9000;
    public static int PORT_PLAY = 8195;// tcp
    public static int PORT_STREAMING = 8580;// udp

    private final Game game;
    private int gamersNum;

    public Server() throws IOException {
        System.out.println("****** Server online ******");
        ServerSocket serverSocket = new ServerSocket(PORT_CONNECTION);

        game = new Game();
        int connectedGamers = 0;

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(new ThreadInitialize(this.game));

        /*ThreadInitialize th = new ThreadInitialize(this.game);
        th.start();*/

        while (connectedGamers < Game.MAX_PLAYERS) {
            Socket socket;
            socket = serverSocket.accept();

            service.execute(new AssignPort(socket, this));

            /*AssignPort assign = new AssignPort(socket, this);
            assign.start();*/

            connectedGamers++;
        }
    }

    public static void main(String[] args) {
        try {
            Server s = new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signIn(String nickname) {
        DataBase.registerUser(nickname);
    }

    public int validateLogin(String name) {
        boolean log = DataBase.validateLogin(name);
        if (log) {
            gamersNum++;
            int id = nextId();
            game.addAvatar(name, id);
            System.out.println("gamers: " + gamersNum);
            return id;
        } else {
            return -1;
        }
    }

    public int nextId() {
        int id_ = game.getIdAvailable();
        return id_;
    }

    public String sendBaseGame() {

        CopyOnWriteArrayList<Circle> gamers = game.getAvatars();
        String message = "";

        for (int i = 0; i < gamers.size(); i++) {

            if (gamers.get(i) != null) {
                String id = gamers.get(i).getId() + "";
                String nickname = gamers.get(i).getNickName();
                String radius = gamers.get(i).getRadius() + "";
                String posX = gamers.get(i).getPosX() + "";
                String posY = gamers.get(i).getPosY() + "";
                String rgb = gamers.get(i).getColor().getRGB() + "";
                String live = gamers.get(i).isAlive() ? "true" : "false";
                String player = "";

                if (i == gamers.size() - 1) {
                    player = id + "/" + nickname + "/" + radius + "/" + posX + "/" + posY + "/" + rgb + "/" + live;
                } else {
                    player = id + "/" + nickname + "/" + radius + "/" + posX + "/" + posY + "/" + rgb + "/" + live + ",";
                }

                message += player;
            }
        }

        message += "_";

        CopyOnWriteArrayList<Circle> food = game.getFood();

        for (int i = 0; i < food.size(); i++) {

            if (food.get(i) != null) {
                String rgb = food.get(i).getColor().getRGB() + "";
                String posX = food.get(i).getPosX() + "";
                String posY = food.get(i).getPosY() + "";
                String ball = rgb + "/" + posX + "/" + posY;

                if (i < food.size() - 1) {
                    ball += ",";
                }
                message += ball;
            }
        }
        return message;
    }


    public Game getGame() {
        return game;
    }

    public void updateGame(String[] player) {
        int id = Integer.parseInt(player[0]);
        double x = Double.parseDouble(player[1]);
        double y = Double.parseDouble(player[2]);

        boolean isAlive = player[3].equalsIgnoreCase("true");

        double radius = Double.parseDouble(player[4]);

        if (this.game.getAvatar(id) != null) {
            this.game.updatePlayer(id, x, y, isAlive, radius);
        }
    }

}