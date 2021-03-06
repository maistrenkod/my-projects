package ru.skillbench.tasks.text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class ContactCardImpl implements ContactCard {
    String fn;
    String org;
    char gender;
    Calendar Bday = new GregorianCalendar();
    ArrayList<String> typ = new ArrayList<>();
    ArrayList<String> voice = new ArrayList<>();

    public ContactCardImpl getInstance(Scanner scanner) throws NoSuchElementException, InputMismatchException {
        StringBuilder stringBuilder = new StringBuilder();
        String op = stringBuilder.toString();
        while (!op.contains("END:VCARD")) {
            stringBuilder.append(scanner.nextLine());
            stringBuilder.append("\n");
            op = stringBuilder.toString();
        }

        int n1 = stringBuilder.lastIndexOf("BEGIN:VCARD");
        int n2 = stringBuilder.lastIndexOf("FN:");
        int n3 = stringBuilder.lastIndexOf("ORG:");
        int n4 = stringBuilder.lastIndexOf("END:VCARD");
        if (n1 == -1 || n2 == -1 || n3 == -1 || n4 == -1) throw new NoSuchElementException("NoSuchElementException");

        String s = stringBuilder.toString();
        String[] ss = s.split("\n");
        for (String i : ss) {
            if (i.contains("FN")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                this.fn = s0[1];
            }
            if (i.contains("ORG")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                this.org = s0[1];
            }
            if (i.contains("GENDER")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                if (!s0[1].matches("F|M")) throw new InputMismatchException("InputMismatchException");
                this.gender = s0[1].charAt(0);
            }
            if (i.contains("BDAY")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                if (!s0[1].matches("\\d{2}-\\d{2}-\\d{4}")) throw new InputMismatchException("InputMismatchException");
                DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    this.Bday.setTime(form.parse(s0[1]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (i.contains("TEL")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s00 = i.split("=|;|,|:");
                typ.add(s00[2]);
                if (!s00[3].matches("\\d{10}")) throw new InputMismatchException("InputMismatchException");
                voice.add(s00[3]);
            }
        }
        return ContactCardImpl.this;
    }

    public ContactCard getInstance(String data) throws NoSuchElementException, InputMismatchException {
        StringBuilder str = new StringBuilder();
        String op1 = str.toString();
        Scanner scanner = new Scanner(data);
        while (!op1.contains("END:VCARD")) {
            str.append(scanner.nextLine());
            str.append("\n");
            op1 = str.toString();
        }
        int n11 = str.lastIndexOf("BEGIN:VCARD");
        int n22 = str.lastIndexOf("FN:");
        int n33 = str.lastIndexOf("ORG:");
        int n44 = str.lastIndexOf("END:VCARD");
        if (n11 == -1 || n22 == -1 || n33 == -1 || n44 == -1)
            throw new NoSuchElementException("NoSuchElementException");
        String s1 = str.toString();
        String[] ss1 = s1.split("\n|\r");
        for (String i : ss1) {
            if (i.contains("FN")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                this.fn = s0[1];
            }
            if (i.contains("ORG")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                this.org = s0[1];
            }
            if (i.contains("GENDER")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                if (!s0[1].matches("F|M")) throw new InputMismatchException("InputMismatchException");
                this.gender = s0[1].charAt(0);
            }
            if (i.contains("BDAY")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] s0 = i.split(":");
                if (!s0[1].matches("\\d{2}-\\d{2}-\\d{4}"))
                    throw new InputMismatchException("InputMismatchException");
                DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    this.Bday.setTime(form.parse(s0[1]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (i.contains("TEL")) {
                if (!i.contains(":")) throw new InputMismatchException("InputMismatchException");
                String[] ss0 = i.split("=|;|,|:");
                this.typ.add(ss0[2]);
                if (!ss0[3].matches("\\d{10}")) throw new InputMismatchException("InputMismatchException");
                this.voice.add(ss0[3]);
            }
        }
        return ContactCardImpl.this;

    }

    public String getFullName() {
        return fn;
    }

    public String getOrganization() {
        return org;
    }

    public boolean isWoman() {
        if (gender == 'F') return true;
        else return false;
    }

    public Calendar getBirthday() throws NoSuchElementException {
        if (Bday == null) throw new NoSuchElementException("NoSuchElementException");
        return Bday;
    }

    public Period getAge() throws NoSuchElementException {
        if (Bday == null) throw new NoSuchElementException("NoSuchElementException");
        LocalDate today = LocalDate.now();
        LocalDate start = LocalDate.of(Bday.get(GregorianCalendar.YEAR), Bday.get(GregorianCalendar.MONTH), Bday.get(GregorianCalendar.DAY_OF_MONTH));
        Period aage = Period.between(start, today);
        return aage;
    }

    public int getAgeYears() {
        if (Bday == null) throw new NoSuchElementException("NoSuchElementException");
        LocalDate today = LocalDate.now();
        LocalDate start = LocalDate.of(Bday.get(GregorianCalendar.YEAR), Bday.get(GregorianCalendar.MONTH), Bday.get(GregorianCalendar.DAY_OF_MONTH));
        Period aage = Period.between(start, today);
        return aage.getYears();
    }

    public String getPhone(String type) {
        for (int j = 0; j < typ.size(); j++) {
            if (typ.get(j).equals(type)) {
                String ph = voice.get(j);
                String ret = "(" + ph.charAt(0) + ph.charAt(1) + ph.charAt(2) + ")" + " " + ph.charAt(3) + ph.charAt(4) + ph.charAt(5) + "-" + ph.charAt(6) + ph.charAt(7) + ph.charAt(8) + ph.charAt(9);
                return ret;
            }
        }
        throw new NoSuchElementException("NoSuchElementException");
    }
}
