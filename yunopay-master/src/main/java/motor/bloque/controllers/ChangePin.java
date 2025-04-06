package motor.bloque.controllers;

import motor.bloque.exceptions.IncorrectPin;
import motor.bloque.exceptions.NoSuchCard;
import motor.bloque.handlers.Persistence;
import motor.bloque.views.ClientView;
import motor.bloque.interfaces.Card;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public abstract class ChangePin extends AbstractAction {

    private static final String ERROR = "Error";

    private static final Logger logger = LogManager.getLogger(ChangePin.class);


    public static class OkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Map<ClientView.formField, String> map = MainMenu.getFrame().getFormData();
            String pin;
            String confirmPin;
            String cardNumber;
            String newPin;

            if (!(map.containsKey(ClientView.formField.PIN) && map.containsKey(ClientView.formField.CONFIRMPIN) && map.containsKey(ClientView.formField.CARDNUMBER) &&map.containsKey(ClientView.formField.NEWPIN))) {
                JOptionPane.showMessageDialog(MainMenu.getFrame(), "Fields cannot be empty", ERROR, JOptionPane.ERROR_MESSAGE);
                logger.error("Some fields are empty");
            }
            else {

                cardNumber = map.get(ClientView.formField.CARDNUMBER);
                pin = map.get(ClientView.formField.PIN);
                confirmPin = map.get(ClientView.formField.CONFIRMPIN);
                newPin = map.get(ClientView.formField.NEWPIN);


                if (cardNumber.length() != 12) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect card number format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else if (newPin.length() != 4 || !StringUtils.isNumeric(newPin)) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect PIN format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else if (!newPin.equals(confirmPin)) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "PINs don't match", ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Card card = Persistence.getCard(cardNumber, pin);
                        card.changePIN(newPin);
                        Ticket.changePin(card.getName());
                    } catch (NoSuchCard nsc) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), nsc.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (IncorrectPin ip) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), ip.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
