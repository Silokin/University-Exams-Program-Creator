package gr.aueb.mscis.sample.util;

import java.util.Calendar;


/**
 * Αμετάβλητη κλάση για τη διαχείριση ημερομηνιών
 * @author Νίκος Διαμαντίδης
 *
 */
public class SimpleCalendar implements  Comparable<SimpleCalendar> {
    private static final long MILLIS_PER_DAY = 86400000;
    private Calendar date;

    /**
     * Κατασκευάζει μία ημερομηνία με βάση το έτος,
     * το μήνα και την ημέρα του μήνα.
     * @param year Το έτος
     * @param month Ο μήνας από 1 έως 12
     * @param day Η ημέρα του μήνα
     */
    public SimpleCalendar(int year, int month, int day, int hour, int minutes) {
        date = Calendar.getInstance();
        date.set(year, month - 1, day, hour, minutes);
       
    }
 
    /**
     * Κατασκευάζει μία ημερομηνία λαμβάνοντας.
     * ως παράμετρο αντικείμενο της κλάσης {@code Calendar}
     * @param date Η ημερομηνία
     */
    public SimpleCalendar(Calendar date) {
        this.date = Calendar.getInstance();
        this.date.setTimeInMillis(date.getTimeInMillis());
        //trimToDays(this.date); 
    }

    //agnohsh ths wras, emeis den to xreiazomaste giati mas endiaferei i wra
    private void trimToDays(Calendar javaDate) {
        javaDate.set(Calendar.HOUR_OF_DAY, 0);
        javaDate.set(Calendar.MINUTE, 0);
        javaDate.set(Calendar.SECOND, 0);
        javaDate.set(Calendar.MILLISECOND, 0);
    }
 
//    /**
//     * Η διάρκεια σε ημέρες σε σχέση με μία άλλη ημερομηνία.
//     * @param other Η δεύτερη ημερομηνία για την οποία
//     * υπολογίζεται η διάρκεια
//     * @return Ο αριθμός των ημερών. Θετικός αριθμός ημερών
//     * σημαίνει ότι η άλλη ημερομηνία είναι μεταγενέστερη,
//     * ενώ αρνητικός το αντίθετο.
//     */
//    public long durationInDays(SimpleCalendar other) {
//        long timeDiff = other.date.getTimeInMillis() - date.getTimeInMillis();
//        return timeDiff / MILLIS_PER_DAY;
//    }

    /**
     * Επιστρέφει το έτος της ημερομηνίας.
     * @return Το έτος
     */
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    /**
     * Επιστρέφει το μήνα της ημερομηνίας (1-12).
     * @return Ο μήνας
     */
    public int getMonth() {
        return date.get(Calendar.MONTH) + 1;
    }

    /**
     * Επιστρέφει την ημέρα σε του μήνα.
     * @return Η ημέρα του μήνα
     */
    public int getDayOfMonth() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Επιστρέφει την ημέρα της εβδομάδας της ημερομηνίας.
     * @return Η ημέρα της εβδομάδας
     */
    public int getDayOfWeek() {
        return date.get(Calendar.DAY_OF_WEEK);
    }

//    /**
//     * Επιστρέφει {@code true} αν η ημερομηνία είναι.
//     * μεταγενέστερη μίας άλλης ημερομηνίας
//     * @param other Η άλλη ημερομηνία
//     * @return {@code true} αν η ημερομηνία είναι
//     * μεταγενέστερη της άλλης
//     */
    public boolean after(SimpleCalendar other) {
        if (equals(other)) {
            return false;
        }

       return date.after(other.date);
    }

//    /**
//     * Επιστρέφει {@code true} αν η ημερομηνία είναι.
//     * προγενέστερη μίας άλλης ημερομηνίας
//     * @param other Η άλλη ημερομηνία
//     * @return {@code true} αν η ημερομηνία είναι
//     * προγενέστερη της άλλης
//     */
    public boolean before(SimpleCalendar other) {
        if (equals(other)) {
            return false;
        }

        return date.before(other.date);
    }

    /**
     * Επιστρέφει μία ημερομηνία προσθέτοντας κάποιο
     * αριθμό ημερών.
     * @param days Ο αριθμός των ημερών που προστίθενται
     * @return Η νέα ημερομηνία
     */
    public SimpleCalendar addDays(int days) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(date.getTimeInMillis());
        newDate.add(Calendar.DAY_OF_MONTH, days);
        return new SimpleCalendar(newDate);
    }

    /**
     * Επιστρέφει μία ημερομηνία τύπου {@code Calendar}.
     * @return Η ημερομηνία
     */
    public Calendar getJavaCalendar() {
        Calendar javaCalendar = Calendar.getInstance();
        javaCalendar.setTimeInMillis(date.getTimeInMillis());
        //trimToDays(javaCalendar);
        return javaCalendar;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(SimpleCalendar other) {
        return date.compareTo(other.date);
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (!(other instanceof SimpleCalendar)) {
            return false;
        }

        SimpleCalendar theDate = (SimpleCalendar) other;

        if (date == null) {
            return theDate.date == null;
        }

        if (getYear() != theDate.getYear()) {
            return false;
        }

        if (getMonth() != theDate.getMonth()) {
            return false;
        }

        if (getDayOfMonth() != theDate.getDayOfMonth()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return date == null ? 0 : date.hashCode();
    }

//	public static boolean isValid(int year, int month, int day, int hour, int minute) {
//	    if (year < 0) return false;
//	    if ((month < 1) || (month > 12)) return false;
//	    if ((day < 1) || (day > 31)) return false;
//	    if ((hour < 0) || (hour > 24)) return false;
//	    if ((minute < 0) || (minute > 60)) return false;
//	    switch (month) {
//	        case 1: return true;
//	        case 2: return (isLeap(year) ? day <= 29 : day <= 28);
//	        case 3: return true;
//	        case 4: return day < 31;
//	        case 5: return true;
//	        case 6: return day < 31;
//	        case 7: return true;
//	        case 8: return true;
//	        case 9: return day < 31;
//	        case 10: return true;
//	        case 11: return day < 31;
//	        default: return true;
//	    }
//	}
//
//	public static boolean isLeap(int year) {
//	    if (year % 4 != 0) {
//	      return false;
//	    } else if (year % 400 == 0) {
//	      return true;
//	    } else if (year % 100 == 0) {
//	      return false;
//	    } else {
//	      return true;
//	    }        
//	}    

}
