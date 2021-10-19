package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("program wystartowal");

        run(args_to_enum(args));

        System.out.println("\nprogram zakonczyl dzialanie");
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
