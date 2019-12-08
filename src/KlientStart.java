import java.io.IOException;

public class KlientStart {
    public static void main(String[] args) {
        Klient klient = new Klient("localhost", 8888);
        // Start forbind metoden
        klient.forbind();
    }
}
