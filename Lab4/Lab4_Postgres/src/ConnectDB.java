import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.plaf.nimbus.State;

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
            //task execution
            task1(conn);
            task2(conn);
            task3(conn);
            task4(conn);
            task5(conn);
            task6(conn);
            task7(conn);
            task8(conn);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    private static void task1(Connection conn) throws SQLException{
        //Display the schedule given 3 attributes
        String sql = """
        SELECT 
            \"TO\".\"TripNumber\",
            \"TO\".\"Date\",
            \"TO\".\"ScheduledStartTime\",
            \"TO\".\"ScheduledArrivalTime\",
            \"TO\".\"DriverName\",
            \"TO\".\"BusID\"
        FROM 
            \"TripOffering\" \"TO\"
        JOIN 
            \"Trip\" \"T\" ON \"TO\".\"TripNumber\" = \"T\".\"TripNumber\"
        WHERE 
            \"T\".\"StartLocationName\" = 'New York'
            AND \"T\".\"DestinationName\" = 'Los Angeles'
            AND \"TO\".\"Date\" = '2024-05-06'        
                """;                
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 1");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | DriverID:%s | BusID:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("DriverName"),rs.getString("BusID"));
        }
    }
    private static void task2(Connection conn) throws SQLException{
        //Display the table before editing
        String sql = """
        SELECT * FROM \"TripOffering\" \"TO\"       
                """;                
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 2\nBefore edit");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | DriverID:%s | BusID:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("DriverName"),rs.getString("BusID"));
        }
        //Delete a trip offering
        sql = """
        DELETE FROM \"TripOffering\"
        WHERE 
            \"TripNumber\" = 2
            AND \"Date\" = '2024-05-07'
            AND \"ScheduledStartTime\" = '09:30';            
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //add trips offering
        sql = """
        INSERT INTO \"TripOffering\" (\"TripNumber\", \"Date\", \"ScheduledStartTime\", \"ScheduledArrivalTime\", \"DriverName\", \"BusID\")
        VALUES 
            (4, '2024-05-09', '07:15', '11:30', 'Alice Johnson', 'JKL321'),
            (5, '2024-05-10', '11:00', '15:30', 'Michael Brown', 'MNO456'),
            (6, '2024-05-11', '13:45', '18:15', 'Emily Davis', 'PQR789');
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //change the driver
        sql = """
        UPDATE \"TripOffering\"
        SET \"DriverName\" = 'John Wick'
        WHERE 
            \"TripNumber\" = 4
            AND \"Date\" = '2024-05-09'
            AND \"ScheduledStartTime\" = '07:15';
            
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //change the bus
        sql = """
            UPDATE \"TripOffering\"
            SET \"BusID\" = 'XYZ434'
            WHERE 
                \"TripNumber\" = 5
                AND \"Date\" = '2024-05-10'
                AND \"ScheduledStartTime\" = '11:00';                           
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //Display table after edits
        sql = """
        SELECT * FROM \"TripOffering\" \"TO\"        
                """;
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("After edit");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | DriverID:%s | BusID:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("DriverName"),rs.getString("BusID"));
    }
}
    private static void task3(Connection conn) throws SQLException{
        //Display the stops of a given trip
        String sql = """
        SELECT 
            \"TripNumber\",
            \"StopNumber\",
            \"SequenceNumber\",
            \"DrivingTime\"
        FROM 
            \"TripStopInfo\"
        WHERE 
            \"TripNumber\" = 1;
                """;                
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 3");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | StopNumber:%s | SequenceNumber:%s | DrivingTime:%s\n",rs.getLong("TripNumber"),rs.getString("StopNumber"),rs.getString("SequenceNumber"),rs.getString("DrivingTime"));
        }
    }
    private static void task4(Connection conn) throws SQLException{
        //Display the stops of a given trip
        String sql = """
            SELECT 
            \"TO\".\"TripNumber\",
            \"TO\".\"Date\",
            \"TO\".\"ScheduledStartTime\",
            \"TO\".\"ScheduledArrivalTime\",
            \"TO\".\"BusID\"
        FROM 
            \"TripOffering\" \"TO\"
        JOIN 
            \"Driver\" \"D\" ON \"TO\".\"DriverName\" = \"D\".\"DriverName\"
        WHERE 
            \"D\".\"DriverName\" = 'Bob Johnson'
            AND \"TO\".\"Date\"::date >= '2024-05-08'::date
            AND \"TO\".\"Date\"::date <= ('2024-05-08'::date + interval '6 days');
                """;                
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 4");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | BusID:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("BusID"));
        }
    }
    private static void task5 (Connection conn) throws SQLException{
        //display table before edits
        String sql = """
                SELECT * FROM \"Driver\"
                """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 5\nBefore adding a driver");
        while(rs.next()) {
            System.out.printf("DriverName:%s | DriverTelephoneNumber:%s\n",rs.getString("DriverName"),rs.getString("DriverTelephoneNumber"));
        }
        //update value
        sql = """
            INSERT INTO \"Driver\" (\"DriverName\", \"DriverTelephoneNumber\")
            VALUES ('Katniss Everdeen', '555-123-4567');            
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //display table after edits
        sql = """
            SELECT * FROM \"Driver\"
            """;
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("Task 5\nAfter adding a driver");
        while(rs.next()) {
            System.out.printf("DriverName:%s | DriverTelephoneNumber:%s\n",rs.getString("DriverName"),rs.getString("DriverTelephoneNumber"));
        }
    }
    private static void task6 (Connection conn) throws SQLException{
        //display table before edits
        String sql = """
                SELECT * FROM \"Bus\"
                """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 6\nBefore adding a bus");
        while(rs.next()) {
            System.out.printf("BusID:%s | Model:%s | Year:%s\n",rs.getString("BusID"),rs.getString("Model"),rs.getString("Year"));
        }
        //update value
        sql = """
            INSERT INTO \"Bus\" (\"BusID\", \"Model\", \"Year\")
            VALUES ('ABC123', 'Volvo', '2022');                       
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //display table after edits
        sql = """
            SELECT * FROM \"Bus\"
            """;
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("Task 6\nAfter adding a bus");
        while(rs.next()) {
            System.out.printf("BusID:%s | Model:%s | Year:%s\n",rs.getString("BusID"),rs.getString("Model"),rs.getString("Year"));
        }
    }
    private static void task7 (Connection conn) throws SQLException{
        //display table before edits
        String sql = """
                SELECT * FROM \"Bus\"
                """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 7\nBefore deleting a bus");
        while(rs.next()) {
            System.out.printf("BusID:%s | Model:%s | Year:%s\n",rs.getString("BusID"),rs.getString("Model"),rs.getString("Year"));
        }
        //update value
        sql = """
            DELETE FROM \"Bus\"
            WHERE \"BusID\" = 'ABC123';                                  
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //display table after edits
        sql = """
            SELECT * FROM \"Bus\"
            """;
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("Task 7\nAfter deleting a bus");
        while(rs.next()) {
            System.out.printf("BusID:%s | Model:%s | Year:%s\n",rs.getString("BusID"),rs.getString("Model"),rs.getString("Year"));
        }
    }
    private static void task8 (Connection conn) throws SQLException{
        //display table before edits
        String sql = """
                SELECT * FROM \"ActualTripStopInfo\"
                """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Task 8\nBefore adding actual trips info");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | ActualStartTime:%s | ActualArrivalTime:%s | NumberOfPassengerIn:%s | NumberOfPassengerOut:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("ActualStartTime"),rs.getString("ActualArrivalTime"),rs.getString("NumberOfPassengerIn"),rs.getString("NumberOfPassengerOut"));
        }
        //update value
        sql = """
            INSERT INTO "ActualTripStopInfo" ("TripNumber", "Date", "ScheduledStartTime", "StopNumber", "ScheduledArrivalTime", "ActualStartTime", "ActualArrivalTime", "NumberOfPassengerIn", "NumberOfPassengerOut")
            VALUES (10, '2024-02-15', '08:00', '001', '12:00', '08:05', '12:10', 20, 10),
                   (11, '2024-02-16', '09:30', '002', '14:00', '09:35', '14:05', 15, 5),
                   (12, '2024-02-17', '10:45', '003', '23:00', '10:50', '23:10', 25, 8);
                                   
                """;
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        //display table after edits
        sql = """
            SELECT * FROM \"ActualTripStopInfo\"
            """;
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("Task 8\nAfter adding actual trips info");
        while(rs.next()) {
            System.out.printf("TripNumber:%d | Date:%s | ScheduledStartTime:%s | ScheduledArrivalTime:%s | ActualStartTime:%s | ActualArrivalTime:%s | NumberOfPassengerIn:%s | NumberOfPassengerOut:%s\n",rs.getLong("TripNumber"),rs.getString("Date"),rs.getString("ScheduledStartTime"),rs.getString("ScheduledArrivalTime"),rs.getString("ActualStartTime"),rs.getString("ActualArrivalTime"),rs.getString("NumberOfPassengerIn"),rs.getString("NumberOfPassengerOut"));
        }
    }
}