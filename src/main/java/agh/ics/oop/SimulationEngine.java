package agh.ics.oop;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] moves;
    private final Animal[] animals;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] startingPositions){
        this.map = map;
        this.moves = moves;
        this.animals = new Animal[startingPositions.length];


        for (int i=0; i<startingPositions.length; i++){
            animals[i] = new Animal(map, startingPositions[i]);
        }
    }

    public void run(){
        for (int i=0; i<moves.length; i++) {
            animals[i % animals.length].move(moves[i]);
        }
    }
    public void runPrintSteps(){
        for (int i=0; i<moves.length; i++){
            animals[i%animals.length].move(moves[i]);
            System.out.println("Animal: " + i%animals.length + ", move: " + moves[i]);
            System.out.println(map);
        }
    }
}
