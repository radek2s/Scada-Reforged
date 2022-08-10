package org.reggster.srfcommons.external

data class ExternalServiceResponse(
    val status: Int,
    val message: String,
    val payload: Any?,
)