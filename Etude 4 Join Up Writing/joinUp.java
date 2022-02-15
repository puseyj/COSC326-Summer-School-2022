import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 * joinUp program, finds the shortest singly linked and doubly linked chain of words from
 * a dictionary of words, from one word to another.
 * 
 * @author Suppanut Chaimathikul, 6991678
 * @author Samuel Terry, 6786350
 */
public class joinUp {
    private static String start;
    private static String target;
    private static Map<Character, Map<Character, List<String>>> dict;
    private static Map<String, List<String>> adj;
    private static Map<String, String> parent;
    private static Set<String> seen;
    private static Set<String> seenParent;

    public static void main(String[] args) {
        start = args[0].toLowerCase();
        target = args[1].toLowerCase();
        dict = new HashMap<Character, Map<Character, List<String>>>();
        adj = new HashMap<String, List<String>>();
        parent = new HashMap<String, String>();
        seen = new HashSet<String>();
        seenParent = new HashSet<String>();
        long startTime = System.currentTimeMillis();
        for (char c = 'a'; c <= 'z'; ++c){
            dict.put(Character.valueOf(c), new HashMap<Character, List<String>>());
            for (char x = 'a'; x <= 'z'; ++x){
                dict.get(Character.valueOf(c)).put(x, new ArrayList<String>());
            }
            dict.get(Character.valueOf(c)).put(' ', new ArrayList<String>());
        }
        
        //System.out.println(dict.get('g').size());
        String reg = "[a-zA-Z]+";
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine().toLowerCase();
            if (!input.isBlank() && input.length() == 1 && input.matches(reg)){
                dict.get(input.charAt(0)).get(' ').add(input);
            } else if (!input.isBlank() && input.matches(reg)){
                dict.get(input.charAt(0)).get(input.charAt(1)).add(input);
            } else {
                continue;
            }
        }
        scan.close();

        //findAllSingly(start);
        //findAllDoubly(start);
        //System.out.println(adj.get(start).toString());
        //System.out.println("Check parents for 'aa' : " + parent.get("aa").toString());
        //System.out.println(breadthFirstSearch(1));
        //System.out.println("Check singly: " + checkIfSingly("allow", "we"));

        System.out.println(start + " " + target);
        String resultSingly = breadthFirstSearch(0);
        if (resultSingly != null){
            printChain(resultSingly);
            //System.out.println("result : " + resultSingly);
        } else {
            System.out.println("0");
        }
        seen.clear();
        seenParent.clear();
        parent.clear();
        adj.clear();

        String resultDoubly = breadthFirstSearch(1);
        if (resultDoubly != null){
            printChain(resultDoubly);
            //System.out.println("result : " + resultDoubly);
        } else {
            System.out.println("0");
        }

        long endTime = System.currentTimeMillis();

        //System.out.println("Time took: " + (endTime - startTime) + " ms");
        //printChain(breadthFirstSearch(1));
       // System.out.println(dict.get('a').get(' ').toString());
    }

    private static void findAllSingly(String input){
        //System.out.println("find all singly input : " + input);
        List<String> inList = new ArrayList<String>();

        if (input.length() == 1){
            dict.get(input.charAt(0)).forEach((key, value) -> {
                inList.addAll(value);
            });
        }
        else if (input.length() == 2){
            //inList.addAll(dict.get(input.charAt(0)).get(' '));
            inList.addAll(dict.get(input.charAt(0)).get(input.charAt(1)));
            dict.get(input.charAt(1)).forEach((key, value) -> {
                inList.addAll(value);
            });
        } else {
            for (int x = 2; x < input.length(); x++){
                inList.addAll(dict.get(Character.toLowerCase(input.charAt(input.length() - x)))
                    .get(Character.toLowerCase(input.charAt(input.length() - (x-1)))));
            }
            dict.get(input.charAt(input.length() - 1)).forEach((key, value) -> {
                inList.addAll(value);
            });
        }
        //System.out.println("inList for " + input + ": " + inList.toString());
        //System.out.println("inList: " + inList.toString());
        inList.forEach((value) -> {
            if (!seenParent.contains(value) && value.compareTo(input) != 0){
                if (checkIfSingly(input, value)){
                    if (adj.get(input) == null){
                        adj.put(input, new ArrayList<String>());
                    }
                    adj.get(input).add(value);
                    parent.put(value, input);
                    seenParent.add(value);
                }
            }
        });
        try {
            if (adj.get(input).isEmpty()){
                
            }
        } catch (NullPointerException e) {
            //TODO: handle exception
            adj.put(input, new ArrayList<String>());
        }
    }
    
    private static Boolean checkIfSingly(String word1, String word2){
        int word1HalfSize = word1.length()/2;
        if (word1.length() % 2 != 0){
            word1HalfSize = word1.length()/2 + 1;
        }
        int word2HalfSize = word2.length()/2;
        if (word2.length() % 2 != 0){
            word2HalfSize = word2.length()/2 + 1;
        }
        for (int x = 1; x <= word1.length(); x++){
            String prefix = word1.substring(word1.length() - x).toLowerCase();
            //System.out.println("Check prefix: " + prefix);
            Boolean check = prefix.length() >= word1HalfSize;
            if ((word2.startsWith(prefix) && prefix.length() >= word1HalfSize && prefix.length() >= word2HalfSize || word2.startsWith(prefix) 
            && check && prefix.length() < word2HalfSize || word2.startsWith(prefix) && check == false && prefix.length() >= word2HalfSize)){

                //System.out.println("does it gets here 2");
                return true;
            }
        }
        return false;
    }
    private static void findAllDoubly(String input){
        List<String> inList = new ArrayList<String>();
        int inputHalfSize = input.length()/2;
        if (input.length() % 2 != 0){
            inputHalfSize = input.length()/2 + 1;
        }
        if (input.length() == 1){
            dict.get(input.charAt(0)).forEach((key, value) -> {
                inList.addAll(value);
            });
        }
        else if (input.length() == 2){
            inList.addAll(dict.get(input.charAt(0)).get(input.charAt(1)));
            dict.get(input.charAt(1)).forEach((key, value) -> {
                inList.addAll(value);
            });
        }
        else { 
            for (int x = inputHalfSize; x < input.length(); x++){
                inList.addAll(dict.get(Character.toLowerCase(input.charAt(input.length() - x)))
                    .get(Character.toLowerCase(input.charAt(input.length() - (x-1)))));
            }
        }
        //System.out.println("inList for " + input + ": " + inList.toString());


        //System.out.println("inList: " + inList.toString());
        inList.forEach((value) -> {
            //System.out.println("Compares " + input + " To " + value);
            if (!seenParent.contains(value) && value.compareTo(input) != 0){
                if (checkIfDoubly(input, value)){
                    //System.out.println("Did it gets through check doubly?");
                    if (adj.get(input) == null){
                        adj.put(input, new ArrayList<String>());
                    }
                    adj.get(input).add(value);
                    parent.put(value, input);
                    seenParent.add(value);
                }
            }
        });

        try {
            if (adj.get(input).isEmpty()){
                
            }
        } catch (NullPointerException e) {
            //TODO: handle exception
            adj.put(input, new ArrayList<String>());
        }
    }
    private static Boolean checkIfDoubly(String word1, String word2){
        int word1HalfSize = word1.length()/2;
        if (word1.length() % 2 != 0){
            word1HalfSize = word1.length()/2 + 1;
        }
        int word2HalfSize = word2.length()/2;
        if (word2.length() % 2 != 0){
            word2HalfSize = word2.length()/2 + 1;
        }
        for (int x = 1; x <= word1.length(); x++){
            String prefix = word1.substring(word1.length() - x).toLowerCase();
            //System.out.println("Check prefix: " + prefix);
            Boolean check = prefix.length() >= word1HalfSize;
            if (word2.startsWith(prefix) && prefix.length() >= word2HalfSize && check){

                //System.out.println("does it gets here 2");
                return true;
            }
        }
        return false;
    }
    private static String breadthFirstSearch(int mode){
        LinkedList<String> queue = new LinkedList<String>();

        //Add the first input to queue (most likely the start word)
        queue.add(start);
        seen.add(start);

        while (queue.size() > 0) {
            String word = queue.poll();
            //System.out.println("<------ Popped out of Q: " + word);
            //mode 0 is singly, so set edges to result of findAllSingly then follow with findAllDoubly
            if (mode == 0){
                if (checkIfSingly(word, target)){
                    return word;
                }
                //System.out.println("ACTIVATED FINDSINGLY");
                findAllSingly(word);
                /*
                Set<Node> temp = word.getEdges();
                temp.addAll(findAllDoubly(word));
                word.setEdges(temp);*/
            } else { //else mode 1 is doubly, so set edges to result of findAllDoubly only
                if (checkIfDoubly(word, target)){
                    return word;
                }
                findAllDoubly(word);
            }
            //bfs part
            
                //System.out.println("Adjacent for " + word + " : " + adj.get(word).toString());
                adj.get(word).forEach(edge -> {
                    if (seen.contains(edge) != true) {
                        //System.out.println("====ACTIVATED===== : " + edge.getWord());
                        seen.add(edge);
                        queue.add(edge);
                        //System.out.println("-----> Added to Q: " + edge);
                    }
                });
        
            
        }

        return null;
    }

    private static void printChain(String last){
        List<String> result = new ArrayList<String>();
        result.add(target); 
        while (parent.get(last).compareTo(start) != 0){
            result.add(last);
            String temp = parent.get(last);
            last = temp;
        }
        result.add(last);
        result.add(start);
        System.out.print(Integer.toString(result.size()));

        for (int x = result.size() - 1; x >= 0; x--) {
            System.out.print(" " + result.get(x));
        }
        System.out.println();
    }
    
}