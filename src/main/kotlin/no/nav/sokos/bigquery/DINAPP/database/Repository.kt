package no.nav.sokos.bigquery.DINAPP.database

import mu.KotlinLogging
import no.nav.sokos.bigquery.DINAPP.database.RepositoryExtensions.toExampleObject
import no.nav.sokos.bigquery.DINAPP.domain.db2.Db2EksempelMappingObject
import java.sql.Connection

object Repository {
    private val log = KotlinLogging.logger {}

    //Viktig for DB2 databasene. Gjør at det går mye raskere. Vil få feilmelding i test men det har ingenting å si.
    fun Connection.setAcceleration() {
        try {
            prepareStatement("SET CURRENT QUERY ACCELERATION ALL;").execute()
        } catch (e: Exception) {
            log.error("Exception i acceleration: ${e.message}")
        }
    }

    fun Connection.getData(): List<Db2EksempelMappingObject> {
        return try {
            prepareStatement(
                """
                    SELECT KOLONNE1, KOLONNE2 FROM $schema.TEST_TABLE
                """.trimIndent()
            ).executeQuery().toExampleObject()
        }catch (e: Exception){
            log.error("exception i henting av data: ${e.message}")
            listOf()
        }finally {
            close()
        }
    }
}
