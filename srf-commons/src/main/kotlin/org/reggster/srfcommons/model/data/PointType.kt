package org.reggster.srfcommons.model.data

/*
I was experimenting with different ScadaValue objects to store
Binary/Integer/Double values separately, but it was not making sense.

In InfluxDB it is enough to store all values as Double. We just need to
remember to convert all values before to specific format.

PointTypes are required for further development to provide a specific
rendering options, like ON/OFF state for Binary Point or
State machines for Integer Points. Now it is not useful.
 */
enum class PointType {
    BINARY,
    INTEGER,
    DOUBLE
}
