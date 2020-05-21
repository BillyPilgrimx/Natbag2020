package project;

import java.util.Comparator;

public class DepartureFlightComparator extends FlightComparator {
	
	class DestinationComparator implements Comparator<DepartureFlight> {

		@Override
		public int compare(DepartureFlight df1, DepartureFlight df2) {
			return df1.getDestination().compareTo(df2.getDestination());
		}
	}
}
