public class ServerHandler {

    // Opretter metoden "opretNavn" som har paramteren brugernavn"
    public String opretNavn(String brugernavn) {
        try {
            // Brugernavn må kun indeholde visse tegn og vi tjekker derfor om dette er overholdt her
            if (!brugernavn.matches("^[a-zA-Z0-9\\-_]*$")) {
                return "J_ER 401: Ulovlige tegn. Brugernavne. Må kun bestå af tal og bogstaver fra A til Z og indeholde tegnene - og _ ";

            }else if(brugernavn.equals("")){
                // Brugernavn må ikke være tomt
                return "J_ER 402: brugernavn må ikke være tomt";
            }else if (brugernavn.length() > 12) {
                // Brugernavnet må kun have en længe på 12 tegn. Dette tjekker vi her
                return "J_ER 403: brugernavn må kun være mellem 1 til 12 tegn";

            // Brugernavnet skal være unikt. Inden vi opretter brugeren tjekker vi derfor vores kleitnBrugernavn arrayliste og ser om det samme brugernavn allerede eksisterer
            } else if (Server.klientBrugernavne.contains(brugernavn)) {
                return "J_ER 404: Brugernavn eksisterer allerede. Prøv et andet";
            }

        } catch (IndexOutOfBoundsException e) {
            return "J_ER 400: Noget gik galt. Prøv venligst igen";
        }

        // Her tilføjer vi brugernavn til vores arrayliste  og returnere med teksten "J_OK"
        Server.klientBrugernavne.add(brugernavn);
        return "J_OK";
    }
}
