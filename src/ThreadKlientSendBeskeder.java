import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ThreadKlientSendBeskeder implements Runnable{

    DataOutputStream dataOutStream;

    public ThreadKlientSendBeskeder(DataOutputStream dataOutStream) {
        this.dataOutStream = dataOutStream;
    }

    @Override
    public void run() {
        while (true) {
            String besked = Klient.scan.nextLine();

            if(besked.equals("QUIT")){
                System.out.println("Lukker applikation...");
            }
            try {
                dataOutStream.writeUTF(besked);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
