package no.nav.sokos.bigquery.koder

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.sokos.bigquery.koder.database.Db2DataSource
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
    HttpServer().start()
}


private class HttpServer(
    private val db2DataSource: Db2DataSource = Db2DataSource(),
    port: Int = 8080
) {
    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            db2DataSource.close()
            this.stop()
        })
    }

    private val embeddedServer = embeddedServer(
        Netty, port,
        configure = {
            nettyConfig()
        },
        module = {
            applicationModule(db2DataSource)
        })

    fun start() = embeddedServer.start(wait = true)

    private fun stop() = embeddedServer.stop(500, 500, TimeUnit.SECONDS)

}

private fun Application.applicationModule(
    db2DataSource: Db2DataSource,
) = environment.monitor.subscribe(ServerReady) {
    log.info("Server startet - ${Calendar.getInstance().time}")

    // Kall fetch/insert jobb her

}

private fun NettyApplicationEngine.Configuration.nettyConfig() {
    requestReadTimeoutSeconds = 1800
    responseWriteTimeoutSeconds = 1800
}

