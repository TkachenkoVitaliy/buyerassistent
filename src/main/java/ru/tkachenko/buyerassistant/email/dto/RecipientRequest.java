package ru.tkachenko.buyerassistant.email.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipientRequest {

    @JsonProperty("branchName")
    private String branchName;
    @JsonProperty("emailAddress")
    private String email;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
