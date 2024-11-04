package com.veniamin.taskplanner.modelLogs;

public enum LogsType {
    AUTH(AuthLogs.class),
    MAIL(MailLogs.class),
    USERS(UsersLogs.class);

    public Class<? extends Logs> type;

    LogsType(Class<? extends Logs> type) {
        this.type = type;
    }
}
