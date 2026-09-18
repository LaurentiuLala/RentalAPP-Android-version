package com.example.proiect.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MasinaDTO {
    private Long id;
    private String marca;
    private String model;
    private int anFabricatie;
    private double pretPeZi;
    private boolean disponibil;
    private Long locatieId;
    private String locatieDescriere;
    private List<String> images;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getAnFabricatie() { return anFabricatie; }
    public void setAnFabricatie(int anFabricatie) { this.anFabricatie = anFabricatie; }
    public double getPretPeZi() { return pretPeZi; }
    public void setPretPeZi(double pretPeZi) { this.pretPeZi = pretPeZi; }
    public boolean isDisponibil() { return disponibil; }
    public void setDisponibil(boolean disponibil) { this.disponibil = disponibil; }
    public Long getLocatieId() { return locatieId; }
    public void setLocatieId(Long locatieId) { this.locatieId = locatieId; }
    public String getLocatieDescriere() { return locatieDescriere; }
    public void setLocatieDescriere(String locatieDescriere) { this.locatieDescriere = locatieDescriere; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}
