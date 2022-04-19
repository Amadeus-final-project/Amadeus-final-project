package com.example.pds.model.vacations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer> {

    List<Vacation> getAllByIsApprovedFalseAndIsRejectedFalse();

    List<Vacation> getAllByProfileId(int id);

    Vacation findById(int id);

    List<Vacation> findAllByStartDateAndIsApproved(LocalDate startDate, boolean approved);

    List<Vacation> findAllByEndDateAndIsRejected(LocalDate endDate, boolean rejected);

}
