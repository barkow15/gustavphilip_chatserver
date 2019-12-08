public class Main{
    public static void main(String args[]){

        ServerHandler serverHandler = new ServerHandler();
        String brugernavn = "JOIN gustav, 1292131, 2000".substring(5, "JOIN gustav, 1292131, 2000".indexOf(','));
        System.out.println(brugernavn);
        System.out.println(serverHandler.opretNavn(brugernavn));

    }
}