package sample;

import java.util.Arrays;

public class Automaton {
    private boolean paused;
    private int fieldWidth, fieldHeight;
    private double density = .2;
    private RandomArrayGenerator rag;
    private byte[] state;
    private int[] birthValues = {3,13,14,15,16,17,18};
    private int[] stayValues = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
    private int countRadius = 2;

    private void setFieldWidth(int w) {
        fieldWidth = w;
    }

    private void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public void setRag(RandomArrayGenerator rg) {
        rag = rg;
    }

    public int getFieldWidth(){
        return fieldWidth;
    }

    public void setBirthValues(int[] nr){
        birthValues = nr;
    }

    public int[] getBirthValues() {
        return birthValues;
    }

    public void setStayValues(int[] nr) {
        stayValues = nr;
    }

    public int[] getStayValues() {
        return stayValues;
    }

    public void setFillDensity(double d) {
        density = d;
    }

    public double getFillDensity(){
        return density;
    }



    public void setRules(int[] nb, int[] ns) {
        setBirthValues(nb);
        setStayValues(ns);
    }

    public void setState(byte[] ns) {
        state = ns;
    }

    public byte[] getState() {
        return state;
    }

    public Automaton() {
        paused = false;
        rag = new RandomArrayGenerator();
    }

    public Automaton(int w, int h) {
        this();
        setFieldWidth(w);
        setFieldHeight(h);
        setState(new byte[w*h]);
    }

    void tick() {
        int w = state.length;
        byte[] result = Arrays.copyOf(state, w);
        for(int i = 0; i < w; i++) {
            int count = 0;
            int x = i % fieldWidth;
            int y = i / fieldWidth;
            for(int j = -countRadius; j <= countRadius; j++) {
                for(int k = -countRadius; k <= countRadius; k++) {
                    if(j != 0 || k != 0) {
                        count += state[((y+k+fieldHeight)%fieldHeight)*fieldWidth+(x+j+fieldWidth)%fieldWidth];
                    }
                }
            }
            if(state[i] == 0 && belongs(count, birthValues))
                result[i] = 1;
            if(state[i] == 1 && !belongs(count, stayValues))
                result[i] = 0;
        }
        state = result;
    }

    public void reset() {
        state = rag.generate(fieldWidth,fieldHeight,density);
    }

    private boolean belongs(int a, int[] array) {
        int l = array.length;
        /*for(int i: array) {
            if (a == i) return true;
        }*/
        for(int i = 0; i < l; i++) {
            if(a == array[i]) return true;
        }
        return false;
    }

    public void togglePause(){
        paused = !paused;
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public boolean getPaused() {
        return paused;
    }

}
