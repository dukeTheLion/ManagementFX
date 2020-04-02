package model.dao;

import model.entities.Control;

import java.util.List;

public interface ControlDAO {
    void cardUpdate(Control obj);
    Double monthSalary(String name, String lastName, Integer month);
    Double monthSalary(Long id, Integer month);
    List<Control> findById(Long id);
    List<Control> findByName(String name, String lastName);
    List<Control> findByNameYMD(String date, String num, String name, String lastName);
}
