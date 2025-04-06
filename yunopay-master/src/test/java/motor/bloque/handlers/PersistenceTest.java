package motor.bloque.handlers;

import motor.bloque.entities.PrepayCard;
import motor.bloque.exceptions.IncorrectPin;
import motor.bloque.exceptions.NoSuchCard;
import motor.bloque.interfaces.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PersistenceTest {

    private PrepayCard testCard;
    private String cardNumber;

    @BeforeEach
    void setCards() {
        testCard = new PrepayCard("TestName", "Test Surname", "1234", 100);
        cardNumber = testCard.getNumber();
        PrepayCard emptyCard = new PrepayCard();
    }

    @Test
    @DisplayName("PersistenceTest get valid card")
    void getCard() throws NoSuchCard, IncorrectPin {
        Persistence.putCard(testCard);
        assertEquals(testCard, Persistence.getCard(cardNumber, "1234"));
    }

    @Test
    @DisplayName("PersistenceTest get invalid card")
    void getCard1() {
        assertThrows(NoSuchCard.class, () -> Persistence.getCard(cardNumber, "1234"));
    }

    @Test
    @DisplayName("PersistenceTest get card with wrong PIN number")
    void getCard2() {
        Persistence.putCard(testCard);
        assertThrows(IncorrectPin.class, () -> Persistence.getCard(cardNumber, "1111"));
    }

    @Test
    @DisplayName("PersistenceTest putCard")
    void putCard() throws NoSuchCard, IncorrectPin {
        Persistence.putCard(testCard);
        assertEquals(testCard, Persistence.getCard(cardNumber, "1234"));
    }

    @Test
    @DisplayName("PersistenceTest check if card exists")
    void existsCard() {
        Persistence.putCard(testCard);
        assertTrue(Persistence.existsCard(cardNumber));
        assertFalse(Persistence.existsCard("1111111"));
    }

    @Test
    void loadPersistence() throws NoSuchCard, IncorrectPin {
        Persistence.putCard(testCard);
        cardNumber = testCard.getNumber();
        Persistence.saveAll();
        Persistence.loadPersistence();
        Card res = Persistence.getCard(cardNumber, "1234");
        assertEquals(testCard.getName(), res.getName());
        assertEquals(testCard.getBalance(), res.getBalance());
        assertEquals(testCard.getExpirationDate(), res.getExpirationDate());
    }

    @Test
    void saveAll() {
    }

    @Test
    void requestCredentials() {
    }

}