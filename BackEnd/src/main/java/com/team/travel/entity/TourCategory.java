package com.team.travel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.GenerationType;

import java.util.Objects;

@Entity
@Table(name = "tour_category")
public class TourCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tour_category_name",nullable = false,length = 128)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Tour> tours;

    public TourCategory(){

    }

    public TourCategory(Long id, String name) {
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

    public List<Tour> getTours(){
        return tours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourCategory tourCategory = (TourCategory) o;
        return id.equals(tourCategory.id);
    }

    @Override
    public String toString() {
        return "TourCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
