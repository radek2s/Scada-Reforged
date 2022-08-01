package org.reggster.srfcommons.acquisition

/**
 * Scada DataSource API methods that
 * must be implemented to handle basic
 * data source operations
 */
interface ScadaDataSourceApi {

    fun onCreateRequest()

    fun onEditRequest()

    fun onDeleteRequest()

    /**
     * On state change message
     *
     * Disable or Enable the DataSource based on
     * the state taken from received message
     */
    fun onStateChangeRequest()




}