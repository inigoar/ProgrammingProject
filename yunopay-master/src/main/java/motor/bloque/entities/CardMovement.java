package motor.bloque.entities;

import motor.bloque.interfaces.Movement;

import java.time.LocalDateTime;


public class CardMovement implements Movement {

    private double amount;
    private double remainingBalance;
    private LocalDateTime date;

    public CardMovement(double amount){
        this.amount = amount;
        date = LocalDateTime.now();
    }

    public CardMovement(){}

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    @Override
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getRemainingBalance() {
        return remainingBalance;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }
}
