package project;

import java.util.Comparator;

public class ArrivalFlightComparator extends FlightComparator {

	class OriginComparator implements Comparator<ArrivalFlight> {

		@Override
		public int compare(ArrivalFlight af1, ArrivalFlight af2) {
			return af1.getOrigin().compareTo(af2.getOrigin());
		}
	}
}
