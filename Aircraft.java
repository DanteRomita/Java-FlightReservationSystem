/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

/* Represents an aircraft
 * 
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of forst class seats 
 * 
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats 
 */
public class Aircraft  
{
  int numEconomySeats;
  int numFirstClassSeats;
  
  String model;

  String[][] vacantSeatLayout;	//A seat layout that never gets updated. This is used for cancel reservations by overriding "XX" with the corresponding seat provided by this
  String[][] seatLayout;		//A seat layout that gets updated whenever a reservation or cancellation is made

  final static String[] ROW_LETTERS = {"A", "B", "C", "D"};	//An array containing the row letters. These are used when generating the seat layout
  
  /**
   * Creates an aircraft with the specific number of economy seats and model
   * @param seats An integer representing the total number of seats on that specific aircraft
   * @param model A string representing the aircraft's model
   */
  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.model = model;

	//Creates and fills the seat layout (A 2D Array) for the corresponding aircraft

	this.vacantSeatLayout = new String[4][seats/4];

	for (int i = 0; i < vacantSeatLayout.length; i++) {
		for (int j = 0; j < vacantSeatLayout[0].length; j++) {
			vacantSeatLayout[i][j] = (j + 1) + ROW_LETTERS[i];
		}
	}

	this.seatLayout = new String[4][seats/4];
	
	for (int i = 0; i < vacantSeatLayout.length; i++) {
		for (int j = 0; j < vacantSeatLayout[0].length; j++) {
			this.seatLayout[i][j] = (j + 1) + ROW_LETTERS[i];
		}
	}

  }

  /**
   * Creates an aircraft with the specific number of economy seats, first class seats and model
   * @param economy An integer representing the total number of economy seats on that specific aircraft
   * @param firstClass An integer representing the total number of first class seats on that specific aircraft
   * @param model A string representing the aircraft's model
   */
  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;

	//Creates and fills the seat layout (A 2D Array) for the corresponding aircraft

	this.vacantSeatLayout = new String[4][this.getTotalSeats()/4];
	int num1ClassSeatsPerRow = this.numFirstClassSeats / 4;

	for (int i = 0; i < this.vacantSeatLayout.length; i++) {
		for (int j = 0; j < this.vacantSeatLayout[0].length; j++) {
			this.vacantSeatLayout[i][j] = (j + 1) + ROW_LETTERS[i];
			if (j < num1ClassSeatsPerRow) {
				this.vacantSeatLayout[i][j] += "+";
			}
		}
	}

	this.seatLayout = new String[4][this.getTotalSeats()/4];

	for (int i = 0; i < this.seatLayout.length; i++) {
		for (int j = 0; j < this.seatLayout[0].length; j++) {
			this.seatLayout[i][j] = (j + 1) + ROW_LETTERS[i];
			if (j < num1ClassSeatsPerRow) {
				this.seatLayout[i][j] += "+";
			}
		}
	}
  }
  
  	/**
   * Gets the number of economy seats on an aircraft
   * @return An integer representing the number of economy seats on an aircraft
   */
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	/**
	 * Gets the number of total seats on an aircraft
	 * @return An integer representing the sum of economy and first class seats on an aircraft
	 */
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	/**
	 * Gets the number of first class seats on an aircraft
	 * @return An integer representing the number of first class seats on an aircraft
	 */
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	/**
	 * Gets the aircraft's model
	 * @return A string reresenting the aircraft's model
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * Sets the aircraft's model
	 * @param model A string reresenting the aircraft's model
	 */
	public void setModel(String model)
	{
		this.model = model;
	}

	/**
	 * Returns the aircraft's vacant seat layout
	 * @return A 2D array representing the vacant seat layout
	 */
	public String[][] getVacantSeatLayout()
	{
		return vacantSeatLayout;
	}

	/**
	 * Returns the aircraft's seat layout
	 * @return A 2D array representing the seat layout
	 */
	public String[][] getSeatLayout()
	{
		return seatLayout;
	}
	
	/**
	 * Prints the aircraft's model, number of economy seats and number of first class seats
	 */
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}
}