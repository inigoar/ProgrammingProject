package motor.bloque.handlers;

import motor.bloque.entities.PrepayCard;
import motor.bloque.exceptions.IncorrectPin;
import motor.bloque.exceptions.NoSuchCard;
import motor.bloque.interfaces.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Persistence {

    private static final Logger logger = LogManager.getLogger(Persistence.class);
    private static final String DATAFILE = "data.json";

    private static Map<String, Card> cards = new HashMap<>();

    public static Card getCard(String cardNumber, String pin) throws NoSuchCard, IncorrectPin {
        if (cards.containsKey(cardNumber)) {
            if (Credentials.validatePassword(pin, cardNumber)) return cards.get(cardNumber);
            throw new IncorrectPin();
        }
        throw new NoSuchCard(cardNumber);
    }

    public static void putCard(Card card) {
        cards.put(card.getNumber(), card);
    }

    static boolean existsCard(String cardNumber) {
        return cards.containsKey(cardNumber);
    }

    static void loadPersistence() {
        File tmpDir = new File(DATAFILE);
        if (!tmpDir.exists()) return;
        String result = "";
        cards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATAFILE))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            logger.error(e);
        }

        JSONTokener tokener = new JSONTokener(result);
        JSONObject data = new JSONObject(tokener);
        Iterator<String> it = data.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (data.get(key) instanceof JSONObject) {
                JSONObject cardJSON = (JSONObject) data.get(key);
                Card card = new PrepayCard();
                card.setName(cardJSON.getString("name"));
                card.setNumber(cardJSON.getString("number"));
                card.setHashedPIN(cardJSON.getString("hashedPIN"));
                card.setSalt(cardJSON.getString("salt"));
                card.setBalance(cardJSON.getInt("balance"));
                card.setExpirationDate(LocalDateTime.parse(cardJSON.getString("Expiration Date")));

                cards.put(card.getNumber(), card);
                logger.info("Card " + card.getNumber() + " recovered");
            }
        }

    }

    public static void saveAll() {
        JSONObject data = new JSONObject();
        for (Map.Entry entry : cards.entrySet()) {
            Card card = (Card) entry.getValue();
            JSONObject cardDetails = new JSONObject();
            cardDetails.put("name", card.getName());
            cardDetails.put("number", card.getNumber());
            cardDetails.put("hashedPIN", card.getHashedPIN());
            cardDetails.put("salt", card.getSalt());
            cardDetails.put("balance", card.getBalance());
            cardDetails.put("Expiration Date", card.getExpirationDate().toString());

            data.put(card.getNumber(), cardDetails);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DATAFILE))) {
            data.write(writer, 4, 0);
            writer.write("\n");
            logger.info("Card file updated successfully");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private Persistence() {
        throw new IllegalStateException("Utility class");
    }

    static void requestCredentials(String cardNumber) {
        Card card = cards.get(cardNumber);
        Credentials.setPin(card.getHashedPIN());
        Credentials.setSalt(card.getSalt());
    }
}
