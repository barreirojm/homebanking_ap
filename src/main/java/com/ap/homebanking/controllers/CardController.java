package com.ap.homebanking.controllers;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.CardService;
import com.ap.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard (@RequestParam CardType type, @RequestParam CardColor color, Authentication auth) {

        Client client = clientService.getClientByAuth(auth);

        if (client == null || !client.getRole().equals(RoleType.CLIENT)) {
            return new ResponseEntity<>("Only authenticated clients can apply for cards.", HttpStatus.FORBIDDEN);
            }

        if (type == null) {
            return new ResponseEntity<>("Missing card type", HttpStatus.FORBIDDEN);
        }

        if (color == null) {
            return new ResponseEntity<>("Missing card color", HttpStatus.FORBIDDEN);
        }

        if (client.getCards().stream().filter(card -> card.getCardType() == type).count() >= 3) {
            return new ResponseEntity<>("You can only apply for 3 cards of the same type.", HttpStatus.FORBIDDEN);
            }

        if (client.getCards().stream().anyMatch(card -> card.getCardColor() == color && card.getCardType() == type)) {
            return new ResponseEntity<>("You can only have one card of each color.", HttpStatus.FORBIDDEN);
            }

        String cardHolder = (client.getFirstName()).concat(" ").concat(client.getLastName());

        String number = generateRandomCardNumber();

        if (cardService.getCardByNumber(number) != null) {

            number = generateRandomCardNumber();
        }

        int cvv = generateRandomCVV();

        LocalDateTime fromDate = LocalDateTime.now();

        LocalDateTime thruDate = LocalDateTime.now().plusYears(5);

        Card card = new Card(cardHolder, type, color, number, cvv, fromDate, thruDate);

        card.setCard_holder(client);

        cardService.saveCard(card);

        return new ResponseEntity<>(HttpStatus.CREATED);

        }

        private String generateRandomCardNumber() {
            Random rand = new Random();
            return String.format("%04d-%04d-%04d-%04d",
                    rand.nextInt(10000), rand.nextInt(10000),
                    rand.nextInt(10000), rand.nextInt(10000));
        }

        private int generateRandomCVV() {
            Random rand = new Random();
            return rand.nextInt(1000);
        }


}
