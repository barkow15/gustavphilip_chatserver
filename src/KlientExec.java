import java.io.IOException;

public class KlientExec {
    public static void main(String[] args) {
        Klient klient = new Klient("localhost", 2000);
        // Start forbind metoden
        klient.forbind();
    }
}
