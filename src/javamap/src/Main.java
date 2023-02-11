import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Javamap!");
        Map map = new Map(10,30,0.005, 0.25, 3);
        StepEngine stepEngine = new StepEngine();
        System.out.println(flat(map.printableStructure()));
        for (int i = 0; i < 300; i++) {
            map = stepEngine.step(map);
        }
//        Map nextMap = map;
//        while (nextMap.isConnected()) {
//            map = nextMap;
//            nextMap = stepEngine.step(map);
//        }
        System.out.println(flat(map.printableStructure()));
    }

    static String flat(List<List<String>> structure) {
        StringBuilder stringBuilder = new StringBuilder();
        structure.forEach(l -> {
            l.forEach(f -> stringBuilder.append(f));
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }
}