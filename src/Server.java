import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<String> klientBrugernavn = new ArrayList<>();

    ServerStart serverStart;

    public void main(String[] args) throws IOException {
        this.serverStart.startServer();
    }


    public Server() {

    }
}
