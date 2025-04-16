package test;

import org.jfree.data.time.Year;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

public class YearTest {
    Year year;

    private void arrange() {
        year = new Year();
    }
    
    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }
    
    @Test
    public void testConstructorWithDateParameter() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        Year year = new Year(date);
        assertEquals(2024, year.getYear());
    }

    @Test
    public void testGetYear() {
        Year year = new Year(2024);
        assertEquals(2024, year.getYear());
    }

    @Test
    public void testGetSerialIndex() {
        Year year = new Year(2024);
        assertEquals(2024, year.getSerialIndex());
    }

    @Test
    public void testGetFirstMillisecond() {
        Year year = new Year(2024);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getFirstMillisecond(calendar));
    }

    @Test
    public void testGetLastMillisecond() {
        Year year = new Year(2024);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    @Test
    public void testGetFirstMillisecondWithNullCalendar() {
        Year year = new Year(2024);
        assertThrows(IllegalArgumentException.class, () -> {
            year.getFirstMillisecond(null);
        });
    }

    @Test
    public void testGetLastMillisecondWithNullCalendar() {
        Year year = new Year(2024);
        assertThrows(IllegalArgumentException.class, () -> {
            year.getLastMillisecond(null);
        });
    }

    @Test
    public void testEquals() {
        Year year1 = new Year(2024);
        Year year2 = new Year(2024);
        Year year3 = new Year(2023);
        assertTrue(year1.equals(year2));
        assertFalse(year1.equals(year3));
        assertFalse(year1.equals(null));
        assertFalse(year1.equals("2024"));
    }

    @Test
    public void testHashCode() {
        Year year1 = new Year(2024);
        Year year2 = new Year(2024);
        assertEquals(year1.hashCode(), year2.hashCode());
    }

    @Test
    public void testCompareTo() {
        Year year1 = new Year(2024);
        Year year2 = new Year(2024);
        Year year3 = new Year(2023);
        Year year4 = new Year(2025);
        
        assertEquals(0, year1.compareTo(year2));
        assertTrue(year1.compareTo(year3) > 0);
        assertTrue(year1.compareTo(year4) < 0);
    }

    @Test
    public void testCompareToWithNull() {
        Year year = new Year(2024);
        assertThrows(IllegalArgumentException.class, () -> {
            year.compareTo(null);
        });
    }

    @Test
    public void testToString() {
        Year year = new Year(2024);
        assertEquals("2024", year.toString());
    }

    @Test
    public void testParseYear() {
        Year year = Year.parseYear("2024");
        assertEquals(2024, year.getYear());
    }

    @Test
    public void testParseYearWithInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            Year.parseYear("invalid");
        });
    }

    @Test
    public void testParseYearWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Year.parseYear(null);
        });
    }

    @Test
    public void testPrevious() {
        Year year = new Year(2024);
        Year previous = (Year) year.previous();
        assertEquals(2023, previous.getYear());
    }

    @Test
    public void testNext() {
        Year year = new Year(2024);
        Year next = (Year) year.next();
        assertEquals(2025, next.getYear());
    }

    @Test
    public void testGetStart() {
        Year year = new Year(2024);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        assertEquals(expected, year.getStart());
    }

    @Test
    public void testGetEnd() {
        Year year = new Year(2024);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date expected = calendar.getTime();
        assertEquals(expected, year.getEnd());
    }
}
