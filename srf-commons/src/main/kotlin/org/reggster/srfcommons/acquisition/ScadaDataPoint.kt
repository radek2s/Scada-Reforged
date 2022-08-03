package org.reggster.srfcommons.acquisition

interface ScadaDataPoint {
    var id: Int
    var sid: String
    var name: String
    var enabled: Boolean
    var settable: Boolean
    var dataType: Int
}