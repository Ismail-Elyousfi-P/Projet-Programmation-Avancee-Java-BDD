package model;

import javax.persistence.*;

@Entity
@Table(name = "responsable")
public class Responsable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	
	@Column(name = "nom", nullable = false, length = 100)
    private String nom;
	
	@Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
	
	@Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
	
	@Column(name = "telephone", nullable = false, length = 20)
    private String telephone;
	
	@Column(name = "mdp_hash", nullable = false, length = 32)
    private String mdpHash;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMdpHash() {
        return mdpHash;
    }

    public void setMdpHash(String mdpHash) {
        this.mdpHash = mdpHash;
    }
}
