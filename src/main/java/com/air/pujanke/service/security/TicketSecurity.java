package com.air.pujanke.service.security;

import com.air.pujanke.model.entity.TicketEntity;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.TicketRepository;
import com.air.pujanke.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketSecurity {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public boolean isTicketOwner(Integer ticketId, String username) {
        Optional<TicketEntity> ticket = ticketRepository.findById(ticketId);
        return ticket.map(ticketEntity ->
                (ticketEntity.getUser().getUsername().equals(username))).orElse(false);
    }
}
