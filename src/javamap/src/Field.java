import java.util.Objects;

public class Field<ColorType> {
    FieldType fieldType;
    Double probability;
    ColorType color;

    public Field(FieldType fieldType, Double probability, ColorType color) {
        this.fieldType = fieldType;
        this.probability = probability;
        this.color = color;
    }

    public Field(Field<ColorType> field) {
        this(field.getFieldType(), field.getProbability(), field.getColor());
    }

    public <NewColorType> Field<NewColorType> colorize(NewColorType newColor) {
        return new Field<NewColorType>(fieldType, probability, newColor);
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Double getProbability() {
        return probability;
    }

    public ColorType getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field<?> field = (Field<?>) o;
        return fieldType == field.fieldType && Objects.equals(probability, field.probability) && Objects.equals(color, field.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, probability, color);
    }
}
