/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

/*
 * A simple class to model an electronic airline flight reservation
 */
public class Reservation
{
	String flightNum;
	String flightInfo;
	boolean firstClass;

	String passengerName;
	String passengerPassport;
	String seat;
	
	/**
	 * Creates a reservation with the specified parameters
	 * @param flightNum A string that represents the flight number
	 * @param info A string that represent's the reservations info
	 */
	public Reservation(String flightNum, String passengerName, String passengerPassport, String seat)
	{
		this.flightNum = flightNum;
		this.firstClass = false;
		this.passengerName = passengerName;
		this.passengerPassport = passengerPassport;
		this.seat = seat;

		flightInfo = "";
	}
	
	/**
	 * Returns the corresponding reservation's state of being first class
	 * @return A boolean value dependent on whether or not this reservation is a first class reservation
	 */
	public boolean isFirstClass()
	{
		return firstClass;
	}

	/**
	 * Sets the first status of this reservation to be true
	 */
	public void setFirstClass()
	{
		this.firstClass = true;
	}

	/**
	 * Gets the reservation's flight number
	 * @return A string representing the reservation's flight number
	 */
	public String getFlightNum()
	{
		return flightNum;
	}

	/**
	 * Gets this reservation's passenger name
	 * @return A string representing the passenger's name
	 */
	public String getPassengerName()
	{
		return passengerName;
	}

	/**
	 * Gets this reservation's passenger passport
	 * @return A string representing the passenger's passport
	 */
	public String getPassengerPassport()
	{
		return passengerPassport;
	}

	/**
	 * Gets this reservation's passenger seat
	 * @return A string representing the passenger's seat
	 */
	public String getPassengerSeat()
	{
		return seat;
	}

	/**
	 * Sets the reservation's passport
	 * @param passport A string representing the passenger's passport
	 */
	public void setPassengerPassport(String passport)
	{
		this.passengerPassport = passport;
	}
	
	/**
	 * Gets the reservation's flight info
	 * @return A string representing the reservation's corresponding flight info
	 */
	public String getFlightInfo()
	{
		return flightInfo;
	}

	/**
	 * Sets the reservation's flight info
	 * @param flightInfo A string representing the reservation's corresponding flight info
	 */
	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}

	/**
	 * Prints the reservation's corresponding flight info
	 */
	public void print()
	{
		System.out.println(flightInfo);
	}

	public boolean equals(Reservation other) {
		return (this.flightNum.equals(other.flightNum) && this.passengerName.equals(other.passengerName) && this.passengerPassport.equals(other.passengerPassport));
	}
}
