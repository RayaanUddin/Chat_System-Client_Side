import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Packet implements Serializable {
    final private String message;

    final private LocalDate date;

    final private LocalTime time;

    public Packet(String messageInp) {
        message = messageInp;
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return date.toString() + " " + time.toString();
    }
}
