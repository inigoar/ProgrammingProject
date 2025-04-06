package motor.bloque.exceptions;

public class InsufficientFunds extends Exception {

    private final double shortBy;


    public InsufficientFunds(double shortBy){
        this.shortBy = shortBy;
    }

    @Override
    public String getMessage(){
        return "Insufficient funds. You need an extra " + String.format("%.2f", shortBy) + " euros to complete this transaction.";
    }

}
