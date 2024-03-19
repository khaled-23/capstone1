package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Message {
    @NotEmpty(message = "senderId should not be empty")
    private String senderId;
    @NotEmpty(message = "receiver id should not be empty")
    private String receiverId;
    @NotEmpty(message = "message should not be empty")
    private String message;
    private LocalDateTime dateTime;

}
