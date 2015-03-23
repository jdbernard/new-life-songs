package com.jdbernard.nlsongs.servlet

import javax.servlet.ServletContext
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

import com.jdbernard.nlsongs.db.NLSongsDB

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

public final class NLSongsContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        def context = event.servletContext

        // Load the context configuration.
        Properties props = new Properties()
        NLSongsContextListener.getResourceAsStream(
            context.getInitParameter('context.config.file')).withStream { is ->
                props.load(is) }

        // Create the pooled data source
        HikariConfig hcfg = new HikariConfig(
            context.getInitParameter('datasource.config.file'))

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
