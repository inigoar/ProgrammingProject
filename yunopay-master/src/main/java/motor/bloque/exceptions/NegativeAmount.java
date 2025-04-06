package motor.bloque.exceptions;

public class NegativeAmount extends Exception {

    @Override
    public String getMessage(){
        return "The amount for this transaction was a negative number";
    }

}
