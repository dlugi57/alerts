package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.Person;

import java.util.List;

public class ChildrenByAddress {
    private List<PersonAlert> adults;
    private List<PersonAlert> children;

    public ChildrenByAddress(List<PersonAlert> adults, List<PersonAlert> children) {
        this.adults = adults;
        this.children = children;
    }

    public ChildrenByAddress() {
    }

    public List<PersonAlert> getAdults() {
        return adults;
    }

    public void setAdults(List<PersonAlert> adults) {
        this.adults = adults;
    }

    public List<PersonAlert> getChildren() {
        return children;
    }

    public void setChildren(List<PersonAlert> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ChildrenByAddress{" +
                "adults=" + adults +
                ", children=" + children +
                '}';
    }
}
