package motor.bloque.controllers;

import motor.bloque.exceptions.IncorrectPin;
import motor.bloque.exceptions.NoSuchCard;
import motor.bloque.handlers.Persistence;
import motor.bloque.interfaces.Card;
import motor.bloque.views.ClientView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public abstract class ConsultBalance extends AbstractAction {
    private static final Logger logger = LogManager.getLogger(ConsultBalance.class);

    private static final String ERROR = "Error";

    public static class OkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<ClientView.formField, String> map = MainMenu.getFrame().getFormData();
            String cardNumber;
            String pin;
            if (!(map.containsKey(ClientView.formField.CARDNUMBER) && map.containsKey(ClientView.formField.PIN))) {
                JOptionPane.showMessageDialog(MainMenu.getFrame(), "Fields cannot be empty", ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                pin = map.get(ClientView.formField.PIN);
                cardNumber = map.get(ClientView.formField.CARDNUMBER);
                if (cardNumber.length() != 12) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect card number format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else if (pin.length() != 4) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect PIN format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Card card = Persistence.getCard(cardNumber, pin);
                        Ticket.balance(card.getNumber(), card.getBalance(), card.getName());
                    } catch (NoSuchCard nsc) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), nsc.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (IncorrectPin ip) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), ip.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                        logger.warn("Authentication error for card " + cardNumber);
                    }
                }
            }
        }
    }

}
