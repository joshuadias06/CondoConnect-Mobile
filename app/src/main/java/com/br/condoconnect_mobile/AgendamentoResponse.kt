package com.br.condoconnect_mobile

import java.sql.Date
import java.time.LocalTime

data class AgendamentoResponse(
    val id: Int?,
    val data: Date?,
    val evento: String?,
    val horario: LocalTime?,
    val descricao: String?,
)

