import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Lender {

    private String name;
    private double rate;
    private int available;

    public Lender(String name, String rate, String available){
        this.name = name;
        this.rate = Double.parseDouble(rate);
        this.available = Integer.parseInt(available);
    }
}
