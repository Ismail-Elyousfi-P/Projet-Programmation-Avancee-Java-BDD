# Partie Java – Application de gestion de maintenance

Ce dossier est dédié au développement de la partie Java du projet
**Projet Java ISN – Application de Gestion de Maintenance**.

La partie Java constitue le cœur applicatif du projet.  
Elle sera développée après la mise en place de l’infrastructure
(base de données, Docker, SQL), déjà réalisée.

---

## Objectif de la partie Java

L’objectif est de développer une application Java permettant
d’interagir avec la base de données MariaDB du projet afin de :

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

## Connexion à la base de données

L’application Java utilisera la base de données mise en place avec Docker.

Paramètres de connexion (en environnement de développement) :

- Base de données : maintenance
- Utilisateur : app
- Mot de passe : app
- Port : 3306
- URL JDBC :
  jdbc:mariadb://localhost:3306/maintenance

Ces paramètres correspondent à ceux définis dans le fichier
`docker-compose.yml` à la racine du projet.

---

## Organisation du code (à venir)

Le code Java sera organisé de manière progressive et claire,
afin de faciliter le travail en groupe et la maintenance du projet.

Ce dossier sera complété au fur et à mesure de l’avancement
du développement Java.

---

## Remarque importante

Avant de développer ou tester la partie Java, il est indispensable que :

- Docker soit démarré
- la base de données MariaDB soit en cours d’exécution
- les tables aient été créées via les scripts SQL du projet

Toutes les instructions concernant Docker et la base de données
sont décrites dans le fichier `README.md` principal du projet.
