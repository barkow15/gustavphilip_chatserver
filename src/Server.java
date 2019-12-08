import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream  dataInputStream;
    private DataOutputStream dataOutputStream;
    static ArrayList<String>        klientBrugernavne = new ArrayList<>();
    static ArrayList<Klienthandler> klienter = new ArrayList<Klienthandler>();
    private Server Klienthandler;
    private ServerHandler serverHandler;

    public Server(){
        serverHandler = new ServerHandler();
        try {
            this.serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException {
        while (true) {
            System.out.println("Venter på klient...");

            this.socket = serverSocket.accept();
            System.out.println("Ny klient anmodning modtaget" + socket);

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

            
            String joinAnmodning = dataInputStream.readUTF();
            String brugernavn = joinAnmodning.substring(5, joinAnmodning.indexOf(','));
            String bekraeftNavn = serverHandler.opretNavn(brugernavn);
            if(bekraeftNavn.equals("J_OK")){
                dataOutputStream.writeUTF(bekraeftNavn);
                System.out.println("Laver ny handler til klient");
                Klienthandler klienthandler = new Klienthandler(socket, dataInputStream, dataOutputStream, brugernavn);
                Thread thread = new Thread(klienthandler);
                System.out.println("Tilføjer klient til klient liste");
                this.klienter.add(klienthandler);

                thread.start();
            }else if(bekraeftNavn.startsWith("J_ER")){
                dataOutputStream.writeUTF(bekraeftNavn);
            }
        }

    }
}
