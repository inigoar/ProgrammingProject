package motor.bloque.entities;

import motor.bloque.interfaces.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardMovementTest {

    private CardMovement testMovement;

    @BeforeEach
    void setTestMovement() {
        testMovement = new CardMovement();
    }

    @Test
    @DisplayName("CardMovement setAmount")
    void setAmount() {
        testMovement.setAmount(10);
        assertEquals(10, testMovement.getAmount());
    }

    @Test
    @DisplayName("CardMovement setAmount on delcaration")
    void setAmount1() {
        Movement testMovement1 = new CardMovement(10);
        assertEquals(10, testMovement1.getAmount());
    }

    @Test
    @DisplayName("CardMovement setRemainingBalance")
    void setRemainingBalance() {
        testMovement.setRemainingBalance(10);
        assertEquals(10, testMovement.getRemainingBalance());
    }

    @Test
    @DisplayName("CardMovement setDate")
    void setDate() {
        LocalDateTime date = LocalDateTime.now();
        testMovement.setDate(date);
        assertEquals(date, testMovement.getDate());
    }

}