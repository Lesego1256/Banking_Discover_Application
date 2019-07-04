package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "ATM_ALLOCATION")
public class AtmAllocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long atmAllocationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATM_ID")
    private Atm atm;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "denomination_Id", referencedColumnName = "denomination_Id")
    private Denomination denomination;

    private int count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getAtmAllocationId() {
        return atmAllocationId;
    }

    public void setAtmAllocationId(Long atmAllocationId) {
        this.atmAllocationId = atmAllocationId;
    }

    public Atm getAtm() {
        return atm;
    }

    public void setAtm(Atm atm) {
        this.atm = atm;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
