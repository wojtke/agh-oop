package agh.ics.oop;

import java.util.TreeMap;

public class Animal implements IMapElement{
    private Vector2d position;
    private Direction direction;
    private int energy;
    private final Genom genom;

    public int childrenCount=0;
    public int lifespan=0;

    private ITrackingObserver trackingObserver;

    public Animal(Vector2d position, Direction direction, int energy, Genom genom){
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        this.genom = genom;
    }

    public Vector2d getNextMove() {
        int move = genom.random();

        if(move == 0){
            return position.add(direction.toUnitVector());
        } else if(move == 4){
            return position.subtract(direction.toUnitVector());
        } else {
            this.direction = direction.rotate(move);
            return null;
        }
    }

    public void move(Vector2d new_position){
        this.position = new_position;
    }

    public Animal reproduce(Animal other){
        double energy_ratio = (double)this.energy/other.energy;
        Genom child_genom = this.genom.cross(other.genom, energy_ratio);

        int child_energy = ((int) ((double)this.energy*0.25)) + ((int) ((double)other.energy*0.25));
        this.energy -= (int) ((double)this.energy*0.25);
        other.energy -= (int) ((double)other.energy*0.25);

        this.childrenCount++;
        other.childrenCount++;

        Animal child =  new Animal(this.position, Direction.random(), child_energy, child_genom);

        notifyTrackingObserverOnReproduce(child);

        return child;
    }


    public Vector2d getPosition(){
        return position;
    }

    public Genom getGenom(){
        return genom;
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Direction getDirection() {
        return direction;
    }

    public String toString(){
        return "A";
    }

    public boolean isTracked() {
        if (trackingObserver != null) {
            return trackingObserver.isTracked(this);
        }
        return false;
    }

    public void addTrackingObserver(ITrackingObserver trackingObserver){
        this.trackingObserver = trackingObserver;
    }

    public void removeTrackingObserver(){
        this.trackingObserver = null;
    }

    public void notifyTrackingObserverOnReproduce(Animal child){
        if(trackingObserver != null){
            trackingObserver.updateOnReproduce(this, child);
        }
    }
    public void notifyTrackingObserverOnDeath(int epoch){
        if(trackingObserver != null){
            trackingObserver.updateOnDeath(this, epoch);
        }
    }
    public void notifyTrackingObserverNormal(){
        if(trackingObserver != null){
            trackingObserver.updateNormal(this);
        }
    }
}
