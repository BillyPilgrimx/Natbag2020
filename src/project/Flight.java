// Name: Vadim Kobrinsky

package project;

import java.time.LocalDateTime;

public abstract class Flight {

	private LocalDateTime departureDT;
	private LocalDateTime arrivalDT;
	private String company;
	private String code;
	private int terminalNum;

	public Flight(LocalDateTime departureDT, LocalDateTime arrivalDT, String company, String code, int terminalNum) {
		this.departureDT = departureDT;
		this.arrivalDT = arrivalDT;
		this.company = company;
		this.code = code;
		this.terminalNum = terminalNum;
	}

	public LocalDateTime getDepartureDT() {
		return departureDT;
	}

	public void setDepartureDT(LocalDateTime departureDT) {
		this.departureDT = departureDT;
	}

	public LocalDateTime getArrivalDT() {
		return arrivalDT;
	}

	public void setArrivalDT(LocalDateTime arrivalDT) {
		this.arrivalDT = arrivalDT;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(int terminalNum) {
		this.terminalNum = terminalNum;
	}

	@Override
	public String toString() {
		return "departureDT=" + departureDT + ", arrivalDT="
				+ arrivalDT + ", company=" + company + ", code=" + code + ", terminalNum=" + terminalNum;
	}
}
