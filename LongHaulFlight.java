/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

/*
 * A long haul flight is a flight that travels thousands of kilometers and typically has separate seating areas 
 */

public class LongHaulFlight extends Flight
{
	int numFirstClassPassengers;
	String seatType;
	
	// Possible seat types
	public static final String firstClass = "First Class Seat";
	public static final String economy = "Economy Seat";
	
	/**
	 * Creates a long haul flight with the specified parameters
	 * @param flightNum
	 * @param airline
	 * @param dest
	 * @param departure
	 * @param flightDuration
	 * @param aircraft
	 */
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		// use the super() call to initialize all inherited variables
		// also initialize the new instance variables
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		this.numFirstClassPassengers = 0;
		this.seatType = economy;
	}

	/**
	 * default constructor
	 */
	public LongHaulFlight()
	{
	 	this.numFirstClassPassengers = 0;
		this.seatType = economy;
	}
	
	/*
	 * Reserves a seat on a flight. Essentially just increases the number of (economy) passengers
	 */
	public boolean reserveSeat()
	{
		// override the inherited reserveSeat method and call the reserveSeat method below with an economy seatType
		// use the constants defined at the top
		return reserveSeat(economy);
	}

	/*
	 * Reserves a seat on a flight. Essentially just increases the number of passengers, depending on seat type (economy or first class)
	 */
	public boolean reserveSeat(String seatType)
	{
		// if seat type is economy 
		//			call the superclass method reserveSeat() and return the result
		// else if the seat type is first class then 
		// 			check to see if there are more first class seats available (use the aircraft method to get the max first class seats
		// 			of this airplane
		//    	if there is a seat available, increment first class passenger count (see instance variable at the top of the class)
		//    	return true;
		// else return false

		if (seatType.equals(economy)) {
			return super.reserveSeat();
		} else if (seatType.equals(firstClass)) {
			if (this.numFirstClassPassengers < this.aircraft.getNumFirstClassSeats()) {
				this.numFirstClassPassengers += 1;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Cancels a seat
	 */
	public void cancelSeat()
	{
	  // override the inherited cancelSeat method and call the cancelSeat method below with an economy seatType
		// use the constants defined at the top
		cancelSeat(economy);
	}
	
	/**
	 * Cancels a seat on a long haul flight
	 * @param seatType A string representing the type of seat to cancel, either first class or economy
	 */
	public void cancelSeat(String seatType)
	{
		// if seat type is first class and first class passenger count is > 0
		//  decrement first class passengers
		// else
		// decrement inherited (economy) passenger count
		if (seatType.equals(firstClass)) {
			if (this.numFirstClassPassengers > 0) {
				this.numFirstClassPassengers -= 1;
			} else {
				System.out.println("No First Class seats reserved.");
			}
		} else {
			if (this.getPassengers() > 0) {
				this.setPassengers(this.getPassengers() - 1);
			} else {
				System.out.println("No seats reserved.");
			}
		}
		
	}

	/**
	 * Gets the total passenger count of a long haul flight; Uses instance variable at top and inherited method that returns economy passenger count
	 * @return The total passenger count of economy passengers *and* first class passengers
	 */
	public int getPassengerCount()
	{
		return 0;
	}

	/** Gets the corresponding flight type for a long haul flight
	 * @return The LONGHUAL flight type
	 */
	public FlightType getFlightType() {
		return FlightType.LONGHAUL;
	}
}
