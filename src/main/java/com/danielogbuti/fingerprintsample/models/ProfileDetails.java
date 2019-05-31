package com.danielogbuti.fingerprintsample.models;

import com.machinezoo.sourceafis.FingerprintTemplate;

public class ProfileDetails {

    private String id;
    private FingerprintTemplate templates;
    private byte[] imageByte;

    public ProfileDetails(String id, FingerprintTemplate template) {
        this.id = id;
        this.imageByte = imageByte;
        templates = template;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public FingerprintTemplate getImageTemplate() {
        return templates;
    }



}
