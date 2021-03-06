package ru.skillbench.tasks.basics.entity;

public class EmployeeImpl implements Employee {
    int salary = 1000;
    String firstname;
    String lastname;
    Employee manager;

    public int getSalary() {
        return salary;
    }

    public void increaseSalary(int value) {
        salary += value;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getManagerName() {
        if (manager == null) return "No manager";
        return manager.getFullName();
    }

    public Employee getTopManager() {
        if(!this.getManagerName().equals("No manager")){
            return manager.getTopManager();
        }
        return this;
    }
}
