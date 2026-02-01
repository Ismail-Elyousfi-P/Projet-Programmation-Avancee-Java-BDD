# Projet Java ISN – Application de Gestion de Maintenance

## Informations générales

**UE :** Programmation Avancée – Java / Base de données  
**Formation :** Master ISN  

**Projet réalisé par :**
- Cheikh Mbake Seye  
- Ismail EL-YOUSFI  
- Oualid CHAREF  

Ce document sert à la fois :
- de **documentation technique**
- de **guide de reproduction**
- de **journal d’avancement du projet**

Il est destiné à tous les membres du groupe ainsi qu’au professeur.

---

## Présentation générale du projet

Le projet consiste à développer une application Java permettant de gérer un service de maintenance technique.

L’application permettra :
- de gérer des **techniciens**
- de gérer des **bâtiments**
- de planifier et suivre des **interventions**
- de conserver un **historique** des interventions réalisées

---

## Organisation générale du projet

```text
projet Java ISN/
├── README.md                 ← Documentation et suivi du projet
├── docker-compose.yml        ← Déploiement de la base de données
├── sql/
│   └── 01_create_tables.sql  ← Création des tables SQL
└── java/                     ← Code Java / JavaFX






## PARTIE 1 – Mise en place de l’environnement Docker

### Objectif
Mettre en place un environnement commun à tous les membres du projet afin de disposer
d’une base de données fonctionnelle sans installation manuelle de MariaDB sur le système.

L’utilisation de Docker permet à chaque membre du groupe, ainsi qu’au professeur,
de lancer exactement le même environnement.

---

### 1.1 Création et configuration de Docker

La base de données est déployée à l’aide d’un fichier `docker-compose.yml` situé à la racine du projet.

Ce fichier définit deux services :
- **MariaDB** : serveur de base de données
- **phpMyAdmin** : interface web permettant de gérer la base de données

Les identifiants (utilisateurs et mots de passe) sont définis directement dans le fichier
`docker-compose.yml`, ce qui permet à toute personne travaillant sur le projet de les consulter.

---

### 1.2 Démarrage de Docker

Pour démarrer l’environnement Docker, la commande suivante est exécutée depuis
la racine du projet :

```bash
sudo docker compose up -d



## PARTIE 2 – Base de données : principe et création

### Objectif
Le projet a besoin d’une base de données pour **enregistrer** et **retrouver** les informations.
Sans base de données, toutes les données seraient perdues dès qu’on ferme l’application.

---

### 2.1 À quoi sert une base de données dans ce projet ?
Une base de données relationnelle sert à :
- stocker les informations de manière organisée
- éviter les doublons
- relier des informations entre elles (ex : une intervention est faite par un technicien dans un bâtiment)
- permettre à l’application Java de faire des opérations :
  - ajouter des données (INSERT)
  - consulter des données (SELECT)
  - modifier (UPDATE)
  - supprimer (DELETE)

---

### 2.2 Choix : MariaDB + Docker
Nous utilisons :
- **MariaDB** : le serveur de base de données (stockage réel des données)
- **Docker / Docker Compose** : pour lancer MariaDB facilement, de manière identique pour tout le monde

Avantage : chaque membre du groupe (et le professeur) peut lancer la base localement, sans installer MariaDB manuellement.

---

### 2.3 Création automatique de la base (pas de création manuelle)
La base est créée automatiquement au démarrage de Docker grâce au fichier :

- `docker-compose.yml`

Dans notre configuration, la base s’appelle :
- `maintenance`

Cette base est créée automatiquement car on définit `MARIADB_DATABASE=maintenance`
dans `docker-compose.yml`.



---
## PARTIE 3 – Création des tables (SQL) : contenu et utilité

### Objectif
Définir la structure de la base de données sous forme de tables, afin de stocker
les informations du projet (techniciens, bâtiments, interventions) et les relations entre elles.

---

### 3.1 Où se trouve le fichier SQL ?
Les tables sont définies dans un fichier SQL versionné dans le projet :

```text
sql/01_create_tables.sql


## Interface phpMyAdmin – Accès et administration de la base de données

phpMyAdmin est l’interface graphique utilisée dans ce projet pour administrer
la base de données MariaDB pendant la phase de développement.

Elle permet de visualiser la structure de la base, de vérifier la création
des tables et de gérer les données plus facilement.

---

### Accès à l’interface phpMyAdmin

Une fois Docker démarré, l’interface phpMyAdmin est accessible depuis
un navigateur web à l’adresse suivante :

Adresse d’accès :
http://localhost:8080

Cette adresse est définie dans le fichier `docker-compose.yml`
via la configuration des ports.

- `localhost` correspond à la machine locale
- chaque membre du projet accède à phpMyAdmin sur sa propre machine

---

### Connexion à phpMyAdmin

Sur l’écran de connexion phpMyAdmin, les informations suivantes doivent être saisies.

Serveur :
db

Les identifiants de connexion sont définis directement dans le fichier
`docker-compose.yml`.

Connexion administrateur :
- Utilisateur : root
- Mot de passe : root

Connexion applicative :
- Utilisateur : app
- Mot de passe : app

Ces comptes sont utilisés uniquement dans le cadre du développement
pour administrer et exploiter la base de données du projet.

---

### Lien avec la création des tables SQL

La structure de la base de données (tables et relations) est définie
dans le fichier SQL suivant :

sql/01_create_tables.sql

Ce fichier contient toutes les instructions CREATE TABLE nécessaires.

Il est exécuté automatiquement par MariaDB lors du démarrage de Docker
lorsque la base de données est vide.

---

### Vérification de la base via phpMyAdmin

Après connexion à phpMyAdmin :

- sélectionner la base de données du projet
- vérifier la présence des tables :
  - technicien
  - batiment
  - intervention

La présence de ces tables confirme que le fichier SQL a bien été exécuté
et que la base de données est prête à être utilisée.

---

### Connexion Java à MariaDB (paramètres)

L’application Java doit se connecter à la base MariaDB avec l’utilisateur applicatif :

- Host : localhost
- Port : 3306
- Base : maintenance
- Utilisateur : app
- Mot de passe : app

URL JDBC :
jdbc:mariadb://localhost:3306/maintenance

