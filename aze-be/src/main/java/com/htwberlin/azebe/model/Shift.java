package com.htwberlin.azebe.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *  Shift Object linked to the shift table in the database.
 */
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

    /**
     * Instantiates a new Shift.
     *
     * @param begin the begin
     * @param user  the user
     */
    public Shift(LocalDateTime begin, User user) {
        this.begin = begin;
        this.user = user;
    }

    /**
     * Instantiates a new Shift.
     */
    public Shift() {

    }

    /**
     * Instantiates a new Shift.
     *
     * @param id    the id
     * @param begin the begin
     * @param end   the end
     * @param user  the user
     */
    public Shift(int id, LocalDateTime begin, LocalDateTime end, User user) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.user = user;
    }

    /**
     * Gets worktime.
     *
     * @return the worktime
     */
    public String getWorktime() {
        return worktime;
    }

    /**
     * Sets worktime.
     *
     * @param worktime the worktime
     */
    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets begin.
     *
     * @return the begin
     */
    public LocalDateTime getBegin() {
        return begin;
    }

    /**
     * Sets begin.
     *
     * @param begin the begin
     */
    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
