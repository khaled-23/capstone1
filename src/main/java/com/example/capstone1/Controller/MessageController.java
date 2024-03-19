package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Message;
import com.example.capstone1.Service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody @Valid Message message, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        messageService.sendMessage(message);
        return ResponseEntity.status(200).body(new ApiResponse("message sent"));
    }

    @GetMapping("/view/{receiverId}/{senderId}")
    public ResponseEntity viewMessage(@PathVariable String receiverId, @PathVariable String senderId){
        ArrayList<Message> messages = messageService.getMessages(receiverId, senderId);
        if(messages.isEmpty()){
            return ResponseEntity.status(400).body("no messages");
        }else return ResponseEntity.status(200).body(messages);
    }
}
