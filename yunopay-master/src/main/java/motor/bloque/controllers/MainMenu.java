package motor.bloque.controllers;

import motor.bloque.handlers.Persistence;
import motor.bloque.views.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class MainMenu extends AbstractAction {

    private static ClientView clientView;

    public static void startClientApp() {

        EventQueue.invokeLater(() -> {
            ClientView frame = new ClientView();
            MainMenu.setFrame(frame);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new MainMenu.QuitButton().actionPerformed(null);
                }
            });
            frame.setVisible(true);
        });
    }

    public static void setFrame(ClientView frame){
        clientView = frame;
    }

    static ClientView getFrame() {
        return clientView;
    }

    public static class NewCardButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.NEWCARD));

        }
    }

    public static class PayButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.PAY));
        }
    }

    public static class RechargeMoneyButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.RECHARGE));
        }
    }

    public static class ChangePinButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.CHANGEPIN));
        }
    }

    public static class ConsultBalanceButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.CHECKBALANCE));
        }
    }

    public static class ConsultMovementsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() ->clientView.changePanel(ClientView.panels.CHECKMOVES));
        }
    }

    public static class CancelButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() -> clientView.changePanel(ClientView.panels.MAIN));
        }
    }

    public static class QuitButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(JOptionPane.showConfirmDialog(clientView, "Are you sure ?") == JOptionPane.OK_OPTION){
                clientView.setVisible(false);
                Persistence.saveAll();
                clientView.dispose();
            }
            System.exit(0);
        }
    }
}
