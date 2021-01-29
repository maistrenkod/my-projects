package ru.ncedu.maistrenkodaria.ac;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Ziparchive {
    String arch;
    ArrayList<String> names;
    ArrayList<Long> sizes;
    ArrayList<String> names1not2;
    ArrayList<Long> sizes1not2;
    ArrayList<String> commonn;
    ArrayList<Long> commons;

    public ArrayList<String> getNames() {
        return this.names;
    }

    public ArrayList<String> getNames1not2() {
        return this.names1not2;
    }

    public ArrayList<Long> getSizes() {
        return this.sizes;
    }

    public ArrayList<Long> getSizes1not2() {
        return this.sizes1not2;
    }

    public ArrayList<String> getCommonn() {
        return this.commonn;
    }

    public ArrayList<Long> getCommons() {
        return this.commons;
    }

    public void readnameoffolder(Scanner in) {
        arch = in.nextLine();
    }

    public void openandrewritetoarrays() {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(arch));
            ZipEntry zip;
            names = new ArrayList<String>();
            names.clear();
            sizes = new ArrayList<Long>();
            sizes.clear();
            while ((zip = zin.getNextEntry()) != null) {
                names.add(zip.getName());
                sizes.add(zip.getSize());
            }
            zin.close();
            names1not2 = new ArrayList<String>();
            names1not2.clear();
            sizes1not2 = new ArrayList<Long>();
            sizes1not2.clear();
            names1not2.addAll(names);
            sizes1not2.addAll(sizes);
            commonn = new ArrayList<String>();
            commonn.clear();
            commons = new ArrayList<Long>();
            commons.clear();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
/*find items, that are contained in this Ziparchive, dut aren't contained in other
@param other - Ziparchive
    */
    public void find1not2(Ziparchive other) {
        for (int i = 0; i < other.getNames().size(); i++)
            if (this.names1not2.contains(other.getNames().get(i))) {
                int m = this.names1not2.indexOf(other.getNames().get(i));
                this.commonn.add(this.names1not2.get(m));
                this.names1not2.remove(m);//subtract matched by name
                this.commons.add(this.sizes1not2.get(m));
                this.sizes1not2.remove(m);
                i--;
            }
    }

    public ArrayList<String> renamed(Ziparchive other) {
        ArrayList<String> rename = new ArrayList<>();
        for (int i = 0; i < this.sizes1not2.size(); i++) {//compare the size of mismatched elements to find deleted,added elements
            if (other.getSizes1not2().contains(this.sizes1not2.get(i))) {
                rename.add(this.names1not2.get(i));
            }
        }
        return rename;
    }

    public ArrayList<String> deletedfor1addedfor2(Ziparchive other) {
        ArrayList<String> n1 = new ArrayList<>();
        n1.addAll(this.names1not2);
        ArrayList<Long> s1 = new ArrayList<>();
        s1.addAll(this.sizes1not2);
        ArrayList<String> n2 = new ArrayList<>();
        n2.addAll(other.names1not2);
        ArrayList<Long> s2 = new ArrayList<>();
        s2.addAll(other.sizes1not2);
        for (int i = 0; i < s1.size(); i++) {//compare the size of mismatched elements to find deleted,added elements
            if (s2.contains(s1.get(i))) {
                int k = s2.indexOf(s1.get(i));
                n1.remove(i);
                s1.remove(i);
                n2.remove(k);
                s2.remove(k);
                i--;
            }
        }
        return n1;
    }

    public ArrayList<String> updated(Ziparchive other) {
        ArrayList<String> update = new ArrayList<String>();
        update.addAll(commonn);
        for (int i = 0; i < this.commons.size(); i++) {
            if (other.commons.contains(this.commons.get(i))) {
                update.remove(i);
            }
        }
        return update;
    }

    public void file(Ziparchive other) {
        try {
            FileOutputStream fos = new FileOutputStream("report.txt");
            FileWriter foswr = new FileWriter("report.txt");
            foswr.write(this.arch + ":\n" + "deleted: ");
            for (String i : this.deletedfor1addedfor2(other)) {
                foswr.write(i + ", ");
            }
            foswr.write("\nrenamed: ");
            for (String i : this.renamed(other)) {
                foswr.write(i + ", ");
            }
            foswr.write("\nupdated: ");
            for (String i : this.updated(other)) {
                foswr.write(i + ", ");
            }
            foswr.write("\n\n" + other.arch + ":\nadded: ");
            for (String i : other.deletedfor1addedfor2(this)) {
                foswr.write(i + ", ");
            }
            foswr.write("\nrenamed: ");
            for (String i : other.renamed(this)) {
                foswr.write(i + ", ");
            }
            foswr.write("\nupdated: ");
            for (String i : other.updated(this)) {
                foswr.write(i + ", ");
            }
            foswr.close();
            fos.close();
            System.out.println("Check created file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
