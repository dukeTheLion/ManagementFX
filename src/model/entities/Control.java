package model.entities;

public class Control {
    private Long ID;
    private String entry;
    private String exit;
    private Double daySalary;
    private Integer index;

    private Employee employee;

    public Control(Long ID, String entry, String exit, Double daySalary, Integer index, Employee employee) {
        this.ID = ID;
        this.entry = entry;
        this.exit = exit;
        this.daySalary = daySalary;
        this.index = index;
        this.employee = employee;
    }

    public Control(Long ID, String entry, String exit) {
        this.ID = ID;
        this.entry = entry;
        this.exit = exit;
        this.daySalary = 0d;
        this.index = null;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public Double getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(Double daySalary) {
        this.daySalary = daySalary;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "\nControl{" +
                "ID=" + ID +
                ", entry=" + entry +
                ", exit=" + exit +
                ", daySalary=" + daySalary +
                ", index=" + index +
                ", employee=" + employee +
                '}';
    }
}
