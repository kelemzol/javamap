import java.util.ArrayList;
import java.util.Random;
public class StepEngine implements StepEngineInterface {
    @Override
    public Map step(Map map) {
        StringBuilder stringBuilder = new StringBuilder();
        Map newMap = new Map(map);
        Random random = new Random();
        int x, y;
        Field field;
        do {
            x = random.nextInt(newMap.rawMap.size());
            y = random.nextInt(newMap.rawMap.get(x).size());
            field = newMap.rawMap.get(x).get(y);
            stringBuilder.append("x:" + x + "y:" + y + "t:" + field.fieldType.getPrintableCharacter() + ";");
        } while (field.getFieldType() == FieldType.WALL);
        ArrayList<Field> lens = newMap.lens(x, y);
        Double sumP = lens.stream().map(Field::getProbability).mapToDouble(Double::doubleValue).sum();
        stringBuilder.append("sumP:" + sumP + ";");
        boolean turn = sumP > random.nextDouble();
        stringBuilder.append("turn:" + turn + ";");
        if (turn) {
            field.fieldType = FieldType.WALL;
            field.probability = newMap.maxWallProbability * random.nextDouble();
        }
//        System.out.println(stringBuilder.toString());
        return newMap;
    }
}
