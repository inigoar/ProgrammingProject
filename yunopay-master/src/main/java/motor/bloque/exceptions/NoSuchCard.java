package motor.bloque.exceptions;

public class NoSuchCard extends Exception {

    private final String invalidCard;

    public NoSuchCard(String invalidCard){
        this.invalidCard = invalidCard;
    }

    @Override
    public String getMessage(){
        return "The card number " + invalidCard + "does not match any card in the system";
    }

}
