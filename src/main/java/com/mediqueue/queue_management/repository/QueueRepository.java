package com.mediqueue.queue_management.repository;

import com.mediqueue.queue_management.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {

    Optional<Queue> findByClinicIdAndDate(int clinicId, LocalDate date);
}
