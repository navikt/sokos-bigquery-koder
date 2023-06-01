import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.sokos.bigquery.DINAPP.database.Repository.getData
import no.nav.sokos.bigquery.DINAPP.domain.db2.Db2EksempelMappingObject

import util.DatabaseTestUtils


internal class testMedTestContainer : FunSpec({
    val datasource = DatabaseTestUtils.getDataSource("initDb2Container.sql")

    test("Data skal bli hentet"){
       val data : List<Db2EksempelMappingObject> = datasource.connection.getData()

        data.size.shouldBe(1)
        data[0].kolonne1.shouldBe("Data for kolonne 1")
        data[0].kolonne2.shouldBe(2)
    }
})



