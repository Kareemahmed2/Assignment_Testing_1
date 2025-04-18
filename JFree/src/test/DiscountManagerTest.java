package test;
import JFree.DiscountCalculator;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jfree.data.time.Week;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class DiscountManagerTest {
    Mockery mockingContext=new Mockery();
    IDiscountCalculator mockedCalculator = mockingContext.mock(IDiscountCalculator.class);
    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsFalse() throws Exception {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        mockingContext.checking(new Expectations(){
            {
                never(mockedDependency).getDiscountPercentage();
                never(mockedDependency).isTheSpecialWeek();
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actualPrice=discountManager.calculatePriceAfterDiscount(originalPrice);
        // Assert
        assertEquals(expectedPrice,actualPrice,0.001);
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalse()throws Exception{
        boolean isDiscountSeason=true;
        double originalPrice=150.0;
        int mockPercentage=5;
        double expectedPrice=150*(1-mockPercentage/100.0);
        mockingContext.checking(new Expectations(){
            {
                oneOf(mockedCalculator).isTheSpecialWeek();
                will(returnValue(false));
                oneOf(mockedCalculator).getDiscountPercentage();
                will(returnValue(mockPercentage));
            }
        });
        DiscountManager discountManager=new DiscountManager(isDiscountSeason,mockedCalculator);
        double actualPrice=discountManager.calculatePriceAfterDiscount(originalPrice);
        assertEquals(expectedPrice,actualPrice,0.001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() throws Exception {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 100.0*0.8;
        mockingContext.checking(new Expectations(){
            {
                oneOf(mockedCalculator).isTheSpecialWeek();
                will(returnValue(true));
                never(mockedCalculator).getDiscountPercentage();
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedCalculator);
        // Act
        double actualPrice=discountManager.calculatePriceAfterDiscount(originalPrice);
        // Assert
        assertEquals(expectedPrice,actualPrice,0.001);
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCalculatePriceWhenPriceIsNegativeAndDiscountsSeasonIsFalse() throws Exception {
        boolean isDiscountsSeason = false;
        double originalPrice = -100.0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 1);
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, discountCalculator);
        discountManager.calculatePriceAfterDiscount(originalPrice);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCalculatePriceWhenPriceIsNegativeAndDiscountsSeasonIsTrue() throws Exception {
        boolean isDiscountsSeason = true;
        double originalPrice = -200.0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 3);
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, discountCalculator);
        discountManager.calculatePriceAfterDiscount(originalPrice);
    }

    @Test
    public void testCalculatePriceWhenPriceIsZeroAndDiscountsSeasonIsTrue() throws Exception {
        boolean isDiscountsSeason = true;
        double originalPrice = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 3);
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, discountCalculator);
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);
        assertEquals(originalPrice, actualPrice, 0.001);
    }

    @Test
    public void testCalculatePriceWhenPriceIsZeroAndDiscountsSeasonIsFalse() throws Exception {
        boolean isDiscountsSeason = false;
        double originalPrice =0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 3);
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, discountCalculator);
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);
        assertEquals(originalPrice, actualPrice, 0.001);
    }
}
