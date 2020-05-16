package model.entities;

import java.util.Objects;

public class Employee {
    private Long id;
    private String name;
    private String lastName;
    private Long CPF;
    private String email;
    private Double salaryHour;
    private Integer weeklyHour;
    private Long controlID;

    private Department department;

    public Employee(Long id, String name, String lastName, Long CPF, String email, Double salaryHour,
                    Integer weeklyHour, Department department, Long controlID) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.CPF = CPF;
        this.email = email;
        this.salaryHour = salaryHour;
        this.weeklyHour = weeklyHour;
        this.controlID = controlID;
        this.department = department;
    }

    public Employee () {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCPF() {
        return CPF;
    }

    public void setCPF(Long CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalaryHour() {
        return salaryHour;
    }

    public void setSalaryHour(Double salaryHour) {
        this.salaryHour = salaryHour;
    }

    public Integer getWeeklyHour() {
        return weeklyHour;
    }

    public void setWeeklyHour(Integer weeklyHour) {
        this.weeklyHour = weeklyHour;
    }

    public Long getControlID() {
        return controlID;
    }

    public void setControlID(Long controlID) {
        this.controlID = controlID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "\nEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CPF=" + CPF +
                ", email='" + email + '\'' +
                ", salaryHour=" + salaryHour +
                ", weeklyHour=" + weeklyHour +
                ", controlID=" + controlID +
                ", department=" + department +
                '}';
    }
}
