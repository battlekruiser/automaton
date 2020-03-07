package sample;

public class Rule {
    private int[] arr;
    public Rule() {
        arr = null;
    }

    public Rule(int[] r) {
        arr = r;
    }

    public Rule(String r) {
        this(strArrayToIntArray(r.split(",")));
    }

    //checks a against arr: returns true if a is in arr at least once
    public boolean contains(int a) {
        for(int i = 0; i < arr.length; i++) {
            if(a == arr[i]) return true;
        }
        return false;
    }

    //assumes same parsing rules for both birth and stay rules.
    public static Rule parseRuleString(String input) {
        return new Rule(input);
    }

    //same assumption as above
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i++) {
            sb.append(Integer.toString(arr[i]));
            if(i != arr.length-1)
                sb.append(", ");
        }
        return sb.toString();
    }

    public static String toString(Rule r) {
        return r.toString();
    }

    private static int[] strArrayToIntArray(String[] input) {
        int[] res = new int[input.length];
        for(int i = 0; i < input.length; i++) {
            res[i] = Integer.parseInt(input[i].trim());
        }
        return res;
    }
}
