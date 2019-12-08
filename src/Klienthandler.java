import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Klienthandler implements Runnable {

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Socket s;
    private String username;
    private ServerHandler serverHandler = new ServerHandler();

    public Klienthandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String joinRequest = dis.readUTF();
                username = joinRequest.substring(5, joinRequest.indexOf(','));
                String verifyName = serverHandler.opretNavn(username);
                dos.writeUTF(verifyName);
                if (verifyName.equals("J_OK"))
                    break;
            }
            broadCast();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String besked = dis.readUTF();

                if (besked.equals("IMAV")){
                    System.out.println("Server recieved heartbeat from: " + username);
                } else {
                    if (besked.equals("QUIT")) {
                        sendPublicMessage(">> " + username + " <<" + " har forladt chatten");
                        this.removeFromServer();
                        break;
                    }
                    sendPublicMessage(username + " : " + besked);
                }
            } catch (IOException e) {
                this.removeFromServer();
                System.out.println(username + ": has lost connetion to the server");
                break;
            }
        }

        try {
            s.close();
            dis.close();
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadCast() throws IOException {
        for (Klienthandler client : Server.klienter) {
            client.dos.writeUTF("[ Brugere forbundet til serveren nu ]");
            for (String clients : Server.klientBrugernavne) {
                client.dos.writeUTF(">> " + clients + " <<");
            }
        }
    }

    private void sendPublicMessage(String besked) throws IOException {
        for (Klienthandler klienter : Server.klienter) {
            klienter.dos.writeUTF(besked);
        }
    }
    private void removeFromServer(){
        Server.klienter.remove(this);
        Server.klientBrugernavne.remove(this.username);
    }

}