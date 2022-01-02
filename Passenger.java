/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 * 
 * Class to model a flight's passenger.
 */
public class Passenger {
    
    private String name;
    private String passport;
    private String seat;
    private String seatType;

    /**
     * Default constructor
     */
    public Passenger() {
        this.name = "";
        this.passport = "";
        this.seat = "";
        this.seatType = "Economy Seat";
    }

    /**
     * Creates a passenger with the specified parameters
     * @param name The passenger's name
     * @param passportNum The passenger's passport number 
     * @param seatNum The passenger's randomly assigned seat number
     */
    public Passenger(String name, String passport, String seat) {
        this.name = name;
        this.passport = passport;
        this.seat = seat;
        this.seatType = "Economy Seat";
    }

    /**
     * Gets the seat number
     * @return An integer representing the passenger's corresponding seat number
     */
    public String getSeat() {
        return seat;
    }

    /**
     * Gets the passport number
     * @return An integer representing the passenger's corresponding passport number
     */
    public String getPassport() {
        return passport;
    }

    /**
     * Sets the passport number
     * @param passportNum An integer representing the passenger's passport number
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }
    
    /**
     * Gets the passenger's name
     * @return A string representing the passenger's corresponding name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the passenger's seat type
     * @param type A string representing the passenger's type of seat
     */
    public void setSeatType(String type) {
        this.seatType = type;
    }

    /**
     * Gets the passenger's seat type
     * @return A string representing the passenger's seat type
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * Compares this instance of the passport number to another passport number
     * @param other A passenger object that this passport number will be compared to
     * @return A boolean value depending on if the passport num in question is equal 
     */
    public boolean equals(Passenger other) {
        return (this.name.equals(other.name) && this.passport.equals(other.passport));
    }
}
