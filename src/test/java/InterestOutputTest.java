import org.junit.Test;

import java.lang.annotation.Target;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class InterestOutputTest {

    @Test
    public void willRoundMonthlyPayment_was6decimals() {
        InterestOutput output = new InterestOutput(200, 0.22, 100.666666, 100.666666);
        assertThat(output.getMonthlyRepayment(), is(100.67));
        assertThat(output.getTotalRepayment(), is(100.67));
    }

    @Test
    public void willRoundMonthlyPayment_was0decimals() {
        InterestOutput output = new InterestOutput(200, 0.22, 100, 100);
        assertThat(output.getMonthlyRepayment(), is(100.00));
        assertThat(output.getTotalRepayment(), is(100.00));
    }

    @Test
    public void willRoundMonthlyPayment_was0() {
        InterestOutput output = new InterestOutput(200, 0.22, 0, 100);
        assertThat(output.getMonthlyRepayment(), is(0.00));
        assertThat(output.getTotalRepayment(), is(100.00));
    }
}