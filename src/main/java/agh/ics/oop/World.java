package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Animal animal = new Animal();

        System.out.println(animal);

        for (MoveDirection dir: OptionsParser.parse(args)){
            animal.move(dir);
        }

        System.out.println(animal);

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
}
