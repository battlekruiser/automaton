package sample;

import java.util.Arrays;

public class Automaton {
    private boolean paused;
    private int fieldWidth, fieldHeight;
    private double density = .2;
    private RandomArrayGenerator rag;
    private byte[] state;
    private Rule birthRule = new Rule(new int[]{3,13,14,15,16,17,18});
    private Rule stayRule = new Rule(new int[]{9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24});
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

    public void setBirthRule(Rule nr){
        birthRule = nr;
    }

    public Rule getBirthRule() {
        return birthRule;
    }

    public void setStayRule(Rule nr) {
        stayRule = nr;
    }

    public Rule getStayRule() {
        return stayRule;
    }

    public void setFillDensity(double d) {
        density = d;
    }

    public double getFillDensity(){
        return density;
    }



    public void setRules(Rule nb, Rule ns) {
        setBirthRule(nb);
        setStayRule(ns);
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

    //todo add threading; should be trivial due to not working in-place. Add possibility to select arbitrary number of threads; split the working array accordingly.
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
            if(state[i] == 0 && birthRule.contains(count))
                result[i] = 1;
            if(state[i] == 1 && !stayRule.contains(count))
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
