import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map {
    ArrayList<ArrayList<Field>> rawMap;
    Double maxPlainProbability;
    Double maxWallProbability;

    public Map(ArrayList<ArrayList<Field>> rawMap) {
        this.rawMap = rawMap;
    }


    public Map(Map map) {
        rawMap = new ArrayList<>();
        map.rawMap.forEach(l -> {
            ArrayList<Field> line = new ArrayList<>();
            rawMap.add(line);
            l.forEach(f -> line.add(new Field(f)));
        });
        this.maxPlainProbability = map.maxPlainProbability;
        this.maxWallProbability = map.maxWallProbability;
    }

    public Map(Integer width, Integer height, Double maxPlainProbability, Double maxWallProbability, Integer numberOfStartWalls) {
        this.maxPlainProbability = maxPlainProbability;
        this.maxWallProbability = maxWallProbability;
        Random random = new Random();
        rawMap = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            ArrayList<Field> line = new ArrayList();
            rawMap.add(line);
            for (int y = 0; y < height; y++) {
                Field field = new Field<>(FieldType.PLAIN, maxPlainProbability * random.nextDouble(), Color.BLACK);
                line.add(field);
//                rawMap.add(Stream.of(new Field<>(FieldType.PLAIN, maxPlainProbability * random.nextDouble(), Color.BLACK)).
//                        collect(Collectors.toCollection(ArrayList::new)));
            }
        }

        for(int i = 0; i < numberOfStartWalls; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Field field = rawMap.get(x).get(y);
            field.fieldType = FieldType.WALL;
            field.probability = maxWallProbability * random.nextDouble();
        }
    }

    public List<List<String>> printableStructure() {
        return rawMap.stream().map(l -> l.stream().map(Field::getFieldType).map(FieldType::getPrintableCharacter).toList()).toList();
    }

    public ArrayList<Field> lens(int x, int y) {
        ArrayList<Field> array = new ArrayList<>();
        for (int x_ = x-1; x_ <= x+1; x_++) {
            for (int y_ = y-1; y_ <= y+1; y_++) {
                if (x_ > 0 && x_ < rawMap.size()) {
                    if (y_ > 0 && y_ < rawMap.get(x_).size()) {
                        array.add(rawMap.get(x_).get(y_));
                    }
                }
            }
        }
        return array;
    }

    public static <T> ArrayList<Field<T>> lens(ArrayList<ArrayList<Field<T>>> map, int x, int y) {
        ArrayList<Field<T>> array = new ArrayList<>();
        for (int x_ = x-1; x_ <= x+1; x_++) {
            for (int y_ = y-1; y_ <= y+1; y_++) {
                if (x_ >= 0 && x_ < map.size()) {
                    if (y_ >= 0 && y_ < map.get(x_).size()) {
                        array.add(map.get(x_).get(y_));
                    }
                }
            }
        }
        return array;
    }

    public static <T> ArrayList<Field<T>> neighbours(ArrayList<ArrayList<Field<T>>> map, int x, int y) {
        ArrayList<Field<T>> array = new ArrayList<>();
        for (int x_ = x-1; x_ <= x+1; x_++) {
            for (int y_ = y-1; y_ <= y+1; y_++) {
                if (x_ >= 0 && x_ < map.size()) {
                    if (y_ >= 0 && y_ < map.get(x_).size()) {
                        if (x_ == x || y_ == y) {
                            array.add(map.get(x_).get(y_));
                        }
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
                    break;
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
                            neighbours(map, x, y).stream().anyMatch(n -> n.color == Color.RED)) {
                        current.color = Color.RED;
                        newColorized = true;
                    }
                }
            }
        }

        return !map.stream().flatMap(ArrayList::stream).anyMatch(f -> f.fieldType == FieldType.PLAIN && f.color == Color.BLACK);
    }

    protected enum Color {
        RED, BLACK;
    }
}
