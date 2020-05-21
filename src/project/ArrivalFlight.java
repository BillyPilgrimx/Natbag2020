package project;

import java.time.LocalDateTime;

public class ArrivalFlight extends Flight {
	
	private String origin;
	
	public ArrivalFlight(String origin, LocalDateTime departureDT, LocalDateTime arrivalDT, String company, String code, int terminalNum) {
		super(departureDT, arrivalDT, company, code, terminalNum);
		this.origin = origin;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "ArrivalFlight [origin=" + origin + ", " + super.toString() + "]\n";
	}
}
