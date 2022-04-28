package ru.graduation.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.graduation.restaurantvoting.model.VotingResult;

import java.time.LocalDateTime;
import java.util.List;

public interface VotingResultRepository extends BaseRepository<VotingResult> {
    @Query("Select v from VotingResult  v where v.registered >= :startDate")
    List<VotingResult> findAllTodayVotes(@Param("startDate") LocalDateTime startDate);
}