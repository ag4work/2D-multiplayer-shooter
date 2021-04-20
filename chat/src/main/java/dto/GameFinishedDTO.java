package dto;

import enums.MessageType;

import java.util.List;


public class GameFinishedDTO extends BaseDTO {
    String winnerName;
    public GameFinishedDTO(String winnerName) {
        super(MessageType.GameFinished);
        this.winnerName = winnerName;
    }
}
