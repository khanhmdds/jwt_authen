package com.bezkoder.springjwt.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class PasswordDTO {

    @NotEmpty(message = "Not empty")
    private String oldPassword;

    @NotEmpty(message = "Not empty")
    @Min(8)
    private String newPassword;

    @NotEmpty(message = "Not empty")
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "PasswordDTO [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmNewPassword="
                + confirmNewPassword + "]";
    }
}
