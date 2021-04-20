package dto;

import enums.MessageType;
import game.Bullet;

import java.util.List;

public class StateDTO extends BaseDTO {
    List<PlayerDTO> playerDTOs;
    List<Bullet> bullets;

    public StateDTO(List<PlayerDTO> playerDTOs, List<Bullet> bullets) {
        super(MessageType.GameState);
        this.playerDTOs = playerDTOs;
        this.bullets = bullets;
    }

}
