package dto;

import enums.MessageType;

public class BaseDTO {
    private MessageType messageType;
    public BaseDTO(MessageType messageType) {
        this.messageType = messageType;
    }
}
