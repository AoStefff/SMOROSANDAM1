import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Producte {

    int codi;
    String nom;
    int stock;
    int preu;
    int iva;

    public Producte(int codi, String nom, int stock, int preu, int iva) {
        this.codi = codi;
        this.nom = nom;
        this.stock = stock;
        this.preu = preu;
        this.iva = iva;
    }

    public Producte() {
    }

    public void altaProducte(Scanner lector, Statement stmt) throws SQLException {
        boolean valInt;
        int codi = 0;
        do {
            valInt = false;
            System.out.println("Entra el codi del Producte");
            if (lector.hasNextInt()) {
                codi = lector.nextInt();
                valInt = true;
            }
            lector.nextLine();
        } while (!valInt);
        ResultSet comprovarCodi = stmt.executeQuery("SELECT codi from producte;");
        boolean trobat = false;
        while (!trobat && comprovarCodi.next()) {
            if (comprovarCodi.getInt(1) == codi) {
                trobat = true;
                System.out.println("Aquest codi ja existeix!");
            }
        }
        if (!trobat) {
            System.out.println("Entra el nom del producte.");
            String nom = lector.nextLine();
            int stock = 0;
            do {
                valInt = false;
                System.out.println("Entra l'stock del producte.");
                if (lector.hasNextInt()) {
                    stock = lector.nextInt();
                    valInt = true;
                }
                lector.nextLine();
            } while (!valInt);
            int preu = 0;
            do {
                valInt = false;
                System.out.println("Entra el preu del producte.");
                if (lector.hasNextInt()) {
                    preu = lector.nextInt();
                    valInt = true;
                }
                lector.nextLine();
            } while (!valInt);
            int iva = 0;
            do {
                valInt = false;
                System.out.println("Entra l'iva del producte.");
                if (lector.hasNextInt()) {
                    iva = lector.nextInt();
                    valInt = true;
                }
                lector.nextLine();
            } while (!valInt);
            stmt.executeUpdate("INSERT INTO producte VALUES (" + codi + ",'" + nom + "'," + stock + "," + preu + "," + iva + ");");
        }
    }
    public void modificarProducteStock(Scanner lector,Statement stmt,int codi)throws Exception{
        int stock = 0;
        boolean valInt;
        do {
            valInt = false;
            System.out.println("Entra el stock del producte.");
            if (lector.hasNextInt()) {
                stock = lector.nextInt();
                valInt = true;
            }
            lector.nextLine();
        } while (!valInt);
        stmt.executeUpdate("UPDATE producte SET stock= " + stock + " WHERE codi=" + codi + ";");
    }
    public void modificarProductePreu(Scanner lector,Statement stmt,int codi)throws Exception{
        int preu = 0;
        boolean valInt;
        do {
            valInt = false;
            System.out.println("Entra el preu del producte.");
            if (lector.hasNextInt()) {
                preu = lector.nextInt();
                valInt = true;
            }
            lector.nextLine();
        } while (!valInt);
        stmt.executeUpdate("UPDATE producte SET preu= " + preu + " WHERE codi=" + codi + ";");
    }
    public void modificarProducteIva(Scanner lector,Statement stmt,int codi)throws Exception{
        int iva = 0;
        boolean valInt;
        do {
            valInt = false;
            System.out.println("Entra el iva del producte.");
            if (lector.hasNextInt()) {
                iva = lector.nextInt();
                valInt = true;
            }
            lector.nextLine();
        } while (!valInt);
        stmt.executeUpdate("UPDATE producte SET iva= " + iva + " WHERE codi=" + codi + ";");


    }
    public void baixaProducte(Scanner lector,Statement stmt)throws Exception{
        System.out.println("Entra el codi del Producte");
        codi = lector.nextInt();
        lector.nextLine();
        ResultSet comprovarCodi = stmt.executeQuery("SELECT codi,stock,preu,iva from producte WHERE codi=" + codi + ";");
        boolean trobat = false;
        while (!trobat && comprovarCodi.next()) {
            if (comprovarCodi.getInt(1) == codi) {
                trobat = true;
            }
        }
        if (trobat) {
            comprovarCodi.deleteRow();
            System.out.println("Esborrat correctament!");
        }
    }
    public void llistarProductes( Statement stmt) throws Exception {
        String llistarProductes = "SELECT * FROM producte;";
        ResultSet rsProductes = stmt.executeQuery(llistarProductes);
        System.out.println("---PRODUCTE---------------------------------PREU-----------------------");
        System.out.println("-----------------------------------------------------------------------");

        while (rsProductes.next()) {
            if (rsProductes.getInt(3)==0)System.out.print(String.format("%-20s",rsProductes.getString(2)+"(esgotat)"));
            else System.out.print(String.format("%-20s",rsProductes.getString(2)));
            System.out.print("             " + rsProductes.getInt(4)+" euros ");
            System.out.println("amb un IVA del " + rsProductes.getInt(5)+"%");
            System.out.println("-----------------------------------------------------------------------");
        }
    }
    public void carregarProductesMemoria(ArrayList<Producte>productes,Statement stmt) throws Exception {
        String llistarProductes = "SELECT * FROM producte;";
        ResultSet rsProductes = stmt.executeQuery(llistarProductes);
        while (rsProductes.next()){
            Producte producte = new Producte();
            producte.codi=rsProductes.getInt(1);
            producte.nom=rsProductes.getString(2);
            producte.stock=rsProductes.getInt(3);
            producte.preu=rsProductes.getInt(4);
            producte.iva=rsProductes.getInt(5);
            productes.add(producte);

        }


    }
    public void treureStockProducte(Statement stmt,int codi,int stock) throws Exception {
        stmt.executeUpdate("UPDATE producte SET stock= " + stock + " WHERE codi=" + codi + ";");
    }
}
