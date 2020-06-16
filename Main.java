package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String importFile = "./";

    public static int countSyllables(String s) {
        final Pattern p = Pattern.compile("([ayeiou]+)");
        final String lowerCase = s.toLowerCase();
        final Matcher m = p.matcher(lowerCase);
        int count = 0;
        while (m.find())
            count++;
        if (lowerCase.endsWith("e"))
            count--;
        return count < 0 ? 1 : count;
    }

    public static int age (int score) {
        int age;
        if (score == 1 ) age = 6;
        else if (score == 2) age = 7;
        else if (score == 3) age = 9;
        else if (score == 4) age = 10;
        else if (score == 5) age = 11;
        else if (score == 6) age = 12;
        else if (score == 7) age = 13;
        else if (score == 8) age = 14;
        else if (score == 9) age = 15;
        else if (score == 10) age = 16;
        else if (score == 11) age = 17;
        else if (score == 12) age = 18;
        else age = 24;
        return age;
    }

    public static int countWordsUsingStringTokenizer(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(sentence);
        return tokens.countTokens();
    }

    public static void main(String[] args) throws FileNotFoundException {
        importFile += args[0];
        File file = new File(importFile);

        List<Integer> list = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        String text0 = scanner.nextLine();
        String text = text0.replaceAll("!", ".")
                .replaceAll("\\?", ".");
        String[] text2 = text.split("\\.");

        for (int i=0; i<text2.length; i++) {
            String[] text3 = text2[i].split(" ");
            list.add(text3.length);
        }

        int words = countWordsUsingStringTokenizer(text0);
        int characters = 0;
        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) != ' ')
                characters++;
        }
        int sentences = text2.length;
        int syllables = countSyllables(text0);

        String text3 = text0.replaceAll("!", "")
                .replaceAll("\\?", "")
                .replaceAll(",", "")
                .replaceAll("\\.", "")
                .replaceAll("\"", "");
        String[] text4 = text3.split(" ");
        int[] mySyllables = new int[text4.length];
        for(int i = 0; i < text4.length; i++) {
            mySyllables[i] = countSyllables(text4[i]);
        }
        int polysyllables = 0;
        for(int i=0;i<mySyllables.length;i++) {
            if(mySyllables[i] > 2)
                polysyllables++;
        }

        double score001 = 4.71 * characters/words + 0.5 * words/sentences - 21.43;
        double score002 = 0.39 * words/sentences + 11.8 * (syllables-10)/words - 15.59;
        double score003 = 1.043 * Math.sqrt((double) polysyllables * 30/sentences) + 3.1291;
        double L = (double) characters / words * 100; 
        double S = (double) sentences / words * 100;
        double score004 = 0.0588 * L - 0.296 * S - 15.8;
        String score01 = String.format("%.2f", score001);
        String score02 = String.format("%.2f", score002);
        String score03 = String.format("%.2f", score003);
        String score04 = String.format("%.2f", score004);
        int score1 = (int) Math.ceil(score001);
        int score2 = (int) Math.ceil(score002);
        int score3 = (int) Math.ceil(score003);
        int score4 = (int) Math.ceil(score004);

        System.out.println("The text is:");
        System.out.println(text0);
        System.out.println();
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + (syllables-20));
        System.out.println("Polysyllables: " + polysyllables);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner2 = new Scanner(System.in);
        String action = scanner2.nextLine();
        System.out.println();

        if (action.equals("ARI")) {
            System.out.println("Automated Readability Index: " + score01 + " (about " + age(score1) + " year olds).");
        } else if (action.equals("FK")) {
            System.out.println("Flesch–Kincaid readability tests: " + score02 + " (about " + age(score2) + " year olds).");
        } else if (action.equals("SMOG")) {
            System.out.println("Simple Measure of Gobbledygook: " + score03 + " (about " + age(score3) + " year olds).");
        } else if (action.equals("CL")) {
            System.out.println("Coleman–Liau index: " + score04 + " (about " + age(score4) + " year olds).");
        } else if (action.equals("all")) {
            double averageAge0 = (double) (age(score1) + age(score2) + age(score3) + age(score4)) / 4;
            String averageAge1 = String.format("%.2f", averageAge0);
            System.out.println("Automated Readability Index: " + score01 + " (about " + age(score1) + " year olds).");
            System.out.println("Flesch–Kincaid readability tests: " + score02 + " (about " + age(score2) + " year olds).");
            System.out.println("Simple Measure of Gobbledygook: " + score03 + " (about " + age(score3) + " year olds).");
            System.out.println("Coleman–Liau index: " + score04 + " (about " + age(score4) + " year olds).");
            System.out.println();
            System.out.println("This text should be understood by in average " + averageAge1 + " year olds.");
        }
        scanner.close();
    }
}
