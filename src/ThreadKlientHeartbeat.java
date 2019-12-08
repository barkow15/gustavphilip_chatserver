import java.io.DataOutputStream;
import java.io.IOException;

public class ThreadKlientHeartbeat implements Runnable{
    DataOutputStream dataOutStream;

    public ThreadKlientHeartbeat(DataOutputStream dataOutStream) {
        this.dataOutStream = dataOutStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                dataOutStream.writeUTF("IMAV");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
