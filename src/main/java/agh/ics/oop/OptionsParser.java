package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        List<MoveDirection> dirList = new ArrayList<>();

        for(String arg : args) {
            if (MoveDirection.fromString(arg) != null) {
                dirList.add(MoveDirection.fromString(arg));
            }
        }
        return dirList.toArray(new MoveDirection[0]);
    }
}
