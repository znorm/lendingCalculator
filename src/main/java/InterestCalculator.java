import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class InterestCalculator {

    private int numberOfInstallments;
    private int installmentsPerYear;
    private int chunkSize;

    public InterestCalculator(int numberOfInstallments, int installmentsPerYear, int chunkSize) {
        this.numberOfInstallments = numberOfInstallments;
        this.installmentsPerYear = installmentsPerYear;
        this.chunkSize = chunkSize;
    }

    public InterestOutput calculateInterest(List<Lender> lenders, int amountToBorrow) {
        lenders.sort((l1, l2) -> Double.compare(l1.getRate(), l2.getRate()));

        double borrowingRate = getBorrowingRate(lenders, amountToBorrow);
        double monthlyRepayment = getMonthlyRepayment(amountToBorrow, borrowingRate / installmentsPerYear, numberOfInstallments);
        double totalRepayment = monthlyRepayment * numberOfInstallments;

        return new InterestOutput(amountToBorrow, borrowingRate, monthlyRepayment, totalRepayment);
    }

    private double getMonthlyRepayment(int amountToBorrow, double interestRate, int numberOfPayments) {
        return (double) amountToBorrow * (interestRate / (1 - Math.pow((1 + interestRate), -numberOfPayments)));
    }

    private double getBorrowingRate(List<Lender> lenders, int amountToBorrow) {
        double chunks = (double) chunkSize / (double) amountToBorrow;
        BigDecimal borrowingRate = new BigDecimal(0);
        while (amountToBorrow > 0) {
            for (int i = 0; i < lenders.size(); i++) {
                if (amountToBorrow <= 0 && lenders.get(i).getAvailable() < chunkSize) {
                    break;
                }
                lenders.get(i).setAvailable(lenders.get(i).getAvailable() - chunkSize);
                borrowingRate = borrowingRate.add(new BigDecimal(chunks).multiply(new BigDecimal(lenders.get(i).getRate())));
                amountToBorrow -= 10;
            }
        }
        return borrowingRate.round(new MathContext(2, RoundingMode.HALF_UP)).doubleValue();
    }

}

