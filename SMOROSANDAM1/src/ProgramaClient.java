import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ProgramaClient {
    public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Botigav", "postgres", "mcgastron99");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            boolean sortirPrograma=false;
            do{
                System.out.println("INTERFICIE DE LA BOTIGA\n\n");
                System.out.println("Per entrar a la botiga entra 1.");
                System.out.println("Per fer la gestio del compte d'usuari entra 2.");
                System.out.println("Per sortir de la botiga entra 3.");
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                int menu = lector.nextInt();
                lector.nextLine();

                switch (menu){
                    case 1:
                        Client botigaClient = new Client();
                        do {
                            System.out.println("Escriu el teu DNI amb una lletra majuscula: ");
                            botigaClient.dni = lector.nextLine().trim();
                        } while (botigaClient.verificarDni(botigaClient.dni));
                        ResultSet comprovarEntrar = stmt.executeQuery("SELECT * from usuari WHERE dni='" + botigaClient.dni +"';");
                        if (comprovarEntrar.next()){
                            System.out.println("Escriu la teva contrasenya: ");
                            botigaClient.contrasenya = lector.nextLine();
                            ResultSet comprovarContrasenya = stmt.executeQuery("SELECT * from usuari WHERE  contrasenya='" + botigaClient.contrasenya + "' AND dni='" + botigaClient.dni +"';");
                            if (comprovarContrasenya.next()){
                                boolean deixarCompra;
                                ArrayList<Producte>productes=new ArrayList<>();
                                ArrayList<Producte>carretCompra=new ArrayList<>();
                                new  Producte().carregarProductesMemoria(productes,stmt);
                                do{
                                    System.out.println("\n");
                                    new  Producte().llistarProductes(stmt);
                                    System.out.println("\n\n\n\n\n");
                                    deixarCompra=false;
                                    System.out.println("Entra el nom d'un producte,un 0 per veure el carret de la compra o un 1 acabar la compra");
                                    String compraProducte = lector.nextLine();
                                    if (compraProducte.equalsIgnoreCase("1"))deixarCompra=true;
                                    else if (compraProducte.equalsIgnoreCase("0")){
                                        boolean sortirCarret=false;
                                        do{
                                            System.out.println("---PRODUCTE---------------QUANTITAT------------------------------------");
                                            System.out.println("-----------------------------------------------------------------------");

                                            for (Producte carretCompres:carretCompra){
                                                System.out.printf("%-27s",carretCompres.nom);
                                                System.out.println(carretCompres.stock+" unitats");
                                                System.out.println("-----------------------------------------------------------------------");
                                            }
                                            System.out.println("Per treure un producte del carret de compra entra 1.");
                                            System.out.println("Per modificar l'stock d'un producte del carret de compra entra 2.");
                                            System.out.println("Per sortir del carret entra 3.");
                                            int menuCarret = lector.nextInt();
                                            lector.nextLine();
                                            switch (menuCarret){
                                                case 1:
                                                    System.out.println("Entra el nom del producte.");
                                                    String producteCarret = lector.nextLine();
                                                    boolean trobat=false;
                                                    int i=0;
                                                    while (!trobat&& i<carretCompra.size()){
                                                        if (producteCarret.equalsIgnoreCase(carretCompra.get(i).nom)){
                                                            trobat=true;
                                                            boolean trobarProducte = false;
                                                            int j=0;
                                                            int stockAnterior=0;
                                                            while (!trobarProducte & j< productes.size()) {
                                                                if(productes.get(j).codi==carretCompra.get(i).codi){
                                                                    stockAnterior = productes.get(j).stock;
                                                                }
                                                                else j++;
                                                            }
                                                            carretCompra.get(i).treureStockProducte(stmt,carretCompra.get(i).codi,stockAnterior);
                                                            carretCompra.remove(i);
                                                        }
                                                        else i++;
                                                    }
                                                    if (!trobat) System.out.println("No tens el producte al carret.");
                                                    System.out.println("Prem enter per tornar al menu.");
                                                    lector.nextLine();
                                                    break;
                                                case 2:
                                                    System.out.println("Entra el nom del producte.");
                                                    producteCarret = lector.nextLine();
                                                    trobat=false;
                                                    i=0;
                                                    while (!trobat&& i<carretCompra.size()){
                                                        if (producteCarret.equalsIgnoreCase(carretCompra.get(i).nom)){
                                                            trobat=true;
                                                            System.out.println("Entra la quantitat que vols tenir.");
                                                            int unitatsProducte = lector.nextInt();
                                                            lector.nextLine();
                                                            boolean trobarProducte = false;
                                                            int j=0;
                                                            int stockAnterior=0;
                                                            while (!trobarProducte & j< productes.size()) {
                                                                if(productes.get(j).codi==carretCompra.get(i).codi){
                                                                    trobarProducte=true;
                                                                    stockAnterior = productes.get(j).stock;
                                                                }
                                                                else j++;
                                                            }
                                                            if (stockAnterior<unitatsProducte){
                                                                System.out.println("Unitats insuficients.");
                                                                System.out.println("Actualment tenim "+stockAnterior+" unitats");
                                                            }
                                                            else {
                                                                carretCompra.get(i).stock=unitatsProducte;
                                                                carretCompra.get(i).treureStockProducte(stmt,carretCompra.get(i).codi,productes.get(j).stock-carretCompra.get(i).stock);
                                                            }

                                                        }
                                                        else i++;
                                                    }
                                                    if (!trobat) System.out.println("No tens el producte al carret.");
                                                    System.out.println("Prem enter per tornar al menu.");
                                                    lector.nextLine();
                                                    break;
                                                case 3: sortirCarret=true;
                                            }

                                        }while (!sortirCarret);
                                    }
                                    else {
                                        boolean trobat=false;
                                        int i=0;
                                        while (!trobat&& i<productes.size()){
                                            if (compraProducte.equalsIgnoreCase(productes.get(i).nom))trobat=true;
                                            else i++;
                                        }
                                        if (!trobat) System.out.println("No existeix el producte.");
                                        else {
                                            System.out.println("Entra cuantes unitats vols.");
                                            int unitatsProducte = lector.nextInt();
                                            lector.nextLine();
                                            if (productes.get(i).stock<unitatsProducte){
                                                System.out.println("Unitats insuficients.");
                                                System.out.println("Actualment tenim "+productes.get(i).stock+" unitats");
                                            }
                                            else {
                                                productes.get(i).treureStockProducte(stmt,productes.get(i).codi,productes.get(i).stock-unitatsProducte);
                                                System.out.println("Producte afegit al carret.");
                                                Producte producteCompra = productes.get(i);
                                                producteCompra.stock=unitatsProducte;
                                                carretCompra.add(producteCompra);
                                            }
                                        }
                                        System.out.println("Prem enter per continuar.");
                                        lector.nextLine();
                                    }
                                }while (!deixarCompra);
                                System.out.println("Stefan Enterprise");
                                System.out.println("AVINGUDA del Mar\n");
                                System.out.printf("%-35s","Data factura: "+ LocalDate.now());
                                System.out.println("Hora factura: "+ LocalTime.now());
                                System.out.println("---PRODUCTE---------------QUANTITAT------------------TOTAL-------------");
                                System.out.println("-----------------------------------------------------------------------");
                                int total=0;
                                for (Producte carretCompres:carretCompra){
                                    System.out.printf("%-27s",carretCompres.nom);
                                    System.out.printf("%-27s",(carretCompres.stock+" unitats"));
                                    System.out.println(carretCompres.preu*carretCompres.stock);
                                    System.out.println("-----------------------------------------------------------------------");
                                    total=total+carretCompres.preu*carretCompres.stock;

                                }
                                System.out.printf("%-54s"," TOTAL EUR.........");
                                System.out.println(total);

                            }
                            else {
                                System.out.println("Contrasenya incorrecta.");
                            }
                            System.out.println("Prem enter per tornar al menu.");
                            lector.nextLine();

                        }
                        else {
                            System.out.println("Aquest client no està entrat");
                            System.out.println("Prem enter per tornar al menu.");
                            lector.nextLine();
                        }



                        break;
                    case  2:
                        boolean sortirGestio=false;
                        do{
                            System.out.println("\n\n\nGESTIO DE L'USUARI\n");
                            System.out.println("Per crear un nou compte entra 1.");
                            System.out.println("Per modificar el compte entra 2.");
                            System.out.println("Per esborrar el compte entra 3.");
                            System.out.println("Per sortir de la gestio entra 4.");
                            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                            int menuCompte = lector.nextInt();
                            lector.nextLine();
                            switch (menuCompte) {
                                case 1:
                                    String dni;
                                    Client nouclient = new Client();
                                    do {
                                        System.out.println("Escriu el teu DNI amb una lletra majuscula: ");
                                        dni = lector.nextLine().trim();
                                    } while (nouclient.verificarDni(dni));
                                    nouclient.dni=dni;
                                    System.out.println("Escriu la teva contrasenya: ");
                                    nouclient.contrasenya = lector.nextLine();
                                    System.out.println("Escriu el teu nom: ");
                                    nouclient.nom = lector.nextLine();
                                    String correuAux;
                                    do {
                                        System.out.println("Escriu el teu correu electronic: ");
                                        correuAux = lector.nextLine();
                                    } while (!nouclient.verificarEmail(correuAux));
                                    String telefon;
                                    do {
                                        System.out.println("Escriu el teu telefon: ");
                                        telefon = lector.nextLine();

                                    } while (!nouclient.verificarTelefon(telefon));
                                    System.out.println("Escriu la teva adreca: ");
                                    nouclient.adreca = lector.nextLine();
                                    nouclient.afegirBD(stmt);
                                    System.out.println("Prem enter per tornar al menu.");
                                    lector.nextLine();
                                    break;
                                case 2:
                                    do {
                                        System.out.println("Escriu el teu DNI amb una lletra majuscula: ");
                                        dni = lector.nextLine().trim();
                                    } while (new Client().verificarDni(dni));
                                    System.out.println("Entra la contrasenya. ");
                                    String contrasenya = lector.nextLine();
                                    ResultSet comprovarUsuari = stmt.executeQuery("SELECT * from usuari WHERE dni='" + dni + "' AND contrasenya='" + contrasenya + "';");
                                    if (comprovarUsuari.next()) {
                                        System.out.println("Entra un numero per actualitzar una dada.\n");
                                        System.out.println("1.Per actualitzar el correu");
                                        System.out.println("2.Per actualitzar el telefon");
                                        System.out.println("3.Per actualitzar l'adreca");

                                        int menuUpdate = lector.nextInt();
                                        lector.nextLine();
                                        switch (menuUpdate) {
                                            case 1:
                                                new Client().modificarClientCE(lector,stmt,dni);
                                                break;
                                            case 2:
                                                new Client().modificarClientTelefon(lector,stmt,dni);
                                                break;
                                            case 3:
                                                new Client().modificarClientAdreca(lector,stmt,dni);
                                        }
                                    }
                                    else System.out.println("No existeix el compte\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                    System.out.println("Prem enter per tornar al menu.");
                                    lector.nextLine();
                                    break;
                                case 3:
                                    new Client().esborrarClient(lector,stmt);
                                    System.out.println("Prem enter per tornar al menu.");
                                    lector.nextLine();
                                    break;
                                case 4: sortirGestio=true;

                            }
                        }while (!sortirGestio);

                    case 3: sortirPrograma=true;
                }
            }while (!sortirPrograma);

    }
        catch (Exception e){
            System.out.println("Error"+e);
        }
    }
}
