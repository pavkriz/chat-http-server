package com.example;

import com.example.model.Message;
import com.example.model.Room;
import com.example.model.Token;
import com.example.repository.MessageRepository;
import com.example.repository.RoomRepository;
import com.example.repository.TokenRepository;
import com.example.services.UhkAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {
    RoomRepository roomRepository;
    MessageRepository messageRepository;
    TokenRepository tokenRepository;
    UhkAuthenticator uhkAuthenticator;

    public ChatController(RoomRepository roomRepository, MessageRepository messageRepository, TokenRepository tokenRepository, UhkAuthenticator uhkAuthenticator) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.tokenRepository = tokenRepository;
        this.uhkAuthenticator = uhkAuthenticator;
        // initialize DB here, a bit quick & dirty way
        if (roomRepository.count() == 0) {
            roomRepository.save(new Room("PRO1"));
            roomRepository.save(new Room("PRO2"));
            roomRepository.save(new Room("PRO3"));
            roomRepository.save(new Room("PPRO"));
            roomRepository.save(new Room("Hospoda"));
        }
    }

    @PostMapping("/login")
    @Transactional
    public String login(@RequestParam String username, @RequestParam String password) {
        // do actual authentication
        boolean authenticated = uhkAuthenticator.authenticate(username, password);
        if (authenticated) {
            // invalidate possible old tokens
            tokenRepository.deleteByUsername(username);
            // create new auth token
            Token t = new Token(username);
            t = tokenRepository.save(t);
            return t.getId().toString();
        } else {
            return "";
        }
    }

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam String token, @RequestParam int roomId) {
        Token t = validateAuthToken(token);
        return messageRepository.findForUserAndRoom(t.getUsername(), roomId);
    }

    @PostMapping("/message")
    public void sendMessage(@RequestParam String token, @RequestParam String to, @RequestParam String text, @RequestParam int roomId) {
        Token t = validateAuthToken(token);
        to = "".equals(to) ? null : to;  // replace empty string with null (=broadcast message with no particular recipient)
        Message m = new Message(text, t.getUsername(), to, LocalDateTime.now(), roomId);
        messageRepository.save(m);
    }

    private Token validateAuthToken(String token) {
        return tokenRepository.findById(UUID.fromString(token)).orElseThrow(() -> new SecurityException("Invalid token"));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> exceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getClass().getName(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    static class ErrorMessage {
        String error;
        String message;

        public ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
