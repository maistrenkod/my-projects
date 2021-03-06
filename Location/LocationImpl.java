package ru.skillbench.tasks.basics.entity;

public class LocationImpl implements Location {
    String name;
    Type type;
    Location parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public String getParentName() {
        if (this.parent == null) return "--";
        return parent.getName();
    }

    public Location getTopLocation() {
        if (this.parent == null) return this;
        return parent.getTopLocation();
    }

    public boolean isCorrect() {
        if (this.parent != null) {
            if (this.getType().compareTo(parent.getType()) <= 0) return false;
            else parent.isCorrect();
        }
        return true;
    }

    public boolean checkname() {
        int counter = 0;
        for (int i = 0; i < this.getName().length(); i++) {
            if (this.getName().charAt(i) == ' ') counter += 1;
        }
        if (counter > 1) return true;
        else return false;
    }

    public String getAddress() {
        String str = "";
        if (this.parent != null) {
            if (this.getName().contains(".") && !this.checkname()) {
                str += this.getName() + ", " + parent.getAddress();
            } else {
                if ((this.getType() == Type.REGION) || (this.getType() == Type.COUNTRY)) {
                    if (this.getType() == Type.REGION) {
                        str += this.getName() + " " + this.getType().getNameForAddress() + ", " + parent.getAddress();
                    }
                    if (this.getType() == Type.COUNTRY) {
                        str += this.getName() + ", " + parent.getAddress();
                    }
                } else {
                    str += this.getType().getNameForAddress() + this.getName() + ", " + parent.getAddress();
                }
            }
        } else {
            if (this.getName().contains(".")) {
                str += this.getName();
            } else {
                if ((this.getType() == Type.REGION) || (this.getType() == Type.COUNTRY)) {
                    if (this.getType() == Type.REGION) {
                        str += this.getName() + " " + this.getType().getNameForAddress();
                    }
                    if (this.getType() == Type.COUNTRY) {
                        str += this.getName();
                    }
                } else {
                    str += this.getType().getNameForAddress() + " " + this.getName();
                }
            }

        }
        return str;
    }

    public String toString() {
        return this.getName() + " (" + this.getType().toString() + ")";
    }
}
