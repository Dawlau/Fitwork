package Models;

import java.util.Comparator;
import java.util.Date;

public class ProportionsSorter implements Comparator<Proportions> {
    @Override
    public int compare(Proportions p1, Proportions p2) {
        Date d1 = p1.getRecordedDate();
        Date d2 = p2.getRecordedDate();

        return d1.compareTo(d2);
    }
}
