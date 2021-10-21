package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }

    public static void run(Direction[] dirs) {
        System.out.print("Zwierzak sobie biegnie ");

        for(Direction dir : dirs) {
            String message = switch (dir) {
                case FORWARD -> "do przodu";
                case BACKWARD -> "do tyłu";
                case RIGHT -> "w prawo";
                case LEFT -> "w lewo";
            };

            System.out.print(message + ", ");
        }

        System.out.print("zatrzymuje się. ");
    }
    public static Direction[] args_to_enum(String[] args){
        Direction[] dirList;
        dirList =  new Direction[args.length];

        for(int i=0; i <args.length; i++) {
            dirList[i] = switch (args[i]) {
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "r" -> Direction.RIGHT;
                case "l" -> Direction.LEFT;
                default -> null;
            };
        }
        return dirList;
    }
}
