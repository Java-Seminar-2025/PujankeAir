package com.air.pujanke.repository.spec;

import com.air.pujanke.model.dto.FlightSearchFormDto;
import com.air.pujanke.model.entity.FlightEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        return (root, query, cb) -> cb.greaterThan(root.get("takeoffDate"), LocalDate.now());
    }

    public static Specification<FlightEntity> fromDto(FlightSearchFormDto dto) {
        return Specification
                .where(originAirportIcaoEquals(dto.originAirportIcao()))
                .and(destinationAirportIcaoEquals(dto.destinationAirportIcao()))
                .and(takeoffDateEquals(dto.takeoffDate()))
                .and(baseFareLessThanOrEqual(dto.baseFareLesserThan()))
                .and(notExpired());
    }
}
