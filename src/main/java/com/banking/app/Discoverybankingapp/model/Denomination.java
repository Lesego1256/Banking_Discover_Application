package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "Denomination")
public class Denomination implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DENOMINATION_ID")
    private Long denominationId;
    @Column(name = "VALUE")
    private double value;

    @OneToOne
    @JoinColumn(name = "denomination_Type_Code")
    private DenominationType denominationTypeCode;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDenominationId() {
        return denominationId;
    }

    public void setDenomination_Id(Long denominationId) {
        this.denominationId = denominationId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public DenominationType getDenominationTypeCode() {
        return denominationTypeCode;
    }

    public void setDenominationTypeCode(DenominationType denominationTypeCode) {
        this.denominationTypeCode = denominationTypeCode;
    }
}
