package com.htwberlin.azebe.repository;

import com.htwberlin.azebe.model.Shift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Shift repository.
 */
@Repository
public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    /**
     * Gets all Shift by id in a specific time.
     *
     * @param id    the id
     * @param begin the begin
     * @param end   the end
     * @return all by id
     */
    @Query(value = "SELECT * FROM shift WHERE uid=?1 AND begin BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Shift> getAllById(int id, Date begin, Date end);

    /**
     * Find shift of today.
     *
     * @param id    the id
     * @param begin the begin
     * @param end   the end
     * @return  optional
     */
    @Query(value = "SELECT * FROM shift WHERE uid=?1 and begin BETWEEN ?2 and ?3", nativeQuery = true)
    Optional<Shift> findShiftToday(int id, LocalDateTime begin, LocalDateTime end);
}
