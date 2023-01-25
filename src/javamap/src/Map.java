import java.util.ArrayList;
import java.util.stream.Collectors;

public class Map {
    ArrayList<ArrayList<Field>> rawMap;

    public Map(ArrayList<ArrayList<Field>> rawMap) {
        this.rawMap = rawMap;
    }

    public Map() {
        rawMap = new ArrayList<>();
    }

    public Map lens(int x, int y) {
        ArrayList<Field> line1 = rawMap.get(x-1);
        ArrayList<Field> line2 = rawMap.get(x-1);
        ArrayList<Field> line3 = rawMap.get(x-1);
        ArrayList<Field> newLine1 = new ArrayList<>();
        ArrayList<Field> newLine2 = new ArrayList<>();
        ArrayList<Field> newLine3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = y + i - 1;
            newLine1.add(i, line1.get(index));
            newLine2.add(i, line2.get(index));
            newLine3.add(i, line3.get(index));
        }
        ArrayList<ArrayList<Field>> newRawMap = new ArrayList<>();
        newRawMap.add(0, newLine1);
        newRawMap.add(1, newLine2);
        newRawMap.add(2, newLine3);
        return new Map(newRawMap);
    }

    public boolean isConnected() {
        return isConnectedWith(FieldType.PLAIN);
    }

    public boolean isConnectedWith(FieldType fieldType) {
        ArrayList<ArrayList<Field<Color>>> map = rawMap.stream()
                .map(arr -> arr.stream().map(f -> (Field<Color>)f.colorize(Color.Black))
                .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        boolean findFirst = false;
        for (int x = 0; x < map.size(); x++) {
            if (findFirst)
                break;
            for (int y = 0; y < map.get(x).size(); y++) {
                if (map.get(x).get(y).getFieldType() == fieldType) {
                    map.get(x).get(y).color = Color.Red;
                }
            }
        }

        boolean newColorized = true;
        while(newColorized) {
            newColorized = false;
            for (int x = 0; x < map.size(); x++) {
                for (int y = 0; y < map.get(x).size(); y++) {
                    Map lens = this.lens(x, y);
                }
            }
        }

        return false;
    }

    private enum Color {
        Red, Black;
    }
}
