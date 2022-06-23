package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AssignPort extends Thread {

    private final Socket socket;
    private final Server server;

    public AssignPort(Socket ss, Server server) {
        this.socket = ss;
        this.server = server;
    }

    @Override
    public void run() {

        boolean control = true;
        while (control) {

            try {
                if (socket.isConnected()) {

                    DataInputStream in;
                    DataOutputStream out;

                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());

                    String key = in.readUTF();

                    if (key.equals(Server.LOGIN) || key.equals(Server.SIGN_IN)) {
                        out.writeUTF(Server.PORT_LOGIN + "");
                        ServerLogin sls = new ServerLogin(this.server);
                    }
                    if (key.equals(Server.PLAY)) {
                        out.writeUTF(Server.PORT_PLAY + "");
                        ServerPlay spg = new ServerPlay(this.server);
                        spg.start();
                    }

                    if (key.equals(Server.OBSERVE)) {
                        out.writeUTF(Server.PORT_STREAMING + "");
                    }
                }
            } catch (IOException ex) {
                control = false;
                System.out.println("client disconnected");
            }
        }
    }
}
