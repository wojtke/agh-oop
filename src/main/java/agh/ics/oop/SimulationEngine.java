package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] moves;
    private final ArrayList<Animal> animals;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] startingPositions){
        this.map = map;
        this.moves = moves;
        this.animals = new ArrayList<>();

        for (Vector2d pos: startingPositions){
            Animal animal = new Animal(map, pos);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    public void run(){
        for (int i=0; i<moves.length; i++) {
            animals.get(i % animals.size()).move(moves[i]);
        }
    }
}
