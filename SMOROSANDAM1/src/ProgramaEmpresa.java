import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ProgramaEmpresa {
    public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Botigav", "postgres", "mcgastron99");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            boolean sortirPrograma=false;
            do{
                System.out.println("INTERFICIE DE L'EMPRESA\n\n");
                System.out.println("Entra 1 per visualitzar les dades d'un client.");
                System.out.println("Entra 2 per llistar els clients.");
                System.out.println("Entra 3 per donar d'alta un producte.");
                System.out.println("Entra 4 per modificar un producte.");
                System.out.println("Entra 5 per donar de baixa un producte.");
                System.out.println("Entra 6 per sortir del programa.");
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                int menu = lector.nextInt();
                lector.nextLine();

                switch (menu) {
                    case 1:
                        String dni;
                        do {
                            System.out.println("Entra el dni del usuari");
                            dni = lector.nextLine().trim();
                        } while (new Client().verificarDni(dni));
                        new Client().llistarClient(dni, stmt);
                        System.out.println("Prem enter per tornar al menu.");
                        lector.nextLine();
                        break;
                    case 2:
                        new Client().llistarClients(stmt);
                        System.out.println("Prem enter per tornar al menu.");
                        lector.nextLine();
                        break;
                    case 3:
                        new  Producte().altaProducte(lector,stmt);
                        System.out.println("Prem enter per tornar al menu.");
                        lector.nextLine();
                        break;
                    case 4:
                        System.out.println("Entra el codi del Producte");
                        int codi = lector.nextInt();
                        lector.nextLine();
                        ResultSet comprovarCodi = stmt.executeQuery("SELECT codi,stock,preu,iva from producte WHERE codi=" + codi + ";");
                        boolean trobat = false;
                        while (!trobat && comprovarCodi.next()) {
                            if (comprovarCodi.getInt(1) == codi) {
                                trobat = true;
                            }
                        }
                        if (trobat) {
                            System.out.println("Entra un numero per actualitzar una dada.\n");
                            System.out.println("1.L'stock del producte es de: " + comprovarCodi.getInt(2));
                            System.out.println("2.El preu del producte es de: " + comprovarCodi.getInt(3));
                            System.out.println("3.L'iva del producte es de: " + comprovarCodi.getInt(4));

                            int menuUpdate = lector.nextInt();
                            lector.nextLine();
                            switch (menuUpdate) {
                                case 1:
                                    new Producte().modificarProducteStock(lector, stmt,codi);
                                    break;
                                case 2:
                                    new Producte().modificarProductePreu(lector, stmt,codi);

                                    break;
                                case 3:
                                    new Producte().modificarProducteIva(lector, stmt,codi);
                            }
                        }
                        System.out.println("Prem enter per tornar al menu.");
                        lector.nextLine();
                        break;
                    case 5:
                        new Producte().baixaProducte(lector,stmt);
                        System.out.println("Prem enter per tornar al menu.");
                        lector.nextLine();
                        break;
                    case 6: sortirPrograma=true;
                }
            }while (!sortirPrograma);


                con.close();
        }
        catch (Exception e){
            System.out.println("Error"+e);
        }
    }
}

