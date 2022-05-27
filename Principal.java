import java.util.Scanner;

public class Main {

    private static long empieza;
    private static long termina;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/practica_5", "root", "1234");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("show databases;");
            System.out.println("CONNECTED");
            try {
                stmt.execute("USE practica_5;");
                stmt.execute("DROP TABLE IF EXISTS apartado3;");
                stmt.execute(
                        "CREATE TABLE apartado3 (emp_no INT NOT NULL, salary INT NOT NULL, from_date DATE NOT NULL,to_date DATE NOT NULL);");

                File f = new File("salaries_data");
                String insert = "INSERT INTO `apartado3` VALUES";
                String optimizacion = "SET autocommit = 0;";
                String optimizacion2 = "SET foreign_keys_checks = 0;";
                String commit = "COMMIT;";
                String join = "";
                try {
                    Scanner sc = new Scanner(f);
                    empieza = System.currentTimeMillis();
                    stmt.executeQuery(optimizacion);
                    stmt.executeQuery(optimizacion2);
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String[] fields = line.split("\t");
                        join = insert + "(" + fields[0] + "," + fields[1] + "," + "'" + fields[2] + "'" + "," + "'"
                                + fields[3]
                                + "');" + "\n";
                        stmt.execute(join);
                    }
                    stmt.executeQuery(commit);
                    stmt.executeQuery("SET foreign_keys_checks = 1;");
                    termina = System.currentTimeMillis();
                    System.out.println("Tiempo de ejecucion: " + (termina - empieza) + " milisegundos");
                    System.out.println("Tiempo de ejecucion: " + (termina - empieza) / 1000 + " segundos");
                    sc.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stmt.close();
            } catch (SQLException sqle) {
                System.out.println("Error en la ejecución: "
                        + sqle.getErrorCode() + " " + sqle.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
