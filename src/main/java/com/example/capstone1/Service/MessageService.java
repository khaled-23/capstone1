package com.example.capstone1.Service;

import com.example.capstone1.Model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class MessageService {
    ArrayList<Message> messages = new ArrayList<>();

    public void sendMessage(Message message){
        message.setDateTime(LocalDateTime.now());
        messages.add(message);
    }

    public ArrayList<Message> getMessages(String receiverId, String senderId) {
        ArrayList<Message> receiverMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getReceiverId().equalsIgnoreCase(receiverId)
                    && message.getSenderId().equalsIgnoreCase(senderId)) {
                receiverMessages.add(message);
            }
        }
        return receiverMessages;
    }
}
