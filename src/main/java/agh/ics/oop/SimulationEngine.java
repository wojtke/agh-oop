package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements Runnable{
    private final Map map;
    private final ArrayList<Animal> animals;
    private final ArrayList<IObserver> observers = new ArrayList<>();

    private final AnimalsOnMapController animalsOnMapController;
    private final Stats stats;
    private int simulationSpeed = 200;

    public SimulationEngine(
            int map_width,
            int map_height,
            int jungle_width,
            int jungle_height,
            int starting_animals,
            int starting_plants,
            int starting_energy,
            int move_energy,
            int plant_energy
    ) {

        this.map = new Map(
                map_width,
                map_height,
                jungle_width,
                jungle_height
        );

        this.animals = new ArrayList<>();

        for (int i = 0; i < starting_animals; i++) {
            Animal animal = new Animal(
                    map.getRandomUnoccupiedPosition("map"),
                    Direction.random(),
                    starting_energy,
                    new Genom()
            );

            map.putAnimal(animal, animal.getPosition());
            animals.add(animal);
        }
        for (int i = 0; i < starting_plants/2; i++) {
            this.map.growGrass();
        }

        this.animalsOnMapController = new AnimalsOnMapController(this.map, this.animals, starting_energy, move_energy, plant_energy);
        this.stats = new Stats(map, animals);

    }

    public void run(){
        try {
            int epoch = 0;
            while (true) {

                animalsOnMapController.move();
                animalsOnMapController.eat();
                animalsOnMapController.reproduce();
                animalsOnMapController.removeDead();

                map.growGrass();

                stats.update(epoch);
                epoch++;

                updateObservers();

                Thread.sleep(this.simulationSpeed);
            }

        }  catch (InterruptedException e) {
            System.out.println("Simulation interrupted: " + e.getMessage());
        }
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    private void updateObservers() {
        for (IObserver observer: this.observers) {
            observer.update();
        }
    }

    public Map getMap() {
        return this.map;
    }

    public Stats getStats() {
        return this.stats;
    }

    public void setSimulationSpeed(int speed) {
        this.simulationSpeed = speed;
    }

}
