package model;

import javax.persistence.*;

@Entity
@Table(name = "batiment")
public class Batiment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "localisation", nullable = false, length = 255)
    private String localisation;

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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    
    @Override
    public String toString() {
    	return nom + " (" + localisation + ")";
    }

}
