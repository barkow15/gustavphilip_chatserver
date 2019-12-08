import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ThreadKlientSendBeskeder implements Runnable{
    DataOutputStream dataOutStream;
    String brugernavn;

    public ThreadKlientSendBeskeder(DataOutputStream dataOutStream, String brugernavn) {
        this.dataOutStream = dataOutStream;
        this.brugernavn = brugernavn;
    }

    @Override
    public void run() {
        while (true) {
            String besked = Klient.scan.nextLine();

            if(besked.equals("QUIT")){
                System.out.println("Lukker applikation...");
            }
            try {
                dataOutStream.writeUTF("DATA " + brugernavn + " : " + besked);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
