package com.team.travel.dto.location;

public class LocationSaveRequest {
    private String city;
    private Long countryId;

    public LocationSaveRequest() {
    }

    public LocationSaveRequest(String city, Long countryId) {
        this.city = city;
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "LocationSaveRequest{" +
                "city='" + city + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
