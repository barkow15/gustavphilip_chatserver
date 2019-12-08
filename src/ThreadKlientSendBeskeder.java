import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ThreadKlientSendBeskeder implements Runnable{
    DataOutputStream dataOutStream;
    Scanner scan;

    public ThreadKlientSendBeskeder(DataOutputStream dataOutStream, Scanner scan) {
        this.dataOutStream = dataOutStream;
        this.scan = scan;
    }

    @Override
    public void run() {
        while (true) {
            String besked = scan.nextLine();
            try {
                dataOutStream.writeUTF(besked);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
