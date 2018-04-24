package dto;

import enums.MessageType;

public class WaitGameDTO extends BaseDTO {
    public WaitGameDTO() {
        super(MessageType.NotAllPlayers);
    }
}
