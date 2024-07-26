package com.example.InformationalPortal.testConnect.models.keys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class ReceiptData {
    private String clientId;
    private Date data;
    private int kilovatt;
    private BigDecimal price;
    private BigDecimal areas;
    private BigDecimal summa;
    private String personalAccount;

    public ReceiptData(String clientId, Date data, int kilovatt, BigDecimal price, BigDecimal areas , String personalAccount ) {
        this.clientId = clientId;
        this.data = data;
        this.kilovatt = kilovatt;
        this.price = price;
        this.areas = areas;
        this.summa = areas;
        this.personalAccount = personalAccount;
    }
}
