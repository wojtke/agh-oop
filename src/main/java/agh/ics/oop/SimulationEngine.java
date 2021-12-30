package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements Runnable{
    private final Map map;
    private final ArrayList<IObserver> observers = new ArrayList<>();

    private final AnimalsOnMapController animalsOnMapController;
    private final Stats stats;
    private int simulationSpeed = 250;
    private boolean isPaused = false;
    private int magicInterventionsLeft = 0;

    public SimulationEngine(
            int map_width,
            int map_height,
            int jungle_width,
            int jungle_height,
            int starting_animals,
            int starting_plants,
            int starting_energy,
            int move_energy,
            int plant_energy,
            boolean isWrappy,
            boolean isMagic
    ) {

        if (isWrappy) {
            this.map = new MapWrappy(map_width, map_height, jungle_width, jungle_height);
        } else{
            this.map = new Map(map_width, map_height, jungle_width, jungle_height);
        }

        if (isMagic) {
            this.magicInterventionsLeft = 3;
        }

        ArrayList<Animal> animals = new ArrayList<>();

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

        this.stats = new Stats(map, animals);
        this.animalsOnMapController = new AnimalsOnMapController(this.map, animals, stats, starting_energy, move_energy, plant_energy);
    }

    public void run(){
        try {
            int epoch = 0;
            while (true) {

                animalsOnMapController.move();
                animalsOnMapController.eat();
                animalsOnMapController.reproduce();
                animalsOnMapController.anotherEpochSurvived();
                animalsOnMapController.removeDead(epoch);
                if (animalsOnMapController.magicIntervention(magicInterventionsLeft>0)) {
                    magicInterventionsLeft--;
                }

                map.growGrass();

                stats.update(epoch);
                epoch++;

                updateObservers(epoch);

                Thread.sleep(this.simulationSpeed);

                while (isPaused) {
                    Thread.sleep(100);
                }
            }

        }  catch (InterruptedException e) {
            System.out.println("Simulation interrupted: " + e.getMessage());
        }
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    private void updateObservers(int epoch) {
        for (IObserver observer: this.observers) {
            observer.update(epoch);
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

    public boolean pauseSimulation(boolean isPaused) {
        this.isPaused = isPaused;
        return isPaused;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

}
