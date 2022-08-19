package org.reggster.srfcommons.async

import org.reggster.srfcommons.acquisition.ScadaDataSourceType

data class PointValue(
    var dsType: ScadaDataSourceType,
    var dsId: Int,
    var dpId: Int,
    var value: Double,
    var time: Long?
)