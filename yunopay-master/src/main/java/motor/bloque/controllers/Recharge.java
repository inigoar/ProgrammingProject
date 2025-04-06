package motor.bloque.controllers;

import motor.bloque.exceptions.ExpiredCard;
import motor.bloque.exceptions.IncorrectPin;
import motor.bloque.exceptions.NegativeAmount;
import motor.bloque.exceptions.NoSuchCard;
import motor.bloque.handlers.Persistence;
import motor.bloque.interfaces.Card;
import motor.bloque.views.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

public abstract class Recharge extends AbstractAction {

    private static final String ERROR = "Error";

    public static class OkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<ClientView.formField, String> map = MainMenu.getFrame().getFormData();
            String cardNumber;
            String pin;
            String amount;
            if (!(map.containsKey(ClientView.formField.CARDNUMBER) && map.containsKey(ClientView.formField.PIN) && map.containsKey(ClientView.formField.AMOUNT))) {
                JOptionPane.showMessageDialog(MainMenu.getFrame(), "Fields cannot be empty", ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                pin = map.get(ClientView.formField.PIN);
                cardNumber = map.get(ClientView.formField.CARDNUMBER);
                amount = map.get(ClientView.formField.AMOUNT);

                if (cardNumber.length() != 12) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect card number format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else if (pin.length() != 4) {
                    JOptionPane.showMessageDialog(MainMenu.getFrame(), "Incorrect PIN format", ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        DecimalFormat decformat = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));
                        double parsedAmount = Double.parseDouble(decformat.format(Double.parseDouble(amount)));
                        Card card = Persistence.getCard(cardNumber, pin);
                        card.recharge(parsedAmount);
                        Ticket.pay(card.getNumber(), card.getBalance(), card.getName(), parsedAmount);
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), "Amount field format is incorrect. Amounts should use a period and a maximum of 2 decimal spaces", ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchCard nsc) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), nsc.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (IncorrectPin ip) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), ip.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (ExpiredCard ec) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), ec.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    } catch (NegativeAmount na) {
                        JOptionPane.showMessageDialog(MainMenu.getFrame(), na.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

}
