package motor.bloque.interfaces;

import java.time.LocalDateTime;

public interface Movement {

    void setAmount(double amount);
    void setRemainingBalance(double remainingBalance);
    void setDate(LocalDateTime date);
    double getAmount();
    double getRemainingBalance();
    LocalDateTime getDate();
}
