package ru.skillbench.tasks.text;

import java.io.PrintStream;
import java.util.*;

public class WordCounterImpl implements WordCounter {

    public static void main(String[] args) {
        WordCounter a = new WordCounterImpl();
        a.setText("Я тебя люблю");
        System.out.println(a.getWordCounts());
        System.out.println(a.getWordCountsSorted());
        a.print(a.getWordCountsSorted(), System.out);
    }

    String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Map<String, Long> getWordCounts() {
        if (text == null) throw new IllegalStateException("IllegalStateException");
        String helptext = text;
        helptext = helptext.toLowerCase();
        StringBuilder a = new StringBuilder(helptext);
        while (helptext.contains("<")){
            a.delete(a.indexOf("<"), a.indexOf(">")+1);
            helptext = a.toString();
        }
        helptext = helptext.replaceAll("\\W", " ");
        System.out.println(helptext);
        String [] ar = helptext.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String i:ar) {
            if (i.matches("\\w+")) {
                list.add(i);
            }
        }
        Map <String, Long> counter = new HashMap<>();
        for (String i:list) {
            long l = Collections.frequency(list, i);
            counter.put(i, l);
        }
        return counter;
    }

    public List<Map.Entry<String, Long>> getWordCountsSorted() {
        if (text == null) throw new IllegalStateException("IllegalStateException");
        Map <String, Long> counter = this.getWordCounts();
        List<Map.Entry<String,Long>> list = new LinkedList<Map.Entry<String,Long>>(counter.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                if (o1.getValue().compareTo(o2.getValue()) == 0) return o1.getKey().compareTo(o2.getKey());
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

    public <K extends Comparable<K>, V extends Comparable<V>> List<Map.Entry<K, V>> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K,V>> list = new LinkedList<Map.Entry<K,V>>(map.entrySet());
        Collections.sort(list, comparator);
        return list;
    }

    public <K, V> void print(List<Map.Entry<K, V>> entries, PrintStream ps) {
        for (Map.Entry<K, V> i:entries) {
            ps.println(i.getKey() + " " + i.getValue());
        }
    }
}
