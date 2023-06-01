
/*
  Nødvendig for Hikari oppstart. Tabellnavn er definert i localdevproperties, som er definert i PropertiesConfig.kt
  Schemanavn er definert i DatabaseTestUtils.kt
 */
CREATE TABLE TEST_SCHEMA.HIKARI_TEST_TABLE
(
    ID INT NOT NULL
);
INSERT INTO TEST_SCHEMA.HIKARI_TEST_TABLE (ID) VALUES (123);


/*
 Tabellen vi bruker for test
 "TEST_TABLE" er hardkodet i Repository.kt
 */
create table TEST_SCHEMA.TEST_TABLE(
    KOLONNE1 VARCHAR(255) NOT NULL,
    KOLONNE2 INT NOT NULL
);

INSERT INTO TEST_SCHEMA.TEST_TABLE (KOLONNE1, KOLONNE2) VALUES ('Data for kolonne 1', 2);