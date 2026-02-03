package model;

import javax.persistence.*;

@Entity
@Table(name = "technicien")
public class Technicien {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "nom", nullable = false, length = 100)
    private String nom;
	
	@Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
    
	@Column(name = "telephone", nullable = false, length = 20)
    private String telephone;
    
    @Column(name = "qualification", nullable = false, length = 100)
    private String qualification;
    
    @Column(name = "disponible", nullable = false)
    private boolean disponible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() {
        return this.nom + " " + this.prenom;
    }

}
