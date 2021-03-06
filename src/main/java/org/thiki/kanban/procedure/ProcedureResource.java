package org.thiki.kanban.procedure;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class ProcedureResource extends RestResource {
    public ProcedureResource(Procedure procedure, String boardId) throws Exception {
        this.domainObject = procedure;
        if (procedure != null) {
            Link selfLink = linkTo(methodOn(ProceduresController.class).findById(procedure.getId(), boardId)).withSelfRel();
            this.add(selfLink);

            Link cardsLink = linkTo(methodOn(CardsController.class).create(null, null, boardId, procedure.getId())).withRel("cards");
            this.add(cardsLink);
        }
        this.add(linkTo(methodOn(ProceduresController.class).loadAll(boardId)).withRel("all"));
    }

    public ProcedureResource(String boardId) throws Exception {
        this.add(linkTo(methodOn(ProceduresController.class).loadAll(boardId)).withRel("all"));
    }
}
