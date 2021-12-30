package agh.ics.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class Stats {
    private final Map map;
    private final ArrayList<Animal> animals;

    public Number current_animal_count;
    public Number current_animal_avg_energy;
    public Number current_grass_count;
    public Number current_animal_avg_children;
    public Number current_animal_avg_lifespan = 0;
    public Number[] current_gene_distribution;
    public Genom current_dominant_genom;

    private final ArrayList<Number>
            animal_count,
            grass_count,
            animal_avg_energy,
            animal_avg_lifespan,
            animal_avg_children;
    private final ArrayList<Number>[] genesDistribution;

    private int death_count;
    private int total_epochs_lived;

    public Stats(Map map, ArrayList<Animal> animals) {
        this.map = map;
        this.animals = animals;

        this.animal_count = new ArrayList<>();
        this.grass_count = new ArrayList<>();
        this.animal_avg_energy = new ArrayList<>();
        this.animal_avg_lifespan = new ArrayList<>();
        this.animal_avg_children = new ArrayList<>();
        this.genesDistribution = new ArrayList[8];
        for (int i = 0; i < 8; i++) {
            this.genesDistribution[i] = new ArrayList<>();
        }
    }

    public void update(int epoch) {

        updateCount();

        updateAvgEnergy();

        updateAvgChildren();

        updateAvgLifespan();

        updateGenesDistribution();

    }

    public ArrayList<Number> getAnimalCount(){
        return this.animal_count;
    }
    public ArrayList<Number> getGrassCount(){
        return this.grass_count;
    }
    public ArrayList<Number> getAnimalAvgEnergy(){
        return this.animal_avg_energy;
    }
    public ArrayList<Number> getAnimalAvgLifespan(){
        return this.animal_avg_lifespan;
    }
    public ArrayList<Number> getAnimalAvgChildren(){
        return this.animal_avg_children;
    }
    public ArrayList<Number>[] getGenesDistribution() {
        return this.genesDistribution;
    }

    public void updateOnDeath(int lifespan) {
        this.total_epochs_lived += lifespan;
        this.death_count++;

        this.current_animal_avg_lifespan = this.total_epochs_lived/(double)this.death_count;
    }

    private void updateAvgLifespan() {
        this.animal_avg_lifespan.add(this.current_animal_avg_lifespan);
    }

    private void updateCount() {
        //animal count
        current_animal_count = animals.size();
        this.animal_count.add(current_animal_count);

        //grass count
        current_grass_count = map.getGrassCount();
        this.grass_count.add(current_grass_count);
    }

    private void updateAvgEnergy() {
        int energy_sum = 0;
        for (Animal animal : animals) {
            energy_sum += animal.getEnergy();
        }
        if (current_animal_count.intValue() > 0) {
            current_animal_avg_energy = energy_sum/current_animal_count.doubleValue();
        } else {
            current_animal_avg_energy = 0;
        }
        this.animal_avg_energy.add(current_animal_avg_energy);
    }

    private void updateAvgChildren() {
        int children_sum = 0;
        for (Animal animal : animals) {
            children_sum += animal.getChildrenCount();
        }
        if (current_animal_count.intValue() > 0) {
            current_animal_avg_children = children_sum/current_animal_count.doubleValue();
        }
        this.animal_avg_children.add(current_animal_avg_children);
    }

    private void updateGenesDistribution() {
        int[] total_genes = new int[8];
        HashMap<Genom, Integer> genom_counter = new HashMap<>();
        for (Animal animal : animals) {
            for (int g: animal.getGenom().getGenes()) {
                total_genes[g]++;
            }

            if (genom_counter.containsKey(animal.getGenom())) {
                genom_counter.put(animal.getGenom(), genom_counter.get(animal.getGenom()) + 1);
            } else {
                genom_counter.put(animal.getGenom(), 1);
            }
        }

        this.current_gene_distribution = new Number[8];
        for (int i = 0; i < 8; i++) {
            this.current_gene_distribution[i] = total_genes[i]/current_animal_count.doubleValue();
            this.genesDistribution[i].add(this.current_gene_distribution[i]);
        }

        if (genom_counter.size() > 0) {
            this.current_dominant_genom = genom_counter.entrySet().stream().max(Comparator.comparingInt(java.util.Map.Entry::getValue)).get().getKey();
        } else {
            this.current_dominant_genom = null;
        }
    }

    public void saveCsv(String filename) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File("saved stats/" + filename));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            StringBuilder builder = new StringBuilder();
            String columnNamesList = "Epoch,AnimalCount,GrassCount,AnimalAvgEnergy,AnimalAvgLifespan,AnimalAvgChildren";

            builder.append(columnNamesList +"\n");
            for (int i = 0; i < this.animal_count.size(); i++) {
                builder.append(i + ","
                        + this.animal_count.get(i) + ","
                        + this.grass_count.get(i) + ","
                        + this.animal_avg_energy.get(i) + ","
                        + this.animal_avg_lifespan.get(i) + ","
                        + this.animal_avg_children.get(i) + "\n");
            }
            builder.append(this.animal_count.size() + ","
                    + this.animal_count.stream().mapToInt(Number::intValue).average().orElse(0) + ","
                    + this.grass_count.stream().mapToInt(Number::intValue).average().orElse(0) + ","
                    + this.animal_avg_energy.stream().mapToDouble(Number::doubleValue).average().orElse(0.0) + ","
                    + this.animal_avg_lifespan.stream().mapToDouble(Number::doubleValue).average().orElse(0.0) + ","
                    + this.animal_avg_children.stream().mapToDouble(Number::doubleValue).average().orElse(0.0) + ",");
            builder.append("\n");

            assert pw != null;
            pw.write(builder.toString());
            pw.close();
        }

}
