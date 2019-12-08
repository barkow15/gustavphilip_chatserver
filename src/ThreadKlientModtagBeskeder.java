import java.io.DataInputStream;
import java.io.IOException;

public class ThreadKlientRecieveMessages implements Runnable{
    DataInputStream dataInStream;

    @Override
    public void run() {
        while (true) {
            try {
                String besked = dataInStream.readUTF();
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
