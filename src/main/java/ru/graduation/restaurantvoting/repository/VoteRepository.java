package ru.graduation.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.restaurantvoting.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends BaseRepository<Vote> {
    @Transactional(readOnly = true)
    @Query("Select new Vote(v.registered, v.restaurantId, v.userId) from Vote v"
            + " where v.registered >= :startDate and v.registered <= :endDate")
    List<Vote> findAllTodayVotes(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Transactional(readOnly = true)
    @Query("Select count (v.restaurantId) from Vote v "
            + " where v.registered >= :startDate and v.registered <= :endDate and v.userId = :userId ")
    Integer countTodayVotesByUserID(@Param("userId") Integer userId,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);
}