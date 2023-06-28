package util

import no.nav.sokos.bigquery.koder.config.PropertiesConfig
import no.nav.sokos.bigquery.koder.database.Db2DataSource
import org.testcontainers.containers.Db2Container


object DatabaseTestUtils {
  private  val container = Db2Container("ibmcom/db2:11.5.0.0a")

    fun getDataSource(initScriptPath: String): Db2DataSource {
        if(container.isRunning) container.stop()
        val config = initContainer(initScriptPath)
        return Db2DataSource(config)
    }

    private fun initContainer(
        initScriptPath: String,
        schemaName: String = "TEST_SCHEMA"
    ): PropertiesConfig.DB2Config {
        container
            .apply {
                withInitScript(initScriptPath)
                acceptLicense()
                start()
            }
        return PropertiesConfig.DB2Config(
            host = container.host,
            port = container.firstMappedPort,
            name = container.databaseName,
            schema = schemaName,
            username = container.username,
            password = container.password
        )
    }
}
