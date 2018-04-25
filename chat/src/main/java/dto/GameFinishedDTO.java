package dto;

import enums.MessageType;

import java.util.List;


public class GameFinishedDTO extends BaseDTO {
    public GameFinishedDTO() {
        super(MessageType.GameFinished);
    }
}
