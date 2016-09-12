package org.thiki.kanban.teams.invitation;

import freemarker.template.TemplateException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationService;
import org.thiki.kanban.teams.team.Team;
import org.thiki.kanban.teams.team.TeamsService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * Created by xutao on 9/12/16.
 */
@Service
public class InvitationService {
    private static final String TEAM_INVITATION_TEMPLATE = "team-invitation-template.ftl";
    @Resource
    private InvitationPersistence invitationPersistence;
    @Resource
    private MailService mailService;
    @Resource
    private RegistrationService registrationService;

    @Resource
    private TeamsService teamsService;

    public Invitation invite(String userName, String teamId, Invitation invitation) throws TemplateException, IOException, MessagingException {
        Registration invitee = registrationService.findByName(invitation.getInvitee());

        Team team = teamsService.findById(teamId);
        invitation.setInviter(userName);
        invitation.setTeamId(teamId);
        invitationPersistence.invite(invitation);

        InvitationEmail invitationEmail = new InvitationEmail();
        invitationEmail.setReceiver(invitee.getEmail());
        invitationEmail.setInviter(userName);
        invitationEmail.setInvitee(invitee.getName());
        invitationEmail.setTeamName(team.getName());

        Link invitationLink = linkTo(methodOn(InvitationController.class).acceptInvitation(teamId, invitation.getId())).withRel("invitationLink");
        invitationEmail.setInvitationLink(invitationLink.getHref());

        mailService.sendMailByTemplate(invitationEmail, TEAM_INVITATION_TEMPLATE);
        return invitationPersistence.findById(invitation.getId());
    }
}
