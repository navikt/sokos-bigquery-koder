package no.nav.sokos.bigquery.DINAPP.database

import no.nav.sokos.bigquery.DINAPP.domain.db2.Db2EksempelMappingObject
import java.sql.ResultSet

object RepositoryExtensions {

    fun ResultSet.toExampleObject() = toList {
        Db2EksempelMappingObject(
            kolonne1 = getString("KOLONNE1"),
            kolonne2 = getInt("KOLONNE2")
        )
    }

    private fun <T> ResultSet.toList(mapper: ResultSet.() -> T) = mutableListOf<T>().apply {
        while (next()) {
            add(mapper())
        }
    }
}