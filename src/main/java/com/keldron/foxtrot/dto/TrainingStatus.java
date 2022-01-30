package com.keldron.foxtrot.dto;

public enum TrainingStatus {
    OPEN("Open"), ENDED ("Ended"), IN_PROGRESS("In Progress");

    private String status;

    TrainingStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
