package dto;

import enums.MessageType;

import java.util.List;

public class MessageDTO {
    private MessageType messageType;
    List<PlayerDTO> playerDTOs;

    public MessageDTO(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setPlayerDTOs(List<PlayerDTO> playerDTOs) {
        this.playerDTOs = playerDTOs;
    }
}
