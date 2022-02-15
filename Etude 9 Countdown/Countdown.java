import java.util.Arrays;
import java.util.Scanner;

public class Countdown {
    private static int target;
    private static String resultOperations;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] inputArray = input.split(" ");
            int[] inputNums = extractNums(Arrays.copyOfRange(inputArray, 1, inputArray.length));
            if (inputNums == null) {
                System.out.println(input + " invalid");
                continue;
            }
            // System.out.println(Arrays.toString(inputNums));
            if (inputArray[0].compareTo("L") == 0) {
                resultOperations = fromLeft(0, "", inputNums[0], inputNums);
                if (resultOperations.compareTo("impossible") == 0) {
                    System.out.println(input + " impossible");
                } else {
                    // System.out.println("resultOperations: " + resultOperations);
                    cleanToPrint("L", resultOperations, inputNums);
                }

            } else if (inputArray[0].compareTo("N") == 0) {
                resultOperations = normal(0, "", inputNums, 0, inputNums[0]);
                // resultOperations = normal(0, "", inputNums);
                if (resultOperations.compareTo("impossible") == 0) {
                    System.out.println(input + " impossible");
                } else {
                    // System.out.println("resultOperations N: " + resultOperations);
                    cleanToPrint("N", resultOperations, inputNums);
                }

            } else {
                System.out.println(input + " invalid");
                continue;
            }
        }
        scan.close();
    }

    private static int[] extractNums(String[] input) {
        int[] result = new int[input.length - 1];
        try {
            target = Integer.parseInt(input[0]);
            for (int x = 1; x < input.length; x++) {
                result[x - 1] = Integer.parseInt(input[x]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    private static void cleanToPrint(String mode, String currentOperations, int[] inputNums) {
        StringBuilder builder = new StringBuilder();
        builder.append(mode + " " + target + " =");
        // System.out.println("inputNums: " + Arrays.toString(inputNums));
        for (int x = 0; x < inputNums.length - 1; x++) {
            builder.append(" " + inputNums[x] + " " + currentOperations.charAt(x));
        }
        builder.append(" " + inputNums[inputNums.length - 1]);
        System.out.println(builder.toString());
    }

    private static String fromLeft(int index, String currentOperations, int prevTotal, int[] input) {
        // System.out.println("Current operations: " + currentOperations);
        if (prevTotal > target) {
            return "impossible";
        }
        if (prevTotal == target && index == input.length) {
            return currentOperations.substring(0, (currentOperations.length() - 1));
        } else if (index >= input.length) {
            return "impossible";
        }
        /*
         * if (prevTotal > target && index >= input.length || prevTotal < target &&
         * index >= input.length) {
         * return "impossible";
         * }
         */
        int newTotal = prevTotal;
        if (currentOperations.length() != 0) {
            String lastOperation = currentOperations.substring((currentOperations.length() - 1));
            if (lastOperation.compareTo("+") == 0) {
                newTotal += input[index];
            } else if (lastOperation.compareTo("*") == 0) {
                newTotal *= input[index];
            }
        }
        // System.out.println("new total: " + newTotal);
        index++;
        // System.out.println("index: " + index);

        // recursive calls
        String addition = fromLeft(index, currentOperations + "+", newTotal, input);
        if (addition.compareTo("impossible") != 0) {
            return addition;
        }
        String multiplication = fromLeft(index, currentOperations + "*", newTotal, input);
        if (multiplication.compareTo("impossible") != 0) {
            return multiplication;
        }
        return "impossible";
    }

    private static String normal(int index, String currentOperations, int[] input, int known, int pending) {
        // System.out.println("Current operations: " + currentOperations);
        if (known + pending > target) {
            return "impossible";
        }
        if (known + pending == target && index == input.length) {
            return currentOperations.substring(0, (currentOperations.length() - 1));

        } else if (index >= input.length) {
            return "impossible";
        }
        /*
         * if (index == input.length) {
         * int currentTotal = 0;
         * 
         * for (int x = 0; x < input.length; x++) {
         * currentTotal += input[x];
         * }
         * // System.out.println("Total after add: " + total);
         * if (currentTotal == target) {
         * return currentOperations.substring(0, (currentOperations.length() - 1));
         * }
         * return "impossible";
         * }
         */

        // int[] newInput = Arrays.copyOf(input, input.length);
        /*
         * String lastOperation = "";
         * try {
         * lastOperation = currentOperations.substring((currentOperations.length() -
         * 1));
         * } catch (IndexOutOfBoundsException e) {
         * lastOperation = "";
         * }
         * // multiply two together and replace one with 0 and another with result
         * if (lastOperation.compareTo("*") == 0 && index < input.length) {
         * int result = (newInput[index - 1] * newInput[index]);
         * int v = newInput[index];
         * newInput[index] = result;
         * newInput[index - 1] = 0;
         * if (isPrevMultiply) {
         * newTotal = (newTotal - temp) + result;
         * } else {
         * newTotal += result;
         * }
         * pending = pending * v;
         * // isPrevMultiply = true;
         * // total += result;
         * } else if (lastOperation.compareTo("+") == 0 && index < input.length) {
         * int v = newInput[index];
         * known = known + pending;
         * pending = v;
         * }
         */
        int newKnown = known;
        int newPending = pending;

        if (currentOperations.length() != 0) {
            String lastOperation = currentOperations.substring((currentOperations.length() - 1));
            if (lastOperation.compareTo("+") == 0) {
                // int v = input[index];
                newKnown = known + pending;
                newPending = input[index];
            } else if (lastOperation.compareTo("*") == 0) {
                /*
                 * int result = (input[index - 1] * input[index]);
                 * input[index] = result;
                 * input[index - 1] = 0;
                 */
                // int v = input[index];
                newPending = pending * input[index];
            }
        }
        index++;
        /*
         * if (index == 1) {
         * newPending = input[0];
         * String addition = normal(index, currentOperations + "+", input, newKnown,
         * newPending);
         * String multiplication = normal(index, currentOperations + "*", input,
         * newKnown, newPending);
         * }
         */

        String addition = normal(index, currentOperations + "+", input, newKnown, newPending);
        if (addition.compareTo("impossible") != 0) {
            return addition;
        }
        String multiplication = normal(index, currentOperations + "*", input,
                newKnown, newPending);
        if (multiplication.compareTo("impossible") != 0) {
            return multiplication;
        }
        return "impossible";
    }

}
