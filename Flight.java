/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

/* 
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto. This class models a simple flight that has only economy seats
 */

import java.util.ArrayList;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};			//Enum that represents the status of a flight.
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};	//Enum that represents the type of flight.

	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status;
	FlightType flightType;
	int flightDuration;
	Aircraft aircraft;
	protected int passengers; // count of (economy) passengers on this flight - initially 0

	protected ArrayList<Passenger> manifest;		//This flight's list of passengers
	protected TreeMap<String, Passenger> seatMap;	//TreeMap that maps a flight number to its corresponding flight.

	/**
	 * Default constructor
	 */
	public Flight()
	{	
		this.flightNum = "";
		this.airline = "";
		this.origin = "";
		this.dest = "";
		this.departureTime = "";
		this.status = Status.ONTIME;
		this.flightType = FlightType.MEDIUMHAUL;
		this.flightDuration = 0;
		this.aircraft = new Aircraft(0, "");
		passengers = 0;

		this.manifest = new ArrayList<Passenger>();
		this.seatMap = new TreeMap<String, Passenger>();
	}
	
	/**
	 * Creates a flight with the specified parameters
	 * @param flightNum The flight's identification number
	 * @param airline The flight's corresponding airline
	 * @param dest The flight's destination
	 * @param departure The flight's time of departure
	 * @param aircraft The type of aircraft used for the flight
	 */
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		this.passengers = 0;
		this.status = Status.ONTIME;
		this.flightType = FlightType.MEDIUMHAUL;

		this.manifest = new ArrayList<Passenger>();
		this.seatMap = new TreeMap<String, Passenger>();
	}

	/**Gets the flight number
	 * @return A string representing the flight's number
	 */
	public String getFlightNum()
	{
		return flightNum;
	}

	/**Sets the flight number
	 * @param flightNum A string representing the flight's number
	 */
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}

	/**Gets the airline
	 * @return A string representing the corresponding airline
	 */
	public String getAirline()
	{
		return airline;
	}

	/**Sets the airline
	 * @param airline A string representing the corresponding airline
	 */
	public void setAirline(String airline)
	{
		this.airline = airline;
	}

	/**Gets the origin
	 * @return A string representing the flight's origin
	 */
	public String getOrigin()
	{
		return origin;
	}

	/**Sets the origin
	 * @param origin A string representing the flight's orgin
	 */
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}

	/**Gets the flight number
	 * @return A string representing the corresponding flight number
	 */
	public String getDest()
	{
		return dest;
	}

	/**Sets the destination.
	 * @param dest A string representing the flight's destination
	 */
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	
	public String getDepartureTime()
	{
		return departureTime;
	}

	/**Sets the departure time.
	 * @param departureTime A string representing the flight's departure time.
	 */
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	/**Gets the flight's status
	 * @return One of for statuses defined by enum 'status'
	 */
	public Status getStatus()
	{
		return status;
	}

	/**Sets the flight's status.
	 * @param status A constant defined by enum 'status'
	 */
	public void setStatus(Status status)
	{
		this.status = status;
	}

	/**
	 * 
	 * @param type
	 */
	public void setFlightType(FlightType type)
	{
		this.flightType = type;
	}

	/**Gets the flight's duration
	 * @return An integer representing the duration of a flight in hours
	 */
	public int getFlightDuration()
	{
		return flightDuration;
	}

	/**Sets the flight's duration
	 * @param dur An integer representing the duration of a flight in hours
	 */
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	/**Gets the flights passenger count
	 * @return dur An integer representing the number of passengers on an economy flight
	 */
	public int getPassengers()
	{
		return passengers;
	}

	/**Sets the flights passenger count
	 * @param passengers An integer representing the number of passengers on an economy flight
	 */
	public void setPassengers(int passengers)
	{
		this.passengers = passengers;
	}

	public Aircraft getAircraft() {return aircraft;}

	public TreeMap<String, Passenger> getSeatMap()
	{
		return seatMap;
	}
	
	// Check to see if there is room on this flight - compare current passenger count
	// with aircraft max capacity of economy seats
	public boolean seatsAvailable()
	{
		return this.passengers < this.aircraft.getNumSeats();
	}
	
	/**
	  * Cancels a seat if a flight's passengers count is greater than 0.
	  * If so, the passeneger count is decremented by 1, else a message is printed.
	  Also, the removes the corresponding seat from the seatMap
	  */
	public void cancelSeat(String seat)
	{
		if (this.passengers >= 1) {
			this.passengers -= 1;
			seatMap.remove(seat);
		}
	}
	
	/**Reserves a seat on a flight if seats are available
	  * @return A boolean representing whether or not seats on a flight are available 
	  */
	public boolean reserveSeat()
	{
		if (this.seatsAvailable()) {
			this.passengers += 1;
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Prints a formatted representation of the seat layout.
	 */
	public void printSeats() {

		for (int i = 0; i < aircraft.getSeatLayout().length; i++) {
			if (i == 2) {System.out.println();}	//Prints a line that represents an aisle before beginning to print the next two rows
			for (int j = 0; j < aircraft.getSeatLayout()[0].length; j++) {
				System.out.print("[" + aircraft.getSeatLayout()[i][j] + "]");
			}
			System.out.println();
		}
	}

	/**
	 * Prints the info of all the passengers on a flight.
	 * Loops through each passenger object in ArrayList 'listOfPassenegers' and prints out each name, passsenger number and seat number.
	 */
	public void printPassengerManifest() {

		if (manifest.size() == 0) {
			System.out.println("No passengers on this fight.");
		} else {
		for (int i = 0; i < manifest.size(); i++) {
			System.out.println("Name: " + manifest.get(i).getName() + "\tPassport Number: " + manifest.get(i).getPassport()
				+ "\tSeat Number: " + manifest.get(i).getSeat());
			}
		}
	}

	/**
	 * Gets this flight's flight type
	 * @return This flights type of flight
	 */
	public FlightType getFlightType() {
		return this.flightType;
	}

	public String toString()
	{	
		String flightString = airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
		if (this.getFlightType().equals(Flight.FlightType.LONGHAUL)) {flightString += "\t Longhaul";}
		return flightString;
	}
}
