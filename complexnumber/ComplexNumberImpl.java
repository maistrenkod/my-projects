package ru.skillbench.tasks.basics.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumberImpl implements ComplexNumber, Cloneable {

    double real = 0;
    double imaginary = 0;

    public ComplexNumberImpl(double re, double im) {
        real = re;
        imaginary = im;
    }

    public ComplexNumberImpl(String value) {
        this.set(value);
    }

    public ComplexNumberImpl() {
        real = 0;
        imaginary = 0;
    }

    public double getRe() {
        return real;
    }

    public double getIm() {
        return imaginary;
    }

    public boolean isReal() {
        if (this.real != 0 && this.imaginary == 0) return true;
        else return false;
    }

    public void set(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    public void set(String value) throws NumberFormatException {
        if (value.contains("i") && (value.contains("+") || value.contains("-"))) {
            Pattern pattern = Pattern.compile("(-?[0-9]+\\.?[0-9]*)([+-]?[0-9]+\\.?[0-9]*)(i)");
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                real = Double.parseDouble(matcher.group(1));
                imaginary = Double.parseDouble(matcher.group(2));
            }
        } else {
            if (value.contains("i")) {
                Pattern pattern2 = Pattern.compile("(-?[0-9]+\\.?[0-9]*)(i)");
                Matcher matcher2 = pattern2.matcher(value);
                if (matcher2.matches()) imaginary = Double.parseDouble(matcher2.group(1));
            } else {
                Pattern pattern1 = Pattern.compile("-?[0-9]+\\.?[0-9]*");
                Matcher matcher1 = pattern1.matcher(value);
                if (matcher1.matches()) real = Double.parseDouble(matcher1.group());
            }

        }
        if (real == 0 && imaginary == 0) throw new NumberFormatException("NumberFormatException");
    }

    public ComplexNumber copy() {
        ComplexNumber copied = new ComplexNumberImpl();
        copied.set(this.real, this.imaginary);
        return copied;
    }

    public ComplexNumber clone() throws CloneNotSupportedException {
        ComplexNumber a = this.copy();
        if (a == null) throw new CloneNotSupportedException("CloneNotSupportedException");
        return a;
    }

    public String toString() {
        if (real == 0 && imaginary == 0) return "0.0";
        if (real != 0 && imaginary == 0) return Double.toString(real);
        if (real == 0 && imaginary != 0) return Double.toString(imaginary) + "i";
        if (real != 0 && imaginary != 0) {
            if (imaginary < 0) return Double.toString(real) + Double.toString(imaginary) + "i";
            else return Double.toString(real) + "+" + Double.toString(imaginary) + "i";
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
        ComplexNumber that = (ComplexNumber) other;
        return (real == that.getRe() && imaginary == that.getIm());
    }

    public int compareTo(ComplexNumber other) {
        double a = real * real + imaginary * imaginary;
        double b = other.getRe() * other.getRe() + other.getIm() * other.getIm();
        if (a > b) return 1;
        else {
            if (a == b) return 0;
            else return -1;
        }
    }

    public void sort(ComplexNumber[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                ComplexNumber buf;
                if (array[j].compareTo(array[j + 1]) > 0) {
                    buf = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = buf;
                }
            }
        }
    }

    public ComplexNumber negate() {
        real = real * (-1);
        imaginary = imaginary * (-1);
        return this;
    }

    public ComplexNumber add(ComplexNumber arg2) {
        real += arg2.getRe();
        imaginary += arg2.getIm();
        return this;
    }

    public ComplexNumber multiply(ComplexNumber arg2) {
        this.set(real * arg2.getRe() - imaginary * arg2.getIm(), imaginary * arg2.getRe() + real * arg2.getIm());
        return this;
    }
}
