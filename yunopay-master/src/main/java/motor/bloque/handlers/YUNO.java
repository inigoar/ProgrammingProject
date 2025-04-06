package motor.bloque.handlers;

import motor.bloque.controllers.MainMenu;

public class YUNO {

    public static void main(String[] args){
        Persistence.loadPersistence();
        MainMenu.startClientApp();
    }
}
