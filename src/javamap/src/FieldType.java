public enum FieldType {
    WALL("#"),
    PLAIN("0");

    private String printableCharacter;

    FieldType(String printableCharacter) {
        this.printableCharacter = printableCharacter;
    }

    String getPrintableCharacter() {
        return printableCharacter;
    }

}
