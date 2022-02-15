import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class repeateddigits {
    private static String input;
    private static String[] inputArr;
    public static void main(String[] args){

        //ArrayList<BigInteger> test = new ArrayList<BigInteger>();
        //Has repeated digits
        //test = convertDecimalToBase(new BigInteger("32"), new BigInteger("497129812023"));
        //String testReturn = findLongestBlock(test, new BigInteger("32"));
        //Doesnt have repeated digits
        //test = convertDecimalToBase(new BigInteger("300"), new BigInteger("10"));
        //test = convertDecimalToBase(new BigInteger("250"), new BigInteger("565400"));
        //System.out.println("Converted number: " + test.toString());
        //System.out.println("Test new longest block: " + findLongestBlockNew(new BigInteger("10"), new BigInteger("100")));
        //System.out.println(convertDecimalToBase(11, 6000).toString());
        //System.out.println("Repeated digits? :" + findAnyRepeatedBlock(test));
        //System.out.println(findFirstRepeated(new BigInteger("250")));
        
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()){
            input = scan.nextLine();
            input = input.trim();
            input = input.replaceAll("\\s+", " ");
            inputArr = input.split(" ",20);
            //Check for bad inputs
            if (checkInputs(inputArr)){
                /*for (String x : inputArr)
                    System.out.print(x + " "); 
                System.out.println("");*/
                if (inputArr[0].equals("A")){
                    //option A

                    System.out.println(findLongestBlock(new BigInteger(inputArr[1]), (new BigInteger(inputArr[2]))));
                    
                } else {
                    //option B
                    BigInteger number = new BigInteger("1");
        
                    while (findAnyRepeatedBlock(convertDecimalToBase(number, new BigInteger(inputArr[1]))) == false ||
                        findAnyRepeatedBlock(convertDecimalToBase(number, new BigInteger(inputArr[2]))) == false){
                            number = number.add(new BigInteger("1"));
                            //System.out.println("Trying: " + number.toString());
                    }

                    System.out.println(number.toString());


                }

            } else {
                    System.out.println("Bad line: " + String.join(" ", inputArr));
            }
        }

    }
    public static ArrayList<BigInteger> convertDecimalToBase(BigInteger number, BigInteger base){
        
        ArrayList<BigInteger> currentArray = new ArrayList<BigInteger>();
        BigInteger result = number;
        if (number.compareTo(BigInteger.ZERO) == 0){
            currentArray.add(BigInteger.ZERO);
            return currentArray;
        }

        while (result.compareTo(BigInteger.ZERO) != 0){
            if (result.divide(base).compareTo(BigInteger.ZERO) == 0){
                currentArray.add(result);
                result = BigInteger.ZERO;
            } else {
                currentArray.add(result.remainder(base));
                result = result.divide(base);
            }
        }
        return currentArray;
    }

    public static boolean checkInputs(String[] inputArr){
        try {
            if (!inputArr[0].equals("A") && !inputArr[0].equals("B")){
                return false;
            }
            int checkIfNumArg1 = Integer.parseInt(inputArr[1]);
            int checkIfNumArg2 = Integer.parseInt(inputArr[2]);
        }catch (NumberFormatException ex) {
            return false;
        }
        if (inputArr.length != 3){
            return false;
        }
        return true;
    }
    public static String findFirstRepeated(BigInteger base){
        Boolean found = false;
        BigInteger currentNumber = new BigInteger("0");
        found = findAnyRepeatedBlock(convertDecimalToBase(currentNumber, base));
        while (!found){
            currentNumber = currentNumber.add(new BigInteger("1"));
            ArrayList<BigInteger> array = new ArrayList<BigInteger>(convertDecimalToBase(currentNumber, base));
            found = findAnyRepeatedBlock(array);
            System.out.println(currentNumber.toString());
            System.out.println(array.toString());
        }
        return "Found :" + currentNumber.toString();    
    }
    public static String findLongestBlock(BigInteger base, BigInteger number){
        BigInteger targetNumber = new BigInteger("0");
        int counter = 0;
        int biggestCounter = 0;
        BigInteger biggestCounterPointer = new BigInteger("0");
        BigInteger counterPointer = new BigInteger("0");
        while (targetNumber.compareTo(number) != 0){
            if (findAnyRepeatedBlock(convertDecimalToBase(targetNumber, base)) == true){
                if (counter == 0){
                    counterPointer = targetNumber;
                }
                counter++;

            } else {
                if (counter > biggestCounter){
                    biggestCounter = counter;
                    biggestCounterPointer = counterPointer;
                }
                counter = 0;
            }

            targetNumber = targetNumber.add(BigInteger.ONE);
        }

        if (counter > 0){
            if (counter > biggestCounter){
                biggestCounter = counter;
                biggestCounterPointer = counterPointer;
            }
        }

        return biggestCounterPointer + " " + biggestCounter;
    }
    /*
    Old Iteration of find longestblock

    public static String findLongestBlock(ArrayList<BigInteger> input, BigInteger base){
        List<BigInteger> currentLongest = new ArrayList<BigInteger>();
        List<BigInteger> possible = new ArrayList<BigInteger>();

        int pointerX = input.size() - 1;
        int pointerY = pointerX - 1;

        currentLongest.add(input.get(pointerX));
        possible.add(input.get(pointerX));
        while (pointerY != -1){
            
            if (input.get(pointerX).compareTo(input.get(pointerY)) != 0){ //<---- hERE
                pointerX = pointerY;
                if (possible.size() > currentLongest.size()){
                    currentLongest.clear();
                    currentLongest.addAll(possible);
                    //System.out.println("Current longest: " + currentLongest.toString() + " Size: " + currentLongest.size());
                }
                possible.clear();
            }
            possible.add(input.get(pointerY));
            pointerY--;
            //System.out.println(possible.toString() + possible.size());
        }
        if (!possible.isEmpty() && possible.size() > currentLongest.size()){
            currentLongest.clear();
            currentLongest.addAll(possible);
        }
        
        if (!currentLongest.isEmpty()){
            BigInteger mostSign = currentLongest.get(currentLongest.size() - 1);
            BigInteger basePower = base.pow(currentLongest.indexOf(mostSign));
            int mostSignInDec = mostSign.multiply(basePower).intValue();
            return "" + mostSignInDec + " " + currentLongest.size();
        }
        //System.out.println("Final longest block: " + currentLongest.toString());
        return "";
    }
    */

    public static boolean findAnyRepeatedBlock(ArrayList<BigInteger> input){
        /* 
        Previous iteration of findanyrepeatedblock method: use two pointers and move them in pairs, 
        have troubles with anything below base-10

        int pointerX = input.size() - 1;
        int pointerY = pointerX - 1;

        while (pointerY != -1){
            
            if (input.get(pointerX).compareTo(input.get(pointerY)) == 0){
                return true;
            }
            pointerY--;
            pointerX--;

        }*/

        for (int x = 0; x < input.size(); x++){
            for (int y = x+1; y < input.size(); y++){
                if (input.get(x).compareTo(input.get(y)) == 0){
                    return true;
                }
            }
        }
        return false;
    }


}