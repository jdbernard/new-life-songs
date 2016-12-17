package com.jdbernard.nlsongs.servlet

import javax.servlet.ServletContext
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

import com.jdbernard.nlsongs.db.NLSongsDB

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import org.slf4j.Logger
import org.slf4j.LoggerFactory

public final class NLSongsContextListener implements ServletContextListener {

    private static final log = LoggerFactory.getLogger(NLSongsContextListener)

    public void contextInitialized(ServletContextEvent event) {
        def context = event.servletContext

        Properties props = new Properties()

        // Load configuration details from the context configuration.
        NLSongsContextListener.getResourceAsStream(
            context.getInitParameter('context.config.file'))
          .withStream { is -> props.load(is) }

        // Load database configuration
        Properties dataSourceProps = new Properties()
        String dbConfigFile = context.getInitParameter('datasource.config.file')

        if (dbConfigFile) {
          NLSongsContextListener.getResourceAsStream(dbConfigFile)
            .withStream { is -> dataSourceProps.load(is) } }

        // Load database configuration from environment variables (may
        // override settings in file).
        System.properties.keySet().findAll { it.startsWith('DB_') }.each { key ->
          dataSourceProps["dataSource.${key.substring(3)}"] = System.properties[key] }

        log.debug("Database configuration: {}", dataSourceProps)

        // Create the pooled data source
        HikariConfig hcfg = new HikariConfig(dataSourceProps)

        HikariDataSource hds = new HikariDataSource(hcfg)

        // Create the NLSonsDB instance.
        NLSongsDB songsDB = new NLSongsDB(hds)

        context.setAttribute('songsDB', songsDB)
        NLSongsContext.songsDB = songsDB
        NLSongsContext.mediaBaseUrl = props["nlsongs.media.baseUrl"] }

    public void contextDestroyed(ServletContextEvent event) {
        def context = event.servletContext

        // Shutdown the Songs DB instance (it will shut down the data source).
        NLSongsDB songsDB = context.getAttribute('songsDB')
        if (songsDB) songsDB.shutdown()

        context.removeAttribute('songsDB') }
}
