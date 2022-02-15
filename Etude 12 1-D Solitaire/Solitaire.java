import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

public class Solitaire {
    private static Set<String> seen = new HashSet<String>();
    private static List<String> finalList = new ArrayList<String>();

    public static void main(String[] args) {
        // findStartingPos(4, "1011", new String[] { "110011", "110101" });
        findStartingPos(3, "1011", findUnhops("1011"));
        printOut();
    }

    private static void findStartingPos(int numPegs, String current, String[] possible) {
        if (numPegs == 20) {
            if (!seen.contains(current) && !seen.contains(toMirror(current))) {
                finalList.add(current);
                seen.add(current);
            }
            return;
        } else {
            for (String board : possible) {
                if (!seen.contains(board) && !seen.contains(toMirror(board))) {
                    findStartingPos(numPegs + 1, board, findUnhops(board));
                    seen.add(board);
                }
            }
            return;
        }
    }

    private static void printOut() {
        Collections.sort(finalList, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        System.out.println("\nWinnable positions: \n\n");
        for (String board : finalList) {
            System.out.println(board);
        }
        System.out.println("\n\nNumbers of winnable positions: " + finalList.size());
    }

    private static String stripString(String input) {
        return input.replace("[", "").replace("]", "").replace(",", "")
                .replace(" ", "");
    }

    private static String[] findUnhops(String input) {
        ArrayList<String> list = new ArrayList<String>();
        String[] indv = input.split("(?!^)");
        // System.out.println("indv: " + Arrays.toString(indv));
        ArrayList<String> indvList = new ArrayList<String>();
        Collections.addAll(indvList, indv);
        int doubleZeroPos = 0;
        for (int x = 1; x < indv.length - 1; x++) {
            if (indv[x].compareTo("0") == 0 && indv[x + 1].compareTo("0") == 0) {
                doubleZeroPos = x;
            }
        }
        if (doubleZeroPos != 0) {
            indvList.set(doubleZeroPos, "1");
            indvList.set(doubleZeroPos + 1, "1");
            indvList.set(doubleZeroPos - 1, "0");
            if (indvList.get(0).compareTo("0") == 0) {
                indvList.remove(0);
            } else if (indvList.get(indvList.size() - 1).compareTo("0") == 0) {
                indvList.remove(indvList.size() - 1);
            }
            list.add(stripString(Arrays.toString(indvList.toArray())));
            indvList.clear();
            Collections.addAll(indvList, indv);
            indvList.set(doubleZeroPos, "1");
            indvList.set(doubleZeroPos + 1, "1");
            indvList.set(doubleZeroPos + 2, "0");
            if (indvList.get(0).compareTo("0") == 0) {
                indvList.remove(0);
            } else if (indvList.get(indvList.size() - 1).compareTo("0") == 0) {
                indvList.remove(indvList.size() - 1);
            }
            list.add(stripString(Arrays.toString(indvList.toArray())));
            indvList.clear();
            Collections.addAll(indvList, indv);
        }
        indvList.set(0, "0");
        indvList.add(0, "1");
        indvList.add(0, "1");
        list.add(stripString(Arrays.toString(indvList.toArray())));
        indvList.clear();
        Collections.addAll(indvList, indv);
        indvList.set(indvList.size() - 1, "0");
        indvList.add(indvList.size(), "1");
        indvList.add(indvList.size(), "1");
        list.add(stripString(Arrays.toString(indvList.toArray())));

        return Arrays.copyOf(list.toArray(), list.toArray().length, String[].class);
    }

    private static String toMirror(String input) {
        StringBuilder builder = new StringBuilder();
        builder.append(input);
        builder.reverse();
        return builder.toString();
    }
}
