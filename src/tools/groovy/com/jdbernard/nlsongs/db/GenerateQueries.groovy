package com.jdbernard.nlsongs.db

public class GenerateQueries {

    public static void main(String[] args) {

    }

    public static Map<String, Map<String, String> > generateQueries(String ddl) {

        def tables = [:]

        // Find the table definitions
        String tableRegex = /(?ms)(?:CREATE TABLE (?:IF NOT EXISTS )?([^\s]+) \(([^\s]+);.+?)+/

        ddl.eachMatch(tableRegex) { matchGroups ->
            String tableName = matchGroups[1]

            // Parse the column definitions.

        // Create new record insert statements.

        // Create insert queries.

        // Create update queries.

        // Create delete queries.

        // Create ID lookup queries.

    }
}
