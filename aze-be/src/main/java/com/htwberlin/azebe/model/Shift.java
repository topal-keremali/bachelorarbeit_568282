package com.htwberlin.azebe.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime begin;
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    private String worktime;

    public Shift(LocalDateTime begin, User user) {
        this.begin = begin;
        this.user = user;
    }

    public Shift() {

    }

    public Shift(int id, LocalDateTime begin, LocalDateTime end, User user) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.user = user;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
