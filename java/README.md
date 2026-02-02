# Partie Java – Application de gestion de maintenance

Ce dossier est dédié au développement de la partie Java du projet **Projet Java ISN – Application de Gestion de Maintenance**.

La partie Java constitue le cœur applicatif du projet.  
Elle sera développée après la mise en place de l’infrastructure (base de données, Docker, SQL), déjà réalisée.

---

## Objectif de la partie Java

L’objectif est de développer une application Java permettant d’interagir avec la base de données MariaDB du projet afin de :

- se connecter à la base de données
- manipuler les données (techniciens, bâtiments, interventions)
- implémenter la logique métier
- proposer une interface utilisateur (JavaFX)

---

## Contenu prévu

La partie Java comprendra notamment :

- une connexion JDBC à la base de données MariaDB
- des classes représentant les entités du projet :
  - Technicien
  - Bâtiment
  - Intervention
- des méthodes permettant :
  - l’ajout, la modification et la suppression des données
  - la consultation des interventions
- une interface graphique développée avec JavaFX

---

# Interface JavaFX – Projet Gestion de Maintenance (ISN)

## 1) Objectif de cette partie

Cette partie du projet correspond à l’interface graphique (JavaFX) pour l’application de maintenance.

L’idée est :
1. Afficher un écran de connexion (Responsable).
2. Vérifier les identifiants dans la base de données `maintenance`.
3. Si c’est correct, ouvrir l’accueil.
4. Depuis l’accueil, le responsable peut accéder aux écrans :
   - Techniciens
   - Bâtiments
   - Interventions

Pour l’instant, l’objectif principal était d’avoir :
- la connexion fonctionnelle
- l’accueil avec boutons reliés aux écrans
- les écrans de base (tableaux) prêts à afficher des données DB ensuite

---

## 2) Organisation des fichiers (Interface)

Dans le dossier Interface (dans le répertoire Java), on a plusieurs fichiers.

### MainApp.java

C’est le point d’entrée de l’application (le “main”).

- Il lance JavaFX.
- Il contient les fonctions de navigation :
  - `afficherConnexion()`
  - `afficherAccueil()`
  - `afficherTechniciens()`
  - `afficherBatiments()`
  - `afficherInterventions()`
- Il conserve aussi les infos du responsable connecté (session) :
  - `responsableId`
  - `responsableEmail`

Donc c’est lui qui oriente l’application vers les différents écrans.

---

### ConnexionView.java

C’est l’écran de connexion.

- Il demande l’email et le mot de passe.
- Il fait une requête SQL vers la table `responsable`.
- Si la connexion est valide, il appelle :
  - `app.setResponsableConnecte(id, email)`
  - `app.afficherAccueil()`

---

### AccueilView.java

C’est l’écran principal après connexion.

- Il affiche “Connecté : …” avec l’email du responsable.
- Il contient les boutons :
  - Techniciens
  - Bâtiments
  - Interventions
- Chaque bouton appelle une méthode de `MainApp`, exemple :
  - `btnTech.setOnAction(e -> app.afficherTechniciens());`

C’est ce lien `setOnAction` qui permet d’ouvrir les pages.

---

### TechnicienView.java, BatimentView.java, InterventionView.java

Ce sont les écrans (tableaux).

- Pour l’instant, l’objectif est de créer une base d’interface propre.
- Ensuite, on ajoutera la récupération réelle depuis la DB (requêtes SQL).

Normalement, ces fichiers doivent afficher uniquement des données qui viennent de la base, pas des données fictives.

---

### DB.java

Ce fichier sert à gérer la connexion à la base MariaDB via JDBC.

Son rôle :
- centraliser l’URL, le user, le mot de passe
- fournir une méthode du style : `DB.getConnection()`

Pourquoi c’est important ?
- Sans `DB.java`, on serait obligé de répéter le code de connexion dans chaque écran.
- Avec `DB.java`, chaque écran peut faire une requête SQL facilement pour récupérer des données :
  - techniciens
  - batiments
  - interventions
  - responsables (connexion)

Donc `DB.java` est le pont entre l’interface JavaFX et la base de données.

---

## 3) Base de données (MariaDB + Docker + phpMyAdmin)

La base de données est lancée dans Docker.

On peut gérer les données via phpMyAdmin :

- phpMyAdmin : `http://localhost:8080`
- Base : `maintenance`

Pour créer un responsable (exemple), on peut faire une requête SQL dans phpMyAdmin :

```sql
INSERT INTO responsable (nom, prenom, email, telephone, mdp_hash)
VALUES ('Admin', 'Test', 'admin@maintenance.local', '0600000000', MD5('admin123'));

---

## Maven et le fichier pom.xml

Dans ce projet, on utilise **Maven** pour gérer le projet Java (dépendances, compilation, exécution).
Le fichier **pom.xml** est le fichier principal de configuration Maven.

Il sert surtout à :
- définir le nom du projet et sa version ;
- préciser la version de Java (ex: 17) ;
- ajouter les dépendances (ex: JavaFX + driver MariaDB pour se connecter à la base) ;
- permettre de lancer l’application avec une commande simple dans le terminal.

### Prérequis (avant de lancer)
- Avoir Java installé (ex: Java 17).
- Avoir Maven installé.
- Avoir Docker lancé si on veut utiliser la base de données.

### Démarrer la base de données (Docker)
Dans le dossier du projet (là où il y a `docker-compose.yml`) :
```bash
sudo docker compose up -d

