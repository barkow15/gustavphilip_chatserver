import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ThreadKlientSendMessages implements Runnable{
    DataOutputStream dataOutStream;
    Scanner scan;

    public ThreadKlientSendMessages(DataOutputStream dataOutStream, Scanner scan) {
        this.dataOutStream = dataOutStream;
        this.scan = scan;
    }

    @Override
    public void run() {
        while (true) {
            String msg = scan.nextLine();
            try {
                dataOutStream.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
