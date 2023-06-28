import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import no.nav.sokos.bigquery.DINAPP.database.Repository.getData
import no.nav.sokos.bigquery.DINAPP.domain.KodeBeskrivelse

import util.DatabaseTestUtils


internal class testMedTestContainer : FunSpec({
    val datasource = DatabaseTestUtils.getDataSource("initDb2Container.sql")

    test("Data skal bli hentet"){
       val data : List<KodeBeskrivelse> = datasource.connection.getData()

        data.size.shouldBe(6)
        data.shouldContain(KodeBeskrivelse("OMRAADE", "OMRAADEA", "Dette er fagområde A"))
    }
})



