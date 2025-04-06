package motor.bloque.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsTest {

    @Test
    @DisplayName("ExpiredCardTest getMessage")
    void expiredCard() {
        LocalDateTime date = LocalDateTime.now();
        ExpiredCard e = new ExpiredCard(date);
        String res = "The card has expired. The expiration date was: " + date;
        assertEquals(res, e.getMessage());

    }

    @Test
    @DisplayName("IncorrectPinTest getMessage")
    void incorrectPin() {
        String res = "Incorrect PIN";
        IncorrectPin e = new IncorrectPin();
        assertEquals(res, e.getMessage());
    }

    @Test
    @DisplayName("InsufficientFundsTest getMessage")
    void insufficientFunds() {
        double shortBy = 10;
        String res = "Insufficient funds. You need an extra " + String.format("%.2f", shortBy) + " euros to complete this transaction.";
        InsufficientFunds e = new InsufficientFunds(shortBy);
        assertEquals(res, e.getMessage());
    }

    @Test
    @DisplayName("NegativeAmountTest getMessage")
    void negativeAmount() {
        String res = "The amount for this transaction was a negative number";
        NegativeAmount e = new NegativeAmount();
        assertEquals(res, e.getMessage());
    }

    @Test
    @DisplayName("NoSuchCardTest getMessage")
    void noSuchCard() {
        String invalidCard = "111111111";
        String res = "The card number " + invalidCard + "does not match any card in the system";
        NoSuchCard e = new NoSuchCard(invalidCard);
        assertEquals(res, e.getMessage());

    }
}