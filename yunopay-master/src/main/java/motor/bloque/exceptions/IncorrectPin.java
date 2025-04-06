package motor.bloque.exceptions;

public class IncorrectPin extends Exception {

    @Override
    public String getMessage(){
        return "Incorrect PIN";
    }

}
