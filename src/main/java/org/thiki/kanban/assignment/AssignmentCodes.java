package org.thiki.kanban.assignment;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 11/4/16.
 */
public enum AssignmentCodes {
    ALREADY_ASSIGNED("001", "你此前已经认领该任务,请勿重新认领。");
    private String code;
    private String message;

    AssignmentCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.BOARD + code);
    }

    public String message() {
        return message;
    }
}
