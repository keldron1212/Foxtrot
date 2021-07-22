package com.keldron.foxtrot.model.trainings;

public enum TrainingType {
    CLASSROOM("Classroom"),
    VIRTUAL("Virtual"),
    MENTORING("Mentoring");

    private String name;

    TrainingType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
