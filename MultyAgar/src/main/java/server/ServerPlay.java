package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPlay extends Thread {

	private final Server server;
	private Socket socket;

	public ServerPlay(Server server) {
		this.server = server;
		this.socket = null;

		try {
			ServerSocket serverSocket = new ServerSocket(Server.PORT_PLAY);
			this.socket = serverSocket.accept();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {

			DataInputStream in;
			DataOutputStream out;

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			
			boolean control = true;
			while (!this.server.getGame().isRunning()) {
				out.writeUTF("wait");
			}
			
			while (control && this.server.getGame().isRunning()) {
				try {
					out.writeUTF(server.sendBaseGame());
					String received = in.readUTF();
					if (!received.equals("exit")) {
						String[] player = received.split("/");
						server.updateGame(player);
					}else{
						control = false;
					}
				} catch (Exception e) {
					break;
				}
			}
			
			out.writeUTF("time");
			System.out.println("someone out");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
