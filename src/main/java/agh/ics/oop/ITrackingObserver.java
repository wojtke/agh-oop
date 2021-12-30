package agh.ics.oop;

public interface ITrackingObserver {
    void updateOnReproduce(Animal animal, Animal child);
    void updateOnDeath(Animal animal, int epoch);
    void updateNormal(Animal animal);
    boolean isTracked(Animal animal);
}
