import java.sql.*;

public class Test {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel?serverTimezone=UTC", "root", "password");

            Statement statement = connection.createStatement();
            ResultSet rs = null;

            rs = statement.executeQuery("select * from Service");
                    System.out.println("All rooms and their respective service:");
                    while (rs.next()) {
                    int serviceCode = rs.getInt("serviceCode");
                    int number = rs.getInt("rID");
                    String serv = rs.getString("service");
                    System.out.println("serviceCode: " + serviceCode + " Room Number: " + number + " Service: " + serv);

                    }
                    rs = statement.executeQuery("select * from Service where service='spa'");
                    System.out.println("\nRooms that come with a spa:");
                    while (rs.next()) {
                    int roomNumber = rs.getInt("rID");
                    String service = rs.getString("service");
                    System.out.println("Room Number: " + roomNumber + " Service: " + service);
                    }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






//            ResultSet resultSet = statement.executeQuery("select * from Guest");

//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                int cID = resultSet.getInt("cID");
//                int age = resultSet.getInt("age");
////                System.out.println(resultSet.getString("name"));
//                System.out.println("cID: " + cID + " Name: " + name + " Age: " + age);
//            }

//            rs = statement.executeQuery("select * from Room");
//
//            while (rs.next()) {
//                int roomNumber = rs.getInt("number");
//                String type = rs.getString("type");
//                System.out.println("Room Number: " + roomNumber + " Type: " + type);
//            }
//
//            rs = statement.executeQuery("select * from Service");
//
//            while (rs.next()) {
//                int serviceCode = rs.getInt("serviceCode");
//                int number = rs.getInt("number");
//                String serv = rs.getString("service");
//                System.out.println("serviceCode: " + serviceCode + " Room Number: " + number + " Service: " + serv);
//
//            }

//
//            rs = statement.executeQuery("select * from Room, Service where Room.rID=Service.number and service='spa'");
//                    System.out.println("\nRooms that come with a spa:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    String type = rs.getString("type");
//                    int price = rs.getInt("price");
//                    System.out.println("Room Number: " + roomNumber + " Type: " + type + " Price: " + price);
//                    }
////
//            rs = statement.executeQuery("select * from Booked");
//                    System.out.println("\nAll booked rooms:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    System.out.println("Room Number: " + roomNumber);
//                    }
//
//                    rs = statement.executeQuery("select * from Booked where cID in (select cID from Guest where age > 50)");
//                    System.out.println("\nFind all booked rooms with guests over 50:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    System.out.println("Room Number: " + roomNumber);
//                    }




//            rs = statement.executeQuery("select * from Room where type='double'");
//                    System.out.println("All double rooms:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    String type = rs.getString("type");
//                    int price = rs.getInt("price");
//                    System.out.println("Room Number: " + roomNumber + " Type: " + type + " Price: " + price);
//                    }
//                    rs = statement.executeQuery("select * from Room where type='double' and price <= all (select price from Room R2 where type='double')");
//                    System.out.println("\nCheapest double room:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    String type = rs.getString("type");
//                    int price = rs.getInt("price");
//                    System.out.println("Room Number: " + roomNumber + " Type: " + type + " Price: " + price);
//                    }

//            rs = statement.executeQuery("select * from Service");
//                    System.out.println("All rooms and their respective service:");
//                    while (rs.next()) {
//                    int serviceCode = rs.getInt("serviceCode");
//                    int number = rs.getInt("rID");
//                    String serv = rs.getString("service");
//                    System.out.println("serviceCode: " + serviceCode + " Room Number: " + number + " Service: " + serv);
//
//                    }
//                    rs = statement.executeQuery("select * from Service where service='spa'");
//                    System.out.println("\nRooms that come with a spa:");
//                    while (rs.next()) {
//                    int roomNumber = rs.getInt("rID");
//                    String service = rs.getString("service");
//                    System.out.println("Room Number: " + roomNumber + " Service: " + service);
//                    }



