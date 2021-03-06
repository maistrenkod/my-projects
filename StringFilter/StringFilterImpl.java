package ru.skillbench.tasks.javaapi.collections;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFilterImpl implements StringFilter {

    public static void main(String[] args) {
        StringFilter a = new StringFilterImpl();
        a.add("1234");
        a.add("1.00");
        a.add("9.99");
        String str = "#.##+";
        System.out.println(a.getCollection());
        Iterator<String> it = a.getStringsByNumberFormat(str);
        while (it.hasNext()) System.out.println(it.next());
    }

    ArrayList<String> coll;

    public  StringFilterImpl(){
        coll = new ArrayList<>();
    }

    public void add(String s) {
        if (s == null) {
            if (coll.contains(null)) return;
            else {
                coll.add(s);
                return;
            }
        }
        if (!coll.contains(s.toLowerCase())) coll.add(s.toLowerCase());
    }

    public boolean remove(String s) {
        return coll.remove(s.toLowerCase());
    }

    public void removeAll() {
        coll.clear();
    }

    public Collection<String> getCollection() {
        return coll;
    }

    public Iterator<String> getStringsContaining(String chars) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(coll);
        list.remove(null);
        if (chars == null || chars.matches("")) {
            Iterator<String> it = coll.iterator();
            return it;
        }
        ArrayList<String> s = new ArrayList<>();
        for (String i : list) {
            if (i.contains(chars)) s.add(i);
        }
        Iterator<String> iter = s.iterator();
        return iter;
    }

    public Iterator<String> getStringsStartingWith(String begin) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(coll);
        list.remove(null);
        if (begin == null || begin.matches("")) {
            Iterator<String> it = coll.iterator();
            return it;
        }
        ArrayList<String> s = new ArrayList<>();
        String str = begin.toLowerCase();
        Pattern pattern = Pattern.compile(str + ".*");
        for (String i : list) {
            Matcher matcher = pattern.matcher(i);
            if (matcher.matches()) s.add(i);
        }
        Iterator<String> iter = s.iterator();
        return iter;
    }

    public Iterator<String> getStringsByNumberFormat(String format) {
        String h = format;
        h = h.replaceAll("-", "");
        h = h.replaceAll("#", "");
        h = h.replaceAll("\\(", "");
        h = h.replaceAll("\\)", "");
        h = h.replaceAll("\\.", "");
        ArrayList<String> list = new ArrayList<>();
        if (!h.matches("")){
            Iterator<String> iit = coll.iterator();
            return iit;
        }
        list.addAll(coll);
        list.remove(null);
        if (format == null || format.matches("")) {
            Iterator<String> it = coll.iterator();
            return it;
        }
        String str = format.replaceAll("#", "[0-9]");
        String as = str.substring(0, str.indexOf("."));
        as = as + "\\" + "." + str.substring(str.indexOf(".")+1);
        ArrayList<String> s = new ArrayList<>();
        for (String i:list) {
            if (i.matches(as)) s.add(i);
        }
        Iterator<String> iter = s.iterator();
        return iter;
    }

    public Iterator<String> getStringsByPattern(String pattern) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(coll);
        list.remove(null);
        if (pattern == null || pattern.matches("")) {
            Iterator<String> it = coll.iterator();
            return it;
        }
        String str = pattern.toLowerCase();
        ArrayList<String> s = new ArrayList<>();
        String[] pat = str.split("\\*");
        if (!str.contains("*")) {
            for (String i : list) {
                if (i.matches(pat[0])) s.add(i);
            }
        }
        if (str.contains("*") && !str.substring(str.indexOf("*")+1).contains("*")) {
            if (str.startsWith("*")) {
                for (String i : list) {
                    if (i.endsWith(pat[1])) s.add(i);
                }
            }
            if (str.endsWith("*")) {
                for (String i : list) {
                    if (i.startsWith(pat[0])) s.add(i);
                }
            }
            if (str.substring(1,str.length()-1).contains("*")) {
                for (String i : list) {
                    if (i.startsWith(pat[0]) && i.endsWith(pat[1])) s.add(i);
                }
            }
        }
        if (str.contains("*") && str.substring(str.indexOf("*")+1).contains("*")) {
            if (str.startsWith("*") && str.endsWith("*")) {
                for (String i : list) {
                    if (i.contains(pat[1]) && !i.startsWith(pat[1])) s.add(i);
                }
            }
            if (str.startsWith("*") && str.substring(1).contains("*") && !str.endsWith("*")) {
                for (String i : list) {
                    if (i.endsWith(pat[2]) && i.contains(pat[1]) && !i.startsWith(pat[1])) s.add(i);
                }
            }
            if (str.endsWith("*") && str.substring(0, str.length() - 1).contains("*") && !str.startsWith("*")) {
                for (String i : list) {
                    if (i.startsWith(pat[0]) && i.contains(pat[1])) s.add(i);
                }
            }
            if (!str.endsWith("*") && !str.startsWith("*")) {
                for (String i : list) {
                    if (i.startsWith(pat[0]) && i.endsWith(pat[2]) && i.contains(pat[1])) s.add(i);
                }
            }
        }
        Iterator<String> iter = s.iterator();
        return iter;
    }
}
