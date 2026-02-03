package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "intervention")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;

    @ManyToOne(optional = false)
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "batiment_id")
    private Batiment batiment;

    @Column(name = "date_intervention", nullable = false)
    private Date dateIntervention;

    @Column(name = "type_intervention", nullable = false, length = 100)
    private String typeIntervention;

    @Column(name = "statut", nullable = false, length = 20)
    private String statut;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Technicien getTechnicien() {
        return technicien;
    }

    public void setTechnicien(Technicien technicien) {
        this.technicien = technicien;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Date getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(Date dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public String getTypeIntervention() {
        return typeIntervention;
    }

    public void setTypeIntervention(String typeIntervention) {
        this.typeIntervention = typeIntervention;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
