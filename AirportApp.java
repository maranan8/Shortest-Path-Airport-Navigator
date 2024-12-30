//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: Application that has options to query airport information from IATA code,
//              find minimum distance between airports, display a help message,  and exit the program.
//              Uses a HashedDictionary to hold IATA Code as the key and information as the value.
//              Uses a Directed Graph to hold the airport connections.
//
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AirportApp {
    public static void main(String[] args) {
        DictionaryInterface<String, String> dictOfAirportCodes = new HashedDictionary<>(1009);
        GraphInterface<String> routeMap = new DirectedGraph<>();
        try {
            Scanner codes = new Scanner(new File("US_Airport_Codes.csv"));
            while (codes.hasNextLine()) {
                String[] name = codes.nextLine().split(",");
                dictOfAirportCodes.add(name[0], name[1]);
                routeMap.addVertex(name[0]);
            }

            Scanner routes = new Scanner(new File("US_Airports_Routes.csv"));
            while (routes.hasNextLine()) {
                String[] toFromDistance = routes.nextLine().split(",");
                routeMap.addEdge(toFromDistance[0], toFromDistance[1], Integer.parseInt(toFromDistance[2]));
            }

            codes.close();
            routes.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Airports v0.24f by Austin Maranan\n");

        boolean running = true;
        while (running) {
            System.out.print("Command? ");
            String command = scan.next();
            switch (command) {
                case "Q":
                    System.out.print("Airport Code? ");
                    String airportCode = scan.next();
                    String airportName = dictOfAirportCodes.getValue(airportCode);
                    if (airportName == null)
                        System.out.println("Airport code unknown");
                    else
                        System.out.println(airportName);
                    break;
                case "D":
                    System.out.print("Airport codes from to? ");
                    String fromAirport = scan.next();
                    String toAirport = scan.next();
                    if (!dictOfAirportCodes.contains(toAirport) || !dictOfAirportCodes.contains(fromAirport)) {
                        System.out.println("Airport code unknown");
                    }
                    else {
                        StackInterface<String> route = new ArrayStack<>();
                        int distance = routeMap.getCheapestPath(fromAirport, toAirport, route);
                        if (distance != -1) {
                            System.out.println("The shortest distance between " + fromAirport + " and " + toAirport + " is " + distance + ":");
                            while (!route.isEmpty()) {
                                String airport = route.pop();
                                System.out.println(dictOfAirportCodes.getValue(airport) + " [" + airport + "]");
                            }
                        }
                        else {
                            System.out.println("Airports not connected");
                        }
                    }
                    break;
                case "H":
                    System.out.println("Q Query the airport information by entering the airport code.\n" +
                            "D Find the minimum distance between two airports.\n" +
                            "H Display this message.\n" +
                            "E Exit.");
                    break;
                case "E":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Command.");
            }
        }


    }
}
