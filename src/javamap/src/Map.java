import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map {
    ArrayList<ArrayList<Field>> rawMap;

    public Map(ArrayList<ArrayList<Field>> rawMap) {
        this.rawMap = rawMap;
    }

    public Map() {
        rawMap = new ArrayList<>();
    }

    public Map(Integer length, Integer width, Double wallProbability) {
        rawMap = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                rawMap.add(Stream.of(new Field<>(FieldType.PLAIN,wallProbability,new Location(i, j),Color.BLACK)).
                        collect(Collectors.toCollection(ArrayList::new)));
            }
        }
    }

    public ArrayList<Field> lens(int x, int y) {
        ArrayList<Field> array = new ArrayList<>();
        for (int x_ = x-1; x_ <= x+1; x_++) {
            for (int y_ = x-1; y_ <= y+1; y_++) {
                if (x_ > 0 && x_ < rawMap.size()) {
                    if (y_ > 0 && y_ < rawMap.get(0).size()) {
                        array.add(rawMap.get(x_).get(y_));
                    }
                }
            }
        }
        return array;
    }

    public boolean isConnected() {
        return isConnectedWith(FieldType.PLAIN);
    }

    public boolean isConnectedWith(FieldType fieldType) {
        ArrayList<ArrayList<Field<Color>>> map = rawMap.stream()
                .map(arr -> arr.stream().map(f -> (Field<Color>)f.colorize(Color.BLACK))
                .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        boolean findFirst = false;
        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.get(x).size(); y++) {
                if (map.get(x).get(y).getFieldType() == fieldType) {
                    map.get(x).get(y).color = Color.RED;
                    findFirst = true;
                }
            }
            if (findFirst)
                break;
        }

        boolean newColorized = true;
        while(newColorized) {
            newColorized = false;
            for (int x = 0; x < map.size(); x++) {
                for (int y = 0; y < map.get(x).size(); y++) {
                    Field<Color> current = map.get(x).get(y);
                    if (current.color == Color.BLACK &&
                            current.getFieldType() == FieldType.PLAIN &&
                            this.lens(x, y).stream().anyMatch(n -> n.color == Color.RED)) {
                        current.color = Color.RED;
                        newColorized = true;
                    }
                }
            }
        }

        return !map.stream().flatMap(a ->a.stream()).anyMatch(f -> f.fieldType == FieldType.PLAIN && f.color == Color.BLACK);
    }

    private enum Color {
        RED, BLACK;
    }
}
