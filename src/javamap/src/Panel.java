import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Panel {

    /** Panel represents a (for now) 3x3 smaller piece of map that is used to construct a larger map */

    ArrayList<ArrayList<Field>> rawMap;
    ArrayList<ArrayList<Field>> linePermutations = new ArrayList<>();

    public Panel(ArrayList<ArrayList<Field>> rawMap) {
        this.rawMap = rawMap;
    }

    public static void panelDFS() throws Exception {
        LinkedHashSet<Panel> panelStack = new LinkedHashSet<>();
        LinkedHashSet<Panel> settledPanelStack = new LinkedHashSet<>();

        panelStack.add(generateRootPanel(3,3));

        while(!panelStack.isEmpty()) {
            Panel currentPanel = panelStack.stream().findFirst().orElseThrow(Exception::new);
            if(!settledPanelStack.contains(currentPanel)) {
                settledPanelStack.add(currentPanel);
                LinkedHashSet<Panel> newPanels = expand(currentPanel);
            }
        }
    }

    private static LinkedHashSet<Panel> expand(Panel currentPanel) {
        LinkedHashSet<Panel> retSet = new LinkedHashSet<>();
        for (int i = 0; i < currentPanel.rawMap.size(); i++) {
            retSet.add(new Panel(expandLine(currentPanel.getLineOfPanel(i))));
        }
        return retSet;
    }

    private static Panel generateRootPanel(Integer width, Integer height) {
        ArrayList<ArrayList<Field>> retPanel = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            ArrayList<Field> line = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    line.add(new Field(FieldType.PLAIN, 0.0005, Map.Color.BLACK));
                }
            retPanel.add(line);
        }
        return new Panel(retPanel);
    }

    /** Generate all possible other combinations of lines based on an input line */
    private static ArrayList<ArrayList<Field>> expandLine(ArrayList<Field> line) {
        ArrayList<ArrayList<Field>> retList = new ArrayList<>();

        // For each field
        for (int i = 0; i < line.size(); i++) {
            // For each type of field we can create
            for (FieldType type : FieldType.values()) {
                // If the field type is not the same, change the field type and add it to the return list
                if(line.get(i).getFieldType() != type) {
                    ArrayList<Field> retLine = new ArrayList<>();
                    // Copy everything over otherwise we switch the original fields
                    for (Field innerField : line) {
                        retLine.add(new Field(innerField.getFieldType(),innerField.getProbability(),innerField.getColor()));
                    }
                    retLine.get(i).setFieldType(type);
                    retList.add(retLine);
                }
            }
        }
        return retList;
    }

    public ArrayList<Field> getLineOfPanel(Integer i) {
        return rawMap.get(i);
    }
}
