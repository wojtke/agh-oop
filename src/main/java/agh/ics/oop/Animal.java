package agh.ics.oop;

import java.util.ArrayList;

public class Animal implements IMapElement{
    private MapDirection ori;
    private Vector2d pos;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observers;


    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.ori = MapDirection.NORTH;
        this.pos = initialPosition;
        this.observers = new ArrayList<>();
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
                    this.positionChanged(this.pos, new_pos);
                    this.pos = new_pos;
                }
            }
            case BACKWARD -> {
                Vector2d new_pos = this.pos.subtract(this.ori.toUnitVector());
                if (this.map.canMoveTo(new_pos)){
                    this.positionChanged(this.pos, new_pos);
                    this.pos = new_pos;
                }
            }
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

}
