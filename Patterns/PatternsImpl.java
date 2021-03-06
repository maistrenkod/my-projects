package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsImpl implements Patterns {

    public Pattern getSQLIdentifierPattern() {
        Pattern p = Pattern.compile("[A-Za-z]\\w{0,29}");
        return p;
    }

    public Pattern getEmailPattern() {
        Pattern p = Pattern.compile("([A-Za-z0-9][\\w\\-\\.]{0,20}[A-Za-z0-9])@([A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9].)+(ru|com|net|org)");
        return p;
    }

    public Pattern getHrefTagPattern() {
        Pattern p = Pattern.compile("<[Aa]\\s*[Hh][Rr][Ee][fF]\\s*=\\s*(\".*\"|[^\\s]*)\\s*/?>");
        return p;
    }

    public List<String> findAll(String input, Pattern pattern) {
        ArrayList<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    public int countMatches(String input, String regex) {
        int counter = 0;
        input = input.toLowerCase();
        regex = regex.toLowerCase();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()){
            counter++;
        }
        return counter;
    }
}
