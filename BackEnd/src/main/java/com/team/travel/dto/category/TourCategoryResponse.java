package com.team.travel.dto.category;

public class TourCategoryResponse {

    private Long id;
    private String name;

    public TourCategoryResponse() {

    }

    public TourCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TourCategoryResponse{" +
                "id=" + id +
                ", name'" + name + '\'' +
                '}';
    }
}
