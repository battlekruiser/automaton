package sample;

import java.util.Random;

public class RandomArrayGenerator {
    private int seed;

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    RandomArrayGenerator(){
        seed = (int)(Math.random()*Integer.MAX_VALUE);
    }
    byte[] generate(int w, int h, double density) {
        Random r = new Random(seed);
        byte[] result = new byte[w*h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                result[i*h+j] = (byte)(r.nextDouble() < density ? 1 : 0);
            }
        }
        return result;
    }
}
