package tcp.multiple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {

    private ServerSocket socket;

    /**
     * Server för att kunna ha tcp kommunikation med flera klienter samtidigt.
     * Flera klienter kan fortfarande inte vara med i samma stream.
     * Utan istället så skapas det upp nya trådar med server-klient kommunikation.
     */
    public void start() throws IOException{
        socket = new ServerSocket(8888);
        while (true){
            new ServerHandler(socket.accept()).start();
        }
    }

    public static void main(String[] args) throws Exception{
        Server s = new Server();
        s.start();
    }

    private static class ServerHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in; //Not used in this example

        public ServerHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run(){
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while(true){
                    out.println(LocalDateTime.now().toString());
                    sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


