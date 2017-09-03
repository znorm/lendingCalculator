import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class main {

    private static final Logger log = LoggerFactory.getLogger(main.class);

    public static void main(String[] args) throws IOException {
        InterestCalculator interestCalculator = new InterestCalculator(36, 12, 10);
        List<Lender> lenders = parseLenderCSV(args[0]);
        int amountToBorrow = validateBorrowAmount(args[1]);

        int availableSum = lenders.stream().mapToInt(Lender::getAvailable).sum();
        validateFundsAreAvailable(availableSum, amountToBorrow);

        InterestOutput output = interestCalculator.calculateInterest(lenders, amountToBorrow);
        System.out.println("Requested Amount: £" + output.getRequestedAmount());
        System.out.println("Rate: " + output.getRate());
        System.out.println("Monthly Repayment: £" + output.getMonthlyRepayment());
        System.out.println("Total Repayment: £" + output.getTotalRepayment());
    }

    private static void validateFundsAreAvailable(int availableSum, int amountToBorrow) {
        if (amountToBorrow > availableSum) {
            throw new IllegalArgumentException(String.format("Error: funds are not available to borrow %d", amountToBorrow));
        }
    }

    private static int validateBorrowAmount(String amount) {
        int ammountToBorrow =  Integer.parseInt(amount);
        if (ammountToBorrow % 100 != 0) {
            throw new IllegalArgumentException("Error: loan amount must be in increments of 100");
        }
        if (ammountToBorrow < 1000 || ammountToBorrow > 15000){
            throw new IllegalArgumentException("Error: loan amount must be between 1000 and 15000");
        }
        return ammountToBorrow;
    }

    private static List<Lender> parseLenderCSV(String csvFile) throws IOException {

        List<Lender> lenderList = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile), ',', '\'', 1);
            String[] line;
            while ((line = reader.readNext()) != null) {
                lenderList.add(new Lender(line[0], line[1], line[2]));
            }
        } catch (Exception e) {
            log.error("Error parsing csv file {}", csvFile);
            throw e;
        }
        return lenderList;
    }
}
