package client;

import controller.Controller;
import server.Server;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientLogin {
    private final Controller controller;
    private final int port;
    //private final boolean login;
    private final String nickname;

    public ClientLogin(Controller controller, String nickname, int port) {
        this.controller = controller;
        this.nickname = nickname;
        this.port = port;
        //this.login = false;
        connection();
    }

    /*public Client_Login_Signin(Controller controller, String mail, String password, int port) {
        this.controller = controller;
        //this.mail = mail;
        //this.password = password;
        this.port = port;
        this.login = true;

        connection();
    }*/


    private void connection() {
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        Socket socket = null;

        try {
            SocketFactory factory = SocketFactory.getDefault();
            socket = factory.createSocket(Controller.SERVER_ADDRESS, port);

            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            register(os, is);

            /*if (login) {
                login(os, is);
            }

            if (!login) {
                register(os, is);
            }*/

            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void login(ObjectOutputStream os, ObjectInputStream is) {

        try {
            os.writeObject(Server.LOGIN);
            os.writeObject(this.nickname);
            os.flush();
            System.out.println("name: " + nickname);
            String s = (String) is.readObject();
            System.out.println(s);
            System.out.println("*****************");
            if (s.equals(Server.LOGIN_OK)) {
                this.controller.setCorrectLogin(true);
                int id = Integer.parseInt((String) is.readObject());
                this.controller.setId(id);
            } else {
                this.controller.setCorrectLogin(false);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void register(ObjectOutputStream os, ObjectInputStream is) {

        try {
            os.writeObject(Server.SIGN_IN);
            //os.writeObject(Server.LOGIN);
            os.writeObject(nickname);

            System.out.println("nickname: " + nickname);
            os.flush();

            String s = (String) is.readObject();
            System.out.println(s);
            System.out.println("*****************");
            if (s.equals(Server.LOGIN_OK)) {
                this.controller.setCorrectLogin(true);
                int id = Integer.parseInt((String) is.readObject());
                this.controller.setId(id);
            } else {
                this.controller.setCorrectLogin(false);
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
