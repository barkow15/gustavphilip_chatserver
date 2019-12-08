public class ServerHandler {

    // Opretter metoden "opretNavn" som har paramteren brugernavn"

    public String opretNavn(String brugernavn) {
        try {

            // Brugernavn må kun indeholde visse tegn og vi tjekker derfor om dette er overholdt her
            if (!brugernavn.matches("^[a-zA-Z0-9\\-_]*$")) {
                return "Ulovlige tegn. Brugernavne. Må kun have tegnne - og _ ";

            // Brugernavnet må kun have en længe på 12 tegn. Dette tjekker vi her
            } else if (brugernavn.length() > 12) {
                return "brugernavn må kun være mellem 1 til 12 tegn";

            // Brugernavnet skal være unikt. Inden vi opretter brugeren tjekker vi derfor vores kleitnBrugernavn arrayliste og ser om det samme brugernavn allerede eksisterer
            } else if (Server.klientBrugernavne.contains(brugernavn)) {
                return "Brugernavn eksiterer allerede. Prøv et andet";
            }

        } catch (IndexOutOfBoundsException e) {
            return "Noget gik galt. Prøv venligst igen";
        }

        // Her tilføjer vi brugernavn til vores arrayliste  og returnere med teksten "J_OK"
        Server.klientBrugernavne.add(brugernavn);
        return "J_OK";
    }
}
