public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Javamap!");
        Map map = new Map(10,10,0.05, 0.2, 3);
        map.lens(1,1); // Out of bounds at line Maps:37
    }
}