package org.reggster.srfcommons.acquisition.virtual

import org.reggster.srfcommons.acquisition.ScadaDataPoint

interface DataPointVirtual: ScadaDataPoint {
    var changeType: Int
    var minValue: Int
    var maxValue: Int
}