import com.google.cloud.bigquery.*
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import no.nav.sokos.bigquery.DINAPP.config.PropertiesConfig

private val log = KotlinLogging.logger{}
internal class InsertionTest : FunSpec({
    val tableName = this::class.simpleName.toString()
    val datasetName: String = RemoteBigQueryHelper.generateDatasetName()

    val datasetInfo: DatasetInfo = DatasetInfo.newBuilder(datasetName).setLocation("europe-north1").build()

    val schema = Schema.of(
        Field.of("Kolonnenavn_1", StandardSQLTypeName.STRING),
        Field.of("Kolonnenavn_2", StandardSQLTypeName.INT64),
    )

    val tableDefinition: TableDefinition = StandardTableDefinition.of(schema)
    val tableInfo = TableInfo.newBuilder(TableId.of(datasetName, tableName), tableDefinition).build()

    val bigQuery: BigQuery = PropertiesConfig.BigQueryConfig().bigQuery.apply {
        create(datasetInfo)
        create(tableInfo)
    }

    //VIKTIG!
    //Det finnes ingen annen måte å teste bigquery uten å faktisk inserte på ekte. Derfor lager vi et nytt dataset og definerer en tabell
    //Til slutt må vi derfor slette tabellen
    // NB! Hvis testen får en exception vil ikke tabellen bli slettet og den må slettes manuelt i GCP
    afterEach {
        RemoteBigQueryHelper.forceDelete(bigQuery, datasetName)
    }

    test("Tester insertion") {
        insertData(datasetName, tableName, bigQuery)

        val query = QueryJobConfiguration.of("SELECT * FROM ${datasetName}.${tableName}" )

        val queryResponse: TableResult = bigQuery.query(query)
        queryResponse.totalRows.shouldBe(1)

        with(queryResponse.values.first()) {
            get("Kolonnenavn_1").value.shouldBe("en verdi")
            get("Kolonnenavn_2").value.shouldBe("2")
        }
    }
})

private fun insertData(datasetName: String, tableName: String, bigQuery: BigQuery){
    val request = InsertAllRequest.newBuilder(datasetName, tableName)
    val insertionMap: Map<String?, Any?> = mapOf(
        "Kolonnenavn_1" to "en verdi",
        "Kolonnenavn_2" to 2
    )

    val rows = listOf(InsertAllRequest.RowToInsert.of(insertionMap))
    val insertAllRequest =  request.setRows(rows).build()
    val response: InsertAllResponse = bigQuery.insertAll(insertAllRequest)

    var errorString = ""

    if (response.hasErrors()) {
        response.insertErrors.entries.forEach { entry ->
            errorString += "error in entry ${entry.key}: ${entry.value}/n"
        }
        log.error(errorString)
    }
}
