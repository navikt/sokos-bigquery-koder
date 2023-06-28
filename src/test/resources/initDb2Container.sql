/*
  Nødvendig for Hikari oppstart. Tabellnavn er definert i localdevproperties, som er definert i PropertiesConfig.kt
  Schemanavn er definert i DatabaseTestUtils.kt
 */
CREATE TABLE TEST_SCHEMA.HIKARI_TEST_TABLE
(
    ID INT NOT NULL
);
INSERT INTO TEST_SCHEMA.HIKARI_TEST_TABLE (ID)
VALUES (123);


/*
 Tabellen T_FAGGRUPPE brukes i Repository.kt
 */

create table TEST_SCHEMA.T_FAGGRUPPE
(
    KODE_FAGGRUPPE VARCHAR(8)  NOT NULL,
    NAVN_FAGGRUPPE VARCHAR(50) NOT NULL
);

create table TEST_SCHEMA.T_FAGOMRAADE
(
    KODE_FAGOMRAADE VARCHAR(8)  NOT NULL,
    NAVN_FAGOMRAADE VARCHAR(50) NOT NULL
);

create table TEST_SCHEMA.T_KLASSEKODE
(
    KODE_KLASSE VARCHAR(20) NOT NULL,
    BESKR_KLASSE VARCHAR(50) NOT NULL
);

insert into TEST_SCHEMA.T_FAGGRUPPE(KODE_FAGGRUPPE, NAVN_FAGGRUPPE) VALUES
    ( 'GRUPPEA', 'Dette er gruppe A'),
    ('GRUPPEB', 'Dette er gruppe B' );
insert into TEST_SCHEMA.T_FAGOMRAADE(KODE_FAGOMRAADE, NAVN_FAGOMRAADE) VALUES
    ( 'OMRAADEA', 'Dette er fagområde A' ),
    ( 'OMRAADEB', 'Dette er fagområde B' );
insert into TEST_SCHEMA.T_KLASSEKODE(KODE_KLASSE, BESKR_KLASSE) VALUES
    ( 'KLASSEA', 'Dette er klassekode A' ),
    ( 'KLASSEB', 'Dette er klassekode B' );
