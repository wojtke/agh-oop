package agh.ics.oop;

public class Animal {
    private MapDirection ori;
    private Vector2d pos;

    public Animal(){
        this.ori = MapDirection.NORTH;
        this.pos = new Vector2d(2, 2);
    }

    public MapDirection getOri() {
        return ori;
    }

    public Vector2d getPos() {
        return pos;
    }

    public String toString(){
        return this.ori.toString() + ", " + this.pos;
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case LEFT -> this.ori = this.ori.previous();
            case RIGHT -> this.ori = this.ori.next();
            case FORWARD -> {
                Vector2d new_pos = this.pos.add(this.ori.toUnitVector());
                if (new_pos.precedes(new Vector2d(4, 4)) && new_pos.follows(new Vector2d(0, 0))) {
                    this.pos = new_pos;
                }
            }
            case BACKWARD -> {
                Vector2d new_pos = this.pos.subtract(this.ori.toUnitVector());
                if (new_pos.precedes(new Vector2d(4, 4)) && new_pos.follows(new Vector2d(0, 0))) {
                    this.pos = new_pos;
                }
            }
        }
    }
}
