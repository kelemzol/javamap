import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

public class Panel {

    /** Panel represents a (for now) 3x3 smaller piece of map that is used to construct a larger map */

    ArrayList<ArrayList<Field>> rawMap;
    ArrayList<ArrayList<Field>> linePermutations = new ArrayList<>();

    public Panel(ArrayList<ArrayList<Field>> rawMap) {
        this.rawMap = rawMap;
    }

    public static LinkedHashSet<Panel> panelDFS() throws Exception {
        LinkedHashSet<Panel> panelStack = new LinkedHashSet<>();
        LinkedHashSet<Panel> settledPanelStack = new LinkedHashSet<>();

        panelStack.add(generateRootPanel(3,3));

        while(!panelStack.isEmpty()) {
            Panel currentPanel = panelStack.stream().findFirst().orElseThrow(Exception::new);
            if(!settledPanelStack.contains(currentPanel)) {
                settledPanelStack.add(currentPanel);
                panelStack.addAll(expand(currentPanel));
            }
            panelStack.remove(currentPanel);
        }
        return settledPanelStack;
    }

    private static LinkedHashSet<Panel> expand(Panel currentPanel) {
        LinkedHashSet<Panel> retSet = new LinkedHashSet<>();
        for (int i = 0; i < currentPanel.rawMap.size(); i++) {
            retSet.add(new Panel(expandLine(currentPanel.getLineOfPanel(i))));
        }
        // This is a set of Panels, each containing X lines that make up a map that
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

    public static Panel mergePanels(Panel panelToMergeInto, Panel... panels) {
        for (Panel panel : panels) {
            // For each panel in the list of panels to add
            for (int i = 0; i < panel.rawMap.size(); i++) {
                // add each of them to the corresponding row in the panel which we merge into
                panelToMergeInto.getLineOfPanel(i).addAll(panel.getLineOfPanel(i));
            }
        }
        return panelToMergeInto;
    }

    public static void print(Panel panel) {
        for (int i = 0; i < panel.rawMap.size(); i++) {
            panel.getLineOfPanel(i).stream().forEach(field -> System.out.print(field.getFieldType().getPrintableCharacter()));
            // Print to break line
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panel panel = (Panel) o;
        return Objects.equals(rawMap, panel.rawMap) && Objects.equals(linePermutations, panel.linePermutations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMap, linePermutations);
    }
}
