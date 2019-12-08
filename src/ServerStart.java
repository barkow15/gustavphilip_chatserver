import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStart {

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;

    static ArrayList<ClientHandler> klientArrayListe = new ArrayList<ClientHandler>();

    private Server ClientHandler;

    public void startServer() throws IOException {

        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            System.out.println("Venter på klient");

            this.socket = serverSocket.accept();

            System.out.println("Ny klient anmodning modtaget" + socket);

            this.dataInputStream(socket.getInputStream());
            this.dataOutputStream(socket.getOutputStream());

            System.out.println("Laver ny handler til klient");

            ClientHandler clientHandler = new ClientHandler(socket, dataInputStream, dataOutputStream);

            Thread thread = new Thread((Runnable) this.ClientHandler);

            System.out.println("Tilføjer klient til klient liste");

            klientArrayListe.add(clientHandler);

            thread.start();
        }

    }

    private void dataOutputStream(OutputStream outputStream) {
    }

    private void dataInputStream(InputStream inputStream) {
    }
}
