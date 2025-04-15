package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DiscountCalculatorTest {
    DiscountCalculator discountCalculator;

    public void setup() throws Exception {
        DiscountCalculator discountCalculator = new DiscountCalculator(new Week());
    }
    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 22);  // March 22, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        discountCalculator = new DiscountCalculator(week);
        Assert.assertFalse(discountCalculator.isTheSpecialWeek());
    }

    @Test
    public void testIsTheSpecialWeekWhenTrue() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23);  // March 29, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        discountCalculator = new DiscountCalculator(week);
        Assert.assertTrue(discountCalculator.isTheSpecialWeek());
    }
}
