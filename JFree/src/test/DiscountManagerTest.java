package test;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

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
}
