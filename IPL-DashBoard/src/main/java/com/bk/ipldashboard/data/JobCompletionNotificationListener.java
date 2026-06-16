package com.bk.ipldashboard.data;

import com.bk.ipldashboard.model.MatchOutput;
import com.bk.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
    public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        private final EntityManager jdbcTemplate;

        @Autowired
        public JobCompletionNotificationListener(EntityManager jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        @Transactional
        public void afterJob(JobExecution jobExecution) {

            if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");

                Map<String, Team> teamData = new HashMap<>();

                jdbcTemplate.createQuery("SELECT m.team1,count(*) FROM match_output m group by m.team1", Object[].class)
                        .getResultList()
                        .stream()
                        .map(e -> new Team((String) e[0],(long)e[1]))
                        .forEach(team -> teamData.put(team.getTeamName(),team));

                jdbcTemplate.createQuery("SELECT m.team2,count(*) FROM match_output m group by m.team2", Object[].class)
                        .getResultList()
                        .stream()
                        .forEach(e -> {
                            Team team = teamData.get((String) e[0]);
                            team.setTotalMatches(team.getTotalMatches() + (long)e[1]);
                        });

                jdbcTemplate.createQuery("SELECT m.matchWinner,count(*) FROM match_output m group by m.matchWinner", Object[].class)
                        .getResultList()
                        .stream()
                        .forEach(e -> {
                            Team team = teamData.get((String) e[0]);
                            if(team != null) team.setTotalWins((long)e[1]);
                        });

                teamData.values().forEach(team -> jdbcTemplate.persist(team));
                teamData.values().forEach(team -> System.out.println(team.toString()));
            }
        }
    }

