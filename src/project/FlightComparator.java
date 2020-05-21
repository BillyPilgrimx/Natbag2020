// Name: Vadim Kobrinsky

package project;

import java.util.Comparator;

public class FlightComparator {

	class DepartureComparator implements Comparator<Flight> {

		@Override
		public int compare(Flight f1, Flight f2) {
			return f1.getDepartureDT().compareTo(f2.getDepartureDT());
		}
	}

	class ArrivalComparator implements Comparator<Flight> {

		@Override
		public int compare(Flight f1, Flight f2) {
			return f1.getArrivalDT().compareTo(f2.getArrivalDT());
		}
	}

	class CompanyComparator implements Comparator<Flight> {

		@Override
		public int compare(Flight f1, Flight f2) {
			return f1.getCompany().compareTo(f2.getCompany());
		}
	}

	class CodeComparator implements Comparator<Flight> {

		@Override
		public int compare(Flight f1, Flight f2) {
			return f1.getCode().compareTo(f2.getCode());
		}
	}

	class TerminalComaparator implements Comparator<Flight> {

		@Override
		public int compare(Flight f1, Flight f2) {
			if (f1.getTerminalNum() < f2.getTerminalNum())
				return -1;
			if (f1.getTerminalNum() > f2.getTerminalNum())
				return 1;
			else
				return 0;
		}
	}
}
