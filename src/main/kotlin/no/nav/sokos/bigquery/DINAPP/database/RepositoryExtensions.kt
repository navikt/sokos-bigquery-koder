package no.nav.sokos.bigquery.koder.database

import no.nav.sokos.bigquery.koder.domain.KodeBeskrivelse
import java.sql.ResultSet

object RepositoryExtensions {

    fun ResultSet.toExampleObject() = toList {
        KodeBeskrivelse(
            type = getString("TYPE"),
            kode = getString("KODE"),
            beskrivelse = getString("BESKRIVELSE")
        )
    }

    private fun <T> ResultSet.toList(mapper: ResultSet.() -> T) = mutableListOf<T>().apply {
        while (next()) {
            add(mapper())
        }
    }
}
