package com.tpcc.soccer.manager.service;

import com.tpcc.soccer.manager.dao.EventParticipantRepository;
import com.tpcc.soccer.manager.dao.UserRepository;
import com.tpcc.soccer.manager.dto.EventParticipantResponse;
import com.tpcc.soccer.manager.dto.ParticipantListResponse;
import com.tpcc.soccer.manager.dto.UserResponseWithId;
import com.tpcc.soccer.manager.entity.*;
import com.tpcc.soccer.manager.exceptions.HostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventParticipantService {
    @Autowired
    private EventParticipantRepository eventParticipantRepository;
    @Autowired
    private UserRepository userRepository;

    public ParticipantListResponse getParticipant(int id) {
        List<EventParticipant> participants = (List<EventParticipant>) eventParticipantRepository.findAll();
        List<User> users = new ArrayList<>();

        for (EventParticipant ep : participants) {
            if (ep.getEventId() == id) {
                users.add(userRepository.findById(ep.getUserId()).get());
            }
        }

        List<UserResponseWithId> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(UserResponseWithId.builder().userId(user.getUserId()).
                    userName(user.getUserName()).email(user.getEmail()).build());
        }

        return ParticipantListResponse.builder().userResponses(userResponses).build();
    }

    public EventParticipantResponse deleteParticipant(int userId, int eventId) throws HostException {
        List<EventParticipant> participants = (List<EventParticipant>) eventParticipantRepository.findAll();
        int id = -1;
        EventParticipant participant = new EventParticipant();
        for (EventParticipant ep : participants) {
            if (ep.getUserId() == userId && ep.getEventId() == eventId) {
                if (ep.getIsHost() == 1) {
                    throw new HostException();
                }
                id = ep.getEventParticipantId();
                participant = ep;
                break;
            }
        }

        if (id == -1) return null;

        EventParticipantCompositeKey ck = new EventParticipantCompositeKey();
        ck.setEventId(eventId);
        ck.setEventParticipantId(id);
        ck.setUserId(userId);
        eventParticipantRepository.deleteById(ck);
        return EventParticipantResponse.builder().eventParticipantId(participant.getEventParticipantId()).
                userId(participant.getUserId()).eventId(participant.getEventId()).isHost(participant.getIsHost()).build();
    }

    public EventParticipantResponse addEventParticipant(int userId, int eventId, int isHost) {
        Timestamp createTime = new Timestamp((System.currentTimeMillis() / 1000) * 1000L);
        EventParticipant eventParticipant = EventParticipant.builder().eventParticipantCreateTime(createTime).isHost(1).eventId(eventId).userId(userId).build();
        EventParticipant result = eventParticipantRepository.save(eventParticipant);
        return EventParticipantResponse.builder().isHost(1).eventId(eventId).userId(userId).eventParticipantId(result.getEventParticipantId()).build();
    }
}