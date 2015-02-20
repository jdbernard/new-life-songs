package com.jdbernard.nlsongs.servlet

import javax.servlet.ServletContext
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

import com.jdbernard.nlsongs.db.NLSongsDB

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

public final class NLSongsContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        context = event.servletContext

        // Create the pooled data source
        HikariConfig hcfg = new HikariConfig()
        hcfg.username = 'TODO'
        hcfg.password = 'TODO'
        hcfg.dataSourceClassName = 'TODO'
        hcfg.addDataSourceProperty('cachePrepStmts', 'true')
        hcfg.addDataSourceProperty('prepStmtCacheSize', '250')
        hcfg.addDataSourceProperty('prepStmtCacheSqlLimit', '2048')
        hcfg.addDataSourceProperty('useServerPrepStmts', 'true')

        HikariDataSource hds = new HikariDataSource(hcfg)

        // Create the NLSonsDB instance.
        NLSongsDB songsDB = new NLSongsDB(hds)

        context.setAttribute('songsDB', songsDB) }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.servletContext

        // Shutdown the Songs DB instance (it will shut down the data source).
        NLSongsDB songsDB = context.getAttribute('songsDB')
        songsDB.shutdown()
        
        context.removeAttribute('songsDB') }
}
