import java.io.*;
import java.net.*;
import java.util.Scanner;


public class HandleClientThread extends Thread {

	ServerSocket serverSocket;

	public HandleClientThread() throws IOException {
		super();
		this.serverSocket = new ServerSocket(8080);
		serverSocket.setSoTimeout(4000);
	}

	public void run() {
		System.out.println("Waiting for client on port " + 
				serverSocket.getLocalPort() + "...");
		while(true) {
			if (Server.closeHandler) {
				System.out.println("Threads are closed");
				break;
			}
				
			try {
				
				Socket server = serverSocket.accept();
				Thread t = new Threadception(server);   
				t.start();

			} catch (SocketTimeoutException s) {
				//System.out.println("Socket timed out!");
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}




}
