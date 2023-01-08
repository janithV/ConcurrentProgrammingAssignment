public enum TextColor {

    RED_TEXT("\u001B[31m"),

    GREEN_TEXT("\u001B[32m"),

    YELLOW_TEXT("\u001B[33m"),

    PURPLE_TEXT("\u001B[35m"),

    BLUE_TEXT("\u001B[34m"),
    RESET("\033[0m");

    private String color;

    private TextColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    @Override
    public String toString() {
        return color;
    }
}
