package com.team.travel.dto.country;

public class CountrySaveRequest {
    private String name;

    public CountrySaveRequest() {
    }

    public CountrySaveRequest(String name) {
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
        return "CountrySaveRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
