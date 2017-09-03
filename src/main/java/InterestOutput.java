import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class InterestOutput {

    private int requestedAmount;
    private double rate;
    private double monthlyRepayment;
    private double totalRepayment;

    public InterestOutput(int requestedAmount, double rate, double monthlyRepayment, double totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.rate = rate * 100;
        this.monthlyRepayment = round(monthlyRepayment, 2);
        this.totalRepayment = round(totalRepayment, 2);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
