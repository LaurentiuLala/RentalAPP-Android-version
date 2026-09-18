package com.example.proiect.model;

import java.time.LocalDate;

public class InchiriereDTO {
    private Long id;
    private Long userId;
    private Long masinaId;
    private Long locatieId;
    private String dataInceput;
    private String dataSfarsit;
    private String code;
    private String status;
    private Double totalPrice;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getMasinaId() { return masinaId; }
    public void setMasinaId(Long masinaId) { this.masinaId = masinaId; }
    public Long getLocatieId() { return locatieId; }
    public void setLocatieId(Long locatieId) { this.locatieId = locatieId; }
    public String getDataInceput() { return dataInceput; }
    public void setDataInceput(String dataInceput) { this.dataInceput = dataInceput; }
    public String getDataSfarsit() { return dataSfarsit; }
    public void setDataSfarsit(String dataSfarsit) { this.dataSfarsit = dataSfarsit; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}
