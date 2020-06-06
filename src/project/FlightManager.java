// Name: Vadim Kobrinsky

package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FlightManager {

	final static String FNAME_ARRIVALS = "arrivals.txt";
	final static String FNAME_DEPARTURES = "departures.txt";
	final static DateTimeFormatter formatt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private static String city;
	private static String company;
	private static String flightCode;
	private static int[] tokens;
	private static int terminalNum;

	private ArrayList<Flight> arrivalFlights;
	private ArrayList<Flight> departureFlights;
	private File arrivalsFile;
	private File departuresFile;

	public static void main(String[] args) {
		FlightManager manager = new FlightManager();
		Scanner scan;
		if (args.length != 0)
			scan = new Scanner(Arrays.toString(args));
		else
			scan = new Scanner(System.in);

		try {
			manager.arrivalDepartureMenuHandler(scan);
		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.close();
	}

	public static String formatInputFromArgs(String str) {
		return str.trim().replace("[", "").replace(",", "").replace("]", "");
	}

	public FlightManager() {
		arrivalsFile = new File(FNAME_ARRIVALS);
		departuresFile = new File(FNAME_DEPARTURES);
		arrivalFlights = new ArrayList<Flight>();
		departureFlights = new ArrayList<Flight>();
		if (arrivalsFile.canExecute()) {
			try {
				initDataFromFile(arrivalFlights, arrivalsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (departuresFile.canExecute()) {
			try {
				initDataFromFile(departureFlights, departuresFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void printFileStatus(File dataFile) throws IOException {
		if (dataFile.createNewFile())
			System.out.println("file " + dataFile.getName() + " has been created!");
		else
			System.out.println(dataFile.getName() + " file already exists!");
	}

	public void initDataFromFile(ArrayList<Flight> flights, File dataFile) throws IOException {
		int k;
		BufferedReader buffReader = new BufferedReader(new FileReader(dataFile));
		String[] tmpTokens;
		String[] finalTokens;
		String line;
		while ((line = buffReader.readLine()) != null && !line.isEmpty()) {
			tmpTokens = line.trim().replace("]", "").split(",");
			finalTokens = new String[tmpTokens.length];
			for (int i = 0; i < tmpTokens.length; i++)
				finalTokens[i] = tmpTokens[i].split("=")[1];
			k = 0;
			if (dataFile.getName().equals(FNAME_ARRIVALS))
				flights.add(new ArrivalFlight(finalTokens[k++], LocalDateTime.parse(finalTokens[k++]),
						LocalDateTime.parse(finalTokens[k++]), finalTokens[k++], finalTokens[k++],
						Integer.parseInt(finalTokens[k++])));
			else if (dataFile.getName().equals(FNAME_DEPARTURES))
				flights.add(new DepartureFlight(finalTokens[k++], LocalDateTime.parse(finalTokens[k++]),
						LocalDateTime.parse(finalTokens[k++]), finalTokens[k++], finalTokens[k++],
						Integer.parseInt(finalTokens[k++])));
		}
		buffReader.close();
	}

	public int printArrivalDepartureMenuAndScanChoice(Scanner scan) {
		System.out.print(
				"\nFlight Type (Choose option):\n1. Arriving Flights\n2. Departing flights\n3. Exit\n\nyour choice: ");
		return Integer.parseInt(formatInputFromArgs(scan.next()));
	}

	public int printCrudMenuAndScanChoice(Scanner scan) {
		System.out.print(
				"\nFlights Menu (Choose option):\n1. Insert flight\n2. Read flights\n3. Delete flights\n4. Back\n\nyour choice: ");
		return Integer.parseInt(formatInputFromArgs(scan.next()));
	}

	public void arrivalDepartureMenuHandler(Scanner scan) throws IOException {
		while (true) {
			int menuChoice = printArrivalDepartureMenuAndScanChoice(scan);
			switch (menuChoice) {
			case 1:
				crudMenuHandler(scan, arrivalFlights, arrivalsFile);
				break;
			case 2:
				crudMenuHandler(scan, departureFlights, departuresFile);
				break;
			case 3:
				System.out.println("Thank you and goodbye!");
				return;
			default:
				System.out.println("Invalid input. please try again...\n");
				menuChoice = printArrivalDepartureMenuAndScanChoice(scan);
				break;
			}
		}
	}

	public void crudMenuHandler(Scanner scan, ArrayList<Flight> flights, File flightsData) throws IOException {
		BufferedWriter buffWriter;
		int menuChoice = printCrudMenuAndScanChoice(scan);
		while (true) {
			switch (menuChoice) {
			case 1:
				buffWriter = new BufferedWriter(new FileWriter(flightsData, true));
				createFlight(scan, flights, flightsData, buffWriter);
				buffWriter.close();
				break;

			case 2:
				readFlights(scan, flights, flightsData);
				break;

			case 3:
				buffWriter = new BufferedWriter(new FileWriter(flightsData, false));
				deleteFlights(scan, flights, flightsData, buffWriter);
				buffWriter.close();
				break;

			case 4:
				return;

			default:
				System.out.println("Invalid input. please try again...\n");
				break;

			}
			menuChoice = printCrudMenuAndScanChoice(scan);
		}
	}

	public Flight createFlight(Scanner scan, ArrayList<Flight> flights, File dataFile,
			BufferedWriter buffWriterAppender) throws IOException {
		Flight newFlight = null;
		int k;
		if (dataFile.getName().equals(FNAME_ARRIVALS)) {
			System.out.print("Please enter fight origin: ");
			city = scan.nextLine();
		} else if (dataFile.getName().equals(FNAME_DEPARTURES)) {
			System.out.print("Please enter fight destination: ");
			city = scan.nextLine();
		}

		System.out.print("Please enter departure DateTime (yyyy-MM-dd-HH-mm): ");
		String[] strTokens = scan.nextLine().trim().split("-");
		tokens = new int[strTokens.length];
		for (int i = 0; i < strTokens.length; i++)
			tokens[i] = Integer.parseInt(strTokens[i]);

		k = 0;
		LocalDateTime departureDT = LocalDateTime.of(tokens[k++], tokens[k++], tokens[k++], tokens[k++], tokens[k++]);
		System.out.print("Please enter arrival DateTime (yyyy-MM-dd-HH-mm): ");
		strTokens = scan.nextLine().trim().split("-");
		tokens = new int[strTokens.length];
		for (int i = 0; i < strTokens.length; i++)
			tokens[i] = Integer.parseInt(strTokens[i]);

		k = 0;
		LocalDateTime arrivalDT = LocalDateTime.of(tokens[k++], tokens[k++], tokens[k++], tokens[k++], tokens[k++]);
		System.out.print("Please enter company name: ");
		company = scan.nextLine();
		System.out.print("Please enter fight code: ");
		flightCode = scan.nextLine();
		System.out.print("Please enter terminal number: ");
		terminalNum = Integer.parseInt(scan.nextLine());

		if (dataFile.getName().equals(FNAME_ARRIVALS)) {
			newFlight = new ArrivalFlight(city, departureDT, arrivalDT, company, flightCode, terminalNum);
			flights.add(newFlight);
		} else if (dataFile.getName().equals(FNAME_DEPARTURES)) {
			newFlight = new DepartureFlight(city, departureDT, arrivalDT, company, flightCode, terminalNum);
			flights.add(newFlight);
		}
		buffWriterAppender.append(newFlight + "");
		return newFlight;
	}

	public void createFlight(Flight flight) throws IOException {
		BufferedWriter buffWriter;
		if (flight instanceof ArrivalFlight) {
			arrivalFlights.add(flight);
			buffWriter = new BufferedWriter(new FileWriter(arrivalsFile, true));
			buffWriter.append(((ArrivalFlight) flight).toString());
			buffWriter.close();
		} else if (flight instanceof DepartureFlight) {
			departureFlights.add(flight);
			buffWriter = new BufferedWriter(new FileWriter(departuresFile, true));
			buffWriter.append(((DepartureFlight) flight).toString());
			buffWriter.close();
		}
	}

	public void createFlights(ArrayList<? extends Flight> flights) throws IOException {
		for (Flight flight : flights)
			createFlight(flight);
	}

	public ArrayList<? extends Flight> readFlights(Scanner scan, ArrayList<Flight> flights, File dataFile) {
		ArrayList<? extends Flight> retFlights = flights;
		String desiredValue = null;
		String submenuChoice = "";

		while (!submenuChoice.equals("cont")) {
			if (!flights.isEmpty() & flights.get(0) instanceof ArrivalFlight)
				System.out.print(
						"Choose a distinct field (origin/datetime/company/code/terminal) or enter \"cont\" to continue: ");
			else
				System.out.print(
						"Choose a distinct field (destination/datetime/company/code/terminal) or enter \"cont\" to continue: ");

			submenuChoice = formatInputFromArgs(scan.next());
			if (!submenuChoice.equals("cont")) {
				if (!submenuChoice.equals("datetime")) {
					System.out.print("Enter the desired value: ");
					desiredValue = formatInputFromArgs(scan.next());
				}
				retFlights = viewFlightsWith(scan, submenuChoice, desiredValue, retFlights);
			}
		}

		if (dataFile.getName().equals(FNAME_ARRIVALS))
			System.out.print(
					"would you like to sort the flight list? (origin/departure/arrival/company/code/terminal) or enter \"cont\" to continue: ");
		else if (dataFile.getName().equals(FNAME_DEPARTURES))
			System.out.print(
					"would you like to sort the flight list? (destination/departure/arrival/company/code/terminal) or enter \"cont\" to continue: ");
		submenuChoice = formatInputFromArgs(scan.next());
		if (!submenuChoice.equals("cont")) {
			System.out.print("Reversed order? (true/false): ");
			retFlights = sortFlightsBy(submenuChoice, Boolean.parseBoolean(formatInputFromArgs(scan.next())),
					retFlights);
		}

		System.out.println(
				"\nThese are the flights that match the description:\n=================================================");
		for (Flight flight : retFlights) {
			System.out.print(flight.toString());
		}

		return retFlights;
	}

	/*
	 * public ArrayList<? extends Flight> readFlights(Scanner scan,
	 * ArrayList<Flight> flights, File dataFile) { ArrayList<? extends Flight>
	 * retFlights = flights; String submenuChoice; String desiredValue = null;
	 * System.out.print("Show flights that match some distinct field value? (y/n): "
	 * ); char answer = scan.nextLine().charAt(0); if (answer == 'y' || answer ==
	 * 'Y') { if (!flights.isEmpty() & flights.get(0) instanceof ArrivalFlight)
	 * System.out.
	 * print("Choose a distinct field (origin/datetime/company/code/terminal): ");
	 * else if (!flights.isEmpty() & flights.get(0) instanceof DepartureFlight)
	 * System.out.
	 * print("Choose a distinct field (destination/datetime/company/code/terminal): "
	 * );
	 * 
	 * submenuChoice = scan.nextLine(); if (!submenuChoice.equals("datetime")) {
	 * System.out.print("Enter the desired value: "); desiredValue =
	 * scan.nextLine(); } retFlights = viewFlightsWith(scan, submenuChoice,
	 * desiredValue, retFlights);
	 * 
	 * System.out.print("Choose another distinct field? (y/n): "); answer =
	 * scan.nextLine().charAt(0); while (answer == 'y' || answer == 'Y') { if
	 * (!flights.isEmpty() & flights.get(0) instanceof ArrivalFlight) System.out.
	 * print("Choose a distinct field (origin/datetime/company/code/terminal): ");
	 * else if (!flights.isEmpty() & flights.get(0) instanceof DepartureFlight)
	 * System.out.
	 * print("Choose a distinct field (destination/datetime/company/code/terminal): "
	 * ); submenuChoice = scan.nextLine(); if (!submenuChoice.equals("datetime")) {
	 * System.out.print("Enter the desired value :"); desiredValue =
	 * scan.nextLine(); } retFlights = viewFlightsWith(scan, submenuChoice,
	 * desiredValue, retFlights);
	 * System.out.print("Choose another distinct field? (y/n): "); answer =
	 * scan.nextLine().charAt(0); } }
	 * 
	 * System.out.print("Do you want to sort by some criteria? (y/n): "); answer =
	 * scan.nextLine().charAt(0); if (answer == 'y' || answer == 'Y') { if
	 * (dataFile.getName().equals(FNAME_ARRIVALS)) System.out.print(
	 * "By which field would you like to sort the flight list? (origin/departure/arrival/company/code/terminal): "
	 * ); else if (dataFile.getName().equals(FNAME_DEPARTURES)) System.out.print(
	 * "By which field would you like to sort the flight list? (destination/departure/arrival/company/code/terminal): "
	 * );
	 * 
	 * submenuChoice = scan.nextLine();
	 * System.out.print("Reversed order? (true/false): "); retFlights =
	 * sortFlightsBy(submenuChoice, Boolean.parseBoolean(scan.nextLine()),
	 * retFlights); }
	 * 
	 * System.out.println(
	 * "\nThese are the flights that match the description:\n================================================="
	 * ); for (Flight flight : retFlights) { System.out.print(flight.toString()); }
	 * 
	 * return retFlights; }
	 */

	public boolean deleteFlights(Scanner scan, ArrayList<Flight> flights, File dataFile, BufferedWriter buffWriter)
			throws IOException {
		System.out.print(
				"By value of which field would you like to delete flights? (origin/destination/departure/arrival/code/terminal): ");
		String field = scan.nextLine();
		System.out.println("What valuoe are you looking to delete? ");
		String lookedUpValue = scan.nextLine();
		ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();
		for (int i = 0; i < flights.size(); i++) {
			switch (field) {
			case "origin":
				if (((ArrivalFlight) flights.get(i)).getOrigin().equals(lookedUpValue))
					indicesToRemove.add(i);
				break;

			case "destination":
				if (((DepartureFlight) flights.get(i)).getDestination().equals(lookedUpValue))
					indicesToRemove.add(i);
				break;

			case "departure":
				if (flights.get(i).getDepartureDT().equals(LocalDateTime.parse(lookedUpValue)))
					indicesToRemove.add(i);
				break;

			case "arrival":
				if (flights.get(i).getArrivalDT().equals(LocalDateTime.parse(lookedUpValue)))
					indicesToRemove.add(i);
				break;

			case "code":
				if (flights.get(i).getCode().equals(lookedUpValue))
					indicesToRemove.add(i);
				break;

			case "terminal":
				if (flights.get(i).getTerminalNum() == Integer.parseInt(lookedUpValue))
					indicesToRemove.add(i);
				break;
			}
		}

		if (indicesToRemove.isEmpty())
			return false;
		else {
			for (Integer integer : indicesToRemove)
				flights.remove(integer.intValue());

			for (Flight flight : flights) {
				buffWriter.write(flight.toString());
			}
			return true;
		}
	}

	public ArrayList<? extends Flight> sortFlightsBy(String field, boolean reversed,
			ArrayList<? extends Flight> flights) {
		FlightComparator flightComp = new FlightComparator();
		switch (field) {
		case "origin":
			ArrayList<ArrivalFlight> arrivalList = new ArrayList<ArrivalFlight>();
			for (Flight flight : flights)
				arrivalList.add((ArrivalFlight) flight);
			if (reversed)
				arrivalList.sort(new ArrivalFlightComparator().new OriginComparator().reversed());
			else
				arrivalList.sort(new ArrivalFlightComparator().new OriginComparator());
			return arrivalList;

		case "destination":
			ArrayList<DepartureFlight> departureList = new ArrayList<DepartureFlight>();
			for (Flight flight : flights)
				departureList.add((DepartureFlight) flight);
			if (reversed)
				departureList.sort(new DepartureFlightComparator().new DestinationComparator().reversed());
			else
				departureList.sort(new DepartureFlightComparator().new DestinationComparator());
			return departureList;

		case "departure":
			if (reversed)
				flights.sort(flightComp.new DepartureComparator().reversed());
			else
				flights.sort(flightComp.new DepartureComparator());
			break;

		case "arrival":
			if (reversed)
				flights.sort(flightComp.new ArrivalComparator().reversed());
			else
				flights.sort(flightComp.new ArrivalComparator());
			break;

		case "company":
			if (reversed)
				flights.sort(flightComp.new CompanyComparator().reversed());
			else
				flights.sort(flightComp.new CompanyComparator());
			break;

		case "code":
			if (reversed)
				flights.sort(flightComp.new CodeComparator().reversed());
			else
				flights.sort(flightComp.new CodeComparator());
			break;

		case "terminal":
			if (reversed)
				flights.sort(flightComp.new TerminalComaparator().reversed());
			else
				flights.sort(flightComp.new TerminalComaparator());
			break;
		}
		return flights;
	}

	// possible fields: (origin/destination/datetime/company/code/terminal)
	public ArrayList<? extends Flight> viewFlightsWith(Scanner scan, String field, String value,
			ArrayList<? extends Flight> flights) {
		ArrayList<Flight> retFlights = new ArrayList<>();

		switch (field) {
		case "origin":
			for (Flight flight : flights)
				if (((ArrivalFlight) flight).getOrigin().equals(value))
					retFlights.add((ArrivalFlight) flight);
			break;

		case "destination":
			for (Flight flight : flights)
				if (((DepartureFlight) flight).getDestination().equals(value))
					retFlights.add((DepartureFlight) flight);
			break;

		case "datetime":
			System.out.print("From (yyyy-MM-dd HH:mm): ");
			LocalDateTime from = LocalDateTime.parse(scan.nextLine(), formatt);
			System.out.print("To (yyyy-MM-dd HH:mm): ");
			LocalDateTime to = LocalDateTime.parse(scan.nextLine(), formatt);
			for (Flight flight : flights) {
				if (flight instanceof ArrivalFlight) {
					if ((from.compareTo(flight.getArrivalDT()) <= 0) && (to.compareTo(flight.getArrivalDT()) >= 0))
						retFlights.add(flight);
				} else if (flight instanceof DepartureFlight)
					if ((from.compareTo(flight.getDepartureDT()) <= 0)
							&& (to.compareTo(flight.getDepartureDT()) >= 0)) {
						retFlights.add(flight);
					}
			}
			break;

		case "company":
			for (Flight flight : flights)
				if (flight.getCompany().equals(value))
					retFlights.add(flight);
			break;

		case "code":
			for (Flight flight : flights)
				if (flight.getCode().equals(value))
					retFlights.add(flight);
			break;

		case "terminal":
			for (Flight flight : flights)
				if (flight.getTerminalNum() == Integer.parseInt(value))
					retFlights.add(flight);
			break;
		}
		return retFlights;
	}

	public File getArrivalsFile() {
		return arrivalsFile;
	}

	public void setArrivalsFile(File arrivalsFile) {
		this.arrivalsFile = arrivalsFile;
	}

	public File getDeparturesFile() {
		return departuresFile;
	}

	public void setDeparturesFile(File departuresFile) {
		this.departuresFile = departuresFile;
	}
}
