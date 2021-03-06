package ru.skillbench.tasks.basics.math;
import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {
    double[] array = new double[0];

    //@Override
    public void set(double... elements) {
        int l = 0;
        for (double i : elements) {
            l++;
        }
        int j = 0;
        double [] array1 = new double[l];
        for (double i : elements) {
            array1[j] = i;
            j++;
        }
        array = Arrays.copyOf(array1,l);
    }

   // @Override
    public double[] get() {
        return array;
    }

   // @Override
    public ArrayVector clone() {
        double[] clonearray = new double[array.length];
        System.arraycopy(array, 0, clonearray, 0, array.length);
        ArrayVector cl = new ArrayVectorImpl();
        cl.set(clonearray);
        return cl;
    }

   // @Override
    public int getSize() {
        return array.length;
    }

    //@Override
    public void set(int index, double value) {
        if(index < 0) return;
        int n12 = this.array.length;
        if(index >= n12){
            double [] array12 = new double[index + 1];
            System.arraycopy(array,0,array12,0,array.length);
            array12[index] = value;
            array = Arrays.copyOf(array12,index + 1);
            return;
        }
        if(index > 0 && index < n12) {
            array[index] = value;
            return;
        }
    }

    //@Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        return array[index];
    }

   // @Override
    public double getMax() {
        double buf1 = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > buf1){
                buf1 = array[i];
            }
        }
        return buf1;
    }

   // @Override
    public double getMin() {
        double buf = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < buf){
                buf = array[i];
            }
        }
        return buf;
    }

   // @Override
    public void sortAscending() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    double buf = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = buf;
                }
            }
        }
    }

    //@Override
    public void mult(double factor) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] * factor;
        }
    }

    //@Override
    public ArrayVector sum(ArrayVector anotherVector) {
        if (array.length <= anotherVector.getSize()) {
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] + anotherVector.get(i);
            }
        } else {
            for (int i = 0; i < anotherVector.getSize(); i++) {
                array[i] = array[i] + anotherVector.get(i);
            }
        }
        return ArrayVectorImpl.this;
    }

    //@Override
    public double scalarMult(ArrayVector anotherVector) {
        if (array.length <= anotherVector.getSize()) {
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] * anotherVector.get(i);
            }
        } else {
            for (int i = 0; i < anotherVector.getSize(); i++) {
                array[i] = array[i] * anotherVector.get(i);
            }
        }
        double sum1 = 0;
        for (int i = 0; i < array.length; i++) {
            sum1 += array[i];
        }
        return sum1;
    }

    //@Override
    public double getNorm() {
        return Math.sqrt(scalarMult(ArrayVectorImpl.this));
    }

}
