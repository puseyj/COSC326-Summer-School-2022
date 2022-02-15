import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Dictionary {
    private static ArrayList<String> ls;
    private static Set<String> hash;

    public static void main(String[] args) {
        File file = new File(args[0]);
        Scanner sc;
        ls = new ArrayList<String>();
        hash = new LinkedHashSet<String>();
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
            return;
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.strip();
            String[] arr = line.split("\\s+");
            for (String word : arr) {
                word = word.strip();
                // System.out.println("word: " + word);
                checkIfValid(word);
            }

        }
        Collections.sort(ls);
        hash.addAll(ls);
        ls.clear();
        ls.addAll(hash);
        Collections.sort(ls);
        for (String word : ls) {
            System.out.println(word);
        }
        sc.close();
    }

    private static void checkIfValid(String word) {
        int aposCount = (int) word.chars().filter(ch -> ch == '\'').count();
        if (word.matches("(^(\"|\\s+)*[a-zA-Z']+(\\s+|[.,;:?!\"])*$)")) {
            if (aposCount <= 1) {
                String stripped = word.replaceAll("^[\\s+|\"|]", "");
                stripped = stripped.replaceAll("[\\s+|.,;:?!\"]$", "");
                int counter = 0;
                int index = 0;
                int indexOfCap = 0;
                for (final char c : stripped.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        counter++;
                        indexOfCap = index;
                    }
                    index++;
                }
                if (!stripped.startsWith("'") && counter <= 1 && indexOfCap == 0) {
                    String strippedApos = stripped.replaceFirst("'", "");
                    if (strippedApos.matches("^[a-zA-Z]*$"))
                        ls.add(stripped.toLowerCase());
                }
            }
        }
    }
}
