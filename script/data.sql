INSERT INTO source (puissance_plaque_totale, puissance_batterie_totale, id_source)
values (30000, 40000, 'SR001'),
       (28000, 45000, 'SR002'),
       (28000, 45000, 'SR003');

insert into batterie(id_batterie, puissance_max, id_source)
values ('BT001', 15000, 'SR001'),
       ('BT002', 29000, 'SR001'),
       ('BT003', 20000, 'SR002'),
       ('BT004', 24000, 'SR003');

insert into plaque_solaire(id_plaque_solaire, puissance_max, id_source)
values ('PS001', 20000, 'SR001'),
       ('PS002', 19000, 'SR001'),
       ('PS003', 24000, 'SR002'),
       ('PS004', 32000, 'SR003');

insert into secteur (id_secteur, nom_secteur, capacite_max)
values ('SCT001', 'L1', 300),
       ('SCT002', 'L2', 300),
       ('SCT003', 'L3', 260);

insert into battiment (id_batiment, nom, id_secteur)
values ('BAT001', 'AMPHI A', 'SCT001'),
       ('BAT002', 'AMPHI B', 'SCT001'),
       ('BAT003', 'SALLE 1', 'SCT002'),
       ('BAT004', 'SALLE 2', 'SCT002'),
       ('BAT005', 'AMPHI C', 'SCT003');

insert into association_secteur_source (id_secteur, id_source, date_association)
values ('SCT001', 'SR001', NOW()),
       ('SCT002', 'SR002', NOW()),
       ('SCT003', 'SR003', NOW());


insert into pointage_eleve (id_pointage, date_pointage, division_journee, nombre, puissance_requis, id_battiment)
values
    -- matinée
        ('1', '2023-12-01', 1, 165, 20000, 'BAT001'),
        ('2', '2023-12-01', 1, 100, 14000, 'BAT002'),
        ('3', '2023-12-01', 1, 120, 20000, 'BAT003'),
        ('4', '2023-12-01', 1, 126, 23000, 'BAT004'),
        ('5', '2023-12-01', 1, 165, 27000, 'BAT005'),
    -- après midi
        ('6', '2023-12-01', 2, 160, 18000, 'BAT001'),
        ('7', '2023-12-01', 2, 130, 16000, 'BAT002'),
        ('8', '2023-12-01', 2, 102, 14000, 'BAT003'),
        ('9', '2023-12-01', 2, 99,  12000, 'BAT004'),
        ('10', '2023-12-01', 2, 178, 28500, 'BAT005');

insert into lumiere(id_reception, date_reception, heure_reception, luminosite)
values ('REC001', '2023-12-01', '08:00', 6),
       ('REC002', '2023-12-01', '09:00', 7),
       ('REC003', '2023-12-01', '10:00', 8),
       ('REC004', '2023-12-01', '11:00', 9),
       ('REC005', '2023-12-01', '12:00', 9),
       ('REC006', '2023-12-01', '13:00', 10),
       ('REC007', '2023-12-01', '14:00', 9),
       ('REC008', '2023-12-01', '15:00', 8),
       ('REC009', '2023-12-01', '16:00', 7),
       ('REC010', '2023-12-01', '17:00', 5);

SELECT * FROM pointage_eleve WHERE id_battiment='BAT001' AND date_pointage='2023-12-01' AND division_journee='1'