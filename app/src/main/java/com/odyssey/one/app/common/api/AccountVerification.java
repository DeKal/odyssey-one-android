package com.odyssey.one.app.common.api;

import com.google.gson.annotations.SerializedName;

public class AccountVerification {

    @SerializedName("userVerified")
    private Boolean userVerified;

    @SerializedName("userRole")
    private Integer userRole;


    public AccountVerification(Boolean userVerified, Integer userRole) {
        this.setUserVerified(userVerified);
        this.setUserRole(userRole);
    }


    public Boolean getUserVerified() {
        return userVerified;
    }

    public void setUserVerified(Boolean userVerified) {
        this.userVerified = userVerified;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}

