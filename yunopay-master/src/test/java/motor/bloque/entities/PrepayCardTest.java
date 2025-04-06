
package motor.bloque.entities;

import motor.bloque.exceptions.*;
import motor.bloque.handlers.Persistence;
import motor.bloque.interfaces.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrepayCardTest {

    private PrepayCard testCard;
    private PrepayCard emptyCard;
    private String cardNumber;

    @BeforeEach
    void setPrepayCard() {
        testCard = new PrepayCard("TestName", "Test Surname", "1234", 0);
        emptyCard = new PrepayCard();
        Persistence.putCard(testCard);
        cardNumber = testCard.getNumber();
    }

    @Test
    @DisplayName("PrepayCard getName")
    void getName() throws NoSuchCard, IncorrectPin  {
        assertEquals("TestName" + " Test Surname", Persistence.getCard(cardNumber,"1234").getName());
    }

    @Test
    @DisplayName("PrepayCard change pin invalid card")
    void invalidCard(){
        assertThrows(IncorrectPin.class, () ->  Persistence.getCard(cardNumber,"4321"));
    }

    @Test
    @DisplayName("PrepayCard get initial balance")
    void getBalance() {
        assertEquals(0, testCard.getBalance());
    }

    @Test
    @DisplayName("PrepayCard getBalance after recharge")
    void getBalance1() throws NegativeAmount, ExpiredCard,NoSuchCard, IncorrectPin {
        Persistence.getCard(cardNumber,"1234").recharge(10);
        assertEquals(10, Persistence.getCard(cardNumber,"1234").getBalance());
    }

    @Test
    @DisplayName("PrepayCard recharge with wrong pin")
    void getBalance2(){
        assertThrows(IncorrectPin.class, () -> Persistence.getCard(cardNumber,"123").recharge(10));
    }

    @Test
    @DisplayName("PrepayCard getMovements")
    void getMovements() throws InsufficientFunds, NegativeAmount, ExpiredCard, NoSuchCard, IncorrectPin {
        Movement movement = new CardMovement();
        movement.setAmount(10);
        Persistence.getCard(cardNumber,"1234").setBalance(20);
        Persistence.getCard(cardNumber,"1234").addMovement(movement);
        assertEquals(movement, Persistence.getCard(cardNumber,"1234").getMovements().get(0));
    }


    @Test
    @DisplayName("PrepayCard change pin")
    void changePIN() throws NegativeAmount, ExpiredCard, NoSuchCard, IncorrectPin {
        assertTrue(Persistence.getCard(cardNumber,"1234").changePIN("4321"));
        assertTrue(Persistence.getCard(cardNumber,"4321").recharge(10));
        assertEquals(10, Persistence.getCard(cardNumber,"4321").getBalance());

    }

    @Test
    @DisplayName("PrepayCard change pin (Wrong pin number)")
    void changePIN2(){
        assertThrows(IncorrectPin.class, () -> Persistence.getCard(cardNumber,"123").changePIN("4321"));
    }

    @Test
    @DisplayName("PrepayCard recharge positive amount")
    void recharge() throws NegativeAmount, ExpiredCard, NoSuchCard, IncorrectPin {
        assertTrue(Persistence.getCard(cardNumber,"1234").recharge(25));
        assertEquals(25, Persistence.getCard(cardNumber,"1234").getBalance());
    }

    @Test
    @DisplayName("PrepayCard recharge negative amount")
    void recharge2() {
        assertThrows(NegativeAmount.class, () -> Persistence.getCard(cardNumber,"1234").recharge(-10));
    }

    @Test
    @DisplayName("PrepayCard recharge invalid card")
    void recharge3(){
        assertThrows(NullPointerException.class, () ->emptyCard.recharge(10));
    }

    @Test
    @DisplayName("PrepayCard recharge wrong pin number")
    void recharge4() throws NoSuchCard, IncorrectPin {
        assertThrows(IncorrectPin.class, () ->Persistence.getCard(cardNumber,"123").recharge(25));
        assertEquals(0, Persistence.getCard(cardNumber,"1234").getBalance());
    }

    @Test
    @DisplayName("PrepayCard recharge after expiration date")
    void recharge5() throws  NoSuchCard, IncorrectPin{
        Persistence.getCard(cardNumber,"1234").setExpirationDate(LocalDateTime.now().minusYears(1));
        assertThrows(ExpiredCard.class, () -> Persistence.getCard(cardNumber,"1234").recharge(10));
    }

    @Test
    @DisplayName("PrepayCard addMovement")
    void addMovement() throws InsufficientFunds, NegativeAmount, ExpiredCard, NoSuchCard, IncorrectPin {
        Movement movement = new CardMovement();
        movement.setAmount(10);
        Persistence.getCard(cardNumber,"1234").setBalance(50);
        Persistence.getCard(cardNumber,"1234").addMovement(movement);
        assertEquals(40,  Persistence.getCard(cardNumber,"1234").getBalance());
    }

    @Test
    @DisplayName("PrepayCard addMovement with insufficient funds")
    void addMovement0() {
        Movement movement = new CardMovement();
        movement.setAmount(10);

        assertThrows(InsufficientFunds.class, () -> Persistence.getCard(cardNumber,"1234").addMovement( movement));
    }

    @Test
    @DisplayName("PrepayCard addMovement invalid card")
    void addMovement1() {
        Movement movement = new CardMovement(5);
        assertThrows(NullPointerException.class, () ->emptyCard.addMovement(movement));

    }

    @Test
    @DisplayName("PrepayCard addMovement negative amount")
    void addMovement2() {
        Movement movement = new CardMovement();
        movement.setAmount(-10);
        assertThrows(NegativeAmount.class, () -> Persistence.getCard(cardNumber,"1234").addMovement(movement));
    }

    @Test
    @DisplayName("PrepayCard addMovement after expiration date")
    void addMovement3() throws  NoSuchCard, IncorrectPin {
        Persistence.getCard(cardNumber,"1234").setExpirationDate(LocalDateTime.now().minusYears(1));
        Persistence.getCard(cardNumber,"1234").setBalance(20);
        Movement movement = new CardMovement();
        movement.setAmount(10);
        assertThrows(ExpiredCard.class, () ->  Persistence.getCard(cardNumber,"1234").addMovement(movement));
    }

    @Test
    @DisplayName("PrepayCard set a new name")
    void setNewName() throws  NoSuchCard, IncorrectPin  {

        Persistence.getCard(cardNumber,"1234").setName("Francisco");
        assertEquals("Francisco", Persistence.getCard(cardNumber,"1234").getName());
    }

    @Test
    @DisplayName("PrepayCard set a new number")
    void setNewNumber()  throws  NoSuchCard, IncorrectPin {

        Persistence.getCard(cardNumber,"1234").setNumber("190319961994");
        assertEquals("190319961994", Persistence.getCard(cardNumber,"1234").getNumber());

    }

    @Test
    @DisplayName("PrepayCard set a new list of movements")
    void setNewMovements() throws  NoSuchCard, IncorrectPin {
        Movement movement = new CardMovement();
        movement.setAmount(10);
        Movement movement1 = new CardMovement();
        movement1.setAmount(5);
        List<Movement> movements = new ArrayList<>();
        movements.add(movement);
        movements.add(movement1);
        Persistence.getCard(cardNumber,"1234").setMovements(movements);
        assertEquals(movements, Persistence.getCard(cardNumber,"1234").getMovements());
    }

    @Test
    @DisplayName("PrepayCard get expiration date")
    void getExpirationDate() throws  NoSuchCard, IncorrectPin{
        LocalDateTime dateTime = LocalDateTime.now();
        Persistence.getCard(cardNumber,"1234").setExpirationDate(dateTime);
        assertEquals(dateTime, Persistence.getCard(cardNumber,"1234").getExpirationDate());
    }

}