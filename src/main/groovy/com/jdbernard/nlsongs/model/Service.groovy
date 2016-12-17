package com.jdbernard.nlsongs.model

import org.joda.time.LocalDate

public class Service implements Serializable {

    int id
    private LocalDate date
    ServiceType serviceType
    String description

    public boolean equals(Object thatObj) {
        if (thatObj == null) return false
        if (!(thatObj instanceof Service)) return false

        Service that = (Service) thatObj

        return (this.id == that.id &&
                this.date == (that.rawDate) &&
                this.serviceType == that.serviceType) }

    public void setDate(Date date) { this.date = LocalDate.fromDateFields(date) }

    public void setDate(LocalDate date) { this.date = date }

    public Date getDate() { return this.date.toDate() }

    public String toString() { return "$id: $date - $serviceType" }

    // Needed only because the @directFieldAccesor syntax stopped working in
    // Groovy 2.4.7
    private LocalDate getRawDate() { return this.date }
}
