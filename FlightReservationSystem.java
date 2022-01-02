/**
 * Student Name: Dante Romita
 * Student ID: 501019504
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!

public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		//Entire code is contained in a try-except block to catch FileNotFoundException and prevent exceptions from being unhandled when compiling
		try {
		// Create a FlightManager object
		FlightManager manager = new FlightManager();

		// List of reservations that have been made
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) continue;

			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);
			
			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();

			if (action == null || action.equals("")) continue;

			if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			// List all flights
			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			// Reserve a flight based on Flight number string (example input: res AC220)
			else if (action.equalsIgnoreCase("RES"))
			{
				// Get the flight number string from the commndLine scanner (use hasNext() to check if there is a
				// flight number string entered
				
				// call reserveSeatOnFlight() method in manager passing in the flight number string
				// A reference to a Reservation object is returned. Check to make sure it is not == null
				// If it is null, then call manager.getErrorMessage() and print the message
				// If it is not null, add the reservation to the myReservations array list and print the reservation (see class Reservation)
				
				String flightNum = "";
				String name = "";
				String passport = "";
				String seat = "";
				String flightInfo = "";

				if (commandLine.hasNext()) {
					flightNum = commandLine.next();
					if (commandLine.hasNext()) {
						name = commandLine.next();
						if (commandLine.hasNext()) {
							passport = commandLine.next();
							if (commandLine.hasNext()) {
								seat = commandLine.next();

								Flight correspondingFlight = manager.getCorrespondingFlight(flightNum);
								if (correspondingFlight != null) {flightInfo = correspondingFlight.toString();}	//Ensures that the corresponding flight exists before setting flight info

								try {
									Reservation newRes = manager.reserveSeatOnFlight(flightNum, name, passport, seat, myReservations);
									newRes.setFlightInfo(flightInfo + " (" + name + " | " + passport + " | " + seat + ")"); //Sets a reservation's flight info and adds it to the list of reservations
									myReservations.add(newRes);
									(myReservations.get(myReservations.size()-1)).print();
								} catch (FlightManager.FlightFullException exception) {
									System.out.println(exception);
								} catch (FlightManager.InvalidFlightNumException exception) {
									System.out.println(exception);
								} catch (FlightManager.DuplicatePassengerException exception) {
									System.out.println(exception);
								} catch (FlightManager.DuplicateReservationException exception) {
									System.out.println(exception);
								} catch (FlightManager.SeatOccupiedException exception) {
									System.out.println(exception);
								} catch (FlightManager.InvalidSeatException exception) {
									System.out.println(exception);
								}
							}
						}
					}
				}
			}

			// Query the flight manager to see if seats are still available for a specific flight (example input: seats AC220)
		  // This one is done for you as a guide for other commands
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = null;

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();

					try {	//Tries to find the corresponding flight. If found, the flight's seat layout is printed, otherwise exception is thrown
						Flight correspondingFlight = manager.getCorrespondingFlight(flightNum);
						if (correspondingFlight == null) {manager.throwInvalidFlightNumException(flightNum);}
						correspondingFlight.printSeats();
						System.out.println("\nXX = Occupied | + = First Class");
					} catch (FlightManager.InvalidFlightNumException exception) {
						System.out.println(exception);
					}
				}
			}
			// Cancel an existing reservation (example input: cancel AC220) 
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				String flightNum = "";
				String name = "";
				String passport = "";

				boolean matchingFlights = false;
				boolean passengerFound = false;

				int resIndex = 0;	//Index that will keep track of the reservation indicies, used to idenfity the reservation to remove

				Reservation res = null;
				Flight correspondingFlight = null;
				Passenger correspondingPassenger = null;

				if (commandLine.hasNext()) {
					flightNum = commandLine.next();
					if (commandLine.hasNext()) {
						name = commandLine.next();
						if (commandLine.hasNext()) {
							passport = commandLine.next();

							//Checks if the flight numbers entered matches with one of the reservation's flights
							for (int r = 0; r < myReservations.size(); r++) {
								if (flightNum.equals(myReservations.get(r).getFlightNum())) {
									matchingFlights = true;
									break;
								}
							}

							if (matchingFlights) {
								correspondingFlight = manager.getCorrespondingFlight(flightNum);

								//Checks if the corresponding passenger is found in the flight's manifest
								for (int p = 0; p < correspondingFlight.manifest.size(); p++) {
									if (name.equals(correspondingFlight.manifest.get(p).getName()) && 
									passport.equals(correspondingFlight.manifest.get(p).getPassport())) {
										correspondingPassenger = correspondingFlight.manifest.get(p);
										passengerFound = true;
										break;
									}
								}
							}

							if (passengerFound) {
								for (int r = 0; r < myReservations.size(); r++) {
									//Check if the reservation info given matches any reservation in the flight
									if (flightNum.equals(myReservations.get(r).getFlightNum())
									&& name.equals(myReservations.get(r).getPassengerName())
									&& passport.equals(myReservations.get(r).getPassengerPassport())) {
										res = myReservations.get(r);
										resIndex = r;
										break;
									}
								}
							}

							try {	//Tries cancelling the reservation, removing the passenger from the flight's manifest and making the seat vacant
								manager.cancelReservation(res);
								correspondingFlight.manifest.remove(correspondingPassenger);
								myReservations.remove(resIndex);
								System.out.println("Reservation has been cancelled.");
							} catch (FlightManager.NoReservationFoundException exception) {
								System.out.println(exception);
							} catch (FlightManager.SeatVacantException exception) {
								System.out.println(exception);
							}  catch (FlightManager.PassengerNotInManifestException exception) {
								System.out.println(exception);
							}
						}
					}
				}
			}
      		// Print all the reservations in array list myReservations
			else if (action.equalsIgnoreCase("MYRES"))
			{
				if (myReservations.size() == 0) {										//If there are no reservations, state a message saying so, otherwise, print each rerservation.
					System.out.println("There are no reservations.");
				} else {
				for (int i = 0; i < myReservations.size(); i++) {
					System.out.println(myReservations.get(i).getFlightInfo()); }
				}
			}
			//Prints all of the passenger info for a given flight determined by the user
			else if (action.equalsIgnoreCase("PASMAN"))
			{
				if (commandLine.hasNext()) {
					String flight = commandLine.next();
					Flight correspondingFlight = manager.getCorrespondingFlight(flight);

					try {	//Checks if the corresponding flight is not null, otherwise the exception is thrown
						if (correspondingFlight == null) {manager.throwInvalidFlightNumException(flight);}
						correspondingFlight.printPassengerManifest();
					} catch (FlightManager.InvalidFlightNumException exception) {
						System.out.println(exception);
					}
					
				}
			}

			/**
			// Print the list of aircraft (see class Manager)
			else if (action.equalsIgnoreCase("CRAFT"))
		  {
			  manager.printAllAircraft();
			}
			// These commands can be left until we study Java interfaces
			// Feel free to implement the code in class Manager if you already understand interface Comparable
			// and interface Comparator
			else if (action.equalsIgnoreCase("SORTCRAFT"))
		  {
		  	manager.sortAircraft();
		  }
		  else if (action.equalsIgnoreCase("SORTBYDEP"))
		  {
			  manager.sortByDeparture();
			  
		  }
		  else if (action.equalsIgnoreCase("SORTBYDUR"))
		  {
			  manager.sortByDuration();
		  }
		   */
			System.out.print("\n>");
		}

	} catch (FileNotFoundException exception) {
		}
	}
}

