package org.seeding.entity;


//import lombok.Builder;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

//@Builder
public class Pincode {

//    public Pincode() {
//        this.Id = (long) (new Date().getTime() / 10000 * Math.random() + 1000);
//        this.created_date = new java.sql.Date(new java.sql.Date(System.currentTimeMillis()).getTime());
//        this.updated_date = new java.sql.Date(new java.sql.Date(System.currentTimeMillis()).getTime());
//    }

    //    private Long Id = (long) (new Date().getTime() / 10000 * Math.random() + 1000);
    private String Id = UUID.randomUUID().toString();
    private String circleName;
    private String regionName;
    private String divisionName;
    private Integer pincode;
    private String district;
    private String state;
    private Double latitude;
    private Double longitude;
    private String locality;
    private java.sql.Date created_date = new java.sql.Date(new java.sql.Date(System.currentTimeMillis()).getTime());
    private java.sql.Date updated_date = new java.sql.Date(new java.sql.Date(System.currentTimeMillis()).getTime());

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    public void setCreated_date(java.sql.Date created_date) {
        this.created_date = created_date;
    }

    public void setUpdated_date(java.sql.Date updated_date) {
        this.updated_date = updated_date;
    }

    public java.sql.Date getUpdated_date() {
        return updated_date;
    }

    public java.sql.Date getCreated_date() {
        return created_date;
    }


    // office name is the locality


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
                ", locality='" + locality + '\'' +
                ", created_date=" + created_date +
                ", updated_date=" + updated_date +
                '}';
    }
}
