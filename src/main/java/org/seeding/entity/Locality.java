package org.seeding.entity;

public class Locality {
    public Locality() {
        this.Id = 0L;
        this.pincode = null;  // Can be null if pincode is not mandatory
        this.locality = "";  // Or any default string value
    }
    private Long Id;
    private Integer pincode;
    private String locality;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
