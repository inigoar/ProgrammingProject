package motor.bloque.exceptions;

import java.time.LocalDateTime;

public class ExpiredCard extends Exception {

    private final transient LocalDateTime dateTime;

    public ExpiredCard(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String getMessage(){
        return "The card has expired. The expiration date was: " + dateTime;
    }
}
