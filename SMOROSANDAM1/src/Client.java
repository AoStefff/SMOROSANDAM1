import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Client {
    String dni;
    String contrasenya;
    String nom;
    String correu;
    String telefon;
    String adreca;

    public Client(String dni, String contrasenya, String nom, String correu, String telefon, String adreca) {
        this.dni = dni;
        this.contrasenya = contrasenya;
        this.nom = nom;
        this.correu = correu;
        this.telefon = telefon;
        this.adreca = adreca;
    }

    public Client() {
    }

    public boolean verificarDni(String dni) {
        boolean incorrecte = false;
        String lletresDni = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        if (dni.length() != 9) {
            incorrecte = true;
        } else {
            for (int i = 0; i < 8; i++) {
                if (!Character.isDigit(dni.charAt(i))) incorrecte = true;
            }
            if (!incorrecte) if (lletresDni.indexOf(dni.charAt(8)) == -1) incorrecte = true;

        }
        if (incorrecte) System.out.println("Dni incorrecte. Escriu be el DNI.");
        if (!incorrecte) {
            this.dni = dni;
        }
        return incorrecte;
    }

    public boolean verificarEmail(String correuAux) {
        boolean valid = true;
        String[] emailSplit = correuAux.split("@");
        if (!(emailSplit.length == 2)) valid = false;
        else {
            String[] emailSplit2 = emailSplit[1].split("\\.");
            if (emailSplit2.length < 2) valid = false;
        }
        if (valid) {
            this.correu = correuAux;
        }
        return valid;
    }
    public boolean verificarTelefon(String telefon) {
        boolean valid = true;
        if (telefon.length() == 9) {
            int i = 0;
            while (valid && i < 9) {
                if (!Character.isDigit(telefon.charAt(i))) valid = false;
                else i++;
            }
        } else {
            valid = false;
        }
        if (valid) this.telefon = telefon;
        return valid;
    }

    public void afegirBD(Statement stmt) throws Exception {
        ResultSet rsDni = stmt.executeQuery("SELECT * from usuari where dni=" + "'" + this.dni + "';");
        if (!rsDni.next()) {
            stmt.executeUpdate("INSERT INTO usuari VALUES (" + "'" + this.dni + "','" + this.contrasenya + "','" + this.nom + "','" + this.correu + "','" + this.telefon + "','" + this.adreca + "');");
        }
    }

    public void llistarClient(String dni, Statement stmt) throws Exception {
        String sqlUsuari = "SELECT * FROM usuari WHERE dni=" + "'" + dni + "';";
        ResultSet rsUsuari = stmt.executeQuery(sqlUsuari);
        if (rsUsuari.next()) {
            System.out.println("DNI: " + rsUsuari.getString(1));
            System.out.println("CONTRASENYA: " + rsUsuari.getString(2));
            System.out.println("NOM: " + rsUsuari.getString(3));
            System.out.println("CORREU ELECTRONIC: " + rsUsuari.getString(4));
            System.out.println("TELEFON: " + rsUsuari.getString(5));
            System.out.println("ADRECA: " + rsUsuari.getString(6));
        } else System.out.println("No existeix l'usuari per el dni: " + dni);
    }
    public void llistarClients(Statement stmt) throws Exception {
        String sqlUsuaris = "SELECT * FROM usuari;";
        ResultSet rsUsuaris = stmt.executeQuery(sqlUsuaris);
        System.out.println("-------------------------------------------------------");
        while (rsUsuaris.next()) {
            System.out.println("DNI: " + rsUsuaris.getString(1));
            System.out.println("CONTRASENYA: " + rsUsuaris.getString(2));
            System.out.println("NOM: " + rsUsuaris.getString(3));
            System.out.println("CORREU ELECTRONIC: " + rsUsuaris.getString(4));
            System.out.println("TELEFON: " + rsUsuaris.getString(5));
            System.out.println("ADRECA: " + rsUsuaris.getString(6));
            System.out.println("-------------------------------------------------------");
        }


    }
    public  void modificarClientCE(Scanner lector,Statement stmt,String dni)throws Exception{
        String correuAux;
        do {
            System.out.println("Escriu el teu correu electronic: ");
            correuAux = lector.nextLine();
        } while (!verificarEmail(correuAux));
        stmt.executeUpdate("UPDATE usuari SET correu= '" + correuAux + "' WHERE dni='" + dni + "';");
    }
    public  void modificarClientTelefon(Scanner lector,Statement stmt,String dni)throws Exception{
        String telefon;
        do {
            System.out.println("Escriu el teu telefon: ");
            telefon = lector.nextLine();

        } while (!verificarTelefon(telefon));
        System.out.println(telefon);
        stmt.executeUpdate("UPDATE usuari SET telefon= '" + telefon + "' WHERE dni='" + dni + "';");
    }
    public  void modificarClientAdreca(Scanner lector,Statement stmt,String dni)throws Exception{
        System.out.println("Escriu la teva adreca: ");
        String novaAdreca = lector.nextLine();
        stmt.executeUpdate("UPDATE usuari SET adreca= '" + novaAdreca + "' WHERE dni='" + dni + "';");
    }
    public void esborrarClient(Scanner lector,Statement stmt) throws Exception{
        System.out.println("Entra el dni.");
        String dni = lector.nextLine();
        System.out.println("Entra la contrasenya. ");
        String contrasenya = lector.nextLine();
        ResultSet comprovarUsuari = stmt.executeQuery("SELECT * from usuari WHERE dni='" + dni + "' AND contrasenya='" + contrasenya + "';");
        if (comprovarUsuari.next()) {
            comprovarUsuari.deleteRow();
            System.out.println("Esborrat correctament!");
        }
    }
}
