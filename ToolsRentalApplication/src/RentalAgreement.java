import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private int discountPercent;
    private Date checkoutDate;
    private Date dueDate;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, Date checkoutDate) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        calculateCharges();
    }

    private void calculateCharges() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DAY_OF_YEAR, rentalDays);
        dueDate = calendar.getTime();

        int chargeableDays = calculateChargeableDays(checkoutDate, rentalDays);
        preDiscountCharge = chargeableDays * tool.getDailyCharge();
        discountAmount = preDiscountCharge * discountPercent / 100;
        finalCharge = preDiscountCharge - discountAmount;
    }

    private int calculateChargeableDays(Date startDate, int rentalDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int chargeableDays = 0;

        for (int i = 0; i < rentalDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            boolean isWeekend = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);

            if ((isWeekend && tool.isWeekendCharge()) ||
                (!isWeekend && tool.isWeekdayCharge()) ||
                (isHoliday(calendar) && tool.isHolidayCharge())) {
                chargeableDays++;
            }
        }

        return chargeableDays;
    }

    private boolean isHoliday(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Independence Day
        if (month == Calendar.JULY && (day == 4 || (dayOfWeek == Calendar.FRIDAY && day == 3) || (dayOfWeek == Calendar.MONDAY && day == 5))) {
            return true;
        }

        // Labor Day (first Monday in September)
        if (month == Calendar.SEPTEMBER && dayOfWeek == Calendar.MONDAY && day <= 7) {
            return true;
        }

        return false;
    }

    public void printAgreement() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        System.out.println("Tool code: " + tool.getToolCode());
        System.out.println("Tool type: " + tool.getToolType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Checkout date: " + sdf.format(checkoutDate));
        System.out.println("Due date: " + sdf.format(dueDate));
        System.out.println("Daily rental charge: $" + String.format("%.2f", tool.getDailyCharge()));
        System.out.println("Charge days: " + calculateChargeableDays(checkoutDate, rentalDays));
        System.out.println("Pre-discount charge: $" + String.format("%.2f", preDiscountCharge));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: $" + String.format("%.2f", discountAmount));
        System.out.println("Final charge: $" + String.format("%.2f", finalCharge));
    }

    public Tool getTool() {
        return tool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }
}
