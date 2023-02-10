public class Field<ColorType> {
    FieldType fieldType;
    Double probability;
    ColorType color;

    public Field(FieldType fieldType, Double probability, ColorType color) {
        this.fieldType = fieldType;
        this.probability = probability;
        this.color = color;
    }

    public <NewColorType> Field<NewColorType> colorize(NewColorType newColor) {
        return new Field<NewColorType>(fieldType, probability, newColor);
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
