import java.sql.Connection;
import java.sql.DriverManager;

class ConnectDB {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Lab4","postgres","1234");

            if(conn!=null) {
                System.out.println("Connection OK!");
            } else {
                System.out.println("Connection Failed");
            }
            
            

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    private static void 
}