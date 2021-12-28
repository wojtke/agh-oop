package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable {
    private final IWorldMap map;
    private MoveDirection[] moves = {};
    private final ArrayList<Animal> animals;
    private final ArrayList<IGenericObserver> observers = new ArrayList<>();

    public SimulationEngine(IWorldMap map, Vector2d[] startingPositions){
        this.map = map;
        this.animals = new ArrayList<>();

        for (Vector2d pos: startingPositions){
            Animal animal = new Animal(map, pos);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] startingPositions){
        this(map, startingPositions);
        this.moves = moves;
    }

    public void setMoves(MoveDirection[] moves) {
        this.moves = moves;
    }

    public void addObserver(IGenericObserver observer) {
        this.observers.add(observer);
    }

    private void updateObservers() {
        for (IGenericObserver observer: this.observers) {
            observer.update();
        }
    }

    public void run(){
        try {
            for (int i=0; i<moves.length; i++) {
                animals.get(i % animals.size()).move(moves[i]);
                updateObservers();
                Thread.sleep(300);
            }

        }  catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}
