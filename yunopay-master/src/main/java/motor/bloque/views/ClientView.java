package motor.bloque.views;

import motor.bloque.controllers.*;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import static javax.swing.GroupLayout.Alignment.*;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class ClientView extends JFrame {

    private JPanel currentPanel;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField cardNumberField;
    private JTextField amountField;
    private JPasswordField newPinField;
    private JPasswordField pinField;
    private JPasswordField confirmPinField;

    private JLabel nameLabel = new JLabel("Name:");
    private JLabel surnameLabel = new JLabel("Surname:");
    private JLabel pinLabel = new JLabel("PIN:");
    private JLabel newPinLabel = new JLabel("New PIN:");
    private JLabel confirmPinLabel = new JLabel("Confirm new PIN:");
    private JLabel amountLabel = new JLabel("Amount:");
    private JLabel cardNumberLabel = new JLabel("Card number:");
    private String ticket;

    private static final String OKTT = "Validate data and perform selected operation";

    JButton cancelButton = new JButton("Cancel");

    public enum panels {MAIN, NEWCARD, PAY, RECHARGE, CHANGEPIN, CHECKBALANCE, CHECKMOVES, TICKET}
    public enum formField {NAME, SURNAME, PIN, NEWPIN, CONFIRMPIN, AMOUNT, CARDNUMBER}


    public ClientView() {
        mainMenu();
        cancelButton.setToolTipText("Cancel current operation and return to main menu");
        cancelButton.addActionListener(new MainMenu.CancelButton());
    }

    private void mainMenu() {

        JButton newCardButton = new JButton("New card");
        newCardButton.setToolTipText("Create a new card to use in the system");
        newCardButton.addActionListener(new MainMenu.NewCardButton());

        JButton payButton = new JButton("Pay");
        payButton.setToolTipText("Makes a payment with a card, provided there are sufficient funds in it");
        payButton.addActionListener(new MainMenu.PayButton());

        JButton rechargeMoneyButton = new JButton("Recharge money");
        rechargeMoneyButton.setToolTipText("recharge the card's balance with more money");
        rechargeMoneyButton.addActionListener(new MainMenu.RechargeMoneyButton());

        JButton changePinButton = new JButton("Change PIN");
        changePinButton.setToolTipText("Change the PIN code of a card");
        changePinButton.addActionListener(new MainMenu.ChangePinButton());

        JButton consultBalanceButton = new JButton("Consult balance");
        consultBalanceButton.setToolTipText("Check the amount of money left in a card");
        consultBalanceButton.addActionListener(new MainMenu.ConsultBalanceButton());

        JButton consultMovementsButton = new JButton("Consult movements");
        consultMovementsButton.setToolTipText("Check the movements of a card for the current session");
        consultMovementsButton.addActionListener(new MainMenu.ConsultMovementsButton());

        JButton quitButton = new JButton("Quit");
        quitButton.setToolTipText("Quit program");
        quitButton.addActionListener(new MainMenu.QuitButton());

        setTitle("YUNO pay");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        GroupLayout gl = makeGL();

        gl.linkSize(SwingConstants.HORIZONTAL, newCardButton,
                payButton, rechargeMoneyButton, changePinButton,
                consultBalanceButton, consultMovementsButton);

        currentPanel.setToolTipText("Main menu");

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(newCardButton)
                    .addComponent(payButton)
                    .addComponent(rechargeMoneyButton)
                    .addComponent(changePinButton)
                    .addComponent(consultBalanceButton)
                    .addComponent(consultMovementsButton)
                    .addComponent(quitButton)
                )
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(newCardButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(payButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(rechargeMoneyButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(changePinButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(consultBalanceButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(consultMovementsButton))
                .addGroup(gl.createParallelGroup(CENTER)
                    .addComponent(quitButton))
        );

        pack();

    }

    private void newCard() {
        GroupLayout gl = makeGL();
        resetFields();
        JButton okButton = new JButton("OK");
        okButton.setToolTipText(OKTT);
        okButton.addActionListener(new NewCard.OkButton());
        setTitle("New card");
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(TRAILING)
                        .addComponent(nameLabel)
                        .addComponent(surnameLabel)
                        .addComponent(newPinLabel)
                        .addComponent(confirmPinLabel)
                        .addComponent(amountLabel))
                .addGroup(gl.createParallelGroup()
                        .addComponent(nameField)
                        .addComponent(surnameField)
                        .addComponent(pinField)
                        .addComponent(confirmPinField)
                        .addComponent(amountField))
                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addComponent(cancelButton)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(surnameLabel)
                        .addComponent(surnameField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(newPinLabel)
                        .addComponent(pinField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(confirmPinLabel)
                        .addComponent(confirmPinField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(amountLabel)
                        .addComponent(amountField))
                .addPreferredGap(RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl.createParallelGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
        );

        gl.linkSize(SwingConstants.HORIZONTAL, okButton, cancelButton);

        pack();
        
    }

    private void pay() {
        setTitle("Pay");
        makeCardPinAmountPanel(panels.PAY);
    }

    private void recharge() {
        setTitle("Recharge card");
        makeCardPinAmountPanel(panels.RECHARGE);
    }

    private void changePin() {
        GroupLayout gl = makeGL();
        resetFields();
        JButton okButton = new JButton("OK");
        okButton.setToolTipText(OKTT);
        okButton.addActionListener(new ChangePin.OkButton());

        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(TRAILING)
                        .addComponent(cardNumberLabel)
                        .addComponent(pinLabel)
                        .addComponent(newPinLabel)
                        .addComponent(confirmPinLabel))
                .addGroup(gl.createParallelGroup()
                        .addComponent(cardNumberField)
                        .addComponent(pinField)
                        .addComponent(newPinField)
                        .addComponent(confirmPinField))
                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(okButton)
                .addComponent(cancelButton)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(cardNumberLabel)
                        .addComponent(cardNumberField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(pinLabel)
                        .addComponent(pinField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(newPinLabel)
                        .addComponent(newPinField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(confirmPinLabel)
                        .addComponent(confirmPinField))
                .addPreferredGap(RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl.createParallelGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
        );
        pack();

    }

    private void checkBalance(){
        setTitle("Consult card balance");
        makeCardPinPanel(panels.CHECKBALANCE);
    }

    private void checkMovements(){
        setTitle("Consult card movements");
        makeCardPinPanel(panels.CHECKMOVES);
    }

    private void makeCardPinPanel(panels pane) {
        GroupLayout gl = makeGL();

        resetFields();

        JButton okButton = new JButton("OK");
        okButton.setToolTipText(OKTT);
        switch (pane){
            case CHECKBALANCE:
                okButton.addActionListener(new ConsultBalance.OkButton());
                break;
            case CHECKMOVES:
                okButton.addActionListener(new ConsultMovements.OkButton());
                break;
            default:
                okButton.addActionListener(new MainMenu.CancelButton());
        }

        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(TRAILING)
                        .addComponent(cardNumberLabel)
                        .addComponent(pinLabel))
                .addGroup(gl.createParallelGroup()
                        .addComponent(cardNumberField)
                        .addComponent(pinField))
                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(okButton)
                .addComponent(cancelButton)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(cardNumberLabel)
                        .addComponent(cardNumberField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(pinLabel)
                        .addComponent(pinField))
                .addPreferredGap(RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl.createParallelGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
        );

        pack();
    }

    private void makeCardPinAmountPanel(panels pane) {
        GroupLayout gl = makeGL();

        resetFields();

        JButton okButton = new JButton("OK");
        okButton.setToolTipText(OKTT);
        switch (pane){
            case PAY:
                okButton.addActionListener(new Pay.OkButton());
                break;
            case RECHARGE:
                okButton.addActionListener(new Recharge.OkButton());
                break;
            default:
                okButton.addActionListener(new MainMenu.CancelButton());
        }

        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(TRAILING)
                        .addComponent(cardNumberLabel)
                        .addComponent(pinLabel)
                        .addComponent(amountLabel))
                .addGroup(gl.createParallelGroup()
                        .addComponent(cardNumberField)
                        .addComponent(pinField)
                        .addComponent(amountField))
                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(okButton)
                .addComponent(cancelButton)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(cardNumberLabel)
                        .addComponent(cardNumberField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(pinLabel)
                        .addComponent(pinField))
                .addGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(amountLabel)
                        .addComponent(amountField))
                .addPreferredGap(RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl.createParallelGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
        );

        pack();
    }

    private void ticket(){
        GroupLayout gl = makeGL();

        JLabel ticketLabel = new JLabel(ticket);
        ticketLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        ticketLabel.setForeground(new Color(50, 50, 25));

        JButton okButton = new JButton("OK");
        okButton.setToolTipText("Finish current operation and go back to main menu");
        okButton.addActionListener(new Ticket.OkButton());

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(ticketLabel)
                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(okButton)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(BASELINE)
                .addComponent(ticketLabel))
                .addPreferredGap(RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl.createParallelGroup()
                        .addComponent(okButton))
        );
        pack();
    }

    public void setTicket(String text){
        this.ticket = text;
    }

    private GroupLayout makeGL(){
        this.currentPanel = (JPanel) getContentPane();
        GroupLayout gl = new GroupLayout(currentPanel);
        currentPanel.setLayout(gl);
        return gl;
    }

    public void changePanel(panels panels) {
        getContentPane().removeAll();
        switch (panels){
            case NEWCARD:
                newCard();
                break;
            case PAY:
                pay();
                break;
            case RECHARGE:
                recharge();
                break;
            case CHANGEPIN:
                changePin();
                break;
            case CHECKBALANCE:
                checkBalance();
                break;
            case CHECKMOVES:
                checkMovements();
                break;
            case TICKET:
                ticket();
                break;
            case MAIN:
            default:
                mainMenu();
                break;
        }

        getContentPane().doLayout();
        update(getGraphics());
    }

    private void resetFields(){
        cardNumberField = new JTextField(10);
        pinField = new JPasswordField(4);
        amountField = new JTextField(10);
        newPinField = new JPasswordField(4);
        confirmPinField = new JPasswordField(4);
        nameField = new JTextField(10);
        surnameField = new JTextField(10);
    }

    public Map<formField, String> getFormData(){
        EnumMap<formField, String> map = new EnumMap<>(formField.class);
        if (nameField.getText() != null)  map.put(formField.NAME, nameField.getText());
        if (surnameField.getText() != null)  map.put(formField.SURNAME, surnameField.getText());
        if (cardNumberField.getText() != null)  map.put(formField.CARDNUMBER, cardNumberField.getText());
        if (amountField.getText() != null)  map.put(formField.AMOUNT, amountField.getText());
        if (newPinField.getPassword() != null)  map.put(formField.NEWPIN, new String(newPinField.getPassword()));
        if (pinField.getPassword() != null)  map.put(formField.PIN, new String(pinField.getPassword()));
        if (confirmPinField.getPassword() != null)  map.put(formField.CONFIRMPIN, new String(confirmPinField.getPassword()));
        return map;
    }

}

