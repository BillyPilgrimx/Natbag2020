package project;

import java.time.LocalDateTime;

public class DepartureFlight extends Flight {
	
	private String destination;

	public DepartureFlight(String destination, LocalDateTime departureDT, LocalDateTime arrivalDT, String company, String code, int terminalNum) {
		super(departureDT, arrivalDT, company, code, terminalNum);
		this.destination = destination;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "DepartureFlight [destination=" + destination + ", " + super.toString() + "]\n";
	}
}
