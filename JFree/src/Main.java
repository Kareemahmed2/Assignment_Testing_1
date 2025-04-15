import JFree.DiscountCalculator;
import org.jfree.data.time.Week;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(2025,Calendar.JANUARY,1);
        Date date=calendar.getTime();
        Week week=new Week(date);
        DiscountCalculator discountCalculator=new DiscountCalculator(week);
        System.out.println(week.getWeek());
    }
}