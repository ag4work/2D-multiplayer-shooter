package dto;

import enums.MessageType;

public class GameStartedDTO extends BaseDTO {
    private String playerName;
    public GameStartedDTO(String playerName) {
        super(MessageType.GameStarted);
        this.playerName = playerName;
    }
}
