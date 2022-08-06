package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.model.DataSourceVirtualRT

class DataSourceThread(datasourceRt: DataSourceVirtualRT): Thread(datasourceRt) {
    val id = datasourceRt.id
}