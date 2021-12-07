package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new GrassField(5);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(2, 3)};

            IEngine engine = new SimulationEngine(directions, map, positions);
            System.out.println(map);
            engine.run();
            System.out.println(map);
        } catch (IllegalArgumentException e) {
            System.out.println("Something was wrong:\n" + e.getMessage());
        }
    }
}
