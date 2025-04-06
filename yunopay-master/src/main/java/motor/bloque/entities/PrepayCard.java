package motor.bloque.entities;

import motor.bloque.exceptions.ExpiredCard;
import motor.bloque.exceptions.InsufficientFunds;
import motor.bloque.exceptions.NegativeAmount;
import motor.bloque.handlers.Credentials;
import motor.bloque.interfaces.Card;
import motor.bloque.interfaces.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrepayCard implements Card {

    private static final Logger logger = LogManager.getLogger(PrepayCard.class);

    private String name;
    private String cardNumber;
    private String hashedPIN;
    private String salt;
    private double balance;
    private List<Movement> movements;
    private LocalDateTime expirationDate;

    public PrepayCard() {

        this.movements = new ArrayList<>();
    }

    public PrepayCard(String name, String surname, String pin, double amount) {
        this.name = name + " " + surname;
        Map<Credentials.HASHED, String> hashed = Credentials.hashNewPassword(pin);
        this.hashedPIN = hashed.get(Credentials.HASHED.PASSWORD);
        this.salt = hashed.get(Credentials.HASHED.SALT);
        this.balance = amount;
        this.movements = new ArrayList<>();
        this.cardNumber = Credentials.generateCardNumber();
        this.expirationDate = LocalDateTime.now().plusYears(1);
        logger.info("Creating new card. " + cardNumber);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return cardNumber;
    }

    public String getHashedPIN() {
        return hashedPIN;
    }

    public String getSalt() {
        return salt;
    }

    public double getBalance() {
        return balance;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public LocalDateTime getExpirationDate() {

        return expirationDate;
    }

    public boolean changePIN(String newPIN) {
        Map<Credentials.HASHED, String> map = Credentials.hashNewPassword(newPIN);
        this.setHashedPIN(map.get(Credentials.HASHED.PASSWORD));
        this.setSalt(map.get(Credentials.HASHED.SALT));
        logger.info("Changing pin for card " + cardNumber);
        return true;
    }

    public boolean recharge(double amount) throws NegativeAmount, ExpiredCard {
        if (amount < 0) throw new NegativeAmount();
        if (this.expirationDate.isBefore(LocalDateTime.now())) throw new ExpiredCard(this.expirationDate);
        balance = balance + amount;
        logger.info("Recharging " + amount + "to the card " + cardNumber);
        return true;
    }

    public boolean addMovement(Movement movement) throws InsufficientFunds, NegativeAmount, ExpiredCard {
        double amount = movement.getAmount();
        if (amount < 0) throw new NegativeAmount();
        if (this.expirationDate.isBefore(LocalDateTime.now())) throw new ExpiredCard(this.expirationDate);
        if ((balance - amount) >= 0) {
            balance -= amount;
            movement.setRemainingBalance(balance);
            movements.add(movement);
            logger.info("Adding new movement to the card " + cardNumber + ". Amount: " + amount);
        } else {
            throw new InsufficientFunds(amount - balance);
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.cardNumber = number;
    }

    public void setHashedPIN(String hashedPIN) {
        this.hashedPIN = hashedPIN;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
