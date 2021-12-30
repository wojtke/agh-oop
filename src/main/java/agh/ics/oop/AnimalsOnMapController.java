package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AnimalsOnMapController {
    private final Map map;
    private final ArrayList<Animal> animals;
    private final Stats stats;
    private final int starting_energy, move_energy, plant_energy;

    private Set<Vector2d> eat_positions, reproduce_positions;

    public AnimalsOnMapController(
            Map map,
            ArrayList<Animal> animals,
            Stats stats,
            int starting_energy,
            int move_energy,
            int plant_energy
    ) {
        this.map = map;
        this.animals = animals;
        this.stats = stats;

        this.starting_energy = starting_energy;
        this.move_energy = move_energy;
        this.plant_energy = plant_energy;

        this.eat_positions = new HashSet<>();
        this.reproduce_positions = new HashSet<>();

    }

    private ArrayList<Animal> getAnimalsToEat(Vector2d position) {
        ArrayList<Animal> animals_at_pos = this.map.getAnimalsAt(position);

        if(animals_at_pos == null) return null;

        int max_energy = 0;
        for (Animal a: animals_at_pos) {
            max_energy = Math.max(a.getEnergy(), max_energy);
        }

        ArrayList<Animal> animals_to_eat = new ArrayList<>();
        for (Animal a: animals_at_pos) {
            if (a.getEnergy() == max_energy) {
                animals_to_eat.add(a);
            }
        }

        return animals_to_eat;
    }

    private Animal[] getAnimalsToReproduce(Vector2d position) {
        ArrayList<Animal> animals_at_pos = this.map.getAnimalsAt(position);

        if(animals_at_pos == null) return null;
        if(animals_at_pos.size() < 2) return null;

            Animal[] pair = new Animal[2];
            // 0 > 1
            pair[0] = animals_at_pos.get(0);
            pair[1] = animals_at_pos.get(1);
            if (pair[0].getEnergy() < pair[1].getEnergy()) {
                pair[0] = animals_at_pos.get(1);
                pair[1] = animals_at_pos.get(0);
            }

            for (Animal a : animals_at_pos.subList(2, animals_at_pos.size())) {
                if (a.getEnergy() > pair[0].getEnergy()) {
                    pair[1] = pair[0];
                    pair[0] = a;
                } else if (a.getEnergy() > pair[1].getEnergy()) {
                    pair[1] = a;
                }
            }

        return pair;
    }

    public void move() {
        for (Animal animal : animals) {
            Vector2d new_positon = map.moveInterpreter(animal.getNextMove());
            if (new_positon != null) {
                map.move(animal, new_positon);
                animal.move(new_positon);

                if (map.isPlantAt(new_positon)) {
                    eat_positions.add(new_positon);
                }
                if (map.getAnimalCountAt(new_positon) >= 2) {
                    reproduce_positions.add(new_positon);
                }
            }
        }

    }

    public void removeDead(int epoch) {
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                map.removeAnimal(animal, animal.getPosition());
                stats.updateOnDeath(animal.lifespan);
                animal.notifyTrackingObserverOnDeath(epoch);
            }
        }
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    public void eat() {
        for (Vector2d position : eat_positions) {
            ArrayList<Animal> animals_to_eat = getAnimalsToEat(position);
            if (animals_to_eat != null) {
                for (Animal animal : animals_to_eat) {
                    animal.setEnergy(animal.getEnergy() + plant_energy/animals_to_eat.size());
                    map.removePlant(position);
                }
            }

        }

        eat_positions.clear();
    }

    public void reproduce() {
        for (Vector2d position : reproduce_positions) {
            Animal[] animals_to_reproduce = getAnimalsToReproduce(position);
            if (animals_to_reproduce != null) {
                if (animals_to_reproduce[0].getEnergy() >= starting_energy/2 && animals_to_reproduce[1].getEnergy() >= starting_energy/2) {
                    int e = animals_to_reproduce[0].getEnergy() + animals_to_reproduce[1].getEnergy();
                    Animal new_animal = animals_to_reproduce[0].reproduce(animals_to_reproduce[1]);
                    map.putAnimal(new_animal, new_animal.getPosition());
                    animals.add(new_animal);

                    e -= animals_to_reproduce[0].getEnergy() + animals_to_reproduce[1].getEnergy();
                }
            }
        }

        reproduce_positions.clear();
    }

    public void anotherEpochSurvived() {
        for (Animal animal : animals) {
            animal.setEnergy(animal.getEnergy() - move_energy);
            animal.lifespan++;
            animal.notifyTrackingObserverNormal();
        }
    }

    public boolean magicInterventionCheck(boolean magic_intervention_enabled) {
        if (animals.size()==5 && magic_intervention_enabled) {
            for (Animal animal : animals) {
                Animal new_animal = new Animal(
                        map.getRandomUnoccupiedPosition("map"),
                        Direction.random(),
                        starting_energy,
                        animal.getGenom());
                map.putAnimal(new_animal, new_animal.getPosition());
            }
            return true;
        }
        return false;
    }

}
