import java.io.DataInputStream;
import java.io.IOException;

public class ThreadKlientModtagBeskeder implements Runnable{
    DataInputStream dataInStream;

    public ThreadKlientModtagBeskeder(DataInputStream dataInStream){
        this.dataInStream = dataInStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String besked = dataInStream.readUTF();
                System.out.println(besked);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
