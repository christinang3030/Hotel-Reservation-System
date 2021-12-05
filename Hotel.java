import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Hotel {

    // JDBC driver name and database URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_reservation";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "shuju888";
    private static Connection conn = null;
    private static Statement statement = null;

    public static void main(String[] args) {

        try {
            // Create connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL + "?serverTimezone=UTC", USER, PASS);
            Statement statement = conn.createStatement();
            ResultSet rs = null;

            // Data in schema
            System.out.println("\n\n====== Schema after being populated with data ======");
            rs = statement.executeQuery("SELECT * FROM Guest");
            System.out.println("\nPrinting Guests...");
            printResultSetfromGuest(rs);
            rs = statement.executeQuery("SELECT * FROM Room");
            System.out.println("\nPrinting Rooms...");
            printResultSetfromRoom(rs);
            rs = statement.executeQuery("SELECT * FROM Service");
            System.out.println("\nPrinting Services...");
            printResultSetfromService(rs);
            rs = statement.executeQuery("SELECT * FROM Available");
            System.out.println("\nPrinting Available Rooms...");
            printResultSetfromAvailable(rs);
            rs = statement.executeQuery("SELECT * FROM Booked");
            System.out.println("\nPrinting Booked Rooms...");
            printResultSetfromBooked(rs);
            rs = statement.executeQuery("SELECT * FROM Employee");
            System.out.println("\nPrinting Employees...");
            printResultSetfromEmployee(rs);
            rs = statement.executeQuery("SELECT * FROM Transaction");
            System.out.println("\nPrinting Transactions...");
            printResultSetfromTransaction(rs);

            // Functional requirements
            System.out.println("\n\n====== Functional Requirements ======");
            guestsInAlphaOrder(statement);
            findCheapestDouble(statement);
            sortUnder100(statement);
            typesMax100(statement);
            allEmployees(statement);
            futureTransactions(statement);
            roomCleaning(statement);
            checkTransactionHistory(statement);
            findSingleRooms(statement);
            findNonSingleRooms(statement);
            findSpaRooms(statement);
            findOlderBooked(statement);
            findMinibarRooms(statement);
            findSpaOrMealRooms(statement);
            findSingleSpaRooms(statement);
            findTripleRoomServices(statement);

            // Key constraint violations
            System.out.println("\n\n====== Primary Key Constraint Violation ======");
            /*try {
                primaryKeyViolation(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            System.out.println("\n\n^^^ The error message may appear above for the foreign key constraint ^^^");
            System.out.println("\n====== Foreign Key Constraint Violation ======");
            try {
                foreignKeyViolation(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Archive function
            System.out.println("\n\n====== Archive funcion ======");
            callArchiveProcedure();
            rs = statement.executeQuery("SELECT * FROM ArchivedGuest");
            System.out.println("\nPrinting Archived Guests...");
            printResultSetfromArchivedGuest(rs);
            rs = statement.executeQuery("SELECT * FROM Guest");
            System.out.println("\nPrinting Guests...");
            printResultSetfromGuest(rs);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printResultSetfromGuest(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int cID = rs.getInt("cID");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int reservation = rs.getInt("reservation");
            Date updatedAt = rs.getDate("updatedAt");
            System.out.println("Guest ID: " + cID + " Name: " + name + " Age: " + age +
                    " Reservation: " + reservation + " Updated At: " + updatedAt);
        }
    }

    private static void printResultSetfromRoom(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int rID = rs.getInt("rID");
            String type = rs.getString("type");
            int price = rs.getInt("price");
            System.out.println("Room ID: " + rID + " Type: " + type + " Price: " + price);
        }
    }

    private static void printResultSetfromService(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int rID = rs.getInt("rID");
            int serviceCode = rs.getInt("serviceCode");
            String service = rs.getString("service");
            System.out.println("Room ID: " + rID + " Service Code: " + serviceCode + " Service: " + service);
        }
    }

    private static void printResultSetfromEmployee(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int eID = rs.getInt("employee_ID");
            String name = rs.getString("employee_name");
            String position = rs.getString("position");
            String number = rs.getString("phone_number");
            System.out.println("Employee ID: " + eID + " Name: " + name + " Position: " + position + " Number: " + number);
        }
    }

    private static void printResultSetfromBooked(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int rID = rs.getInt("rID");
            int cID = rs.getInt("cID");
            String date = rs.getString("date_booked");
            System.out.println("Room ID: " + rID + " cID: " + cID + " Date Booked: " + date);
        }
    }

    private static void printResultSetfromAvailable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int rID = rs.getInt("rID");
            String date = rs.getString("date_available");
            System.out.println("Room ID: " + rID + " Date Available: " + date);
        }
    }

    private static void printResultSetfromTransaction(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int transID = rs.getInt("transaction_id");
            int cID = rs.getInt("cID");
            int rID = rs.getInt("rID");
            int days = rs.getInt("days");
            int amount = rs.getInt("amount_due");
            String time_of_purchase = rs.getString("time_of_purchase");
            String checkIn = rs.getString("checkIn");
            String checkOut = rs.getString("checkOut");
            System.out.println("Transaction ID: " + transID + " cID: " + cID + " rID: " + rID + " Days: " + days + " Amount Due: $" +
                    amount + " Time of Purchase: " + time_of_purchase + " Check In: " + checkIn + " Check Out: " + checkOut);
        }
    }

    private static void callArchiveProcedure() throws SQLException {
        System.out.println("\nCalling the procedure Archive with cutoff date: '2021-09-10'");
        String sql = "{CALL Archive(?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setString(1, "2021-09-10");
        ResultSet rs = cs.executeQuery();
    }

    private static void printResultSetfromArchivedGuest(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int cID = rs.getInt("cID");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int reservation = rs.getInt("reservation");
            Date updatedAt = rs.getDate("updatedAt");
            System.out.println("Guest ID: " + cID + " Name: " + name + " Age: " + age +
                    " Reservation: " + reservation + " Updated At: " + updatedAt);
        }
    }

    private static void guestsInAlphaOrder(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from Guest order by name");
        System.out.println("\nGetting a lit of all guests in alphabetical order:");
        //printResultSetfromGuest(rs);
        while (rs.next()) {
            int cID = rs.getInt("cID");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("Guest ID: " + cID + " Name: " + name + " Age: " + age);
        }
    }

    private static void findCheapestDouble(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery(
                "select * from Room where type='double' " +
                        "and price <= all (select price from Room R2 where type='double')");
        System.out.println("\nFinding the cheapest double room:");
        printResultSetfromRoom(rs);
    }

    private static void sortUnder100(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery
                ("select * from Room where price < 100 order by price, rID");
        System.out.println("\nSorting the rooms under $100 from cheapest to most expensive:");
        printResultSetfromRoom(rs);
    }

    private static void typesMax100(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery
                ("select type, max(price) as max_price from Available join Room " +
                        "where Available.rID = Room.rID group by type having max(price) <= 100");
        System.out.println("\nFinding the types of available rooms where the max price is $100:");
        while (rs.next()) {
            String type = rs.getString("type");
            int maxPrice = rs.getInt("max_price");
            System.out.println("Type: " + type + " Max_Price: " + maxPrice);
        }
    }

    private static void allEmployees(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from Employee");
        System.out.println("\nFinding all employees:");
        printResultSetfromEmployee(rs);
    }

    private static void futureTransactions(Statement statement) throws SQLException
    {
        ResultSet rs = statement.executeQuery("select * from Transaction where checkIn > \"2021-10-11\"");
        System.out.println("\nFinding all transactions where the check in date is after '2021-10-11':");
        printResultSetfromTransaction(rs);
    }

    private static void roomCleaning(Statement statement) throws SQLException
    {
        ResultSet rs = statement.executeQuery("select * from Employee where position like 'housekeeper'");
        System.out.println("\nFinding people for room cleaning:");
        printResultSetfromEmployee(rs);
    }

    private static void checkTransactionHistory(Statement statement) throws SQLException
    {
        ResultSet rs = statement.executeQuery("select * from Transaction where cID in " +
                                        "( select cID from Guest where name like 'Austin Lee');");
        System.out.println("\nChecking customer (Austin Lee)'s transaction history:");
        printResultSetfromTransaction(rs);
    }

    private static void findSingleRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all available single rooms:");
        ResultSet rs = statement.executeQuery("select * from Available where rID in " +
                                                "(select rID from Room where type = \"single\")");
        printResultSetfromAvailable(rs);
    }

    private static void findNonSingleRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all available rooms with more than one bed:");
        ResultSet rs = statement.executeQuery("select * from Available where rID in " +
                                                "(select rID from Room where type <> \"single\")");
        printResultSetfromAvailable(rs);
    }

    private static void findSpaRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all available rooms that come with a spa:");
        ResultSet rs = statement.executeQuery("select * from Available where rID in " +
                "(select Room.rID from Room, Service where Room.rID=Service.rID and service=\"spa\")");
        printResultSetfromAvailable(rs);
    }

    private static void findOlderBooked(Statement statement) throws SQLException {
        System.out.println("\nFinding all booked rooms with guests over 50:");
        ResultSet rs = statement.executeQuery("select * from Booked where cID in " +
                                                "(select cID from Guest where age > 50)");
        printResultSetfromBooked(rs);
    }

    private static void findMinibarRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all available rooms with a minibar:");
        ResultSet rs = statement.executeQuery("select * from Room where rID in " +
                        "( select Service.rID from Service, Available " +
                        "where Service.rID=Available.rID and service = 'minibar')");
        printResultSetfromRoom(rs);
    }

    private static void findSpaOrMealRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all available rooms with a spa or special meal:");
        ResultSet rs = statement.executeQuery("select * from Room where rID in " +
                "( select Service.rID from Service, Available " +
                "where Service.rID=Available.rID and (service = 'spa' or service = 'special meal'))");
        printResultSetfromRoom(rs);
    }

    private static void findSingleSpaRooms(Statement statement) throws SQLException {
        System.out.println("\nFinding all single rooms that request spa for an extra service:");
        ResultSet rs = statement.executeQuery("select * from Room where rID in " +
                "(select rID from Service where Room.rID = Service.rID and Room.type = 'single' " +
                "and Service.service = 'spa')");
        printResultSetfromRoom(rs);
    }

    private static void findTripleRoomServices(Statement statement) throws SQLException {
        System.out.println("\nFinding all available services with triple rooms:");
        ResultSet rs = statement.executeQuery("select distinct service from Service where rID in " +
                "(select rID from Room where Room.rID = Service.rID and Room.type = 'triple')");

        while(rs.next())
        {
            String service = rs.getString("service");
            System.out.println("Service: " + service);
        }
    }

    private static void primaryKeyViolation(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM Room WHERE rID=101");
        System.out.println("Find if room 101 exists:");
        printResultSetfromRoom(rs);

        System.out.println("Attempt to execute 'INSERT INTO Room VALUES (101, 'single', 100)'");
        statement.executeUpdate("INSERT INTO Room VALUES (101, 'single', 100)");
    }

    private static void foreignKeyViolation(Statement statement) throws SQLException {
        System.out.println("Find if room 101 is booked");
        ResultSet rs = statement.executeQuery("SELECT * FROM Booked WHERE rID=101");
        printResultSetfromBooked(rs);

        System.out.println("Attempt to execute 'DELETE FROM Room WHERE rID=101'");
        statement.executeUpdate("DELETE FROM Room WHERE rID=101");
    }
}


