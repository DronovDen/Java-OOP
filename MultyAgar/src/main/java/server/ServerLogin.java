package server;

import javax.net.ServerSocketFactory;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLogin {

    private final Server server;

    public ServerLogin(Server server) {
        this.server = server;
        start();
    }

    private void start() {
        ObjectInputStream is = null;
        ObjectOutputStream os = null;

        try {
            ServerSocketFactory ssf = ServerSocketFactory.getDefault();
            ServerSocket s = ssf.createServerSocket(Server.PORT_LOGIN);
            Socket socket = s.accept();

            is = new ObjectInputStream(socket.getInputStream());
            os = new ObjectOutputStream(socket.getOutputStream());

            String key = (String) is.readObject();

            /*if (key.equals(Server.LOGIN)) {
                login(is, os);
            }*/

            if (key.equals(Server.SIGN_IN)) {
                signIn(is, os);
            }

            socket.close();
            s.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /*private void login(ObjectInputStream is, ObjectOutputStream os) throws Exception {
        int log;
        String nickname = (String) is.readObject();
        System.out.println("nickname: " + nickname);

        log = server.validateLogin(nickname);

        if (log >= 0) {
            os.writeObject(Server.LOGIN_OK);
            os.writeObject(log + "");
        } else {
            os.writeObject(Server.LOGIN_INCORRECT);
        }
        os.flush();
    }*/

    public void signIn(ObjectInputStream is, ObjectOutputStream os) throws Exception {
        String nickname = (String) is.readObject();
        System.out.println("nickname: " + nickname);

        server.signIn(nickname);
        //os.writeObject(Server.SAVE);
        int log = server.validateLogin(nickname);

        if (log >= 0) {
            os.writeObject(Server.LOGIN_OK);
            os.writeObject(log + "");
        } else {
            os.writeObject(Server.LOGIN_INCORRECT);
        }
        os.flush();
    }
}
