/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Set;

public class FlightManager
{
  // Contains list of Flights departing from Toronto in a single day
	//ArrayList<Flight> flights = new ArrayList<Flight>();
  TreeMap<String, Flight> flightMap = new TreeMap<String, Flight>();
  ArrayList<String> listOfFlightNums = new ArrayList<String>();

  String[] cities = 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  // flight times in hours
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16// Tokyo
  										};
  
  // Contains list of available airplane types and their seat capacity
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
  
  String errorMsg = null; // if a method finds an error (e.g. flight number not found) set this string. See video!
  
  Random random = new Random(); // random number generator - google "Java class Random". Use this in generateFlightNumber
  
  /**
   * Default constructor
   * All flights and corresponding information is generated and stored in the 'flights' ArrayList
   */
  public FlightManager() throws FileNotFoundException
  {
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(84, "Boeing 737"));      //0
  	airplanes.add(new Aircraft(180,"Airbus 320"));      //1
  	airplanes.add(new Aircraft(36, "Dash-8 100"));      //2
  	airplanes.add(new Aircraft(4, "Bombardier 5000"));  //3
  	airplanes.add(new Aircraft(592, 16, "Boeing 747")); //4

    try {
      File flightFile = new File("flights.txt");    //Extracts information from flights.txt
      Scanner flightScanner = new Scanner(flightFile);

      String flightNum;
      String airline;
      String city;
      String departure;
      int duration;
      int[] airplaneTypeArray = {0,1,1,2,3,2,0,4};  //Consistent with Assignment 1, these integers represent the airplane types (See above for number matches)
      int airplaneTypeIndex = 0;                    //Keeps track of the index of airplaneTypeArray

      for (int i = 1; i <= 8; i++) {
        airline = flightScanner.next();
        city = flightScanner.next();
        departure = flightScanner.next();
        duration = flightScanner.nextInt();
        flightNum = generateFlightNumber(airline);
      
        Flight flight = new Flight(flightNum, airline, city, departure, duration, airplanes.get(airplaneTypeArray[airplaneTypeIndex]));
        //flights.add(flight);
        
        flightMap.put(flightNum, flight);
        
        int randNum = random.nextInt(4) + 1;  //Sets a random status for each flight

        if (randNum == 2) {flight.setStatus(Flight.Status.DELAYED);}
        else if (randNum == 3) {flight.setStatus(Flight.Status.INFLIGHT);}
        else if (randNum == 4) {flight.setStatus(Flight.Status.ARRIVED);}

        if (i == 8) {flight.setFlightType(Flight.FlightType.LONGHAUL);}

        airplaneTypeIndex += 1;
      }
  } catch (FileNotFoundException exception) {   //Occurs if the file cannot be found
      System.out.println("File not found.");
    }
  }

  /**
   * Gets the flight corresponding to its flight number
   * @param flightNum A string representing the flight's number
   * @return The flight based on the corresponding flight number
   */
  public Flight getCorrespondingFlight (String flightNum)
  {
    return flightMap.get(flightNum);
  }
  
  /*
   * This private helper method generates and returns a flight number string from the airline name parameter
   * For example, if parameter string airline is "Air Canada" the flight number should be "ACxxx" where xxx is 
   * a random 3 digit number between 101 and 300 (Hint: use class Random - see variable random at top of class)
   * you can assume every airline name is always 2 words. 
   * 
   */
  
   /**
   * Generates a random flight number for the correspondig airline
   * @param airline The airline of the corresponding flight
   * @return A string containing the first two characters of the airline, followed by three randomly generated numbers between 100 and 300 (Ex. 'Air Canada' returns "AC" + "209")
   */
  private String generateFlightNumber(String airline)
  {
    // Your code here
    boolean duplicateFlightNum = true;  //Must be initialized to true to enter the while loop
    String flightNum = "";
    while (duplicateFlightNum) {  //While the flight num is the same as at least one other flight number previously generated
      duplicateFlightNum = false;
      String firstLetters = String.valueOf(airline.charAt(0)) + String.valueOf(airline.charAt(airline.indexOf("_") + 1)); //Extracts the first letter of both words
      flightNum += firstLetters;
      String randNum = String.valueOf(random.nextInt(3) + 1);   //Generates the first number out of the three
      flightNum += randNum;
    
      for (int i = 0; i < 2; i++) { //Loop that generates the last two numbers
        if (randNum.equals("3")) {
          flightNum += "00";                                    //Adds "00" to flight number if MSD 3
          break;
        } else {
          randNum = String.valueOf(random.nextInt(10));         //Generates random integer between 0 and 9 twice if MSD is not 3
        }
        flightNum += randNum;
      }
      
      if (flightNum.charAt(3) == '3') {                             //If the second of the three digits is 3, remove the trailing 0s.
        flightNum = flightNum.substring(0, flightNum.length()-2);
        flightNum += String.valueOf(random.nextInt(10));            //Replace the trailing 0s with one additonal number between 0 and 9 inclusive
      }

      for (int i = 0; i < listOfFlightNums.size(); i++) {                //Checks if the flight number already exists. If so, it is cleared and new number is generated.
        if (flightNum.equals(listOfFlightNums.get(i))) {
          duplicateFlightNum = true;
          flightNum = "";
        }
      }
    }
  	return flightNum;
  }

  /**
   * Prints all flights in flights array list.
   */
  public void printAllFlights()
  { 
    Set<String> numToFlight = flightMap.keySet();

    for (String flightNum : numToFlight) {
      System.out.println(flightMap.get(flightNum).toString());
    }
  }
  
  /**
   * Returns an object of type Reservation if seats of the corresponding flight are available
   * @param flightNum A string containing flight's number
   * @param seatType A string containing information about the seat type, either economy or first class
   * @return newRes, a reservation object if the corresponding flight both exists and has available seats, otherwise null is returned
   * @throws InvalidFlightNumException
   * @throws FlightFullException
   * @throws DuplicateReservationException
   * @throws DuplicatePassengerException
   * @throws SeatOccupiedException
   * @throws InvalidSeatException
   */
  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat, ArrayList<Reservation> myReservations)
    throws InvalidFlightNumException, FlightFullException, DuplicateReservationException, DuplicatePassengerException, SeatOccupiedException, InvalidSeatException
  {
  	// Check for valid flight number by searching through flights array list
  	// If matching flight is not found, set instance variable errorMsg (see at top) and return null 
  	Flight flightToReserveSeatOn = null;

    Reservation newRes = new Reservation(flightNum, name, passport, seat);
    Passenger newPassenger = new Passenger(name, passport, seat);

    //Sets the reservation as first class if conditions are met
    if (seat.length() > 2 && seat.charAt(2) == '+') {
      newRes.setFirstClass();
      newPassenger.setSeatType("First Class");
    }

    flightToReserveSeatOn = this.getCorrespondingFlight(flightNum);

    //Occurs when the flight is not found
    
    if (flightToReserveSeatOn == null) { 
      throwInvalidFlightNumException(flightNum);
    }

    //Checks the flight to see if there is a duplicate passenger. Exception is raised if found.
    for (int p = 0; p < flightToReserveSeatOn.manifest.size(); p++) {
      if (newPassenger.equals(flightToReserveSeatOn.manifest.get(p))) {
        throw new DuplicatePassengerException("Duplicate Passenger " + name + " " + passport);
      }
    }

    boolean validSeat = false;
    int rowIndex = -1;
    int colIndex = -1;

    //Checks if the seat given is a valid one by looping through vacantSeatLayout
    for (int i = 0; i < flightToReserveSeatOn.getAircraft().getVacantSeatLayout().length; i++) {
      for (int j = 0; j < flightToReserveSeatOn.getAircraft().getVacantSeatLayout()[0].length; j++) {
        if (seat.equals(flightToReserveSeatOn.getAircraft().getVacantSeatLayout()[i][j])) {
          validSeat = true;
          rowIndex = i;
          colIndex = j;
          break;
        }
      }
    }

    if (!(validSeat)) {throw new InvalidSeatException(seat + " is not a valid seat number.");}

    //Checks if the seat given is already occupied by another passenger, otherwise updates that seat to XX to indicate it is occupied
    if (flightToReserveSeatOn.getAircraft().seatLayout[rowIndex][colIndex].equals("XX")) {
      throw new SeatOccupiedException("Seat " + seat + " is already occupied.");
    } else {
      flightToReserveSeatOn.getAircraft().seatLayout[rowIndex][colIndex] = "XX";
    }

    if (seat.contains("+")) {newPassenger.setSeatType("First Class Seat");}

    newRes.setPassengerPassport(passport);
    flightToReserveSeatOn.manifest.add(newPassenger);
    flightToReserveSeatOn.seatMap.put(seat, newPassenger);
    return newRes;
  }
  
  /**
   * Cancels a reservation
   * @param res A reservation object representing the reservation to cancel
   * @throws NoReservationFoundException
   * @throws PassengerNotInManifestException
   * @throws SeatVacantException
   */
  public void cancelReservation(Reservation res) throws NoReservationFoundException, PassengerNotInManifestException, SeatVacantException
  {
  	if (res == null) {
      throw new NoReservationFoundException("Reservation Not Found.");
    }

    String resFlightString = res.getFlightNum();
    Flight flightToCancel = this.getCorrespondingFlight(resFlightString);
    
    boolean validSeat = false;
    int rowIndex = -1;
    int colIndex = -1;

    //Checks if the reservation's seat is valid
    for (int i = 0; i < flightToCancel.getAircraft().vacantSeatLayout.length; i++) {
      for (int j = 0; j < flightToCancel.getAircraft().vacantSeatLayout[0].length; j++) {
        if (res.getPassengerSeat().equals(flightToCancel.getAircraft().vacantSeatLayout[i][j])) {
          validSeat = true;
          rowIndex = i;
          colIndex = j;
          break;
        }
      }
    }

    if (!(validSeat)) {throw new PassengerNotInManifestException(res.getPassengerName() + " does not have a seat reserved on this flight.");}

    //Checks if the seat is currently occupied, otherwise an exception is thrown
    if (flightToCancel.getAircraft().seatLayout[rowIndex][colIndex].equals("XX")) {
      flightToCancel.getAircraft().seatLayout[rowIndex][colIndex] = flightToCancel.getAircraft().vacantSeatLayout[rowIndex][colIndex];
    } else {
      throw new SeatVacantException("This reservation's seat is not occupied.");
    }
    
    flightToCancel.cancelSeat(res.getPassengerSeat());
  }

  /**
   * Throws an invalid flight number exception, used for when a flight number is invalid
   * @param flightNum
   * @throws InvalidFlightNumException
   */
  public void throwInvalidFlightNumException(String flightNum) throws InvalidFlightNumException {
    throw new InvalidFlightNumException("Flight " + flightNum + " not found.");
  }

  //-------------CUSTOM EXCEPTIONS-------------

/* Below a are classes to model various exceptions, described by name
 */

public class InvalidFlightNumException extends Exception {
  /**
   * Sends a message indicating that the flight number is invalid
   * @param message A string representing the corresponding error message (Applies to all exceptions below)
   */
  public InvalidFlightNumException(String message) {
    super(message);
  }
}

public class FlightFullException extends Exception {
  /**
   * Sends a message indicating that the flight is full
   * @param message (Same as above)
   */
  public FlightFullException(String message) {
    super(message);
  }
}

public class FirstClassFlightFullException extends Exception {
  /**
   * Sends a message indicating that all first class seats on a flight are full
   * @param message (Same as above)
   */
  public FirstClassFlightFullException(String message) {
    super(message);
  }
}

public class NotLongHaulFlightException extends Exception {
  /**
   * Sends a message indicating that the corresponding flight is not a long haul flight
   * @param message (Same as above)
   */
  public NotLongHaulFlightException(String message) {
    super(message);
  }
}

public class NoReservationFoundException extends Exception {
  /**
   * Sends a message indicating that all first class seats on a flight are full
   * @param message (Same as above)
   */
  public NoReservationFoundException(String message) {
    super(message);
  }
}

public class DuplicateReservationException extends Exception {
  /**
   * Sends a message indicating that the a duplicate reservation with the same passport number already exists
   * @param message (Same as above)
   */
  public DuplicateReservationException(String message) {
    super(message);
  }
}

public class DuplicatePassengerException extends Exception {
  /**
   * Sends a message indicating that the a duplicate reservation with the same passport number already exists
   * @param message (Same as above)
   */
  public DuplicatePassengerException(String message) {
    super(message);
    }
  }

public class PassengerNotInManifestException extends Exception {
  /**
   * Sends a message indicating that the passenger is not in the flight's manifest (i.e. Passenger list)
   * @param message (Same as above)
   */
  public PassengerNotInManifestException(String message) {
    super(message);
  }
  }

public class SeatOccupiedException extends Exception {
  /**
   * Sends a message indicating that the seat the passenger wants to reserve is already occupied.
   * @param message (Same as above)
   */
  public SeatOccupiedException(String message) {
    super(message);
  }
}

public class InvalidSeatException extends Exception {
  /**
   * Sends a message indicating that the seat the passenger wants to reserve is invalid. (i.e. Does not exist) 
   * @param message (Same as above)
   */
  public InvalidSeatException(String message) {
    super(message);
  }
}

public class SeatVacantException extends Exception {
  /**
   * Sends a message indicating that the seat the passenger wants to cancel and make valid is already vacant.
   * Used when cancelling a reservation's seat
   * @param message (Same as above)
   */
  public SeatVacantException(String message) {
    super(message);
  }
}

}