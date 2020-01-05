package com.shelest.booster.utilities.dto;

import com.shelest.booster.utilities.enums.Rank;
import com.shelest.booster.utilities.password_validation.PasswordMatches;
import com.shelest.booster.utilities.password_validation.ValidPassword;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@PasswordMatches
public class DeveloperDTO {

    @NotNull
    @NotEmpty
    private String name;
    private Rank rnk;
    @Range(max = (long) 50.0)
    private double experience;
    @Range(max = (long) 100.0)
    private int qualification;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;
    private String matchingPassword;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRnk() {
        return rnk;
    }

    public void setRnk(Rank rnk) {
        this.rnk = rnk;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
