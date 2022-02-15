import java.util.*;

public class redGreen{
    private static Map<Integer, Character> reference;
    private static Set<Integer> allNears;
    public static StringBuilder builder;
    private static final int START_NUM = 0;
    private static final int END_NUM = 1;

    public static void main(String[] args) {
      reference = new HashMap<Integer, Character>();
      reference.put(1, 'G');
      int[] input = new int[2];
      
      //Integer[] array = findAllNear(10);
      //System.out.println(Arrays.toString(array));
      //System.out.println(findIfGorR(5000));
      long startTime = System.currentTimeMillis();
      Scanner scan = new Scanner(System.in);
      
      while(scan.hasNextLine()){
        String line = scan.nextLine();
        Scanner sc = new Scanner(line);
        builder = new StringBuilder();
        try {
            for (int i = 0; i < 2; i++){
                input[i] = sc.nextInt();
            } 
        } catch (Exception e) {
            System.out.println("Bad input: " + line);
            continue;
        }
        builder.append(Integer.toString(input[START_NUM]) + " ");
        builder.append(Integer.toString(input[END_NUM]) + " ");
        for (int x = input[START_NUM]; x <= input[END_NUM]; x++){
            builder.append(findIfGorR(x));
        }

        System.out.println(builder);
        /*
        int value = 581237;
        System.out.println("New: " + findIfGorR(value));
        System.out.println("OG: " + findIfGorROG(value));*/

        long endTime = System.currentTimeMillis();

        //System.out.println("Time took: " + (endTime - startTime) + " ms");
      }
    }

    private static char findIfGorR(int input){
        int green = 0;
        int red = 0;
        if (reference.containsKey(input)){
            return reference.get(input);
        } else {
            Integer[] nears = findAllNear(input);
            //System.out.println(Arrays.toString(nears));
            for (Integer num : nears) {
                if (findIfGorR(num) == 'G'){
                    green++;
                } else {
                    red++;
                }
            }
            if (green > nears.length/2){
                reference.put(input, 'R');
                return 'R';
            }
            reference.put(input, 'G');
            return 'G';
        }
        
    }
/*

    private static Integer[] findAllNear(int input){
        allNears = new HashSet<Integer>();
        int repeated = 0;
        int repeatedNum = 0;
        allNears.add(Integer.valueOf(1));
        for (int x = input/2; x > 1; x--){
            //System.out.println(repeated);
            //System.out.println(x);
            if (repeatedNum == input/x){
                repeated++;
            } else {
                repeatedNum = input/x;
                repeated = 0;
            }
            if (repeated == 10 && x/10 != 0){
                x = (x/10)*9;
                repeated = 0;
            }
            Integer result = input/x;
           allNears.add(result);
        }
          
        return allNears.toArray(new Integer[allNears.size()]);
    }*/

    private static Integer[] findAllNear(int input){
        allNears = new HashSet<Integer>();
        allNears.add(Integer.valueOf(1));

        for (int x = input/2; x > 1; x--){
            Integer result = input/x;
           allNears.add(result);
        }
          
        return allNears.toArray(new Integer[allNears.size()]);
    }
}