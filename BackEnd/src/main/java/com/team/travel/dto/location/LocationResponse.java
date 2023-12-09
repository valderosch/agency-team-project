package com.team.travel.dto.location;

import com.team.travel.dto.country.CountryResponse;

public class LocationResponse {
    private Long id;
    private String city;
    private CountryResponse country;

    public LocationResponse() {
    }

    public LocationResponse(Long id, String city, CountryResponse country) {
        this.id = id;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryResponse getCountry() {
        return country;
    }

    public void setCountry(CountryResponse country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
