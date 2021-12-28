package agh.ics.oop;


import java.util.Arrays;

public class Genom {
    private final int[] genom;

    public Genom(int[] genom) {
        if (genom.length != 32) {
            throw new IllegalArgumentException("Genom must have 32 genes");
        }
        for (int gene : genom) {
            if (gene < 0 || gene > 7) {
                throw new IllegalArgumentException("Genom must have genes from 0 to 7");
            }
        }

        Arrays.sort(genom);

        this.genom = genom;
    }

    public Genom(){
        genom = new int[32];
        for (int i = 0; i < 32; i++) {
            genom[i] = (int) (Math.random() * 8);
        }

        Arrays.sort(genom);
    }

    public Genom cross(Genom other, double ratio) {
        int[] newGenom = new int[32];
        int side = (int) (Math.random() * 2);

        if (side == 0) { // starting left side
            int i = 0;
            int crossPoint = (int) (32/(ratio+1));
            while(i < crossPoint) {
                newGenom[i] = this.genom[i];
                i++;
            }
            while(i < 32) {
                newGenom[i] = other.genom[i];
                i++;
            }
        } else { // starting right side
            int i = 0;
            int crossPoint = (int) (32 - 32/(ratio+1));
            while(i < crossPoint) {
                newGenom[i] = other.genom[i];
                i++;
            }
            while(i < 32) {
                newGenom[i] = this.genom[i];
                i++;
            }
        }

        return new Genom(newGenom);
    }

    public int random() {
        return genom[(int) (Math.random() * 32)];
    }

}
