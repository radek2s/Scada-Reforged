package org.reggster.srfcommons.acquisition

interface ScadaDataSource {
    val type: ScadaDataSourceType
    var id: Int
    var sid: String
    var name: String
    var enabled: Boolean
    var updatePeriod: Int
    var updatePeriodType: Int
}