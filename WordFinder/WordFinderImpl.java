package ru.skillbench.tasks.javaapi.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordFinderImpl implements WordFinder {

    String text;
    ArrayList<String> list;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null) throw new IllegalArgumentException("IllegalArgumentException");
        this.text = text;
    }

    public void setInputStream(InputStream is) throws IOException {
        if (is == null) throw new IllegalArgumentException("IllegalArgumentException");
        Scanner s = new Scanner(is);
        text = "";
        while (s.hasNext()) {
            text = text + s.nextLine();
        }
        if (text.matches("")) throw new IOException("IOException");
    }

    public void setFilePath(String filePath) throws IOException {
        if (filePath == null) throw new IllegalArgumentException("IllegalArgumentException");
        try {
            FileInputStream fin = new FileInputStream(filePath);
            Scanner s = new Scanner(fin);
            text = "";
            while (s.hasNext()) {
                text = text + s.nextLine();
            }
        }
        catch(IOException ex){
            throw ex;
        }
    }

    public void setResource(String resourceName) throws IOException {
        if (resourceName == null) throw new IllegalArgumentException("IllegalArgumentException");
        InputStream fin = WordFinderImpl.class.getResourceAsStream(resourceName);
        Scanner s = new Scanner(fin);
        text = "";
        while (s.hasNext()) {
            text = text + s.nextLine();
        }
        if (text.matches("")) throw new IOException("IOException");
    }

    public Stream<String> findWordsStartWith(String begin) {
        if (text == null) throw new IllegalStateException("IllegalStateException");
        Pattern pattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> words = new ArrayList<>();
        while (matcher.find()) {
            if (!words.contains(matcher.group())) words.add(matcher.group());
        }
        ArrayList<String> miniwords = new ArrayList<>();
        for (String i:words) {
            miniwords.add(i.toLowerCase());
        }
        list = new ArrayList<>();
        list.clear();
        if (begin == null || begin.matches("")){
            list.addAll(words);
        }
        else {
            String beg = begin.toLowerCase();
            for (int i = 0; i < miniwords.size(); i++) {
                if (miniwords.get(i).startsWith(beg)) list.add(words.get(i));
            }
        }
        Stream<String> stream = list.stream();
        return stream;
    }

    public void writeWords(OutputStream os) throws IOException {
        if (list == null|| text == null) throw new IllegalStateException("IllegalStateException");
        ArrayList<String> str = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            str.add(list.get(i).toLowerCase());
        }
        Collections.sort(str);
        for (int i = 0; i <str.size() - 1 ; i++) {
            list1.add(str.get(i) + " ");
        }
        list1.add(str.get(str.size()-1));
        str.clear();
        for (String i:list1) {
            os.write(i.getBytes());
        }
        os.close();
    }
}
