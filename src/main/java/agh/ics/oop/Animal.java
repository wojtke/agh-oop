package agh.ics.oop;

public class Animal implements IMapElement{
    private Vector2d position;
    private Direction direction;
    private int energy;
    private final Genom genom;

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

        return new Animal(this.position, Direction.random(), child_energy, child_genom);
    }

    public Vector2d getPosition(){
        return position;
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
}
