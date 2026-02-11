package com.air.pujanke.repository.spec;

import com.air.pujanke.model.dto.FlightSearchFormDto;
import com.air.pujanke.model.entity.FlightEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class FlightSpecification {
    private FlightSpecification() {}

    public static Specification<FlightEntity> originAirportIcaoEquals(String originAirportIcao) {
        return (root, query, cb) ->
                (originAirportIcao == null || originAirportIcao.isEmpty())
                        ? cb.conjunction()
                        : cb.equal(root.get("takeoffAirport").get("icaoCode"), originAirportIcao);
    }

    public static Specification<FlightEntity> destinationAirportIcaoEquals(String destinationAirportIcao) {
        return (root, query, cb) ->
                (destinationAirportIcao == null || destinationAirportIcao.isEmpty())
                        ? cb.conjunction()
                        : cb.equal(root.get("destinationAirport").get("icaoCode"), destinationAirportIcao);
    }

    public static Specification<FlightEntity> takeoffDateEquals(LocalDate takeoffDate) {
        return (root, query, cb) ->
                takeoffDate == null
                        ? cb.conjunction()
                        : cb.equal(root.get("takeoffDate"), takeoffDate);
    }

    public static Specification<FlightEntity> baseFareLessThanOrEqual(BigDecimal baseFareLesserThan) {
        return (root, query, cb) ->
                baseFareLesserThan == null
                        ? cb.conjunction()
                        : cb.lessThanOrEqualTo(root.get("baseFare"), baseFareLesserThan);
    }

    public static Specification<FlightEntity> notExpired() {
        return (root, query, cb) -> {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            return cb.or(
                    cb.greaterThan(root.get("takeoffDate"), today),
                    cb.and(
                            cb.equal(root.get("takeoffDate"), today),
                            cb.greaterThan(root.get("takeoffTime"), now)
                    )
            );
        };
    }

    public static Specification<FlightEntity> airportsNotNull() {
        return (root, query, cb) -> cb.and(cb.isNotNull(root.get("takeoffAirport")), cb.isNotNull(root.get("destinationAirport")));
    }


    public static Specification<FlightEntity> fromDto(FlightSearchFormDto dto) {
        return Specification
                .where(originAirportIcaoEquals(dto.originAirportIcao()))
                .and(destinationAirportIcaoEquals(dto.destinationAirportIcao()))
                .and(takeoffDateEquals(dto.takeoffDate()))
                .and(baseFareLessThanOrEqual(dto.baseFareLesserThan()))
                .and(notExpired())
                .and(airportsNotNull());
    }
}
