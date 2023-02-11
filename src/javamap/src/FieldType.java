public enum FieldType {
    WALL("#"),
    PLAIN(" ");

    private String printableCharacter;

    FieldType(String printableCharacter) {
        this.printableCharacter = printableCharacter;
    }

    String getPrintableCharacter() {
        return printableCharacter;
    }
}
