package dto;

import enums.MessageType;

import java.util.List;

public class StateDTO extends BaseDTO {
    List<PlayerDTO> playerDTOs;

    public StateDTO(List<PlayerDTO> playerDTOs) {
        super(MessageType.GameState);
        this.playerDTOs = playerDTOs;
    }

}
