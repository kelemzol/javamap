public class Field<ColorType> {
    FieldType fieldType;
    Double probability;
    ColorType color;

    Location location;

    public Field(FieldType fieldType, Double probability, Location location, ColorType color) {
        this.fieldType = fieldType;
        this.probability = probability;
        this.location = location;
        this.color = color;
    }

    public <NewColorType> Field<NewColorType> colorize(NewColorType newColor) {
        return new Field<NewColorType>(fieldType, probability, null, newColor);
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public Double getProbability() {
        return probability;
    }

    public ColorType getColor() {
        return color;
    }
}
