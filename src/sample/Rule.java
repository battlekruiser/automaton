package sample;


import java.util.TreeSet;

public class Rule {
    private int[] arr;
    public Rule() {
        arr = null;
    }

    public Rule(int[] r) {
        arr = r;
    }

    public Rule(String r) {
        String[] input = r.split(",");
        TreeSet<Integer> arl = new TreeSet<>();
        for(String s:input) {
            s = s.trim();
            if(s.equals(""))
                continue;
            if(s.contains("-")){
                String[] tmp = s.split("-");
                int a0 = Integer.parseInt(tmp[0]), a1 = Integer.parseInt(tmp[1]);
                for(int i = a0; i <= a1; arl.add(i++));
                continue;
            }
            arl.add(Integer.parseInt(s));
        }
        arr = new int[arl.size()];
        int s = 0;
        for(Integer i:arl) {
            arr[s++] = i;
        }
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
    //This ugly mess nices the rule up :^)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int prev = arr[0];
        int seqfirst = prev;
        for(int i = 1; i < arr.length; i++) {
             if(arr[i] == prev+1) {
                 prev++;
                 if(i != arr.length-1)
                    continue;
             }
             if(prev==seqfirst)
                 sb.append(Integer.toString(prev));
             else
                 sb.append(Integer.toString(seqfirst)+"-"+Integer.toString(prev));
             sb.append(i == arr.length-1 ? "" : ", ");
             if(i == arr.length - 1 && prev != arr[i])
                 sb.append(arr[i]);
             seqfirst=prev=arr[i];
        }
        return sb.toString();
    }

    public static String toString(Rule r) {
        return r.toString();
    }
}
