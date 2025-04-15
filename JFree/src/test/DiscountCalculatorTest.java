package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class DiscountCalculatorTest {
    DiscountCalculator discountCalculator;
    Calendar calendar=Calendar.getInstance();

    public void setup() throws Exception {
        DiscountCalculator discountCalculator = new DiscountCalculator(new Week());
    }
    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        calendar.set(2025, Calendar.MARCH, 22);  // March 22, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        discountCalculator = new DiscountCalculator(week);
        Assert.assertFalse(discountCalculator.isTheSpecialWeek());
    }

    @Test
    public void testIsTheSpecialWeekWhenTrue() throws Exception {
        calendar.set(2025, Calendar.JUNE, 23);  // March 29, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        discountCalculator = new DiscountCalculator(week);
        Assert.assertTrue(discountCalculator.isTheSpecialWeek());
    }

    @Test
    public void testDiscountPercentWhenWeekIsOdd() throws Exception{
        calendar.set(2025,Calendar.JANUARY,1);
        Date date=calendar.getTime();
        Week week=new Week(date);
        discountCalculator=new DiscountCalculator(week);
        Assert.assertEquals(discountCalculator.getDiscountPercentage(),5);
    }
    @Test
    public void testDiscountPercentWhenWeekIsEven() throws Exception{
        calendar.set(2025,Calendar.JANUARY,9);
        Date date=calendar.getTime();
        Week week=new Week(date);
        discountCalculator=new DiscountCalculator(week);
        Assert.assertEquals(discountCalculator.getDiscountPercentage(),7);
    }
}

