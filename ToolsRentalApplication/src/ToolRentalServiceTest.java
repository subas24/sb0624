import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestMethodOrder(OrderAnnotation.class)
public class ToolRentalServiceTest {
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    @Test
    @Order (1)
    public void test1InvalidDiscount() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("09/03/15");

        try {
            service.checkout("JAKR", 5, 101, checkoutDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            assertEquals("Discount percent must be between 0 and 100.", e.getMessage());
        }
        System.out.println();
    }

    @Test
    @Order (2)
    public void test2LadderRental() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("07/02/20");

        RentalAgreement agreement = service.checkout("LADW", 3, 10, checkoutDate);
        
        assertEquals("LADW", agreement.getTool().getToolCode());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        agreement.printAgreement();
        System.out.println();
    }

    @Test
    @Order (3)
    public void test3ChainsawRental() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("07/02/15");

        RentalAgreement agreement = service.checkout("CHNS", 5, 25, checkoutDate);
        
        assertEquals("CHNS", agreement.getTool().getToolCode());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(25, agreement.getDiscountPercent());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        agreement.printAgreement();
        System.out.println();
    }

    @Test
    @Order (4)
    public void test4JackhammerRentalDeWalt() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("09/03/15");

        RentalAgreement agreement = service.checkout("JAKD", 6, 0, checkoutDate);
        
        assertEquals("JAKD", agreement.getTool().getToolCode());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        agreement.printAgreement();
        System.out.println();
    }

    @Test
    @Order (5)
    public void test5JackhammerRentalRidgidLong() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("07/02/15");

        RentalAgreement agreement = service.checkout("JAKR", 9, 0, checkoutDate);
        
        assertEquals("JAKR", agreement.getTool().getToolCode());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        agreement.printAgreement();
        System.out.println();
    }

    @Test
    @Order (6)
    public void test6JackhammerRentalRidgidShort() throws ParseException {
        ToolRentalService service = new ToolRentalService();
        Date checkoutDate = sdf.parse("07/02/20");

        RentalAgreement agreement = service.checkout("JAKR", 4, 50, checkoutDate);
        
        assertEquals("JAKR", agreement.getTool().getToolCode());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(50, agreement.getDiscountPercent());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        agreement.printAgreement();
        System.out.println();
    }
}
