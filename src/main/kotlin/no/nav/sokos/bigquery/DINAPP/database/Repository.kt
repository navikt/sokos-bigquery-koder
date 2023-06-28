@file:Suppress("SqlNoDataSourceInspection")

package no.nav.sokos.bigquery.koder.database

import mu.KotlinLogging
import no.nav.sokos.bigquery.koder.database.RepositoryExtensions.toExampleObject
import no.nav.sokos.bigquery.koder.domain.KodeBeskrivelse
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

    fun Connection.getData(): List<KodeBeskrivelse> {
        return try {
            prepareStatement(
                """
                    select TYPE, KODE, BESKRIVELSE from
                        (select 'FAGGRUPPE' as TYPE, KODE_FAGGRUPPE as KODE, NAVN_FAGGRUPPE as BESKRIVELSE from T_FAGGRUPPE
                         union
                         select 'OMRAADE' as TYPE, KODE_FAGOMRAADE as KODE, NAVN_FAGOMRAADE as BESKRIVELSE from T_FAGOMRAADE
                         union
                         select 'KLASSE' as TYPE, KODE_KLASSE as KODE, BESKR_KLASSE as BESKRIVELSE from T_KLASSEKODE) A
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
