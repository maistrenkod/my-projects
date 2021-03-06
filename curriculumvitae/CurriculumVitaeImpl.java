package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurriculumVitaeImpl implements CurriculumVitae {

    String cvtext;
    ArrayList<String> pieces;
    ArrayList<Integer> numbers;
    ArrayList<Integer> indents;
    public static final String FULLNAME_PATTERN = "([A-Z]([a-z]+|\\.) ){1,2}[A-Z]([a-z]+|\\.)";

    public void setText(String text) {
        pieces = new ArrayList<>();
        numbers = new ArrayList<>();
        indents = new ArrayList<>();
        indents.clear();
        pieces.clear();
        numbers.clear();
        cvtext = text;
    }

    public String getText() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        return cvtext;
    }

    public List<Phone> getPhones() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        ArrayList<Phone> phones = new ArrayList<>();
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(cvtext);
        while (matcher.find()) {
            String str = matcher.group();
            Pattern pattern1 = Pattern.compile("(\\()(\\d{3})(\\)) (\\d{3}-\\d{4})");
            Matcher matcher1 = pattern1.matcher(str);
            Pattern pattern2 = Pattern.compile("(\\d{3}) (\\d{3}-\\d{2}-\\d{2}) (ext\\.?)(\\d{4})");
            Matcher matcher2 = pattern2.matcher(str);
            Pattern pattern3 = Pattern.compile("(\\d{3}) (\\d{3} \\d{4})");
            Matcher matcher3 = pattern3.matcher(str);
            if (matcher1.matches()) phones.add(new Phone(matcher1.group(4), Integer.parseInt(matcher1.group(2)), -1));
            if (matcher2.matches()) phones.add(new Phone(matcher2.group(2), Integer.parseInt(matcher2.group(1)),Integer.parseInt(matcher2.group(4))));
            if (matcher3.matches()) phones.add(new Phone(matcher3.group(2), Integer.parseInt(matcher3.group(1)), -1));
        }
        return phones;
    }

    public String getFullName() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        Pattern pattern = Pattern.compile(FULLNAME_PATTERN);
        Matcher matcher = pattern.matcher(cvtext);
        String name = null;
        if (matcher.find()) name = matcher.group();
        if (name == null) throw new NoSuchElementException("NoSuchElementException");
        return name;
    }

    public String getFirstName() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (this.getFullName() == null) throw new NoSuchElementException("NoSuchElementException");
        String[] a = this.getFullName().split(" ");
        return a[0];
    }

    public String getMiddleName() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (this.getFullName() == null) throw new NoSuchElementException("NoSuchElementException");
        String[] a = this.getFullName().split(" ");
        if (a.length == 3) return a[1];
        return null;
    }

    public String getLastName() {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (this.getFullName() == null) throw new NoSuchElementException("NoSuchElementException");
        String[] a = this.getFullName().split(" ");
        return a[a.length - 1];
    }

    public void updateLastName(String newLastName) {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (this.getFullName() == null) throw new NoSuchElementException("NoSuchElementException");
        String str = this.getLastName();
        cvtext = cvtext.replace(str, newLastName);
    }

    public void updatePhone(Phone oldPhone, Phone newPhone) {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (!cvtext.contains(oldPhone.getNumber())) throw new IllegalArgumentException("IllegalArgumentException");
        cvtext = cvtext.replace(oldPhone.getNumber(), newPhone.getNumber());
    }

    public void hide(String piece) {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (!cvtext.contains(piece)) throw new IllegalArgumentException("IllegalArgumentException");
        pieces.add(piece);
        StringBuilder text = new StringBuilder(cvtext);
        char[] a = piece.toCharArray();
        int ns = numbers.size();
        int k = 0;
        while ((numbers.size() != ns + 1) && (k < a.length)) {
            if (piece.charAt(k) != ' ' && piece.charAt(k) != '.' && piece.charAt(k) != '@') {
                numbers.add(text.indexOf(piece) + k);
                indents.add(k);
            }
            k++;
        }
        int c = text.indexOf(piece);
        int b = text.indexOf(piece) + a.length;
        for (int i = c; i < b; i++) {
            if (cvtext.charAt(i) != ' ' && cvtext.charAt(i) != '.' && cvtext.charAt(i) != '@') {
                text.setCharAt(i, 'X');
            }
        }
        cvtext = text.toString();
    }

    public void hidePhone(String phone) {
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        if (!cvtext.contains(phone)) throw new IllegalArgumentException("IllegalArgumentException");
        pieces.add(phone);
        StringBuilder text = new StringBuilder(cvtext);
        char[] a = phone.toCharArray();
        int ns = numbers.size();
        int k = 0;
        while ((numbers.size() != ns + 1) && (k < a.length)) {
            if (phone.charAt(k) == '0' || phone.charAt(k) == '1' || phone.charAt(k) == '2' || phone.charAt(k) == '3' || phone.charAt(k) == '4' || phone.charAt(k) == '5' || phone.charAt(k) == '6' || phone.charAt(k) == '7' || phone.charAt(k) == '8' || phone.charAt(k) == '9') {
                numbers.add(text.indexOf(phone) + k);
                indents.add(k);
            }
            k++;
        }
        int c = text.indexOf(phone);
        int b = text.indexOf(phone) + a.length;
        for (int i = c; i < b; i++) {
            if (cvtext.charAt(i) == '0' || cvtext.charAt(i) == '1' || cvtext.charAt(i) == '2' || cvtext.charAt(i) == '3' || cvtext.charAt(i) == '4' || cvtext.charAt(i) == '5' || cvtext.charAt(i) == '6' || cvtext.charAt(i) == '7' || cvtext.charAt(i) == '8' || cvtext.charAt(i) == '9') {
                text.setCharAt(i, 'X');
            }
        }
        cvtext = text.toString();

    }

    public int unhideAll() {
        int res = 0;
        if (cvtext == null) throw new IllegalStateException("IllegalStateException");
        while (cvtext.contains("X")) {
            StringBuilder text = new StringBuilder(cvtext);
            int a = text.indexOf("X");
            String str = pieces.get(numbers.indexOf(a));
            int x = indents.get(numbers.indexOf(a));
            char[] r = str.toCharArray();
            int s = r.length;
            text.replace(a - x, a + s - x, str);
            cvtext = text.toString();
            res++;
        }
        return res;
    }
}
