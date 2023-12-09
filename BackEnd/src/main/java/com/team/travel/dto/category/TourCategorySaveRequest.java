package com.team.travel.dto.category;

public class TourCategorySaveRequest {
    private String name;

    public TourCategorySaveRequest() {

    }

    public TourCategorySaveRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TourCategorySaveRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
