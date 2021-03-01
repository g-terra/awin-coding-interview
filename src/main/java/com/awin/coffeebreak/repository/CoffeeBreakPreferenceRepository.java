package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CoffeeBreakPreferenceRepository extends CrudRepository<CoffeeBreakPreference, Integer> {

    default List<CoffeeBreakPreference> getPreferencesForToday() {
        return getPreferencesForToday(
              Instant.now().truncatedTo(ChronoUnit.DAYS),
              Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)
        );
    }

    @Query("select p from CoffeeBreakPreference p " +
          "where p.requestedDate > :start " +
          "and p.requestedDate < :end")
    List<CoffeeBreakPreference> getPreferencesForToday(
          @Param("start") Instant start,
          @Param("end") Instant end
    );

}
