package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import project.ArrivalFlight;
import project.DepartureFlight;
import project.FlightManager;

public class CRUDTests {

	// flight manager
	private FlightManager fm = new FlightManager();
	private static ArrayList<ArrivalFlight> arrivalFlights;
	private static ArrayList<DepartureFlight> departureFlights;
	private BufferedReader buffReader;
	private String someLine;
	int arrayIndex;

	private String[] arrivalLinesToMatch = {
			"ArrivalFlight [origin=New York, departureDT=2020-10-10T08:00, arrivalDT=2020-10-10T17:00, company=Delta, code=XG4, terminalNum=2]",
			"ArrivalFlight [origin=Paris, departureDT=2020-08-03T17:00, arrivalDT=2020-08-03T22:00, company=Pegasus, code=AG2, terminalNum=1]",
			"ArrivalFlight [origin=London, departureDT=2020-09-15T12:00, arrivalDT=2020-09-15T18:30, company=Swiss, code=JK7, terminalNum=2]",
			"ArrivalFlight [origin=Rome, departureDT=2020-09-06T14:30, arrivalDT=2020-09-06T18:00, company=Lufthanssa, code=BK4, terminalNum=3]",
			"ArrivalFlight [origin=Istanbul, departureDT=2020-10-02T21:00, arrivalDT=2020-10-02T23:00, company=KLM, code=ZF3, terminalNum=1]" };

	private String[] departureLinesToMatch = {
			"DepartureFlight [destination=Los Angeles, departureDT=2020-11-07T06:30, arrivalDT=2020-11-07T19:30, company=Pacific, code=MR8, terminalNum=2]",
			"DepartureFlight [destination=Madrid, departureDT=2020-09-12T10:30, arrivalDT=2020-09-12T15:00, company=Pegasus, code=NU6, terminalNum=4]",
			"DepartureFlight [destination=Berlin, departureDT=2020-08-04T11:30, arrivalDT=2020-08-04T15:45, company=Swiss, code=SX6, terminalNum=1]",
			"DepartureFlight [destination=Moscow, departureDT=2020-12-10T09:30, arrivalDT=2020-12-10T14:30, company=Aeroflot, code=AA7, terminalNum=3]",
			"DepartureFlight [destination=Eilat, departureDT=2020-08-25T07:30, arrivalDT=2020-08-25T08:15, company=El Al, code=IP3, terminalNum=3]" };

	@BeforeAll
	public static void initClass() {
		DateTimeFormatter formatt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// 5 arrival flights
		ArrivalFlight af1 = new ArrivalFlight("New York", LocalDateTime.parse("2020-10-10 08:00", formatt),
				LocalDateTime.parse("2020-10-10 17:00", formatt), "Delta", "XG4", 2);
		ArrivalFlight af2 = new ArrivalFlight("Paris", LocalDateTime.parse("2020-08-03 17:00", formatt),
				LocalDateTime.parse("2020-08-03 22:00", formatt), "Pegasus", "AG2", 1);
		ArrivalFlight af3 = new ArrivalFlight("London", LocalDateTime.parse("2020-09-15 12:00", formatt),
				LocalDateTime.parse("2020-09-15 18:30", formatt), "Swiss", "JK7", 2);
		ArrivalFlight af4 = new ArrivalFlight("Rome", LocalDateTime.parse("2020-09-06 14:30", formatt),
				LocalDateTime.parse("2020-09-06 18:00", formatt), "Lufthanssa", "BK4", 3);
		ArrivalFlight af5 = new ArrivalFlight("Istanbul", LocalDateTime.parse("2020-10-02 21:00", formatt),
				LocalDateTime.parse("2020-10-02 23:00", formatt), "KLM", "ZF3", 1);
		arrivalFlights = new ArrayList<ArrivalFlight>();
		arrivalFlights.add(af1);
		arrivalFlights.add(af2);
		arrivalFlights.add(af3);
		arrivalFlights.add(af4);
		arrivalFlights.add(af5);
		// 5 departure flights
		DepartureFlight df1 = new DepartureFlight("Los Angeles", LocalDateTime.parse("2020-11-07 06:30", formatt),
				LocalDateTime.parse("2020-11-07 19:30", formatt), "Pacific", "MR8", 2);
		DepartureFlight df2 = new DepartureFlight("Madrid", LocalDateTime.parse("2020-09-12 10:30", formatt),
				LocalDateTime.parse("2020-09-12 15:00", formatt), "Pegasus", "NU6", 4);
		DepartureFlight df3 = new DepartureFlight("Berlin", LocalDateTime.parse("2020-08-04 11:30", formatt),
				LocalDateTime.parse("2020-08-04 15:45", formatt), "Swiss", "SX6", 1);
		DepartureFlight df4 = new DepartureFlight("Moscow", LocalDateTime.parse("2020-12-10 09:30", formatt),
				LocalDateTime.parse("2020-12-10 14:30", formatt), "Aeroflot", "AA7", 3);
		DepartureFlight df5 = new DepartureFlight("Eilat", LocalDateTime.parse("2020-08-25 07:30", formatt),
				LocalDateTime.parse("2020-08-25 08:15", formatt), "El Al", "IP3", 3);
		departureFlights = new ArrayList<DepartureFlight>();
		departureFlights.add(df1);
		departureFlights.add(df2);
		departureFlights.add(df3);
		departureFlights.add(df4);
		departureFlights.add(df5);
	}

	@Test
	public void createArrivals() throws IOException {
		fm.createFlights(arrivalFlights);
		buffReader = new BufferedReader(new FileReader(fm.getArrivalsFile()));
		arrayIndex = 0;
		while ((someLine = buffReader.readLine()) != null && arrayIndex < arrivalLinesToMatch.length)
			Assert.assertEquals(arrivalLinesToMatch[arrayIndex++], someLine);
		buffReader.close();
	}

	@Test
	public void createDepartures() throws IOException {
		fm.createFlights(departureFlights);
		buffReader = new BufferedReader(new FileReader(fm.getDeparturesFile()));
		arrayIndex = 0;
		while ((someLine = buffReader.readLine()) != null && arrayIndex < arrivalLinesToMatch.length)
			Assert.assertEquals(departureLinesToMatch[arrayIndex++], someLine);
		buffReader.close();
	}

}
