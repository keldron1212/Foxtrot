package com.keldron.foxtrot.info;

public enum APIInfoCodes {

    OK(200000L),
    USERNAME_ALREADY_USED(400003L),
    USERNAME_NOT_FOUND(400004L),
    TRAINING_NOT_FOUND(40005L),
    VENUE_NOT_FOUND(40006L),
    INCORECT_REQUEST(400010L);

    private long value;

    APIInfoCodes(Long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
