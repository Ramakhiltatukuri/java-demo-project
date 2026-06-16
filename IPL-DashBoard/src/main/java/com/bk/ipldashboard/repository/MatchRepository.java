package com.bk.ipldashboard.repository;

import com.bk.ipldashboard.model.MatchOutput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<MatchOutput, Long> {
    List<MatchOutput> getByTeam1OrTeam2(String teamName1, String teamName2, Pageable pageReq);


    default List<MatchOutput> findLatestMatchesByTeamName(String teamName, int count){
        return getByTeam1OrTeam2(teamName,teamName, PageRequest.of(0, count));
    }
}
