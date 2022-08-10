package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.ScadaDataSourceService

interface ScadaDataSourceEntityService<T: ScadaDataSourceEntity<*>>: ScadaDataSourceService<T, Int> {
}