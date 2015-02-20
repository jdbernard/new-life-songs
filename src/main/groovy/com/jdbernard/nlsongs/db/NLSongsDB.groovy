package com.jdbernard.nlsongs.db

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.zaxxer.hikari.HikariDataSource

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.transform.CompileStatic

import com.jdbernard.nlsongs.model.*

//@CompileStatic
public class NLSongsDB {

    private HikariDataSource dataSource
    private Sql sql

    public NLSongsDB(HikariDataSource dataSource) {
        this.dataSource = dataSource
        this.sql = new Sql(dataSource) }


    public void shutdown() { dataSource.shutdown() }

    // ### Services
    public Service findService(int id) {
        GroovyRowResult row = sql.firstRow(
            "SELECT * FROM services WHERE id = ?", [id] as List<Object>)
        if (row) return (Service) recordToModel(row, Service)
        else return null }

    // #### Utility functions
    static def recordToModel(def row, Class clazz) {
        def model = clazz.newInstance()

        row.each { recordKey, v ->
            def pts = recordKey.split('_')
            def modelKey = pts.length == 1 ? pts[0] :
                pts[0] + pts[1..-1].collect { it.capitalize() }.join()
            model[modelKey] = v }
        return model }

    static def modelToRecord(def model) {
        def record = [:]

        model.properties.each { modelKey, v ->
            if (modelKey == "class") return
            def recordKey = modelKey.
                replaceAll(/(\p{javaUpperCase})/, /_$1/).toLowerCase()
            record[recordKey] = v }
        return record }

    /*
    static Object recordToModel(GroovyRowResult row, Class clazz) {
        Object model = clazz.newInstance()

        row.each { recordKey, v -> 
            String[] pts = ((String) recordKey).split('_')
            String modelKey = pts[0] +
                pts[1..-1].collect { it.capitalize() }.join()
            model[modelKey] = v }
    }
    */
}
