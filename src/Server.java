import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<KlientHandler> klientArrayListe = new ArrayList<KlientHandler>();
    static ArrayList<String> klientBrugernavn = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        // Vi laver et objekt af en serversocket med port 8888
        ServerSocket serverSocket = new ServerSocket(8888);

        // Vi kører en uendleig løkke, som programmet kører i, når vi starter det
        while (true) {

            System.out.println("Venter på at en klient forbinder sig");

            // Når en klient tilgår port 8888 vil være serversocket som kan modtage forspørgelser på at oprette forbindelse
            Socket socket = serverSocket.accept();

            System.out.println("En ny klient har tilsluttet :  " + socket);

            // Her ser vi hvad vi har modtaget i vores nye socket fra klienten
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            // Det samme gælder når vi skal sende data tilbage
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Her vil vi sende klientens forspørgselse ud til en klient handler, således at vi holde traffiken effktiv
            System.out.println("Laver ny handler til denne klient");

            KlintHandler klintHandler = new KlientHandler(socket, dataInputStream, dataOutputStream);

            Thread thread = new Thread(klintHandler);

            System.out.println("Tilføjer klient til aktiv klient liste");

            klientArrayListe.add(klintHandler);

            thread.start();
        }
    }
}
