package com.tpcc.soccer.manager.service;

import com.tpcc.soccer.manager.dao.InvitationEventRepository;
import com.tpcc.soccer.manager.dao.InvitationTeamRepository;
import com.tpcc.soccer.manager.dto.*;
import com.tpcc.soccer.manager.entity.InvitationEvent;
import com.tpcc.soccer.manager.entity.InvitationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationService {
    @Autowired
    private InvitationTeamRepository invitationTeamRepository;
    @Autowired
    private InvitationEventRepository invitationEventRepository;

    public InvitationTeamResponse getTeamInvitation(int id) {
        InvitationTeam invitation = invitationTeamRepository.findById(id).get();
        return InvitationTeamResponse.builder().senderId(invitation.getSenderId()).
                receiverId(invitation.getReceiverId()).invitation_id(id).
                teamId(invitation.getTeamId()).status(invitation.getStatus()).
                createTime(invitation.getCreateTime()).responseTime(invitation.getResponseTime()).build();
    }
    public InvitationEventResponse getEventInvitation(int id) {
        InvitationEvent invitation = invitationEventRepository.findById(id).get();
        return InvitationEventResponse.builder().senderId(invitation.getSenderId()).
                receiverId(invitation.getReceiverId()).invitation_id(id).
                eventId(invitation.getEventId()).status(invitation.getStatus()).
                createTime(invitation.getCreateTime()).responseTime(invitation.getResponseTime()).build();
}

    public InvitationTeamResponse addTeamInvitation(InvitationTeamRequest ir){
        Timestamp createTime = new Timestamp((System.currentTimeMillis()/1000)*1000L);
        InvitationTeam invitation = InvitationTeam.builder().teamId(ir.getTeamId()).senderId(ir.getSenderId()).
                receiverId(ir.getReceiverId()).status(0).createTime(createTime).build();
        InvitationTeam newInvitation = invitationTeamRepository.save(invitation);
        return InvitationTeamResponse.builder().invitation_id(newInvitation.getInvitationId()).
                senderId(newInvitation.getSenderId()).
                receiverId(newInvitation.getReceiverId()).status(newInvitation.getStatus()).
                createTime(newInvitation.getCreateTime()).build();
    }
    public InvitationEventResponse addEventInvitation(InvitationEventRequest ir){
        Timestamp createTime = new Timestamp((System.currentTimeMillis()/1000)*1000L);
        InvitationEvent invitation = InvitationEvent.builder().eventId(ir.getEventId()).senderId(ir.getSenderId()).
                receiverId(ir.getReceiverId()).status(0).createTime(createTime).build();
        InvitationEvent newInvitation = invitationEventRepository.save(invitation);
        return InvitationEventResponse.builder().invitation_id(newInvitation.getInvitationId()).
                senderId(newInvitation.getSenderId()).
                receiverId(newInvitation.getReceiverId()).status(newInvitation.getStatus()).
                createTime(newInvitation.getCreateTime()).build();
    }

    public InvitationTeamResponse deleteTeamInvitation(int id){
        InvitationTeam invitation = invitationTeamRepository.findById(id).get();
        invitationTeamRepository.deleteById(id);
        return InvitationTeamResponse.builder().invitation_id(id).teamId(invitation.getTeamId()).
                senderId(invitation.getSenderId()).receiverId(invitation.getReceiverId()).
                status(invitation.getStatus()).build();
    }
    public InvitationEventResponse deleteEventInvitation(int id){
        InvitationEvent invitation = invitationEventRepository.findById(id).get();
        invitationEventRepository.deleteById(id);
        return InvitationEventResponse.builder().invitation_id(id).eventId(invitation.getEventId()).
                senderId(invitation.getSenderId()).receiverId(invitation.getReceiverId()).
                status(invitation.getStatus()).build();

    }

    public InvitationListResponse getUserTeamInvitation(int id){
        List<InvitationTeam> invitations = (List<InvitationTeam>) invitationTeamRepository.findAll();
        List<InvitationTeam> invitationList = new ArrayList<>();
        for (InvitationTeam invitation : invitations) {
            if (invitation.getSenderId() == id) {
                invitationList.add(invitationTeamRepository.findById(invitation.getInvitationId()).get());
            }
            if (invitation.getReceiverId() == id) {
                invitationList.add(invitationTeamRepository.findById(invitation.getInvitationId()).get());
            }
        }
        List<InvitationTeamResponse> invitationTeamResponses = new ArrayList<>();
        for (InvitationTeam invitation : invitations){
            invitationTeamResponses.add(InvitationTeamResponse.builder().invitation_id(invitation.getInvitationId()).
                    teamId(invitation.getTeamId()).senderId(invitation.getSenderId()).
                    receiverId(invitation.getReceiverId()).status(invitation.getStatus()).
                    createTime(invitation.getCreateTime()).build());
        }
        return InvitationListResponse.builder().invitationTeamResponses(invitationTeamResponses).build();
    }

    public InvitationListResponse getUserEventInvitation(int id){
        List<InvitationEvent> invitations = (List<InvitationEvent>) invitationEventRepository.findAll();
        List<InvitationEvent> invitationList = new ArrayList<>();
        for (InvitationEvent invitation : invitations) {
            if (invitation.getSenderId() == id) {
                invitationList.add(invitationEventRepository.findById(invitation.getInvitationId()).get());
            }
            if (invitation.getReceiverId() == id) {
                invitationList.add(invitationEventRepository.findById(invitation.getInvitationId()).get());
            }
        }

        List<InvitationEventResponse> invitationEventResponses = new ArrayList<>();
        for (InvitationEvent invitation : invitations) {
            invitationEventResponses.add(InvitationEventResponse.builder().invitation_id(invitation.getInvitationId()).
                    eventId(invitation.getEventId()).senderId(invitation.getSenderId()).
                    receiverId(invitation.getReceiverId()).status(invitation.getStatus()).
                    createTime(invitation.getCreateTime()).build());
        }
        return InvitationListResponse.builder().invitationEventResponses(invitationEventResponses).build();
    }

    public InvitationTeamResponse updateTeamInvitation(UpdateInvitationRequest request) {
        Timestamp responseTime = new Timestamp((System.currentTimeMillis()/1000)*1000L);
        InvitationTeam invitationToUpdate = invitationTeamRepository.findById(request.getInvitationId()).get();
        invitationToUpdate.setStatus(request.getStatus());
        invitationToUpdate.setResponseTime(responseTime);
        invitationTeamRepository.save(invitationToUpdate);
        return InvitationTeamResponse.builder().teamId(invitationToUpdate.getTeamId()).invitation_id(invitationToUpdate.getInvitationId()).
                senderId(invitationToUpdate.getSenderId()).receiverId(invitationToUpdate.getReceiverId()).
                status(invitationToUpdate.getStatus()).createTime(invitationToUpdate.getCreateTime()).build();
    }

    public InvitationEventResponse updateEventInvitation(UpdateInvitationRequest request) {
        Timestamp responseTime = new Timestamp((System.currentTimeMillis()/1000)*1000L);
        InvitationEvent invitationToUpdate = invitationEventRepository.findById(request.getInvitationId()).get();
        invitationToUpdate.setStatus(request.getStatus());
        invitationToUpdate.setResponseTime(responseTime);
        invitationEventRepository.save(invitationToUpdate);
        return InvitationEventResponse.builder().eventId(invitationToUpdate.getEventId()).invitation_id(invitationToUpdate.getInvitationId()).
                senderId(invitationToUpdate.getSenderId()).receiverId(invitationToUpdate.getReceiverId()).
                status(invitationToUpdate.getStatus()).createTime(invitationToUpdate.getCreateTime()).build();
    }

}
