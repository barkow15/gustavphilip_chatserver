import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Klienthandler implements Runnable {

    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private Socket s;
    private String username;
    private ServerHandler serverHandler = new ServerHandler();

    public Klienthandler(Socket s, DataInputStream dis, DataOutputStream dos, String username) {
        this.username = username;
        this.dataInputStream = dis;
        this.dataOutputStream = dos;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            this.sendListeOverKlienterTilAlleKlienter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            s.setSoTimeout(120000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String besked = dataInputStream.readUTF();

                if (besked.equals("IMAV")){
                    System.out.println("Server recieved heartbeat from: " + username);
                } else {
                    if (besked.equals("QUIT")) {
                        sendPublicMessage(">> " + username + " <<" + " har forladt chatten");
                        this.removeFromServer();
                        break;
                    }else if(besked.startsWith("DATA")) {
                        // Hent beskeden efter kolon
                        String beskedTilKlient = besked.substring(besked.lastIndexOf(":") + 1);
                        // Fjeren spaces foran og bagved beskeden
                        beskedTilKlient = beskedTilKlient.trim();
                        sendPublicMessage(username + " : " + beskedTilKlient);
                    }
                }
            } catch (IOException e) {
                this.removeFromServer();
                System.out.println(username + ": har mistet forbindelsen til serveren");
                break;
            }
        }

        try {
            s.close();
            dataInputStream.close();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendListeOverKlienterTilAlleKlienter() throws IOException {
        for (Klienthandler client : Server.klienter) {
            String beskedTilKlient = "LIST ";
            for (String brugernavne : Server.klientBrugernavne) {
                beskedTilKlient += brugernavne + " ";
            }
            client.dataOutputStream.writeUTF(beskedTilKlient);
        }
    }

    private void sendPublicMessage(String besked) throws IOException {
        for (Klienthandler klienter : Server.klienter) {
            klienter.dataOutputStream.writeUTF(besked);
        }
    }
    private void removeFromServer(){
        Server.klienter.remove(this);
        Server.klientBrugernavne.remove(this.username);
    }

}