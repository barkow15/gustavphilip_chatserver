import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Klient {

    private final static Scanner scn = new Scanner(System.in); // Scanner til at kunne skrive til konsollen
    private DataInputStream dataInStream; // Inputstream til at modtage data
    private DataOutputStream dataOutStream; // Outputstream til at sende data

    public static void main(String[] args) {

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
