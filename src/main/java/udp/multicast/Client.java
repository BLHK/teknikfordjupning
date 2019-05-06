package udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

    private MulticastSocket socket;
    private InetAddress group;
    private byte buf[];

    /**
     * Klienten går med i en multicastSocket "group"  på en specifik ip och port.
     *
     */
    public Client(){
        try {
            buf = new byte[256];
            group = InetAddress.getByName("230.0.0.0");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void startClient(){
        try {
            socket = new MulticastSocket(8888);
            socket.joinGroup(group);
            while(true){
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.startClient();
    }
}
