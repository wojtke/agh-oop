package agh.ics.oop;

public class Animal implements IMapElement{
    private MapDirection ori;
    private Vector2d pos;
    private final IWorldMap map;

    public Animal(){
        this.ori = MapDirection.NORTH;
        this.pos = new Vector2d(2,2);
        this.map = new RectangularMap(5, 5);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.ori = MapDirection.NORTH;
        this.pos = initialPosition;
    }

    public MapDirection getOrientation() { return ori; }

    @Override
    public Vector2d getPosition() { return pos; }

    @Override
    public String toString() {
        return switch (this.ori) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case LEFT -> this.ori = this.ori.previous();
            case RIGHT -> this.ori = this.ori.next();
            case FORWARD -> {
                Vector2d new_pos = this.pos.add(this.ori.toUnitVector());
                if (this.map.canMoveTo(new_pos)){
                    this.pos = new_pos;
                }
            }
            case BACKWARD -> {
                Vector2d new_pos = this.pos.subtract(this.ori.toUnitVector());
                if (this.map.canMoveTo(new_pos)){
                    this.pos = new_pos;
                }
            }
        }
    }
}
