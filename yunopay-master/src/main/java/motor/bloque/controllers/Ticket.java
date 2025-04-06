package motor.bloque.controllers;

import motor.bloque.interfaces.Movement;
import motor.bloque.views.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Ticket extends AbstractAction {

    private static final String BYE = "<br><br>Thanks for using our system</html>";

    public static class OkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.MAIN));
        }
    }

    public static void newCard(String cardNumber, String name, double balance){
        StringBuilder ticket = new StringBuilder(hi(name) + "<br><br>Amount: &euro;" + String.format("%.2f", balance));
        ticket.append("<br>Card Number: " + cardNumber + "<br>Balance: &euro;" + String.format("%.2f", balance));
        ticket.append(BYE);
        MainMenu.getFrame().setTicket(ticket.toString());
        EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.TICKET));
    }

    public static void balance(String cardNumber, double balance, String name){
        StringBuilder ticket = new StringBuilder(hi(name) + "<br><br>Card Number: ");
        ticket.append(hideCardNumber(cardNumber) + "<br>" + "Balance: &euro;" + String.format("%.2f", balance));
        ticket.append(BYE);
        MainMenu.getFrame().setTicket(ticket.toString());
        EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.TICKET));
    }

    public static void movements(String cardNumber, List<Movement> movements, String name) {
        StringBuilder ticket = new StringBuilder(hi(name) + "<br><br>Card Number: ");
        ticket.append(hideCardNumber(cardNumber) + "<br><br>");
        for(Movement move : movements){
            LocalDateTime date = move.getDate();
            ticket.append("<br>" + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear()%100);
            ticket.append("&nbsp;&nbsp;&nbsp;&nbsp; &euro;" + String.format("%.2f", move.getAmount()));
        }
        ticket.append(BYE);
        MainMenu.getFrame().setTicket(ticket.toString());
        EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.TICKET));
    }

    public static void pay(String cardNumber, double balance, String name, double amount) {
        StringBuilder ticket = new StringBuilder(hi(name) + "<br><br>Amount: &euro;");
        ticket.append(String.format("%.2f", amount) + "<br>");
        ticket.append("Card Number: " + hideCardNumber(cardNumber) + "<br>" + "Balance: &euro;" + String.format("%.2f", balance));
        ticket.append(BYE);
        MainMenu.getFrame().setTicket(ticket.toString());
        EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.TICKET));
    }

    public static void changePin(String name) {
        MainMenu.getFrame().setTicket(hi(name) + "<br><br>The operation was completed successfully." + BYE);
        EventQueue.invokeLater(() ->MainMenu.getFrame().changePanel(ClientView.panels.TICKET));
    }

    private static String hideCardNumber (String cardNumber){
        return "XXXX XXXX " + cardNumber.substring(8);
    }

    private static String hi (String name){
        return "<html>Dear " + name;
    }

}
