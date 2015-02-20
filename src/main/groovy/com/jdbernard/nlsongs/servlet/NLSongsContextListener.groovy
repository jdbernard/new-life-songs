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

        // Create the pooled data source
        HikariConfig hcfg = new HikariConfig(
            context.getInitParameter("context.config.file"))

        HikariDataSource hds = new HikariDataSource(hcfg)

        // Create the NLSonsDB instance.
        NLSongsDB songsDB = new NLSongsDB(hds)

        context.setAttribute('songsDB', songsDB)
        NLSongsContext.songsDB = songsDB }

    public void contextDestroyed(ServletContextEvent event) {
        def context = event.servletContext

        // Shutdown the Songs DB instance (it will shut down the data source).
        NLSongsDB songsDB = context.getAttribute('songsDB')
        if (songsDB) songsDB.shutdown()
        
        context.removeAttribute('songsDB') }
}
