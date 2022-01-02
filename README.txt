Student Name: Dante Romita

WHAT WORKS:

- All code in the following has been successfully updated to fufill the requirements in:
-> Aircraft.java
-> Flight.java
-> FlightManager.java
-> FlightReservationSystem.java (Entry Point of Program)
-> LongHaulFlight.java
-> Passenger.java
-> Reservation.java

NOTES: 

- I used the Assignment 1 files provided on D2L and built assignment 2 completely from scratch. No functionality exclusive to Assignment 1 is present in Assignment 2.

- For 5. Flight part e, I did not find a need to include one reserve and cancel method in class Passenger, as I was able to identify whether the reservation was first class in FlightManager.java
-> Ex. Reserving seat 1A in a long haul flight is not valid since that seat contains a +. A first class seat can only be reserved if the seat is correctly identified as 1A+,
    otherwise an InvalidSeatException is thrown.
- For 5. Part h, I created the listed exception classes in FlightManager.java instead, alongside all the other exceptions.