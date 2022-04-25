import java.awt.*;

public enum Colors {
    RED( "\u001B[31m", new Color(255,102,102)),
    WHITE("\u001B[37m", new Color(255,255,255)),
    YELLOW("\u001B[33m", new Color(255, 188, 73)),
    GREEN("\u001B[32m", new Color(25, 199, 138)),
    GRAY("", new Color(87, 87, 87)),
    BLACK("", new Color(34, 34, 34)),
    DARKBLACK("", new Color(0,0,0)),
    RESET("\u001B[0m", new Color (0, 0 ,0));

    private String colorString;
    private Color color;

    Colors(String colorString, Color color) {
        this.colorString = colorString;
        this.color = color;
    };

    public String getString() {
        return colorString;
    }
    public Color getColor() { return color; }
}