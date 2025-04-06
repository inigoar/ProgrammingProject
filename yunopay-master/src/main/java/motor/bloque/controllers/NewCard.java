package motor.bloque.controllers;

import motor.bloque.entities.PrepayCard;
import motor.bloque.handlers.Persistence;
import motor.bloque.views.ClientView;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

public abstract class NewCard extends AbstractAction {
    private static final Logger logger = LogManager.getLogger(NewCard.class);
    private static final String ERROR = "Error";

    public static class OkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Map<ClientView.formField, String> map = MainMenu.getFrame().getFormData();
            String name;
            String surname;
            String pin;
            String confirmPin;
            String initialAmount;

            if (!(map.containsKey(ClientView.formField.NAME) && map.containsKey(ClientView.formField.SURNAME) && map.containsKey(ClientView.formField.PIN) && map.containsKey(ClientView.formField.CONFIRMPIN) && map.containsKey(ClientView.formField.AMOUNT))) {
                JOptionPane.showMessageDialog(MainMenu.getFrame(), "Fields cannot be empty", ERROR, JOptionPane.ERROR_MESSAGE);
                logger.error("Some fields are empty");

            } else {

                name = map.get(ClientView.formField.NAME);
                surname = map.get(ClientView.formField.SURNAME);
                pin = map.get(ClientView.formField.PIN);
                confirmPin = map.get(ClientView.formField.CONFIRMPIN);
                initialAmount = map.get(ClientView.formField.AMOUNT);


                if ((pin.length() != 4) || !StringUtils.isNumeric(pin)) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect PIN format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else if (!pin.equals(confirmPin)) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "PINs don't match", ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    logger.info("Ok button pressed");
                    try {
                        DecimalFormat decformat = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));
                        double parsedAmount = Double.parseDouble(decformat.format(Double.parseDouble(initialAmount)));
                        logger.info("Attempting to create new card with the following data: " + name + " " + surname + " " + " " + initialAmount);
                        PrepayCard newCard = new PrepayCard(name, surname, pin, parsedAmount);
                        Persistence.putCard(newCard);
                        Ticket.newCard(newCard.getNumber(), newCard.getName(), newCard.getBalance());
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), "Amount field format is incorrect. Amounts should use a period and a maximum of 2 decimal spaces", ERROR, JOptionPane.ERROR_MESSAGE);
                        logger.error(iae);
                    }
                }
            }
        }
    }

}

