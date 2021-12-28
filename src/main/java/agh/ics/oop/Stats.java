package agh.ics.oop;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Stats {
    private final Map map;
    private final ArrayList<Animal> animals;

    public int animal_count;
    public int animal_avg_energy;
    public int grass_count;

    //TODO encapsulate this shit
    private final XYChart.Series<Number, Number> animal_count_series;
    private final XYChart.Series<Number, Number> grass_count_series;
    private final XYChart.Series<Number, Number> animal_avg_energy_series;


    public Stats(Map map, ArrayList<Animal> animals) {
        this.map = map;
        this.animals = animals;

        this.animal_count_series = new XYChart.Series<>();
        this.animal_count_series.setName("Animal count");

        this.grass_count_series = new XYChart.Series<>();
        this.grass_count_series.setName("Grass count");

        this.animal_avg_energy_series = new XYChart.Series<>();
        this.animal_avg_energy_series.setName("Animal avg energy");

    }

    public void update(int epoch) {
        //animal count
        this.animal_count = animals.size();
        this.animal_count_series.getData().add(new XYChart.Data<>(epoch, this.animal_count));

        //animal avg energy
        animal_avg_energy = 0;
        for (Animal animal : animals) {
            animal_avg_energy += animal.getEnergy();
        }
        if (animal_count> 0) {
            this.animal_avg_energy /= (double) animal_count;
        } else {
            this.animal_avg_energy = 0;
        }
        this.animal_avg_energy_series.getData().add(new XYChart.Data<>(epoch, this.animal_avg_energy));

        //grass count
        this.grass_count = map.getGrassCount();
        this.grass_count_series.getData().add(new XYChart.Data<>(epoch, this.grass_count));
    }

    public XYChart.Series<Number, Number> getAnimalCountSeries(){
        return this.animal_count_series;
    }
    public XYChart.Series<Number, Number> getGrassCountSeries(){
        return this.grass_count_series;
    }
    public XYChart.Series<Number, Number> getAnimalAvgEnergySeries(){
        return this.animal_avg_energy_series;
    }

}
