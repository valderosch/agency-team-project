package com.team.travel.dto.country;

public class CountryResponse {
    private Long id;
    private String name;

    public CountryResponse() {
    }

    public CountryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CountryResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
