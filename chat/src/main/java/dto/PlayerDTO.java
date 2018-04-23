package dto;

public class PlayerDTO {
    private String name;
    private int x;
    public PlayerDTO(String name, int x) {
        this.name = name;
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }
}
