import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String key;
    public static String textForEncrypt;
    public static List<List<Character>> table;

    public static void dataInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nВведите буквенный ключ: ");
        key = bf.readLine();
        System.out.println("\nВведите текст для шифрования: ");
        textForEncrypt = bf.readLine();
    }

    public static String getInitialLettersOfStrings(List<List<Character>> table) {
        String str = "";
        for (List<Character> characters : table) {
            str = str + characters.get(0);
        }
        return str;
    }

    public static void createTable(String str) {
        table = new ArrayList<>();
        int indexOfLastChar = str.indexOf(str.charAt(str.length() - 1)) + 1;
        for (char c : str.toCharArray()) {
            int indexOfThisChar = str.indexOf(c);
            String s = str.substring(indexOfThisChar, indexOfLastChar) + str.substring(0, indexOfThisChar);
            List<Character> characterList = new ArrayList<>();
            for (char ch : s.toCharArray()) {
                characterList.add(ch);
            }
            table.add(characterList);
        }
        System.out.println("\nМатрица шифрования: ");
        for (var i : table) System.out.println(i);
    }

    public static String encodeText(String key, String text) {
        String res = "";
        for (int i = 0, indexKey = 0; i < text.length(); i++) {
            int indexText = table.get(0).indexOf(text.charAt(i));
            String forKey = getInitialLettersOfStrings(table);
            int indexInKey = forKey.indexOf(key.charAt(indexKey));
            res = res + table.get(indexInKey).get(indexText);
            indexKey++;
            if (key.length() == indexKey) indexKey = 0;
        }
        return res;
    }

    public static String decodeText(String key, String enc) {
        String res = "";
        String forKey = getInitialLettersOfStrings(table);
        for (int i = 0, indexKey = 0; i < enc.length(); i++) {
            int indexInKey = forKey.indexOf(key.charAt(indexKey));
            int indexText = table.get(indexInKey).indexOf(enc.charAt(i));
            res = res + table.get(0).get(indexText);
            indexKey++;
            if (indexKey == key.length()) indexKey = 0;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        String forTable = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789!\"№;%:?*()-+=,./\\ ";
        dataInput();
        createTable(forTable);
        String enc = encodeText(key, textForEncrypt);
        String dec = decodeText(key, enc);
        System.out.println("\nЗашифрованный текст: " + enc);
        System.out.println("\nРасшифрованный текст: " + dec);
    }
}
