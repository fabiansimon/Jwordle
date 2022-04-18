public enum Colors {
    RED( "\u001B[31m"),
    WHITE("\u001B[37m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    RESET("\u001B[0m");

    private String colorString;

    Colors(String colorString) {
        this.colorString = colorString;
    };

    public String getColor() {
        return colorString;
    }
}