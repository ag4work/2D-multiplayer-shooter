package dto.mapper;

import dto.PlayerDTO;
import player.Player;

import java.util.LinkedList;
import java.util.List;

public class PlayerDTOMapper {
    public static PlayerDTO map(Player p) {
        return new PlayerDTO(p.getName(), p.getX(), p.getY(), p.isAlive());
    }

    public static List<PlayerDTO> map(List<Player> players) {
        List<PlayerDTO> dtos = new LinkedList<>();
        for (Player player : players) {
            dtos.add(map(player));
        }
        return dtos;
    }
}
