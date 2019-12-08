import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Klient {
    private final static Scanner scan = new Scanner(System.in); // Scanner til at kunne skrive til konsollen
    private DataInputStream dataInStream; // Inputstream til at modtage data
    private DataOutputStream dataOutStream; // Outputstream til at sende data
    private Socket sock;
    private String serverIP;
    private int    serverPort;
    private String brugernavn;

    public Klient(String serverIP, int serverPort){
        System.out.println("Indtast et brugernavn du ønsker at anvende:");
        this.brugernavn = scan.nextLine();

        this.serverIP   = serverIP;
        this.serverPort = serverPort;
    }

    public void forbind(){
        System.out.println("Starter forbindelse til server...");
        while(true) {
            try {
                InetAddress ip = InetAddress.getByName(this.serverIP);
                Socket s = new Socket(ip, this.serverPort);
                this.dataInStream   = new DataInputStream(this.sock.getInputStream());
                this.dataOutStream  = new DataOutputStream(this.sock.getOutputStream());
                System.out.println("Sender brugeroplysninger...");
                dataOutStream.writeUTF("JOIN " + this.brugernavn + ", " + this.serverIP + ":" + this.serverPort);
                String result = dataInStream.readUTF();
                System.out.println(result);
                if (result.equals("J_OK")) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("IP eller PORT er ikke korrekt eller server er ikke tilgængelig. Prøv igen...");
            }
        }
    }

    /**
     * Getters & Setters
     */
    public DataInputStream getDataInStream() {
        return dataInStream;
    }

    public void setDataInStream(DataInputStream dataInStream) {
        this.dataInStream = dataInStream;
    }

    public DataOutputStream getDataOutStream() {
        return dataOutStream;
    }

    public void setDataOutStream(DataOutputStream dataOutStream) {
        this.dataOutStream = dataOutStream;
    }
}
