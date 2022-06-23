package client;

import controller.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientPlay extends Thread {

    //public final static String PATH = "./resources/data/userGame.txt";

    private final Controller controller;
    private Socket socket;

    public ClientPlay(Controller controller, int port) {
        this.controller = controller;
        try {
            this.socket = new Socket(Controller.SERVER_ADDRESS, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream input;
            DataOutputStream output;

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            String read = input.readUTF();
            while (read.equals("wait")) {
                controller.showWait();
                read = input.readUTF();
            }

            controller.cleanMessage();
            String[] infoBig = read.split("_"); //first goes info about players, then after "_" goes food info
            String[] infoPlayers = infoBig[0].split(",");
            String[] infoBalls = infoBig[1].split(",");

            controller.initializeWorld(infoPlayers, infoBalls);
            controller.startMoving();

            boolean control = true;
            while (control) {

                int id = controller.getId();
                if (controller.getGame().getAvatar(id) != null) {

                    double x = controller.getGame().getAvatar(id).getPosX();
                    double y = controller.getGame().getAvatar(id).getPosY();
                    boolean isPlaying = controller.getGame().getAvatar(id).isAlive();
                    double radius = controller.getGame().getAvatar(id).getRadius();
                    output.writeUTF(id + "/" + x + "/" + y + "/" + isPlaying + "/" + radius);

                    read = input.readUTF();

                    if (read.equals("time")) {
                        System.out.println("time");
                        controller.getGame().setRunning(false);
                        boolean win = controller.getGame().calculateWin(controller.getId());
                        if (win) {
                            controller.showWin();
                        } else {
                            controller.showLose();
                        }

                        break;
                    }

                    infoBig = read.split("_");
                    infoPlayers = infoBig[0].split(",");
                    infoBalls = infoBig[1].split(",");

                    controller.updateWorld(infoPlayers, infoBalls);


                    if (controller.youWin()) {
                        output.writeUTF("exit");
                        controller.showWin();
                        break;
                    }
                } else {
                    output.writeUTF("exit");
                    System.out.println("you lose");
                    controller.showLose();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
