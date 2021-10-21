package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        run(args_to_enum(args));
    }

    public static void run(MoveDirection[] dirs) {
        System.out.print("Zwierzak sobie biegnie ");

        for(MoveDirection dir : dirs) {
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
    public static MoveDirection[] args_to_enum(String[] args){
        MoveDirection[] dirList;
        dirList =  new MoveDirection[args.length];

        for(int i=0; i <args.length; i++) {
            dirList[i] = MoveDirection.fromString(args[i]);
        }
        return dirList;
    }
}
