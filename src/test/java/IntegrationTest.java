import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IntegrationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfFundsNotAvailable() throws IOException {
        String[] arguments = {"src/test/resources/marketData.csv", "2500"};
        main.main(arguments);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBorrowAmountIsTooLow() throws IOException {
        String[] arguments = {"src/test/resources/marketData.csv", "500"};
        main.main(arguments);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBorrowAmountIsNotIncrementOf100() throws IOException {
        String[] arguments = {"src/test/resources/marketData.csv", "1550"};
        main.main(arguments);
    }

    @Test
    public void shouldGetCorrectOutput_1000Loan() throws IOException {
        String[] arguments = {"src/test/resources/marketData.csv", "1000"};
        main.main(arguments);
        assertThat(outContent.toString(), is("Requested Amount: £1000\n" +
                "Rate: 7.8\n" +
                "Monthly Repayment: £31.24\n" +
                "Total Repayment: £1124.79\n"));
    }

    @Test
    public void shouldGetCorrectOutput_1500Loan() throws IOException {
        String[] arguments = {"src/test/resources/marketData.csv", "1500"};
        main.main(arguments);
        assertThat(outContent.toString(), is("Requested Amount: £1500\n" +
                "Rate: 7.8\n" +
                "Monthly Repayment: £46.87\n" +
                "Total Repayment: £1687.19\n"));
    }


}