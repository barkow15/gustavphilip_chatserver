import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Klient {
    public final static Scanner scan = new Scanner(System.in); // Scanner til at kunne skrive til konsollen
    private DataInputStream dataInStream; // Inputstream til at modtage data
    private DataOutputStream dataOutStream; // Outputstream til at sende data
    private Socket sock;
    private String serverIP;
    private int    serverPort;
    private String brugernavn;
    private boolean holdILive;
    private Thread   klientModBesked;
    private Thread   klientSendBesked;
    private Thread   klientHeartbeat;

    public Klient(String serverIP, int serverPort){
        this.serverIP   = serverIP;
        this.serverPort = serverPort;
    }

    public void forbind(){
        System.out.println("Starter forbindelse til server...");
        while(true) {
            try {
                System.out.println("Indtast et brugernavn du ønsker at anvende:");
                this.brugernavn = scan.nextLine();

                InetAddress ip = InetAddress.getByName(this.serverIP);
                this.sock = new Socket(ip, this.serverPort);
                this.dataInStream   = new DataInputStream(this.sock.getInputStream());
                this.dataOutStream  = new DataOutputStream(this.sock.getOutputStream());
                System.out.println("Sender brugeroplysninger...");
                // Send forbindelses anmodning
                dataOutStream.writeUTF("JOIN " + this.brugernavn + ", " + this.serverIP + ":" + this.serverPort);

                String result = dataInStream.readUTF();
                if (result.equals("J_OK")) {
                    System.out.println("Forbindelse etableret. Server svar: " + result);
                    System.out.println("Ønsker du at holde forbindelsen åben? [JA/NEJ]");

                    // Spørg om forbindelsen ønskes holdt åben
                    while(true){
                        String svar = scan.nextLine();
                        if(svar.equals("JA")){
                            this.holdILive = true;
                            break;
                        }else if(svar.equals("NEJ")){
                            this.holdILive = false;
                            break;
                        }else{
                            System.out.println("Forstod ikke dit svar. Prøv igen.");
                        }
                    }

                    // Initier tråde for at modtage og sende beskeder
                    this.initModtagSend();
                    if(this.holdILive){
                        // Intier tråd for at sende heartbeat besked til server og holde forbindelse åben
                        this.initHeartbeat();
                    }
                    break;
                } else if (result.startsWith("J_ER")) {
                    // Vis fejlbeskeden hvis det er en fejlbesked der er modtaget
                    System.out.println(result);
                }
            } catch (IOException e) {
                System.out.println("IP eller PORT er ikke korrekt eller server er ikke tilgængelig. Prøv igen...");
            }
        }
    }

    private void initModtagSend(){
        this.klientSendBesked = new Thread(new ThreadKlientSendBeskeder(this.dataOutStream, this.brugernavn));
        this.klientModBesked = new Thread(new ThreadKlientModtagBeskeder(this.dataInStream));

        this.klientSendBesked.start();
        this.klientModBesked.start();
    }
    private void initHeartbeat(){
        this.klientHeartbeat = new Thread(new ThreadKlientHeartbeat(this.dataOutStream));
        this.klientHeartbeat.start();
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

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public boolean isHoldILive() {
        return holdILive;
    }

    public void setHoldILive(boolean holdILive) {
        this.holdILive = holdILive;
    }

    public Thread getKlientModBesked() {
        return klientModBesked;
    }

    public void setKlientModBesked(Thread klientModBesked) {
        this.klientModBesked = klientModBesked;
    }

    public Thread getKlientSendBesked() {
        return klientSendBesked;
    }

    public void setKlientSendBesked(Thread klientSendBesked) {
        this.klientSendBesked = klientSendBesked;
    }

    public Thread getKlientHeartbeat() {
        return klientHeartbeat;
    }

    public void setKlientHeartbeat(Thread klientHeartbeat) {
        this.klientHeartbeat = klientHeartbeat;
    }
}
