package sample;

public class RandomArrayGenerator {
    RandomArrayGenerator(){}
    byte[] generate(int w, int h, double density) {
        byte[] result = new byte[w*h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                result[i*h+j] = (byte)(Math.random() < density ? 1 : 0);
            }
        }
        return result;
    }
}
