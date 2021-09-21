package command;

import java.util.InputMismatchException;
import java.util.Scanner;
import prostredky.Barva;
import prostredky.ProstredekTyp;

/**
 *
 * @author Adam_Tomasek
 */
public final class Keyboard {

    private Keyboard() {
    }

    
    private static void vypisException(String mess) {
        System.out.println("\u001B[31m" + mess + "\u001B[0m");
    }

    public static int getInt(String message, int min, int max) {
        return (int) getDouble(message, min, max);
    }

    public static double getDouble(String message, int min, int max) {
        Scanner sc = new Scanner(System.in);
        double input = 0;

        while (true) {
            System.out.println(message + " v rozsahu od "
                    + min + " do " + max + ".");
            try {
                input = sc.nextDouble();
                if (input > max || input < min) {
                    vypisException("Špatný rozsah");
                    continue;
                }
            } catch (InputMismatchException e) {
                vypisException("Vstup není číslo.");
                sc.nextLine();
                continue;
            }
            break;
        }
        return input;
    }

    public static ProstredekTyp getTyp() {
        Scanner sc = new Scanner(System.in);
        int pointer;
        Enum[] types = ProstredekTyp.getProstredky();

        while (true) {
            System.out.println("Vyberte typ prostředku. "
                    + "(1-" + types.length + ")");
            int i = 1;
            for (Enum typ : types) {
                System.out.println(i + ". pro " + typ.toString());
                i++;
            }
            try {
                pointer = sc.nextInt();
                if (pointer > types.length || pointer < 1) {
                    vypisException("Špatný rozsah");
                    continue;
                }
            } catch (InputMismatchException e) {
                vypisException("Vstup není číslo.");
                sc.nextLine();
                continue;
            }

            break;
        }
        return (ProstredekTyp) types[pointer - 1];

    }

    public static Barva getBarva() {
        Scanner sc = new Scanner(System.in);
        byte pointer;
        Barva[] barvy = Barva.values();

        while (true) {
            System.out.println("Vyberte barvu prostředku. (1-"
                    + barvy.length + ")");
            int i = 1;
            for (Barva typ : barvy) {
                System.out.println(i + ". pro " + typ.toString());
                i++;
            }
            try {
                pointer = sc.nextByte();
                if (pointer > barvy.length || pointer < 1) {
                    vypisException("Špatný rozsah");
                    continue;
                }
            } catch (InputMismatchException e) {
                vypisException("Vstup není číslo.");
                sc.nextLine();
                continue;
            }

            break;
        }
        return barvy[pointer - 1];

    }

    public static String getString(String message, int min, int max) {
        Scanner sc = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.println(message + " v rozsahu od " + min
                    + " do " + max + " znaků");
            input = sc.next();
            if (input.length() > max || input.length() < min) {
                vypisException("Špatný rozsah");

                continue;
            }
            break;
        }
        return input;
    }
}
