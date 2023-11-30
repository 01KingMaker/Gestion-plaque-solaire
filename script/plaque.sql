drop database plaque_solaire;

CREATE DATABASE PLAQUE_SOLAIRE;
\c plaque_solaire

CREATE TABLE Plaque_solaire(
   id_plaque_solaire VARCHAR(50) ,
   puissance_max INTEGER,
   PRIMARY KEY(id_plaque_solaire)
);

CREATE TABLE batterie(
   id_batterie VARCHAR(50) ,
   puissance_max INTEGER,
   PRIMARY KEY(id_batterie)
);

CREATE TABLE secteur(
   id_secteur VARCHAR(50) ,
   nom_secteur VARCHAR(50) ,
   capacite_max INTEGER,
   PRIMARY KEY(id_secteur)
); 

CREATE TABLE status(
   id_status VARCHAR(50) ,
   puissance_solaire NUMERIC(15,10)  ,
   puissance_batterie NUMERIC(15,10)  ,
   besoin NUMERIC(15,10)  ,
   etat INTEGER,
   PRIMARY KEY(id_status)
);

CREATE TABLE Lumiere(
   id_reception VARCHAR(50) ,
   date_reception DATE NOT NULL,
   luminosite INTEGER NOT NULL,
   heure_reception TIME NOT NULL,
   id_status VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_reception),
   FOREIGN KEY(id_status) REFERENCES status(id_status)
);

CREATE TABLE pointage_eleve(
   id_pointage INTEGER,
   date_pointage DATE NOT NULL,
   division_journee SMALLINT NOT NULL,
   nombre INTEGER NOT NULL,
   puissance_requis NUMERIC(15,10)   NOT NULL,
   id_secteur VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_pointage),
   FOREIGN KEY(id_secteur) REFERENCES secteur(id_secteur)
);

CREATE TABLE source(
   id_plaque_solaire VARCHAR(50) ,
   puissance_plaque_totale NUMERIC(15,10)  ,
   puissance_batterie_totale NUMERIC(15,10)  ,
   id_batterie VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_plaque_solaire),
   UNIQUE(id_batterie),
   FOREIGN KEY(id_plaque_solaire) REFERENCES Plaque_solaire(id_plaque_solaire),
   FOREIGN KEY(id_batterie) REFERENCES batterie(id_batterie)
);
