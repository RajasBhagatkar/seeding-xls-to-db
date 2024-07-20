package org.seeding.entity;


import lombok.Builder;

import java.util.Date;

@Builder
public class Pincode {

//    public Pincode() {
//        this.Id = new Date().getTime();
//        this.circleName = "";
//        this.regionName ="";
//        this.divisionName ="";
//        this.pincode = null;
//        this.district ="";
//        this.state ="";
//        this.latitude = 0D;
//        this.longitude = 0D;
//    }
    private Long Id = new Date().getTime();

    private String circleName;
    private String regionName;
    private String divisionName;
    // office name is the locality
    private Integer pincode;

    private String district;
    private String state;

    private Double latitude;
    private Double longitude;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
    public void setId() {
        Id = new Date().getTime();
    }
    public String getCircleName() {
        return circleName;
    }
    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Pincode{" +
                "Id=" + Id +
                ", circleName='" + circleName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", divisionName='" + divisionName + '\'' +
                ", pincode=" + pincode +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
