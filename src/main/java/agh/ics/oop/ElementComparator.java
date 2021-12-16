package agh.ics.oop;

import java.util.Comparator;

public record ElementComparator(char axis) implements Comparator<IMapElement> {

    @Override
    public int compare(IMapElement o1, IMapElement o2) {
        Vector2d v1 = o1.getPosition();
        Vector2d v2 = o2.getPosition();
        switch (axis) {
            case 'x':
                if (v1.x - v2.x != 0) return v1.x - v2.x;
                else if (v1.y - v2.y != 0) return v1.y - v2.y;
                break;
            case 'y':
                if (v1.y - v2.y != 0) return v1.y - v2.y;
                else if (v1.x - v2.x != 0) return v1.x - v2.x;
                break;
        }

        if (o1 instanceof Animal && o2 instanceof Grass) return 1;
        return 0;
    }
}
