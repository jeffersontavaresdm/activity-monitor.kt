package dto

import java.time.LocalTime

data class ProcessDTO(
  val pid: Int,
  val user: String,
  val cpu: Double,
  val mem: Double,
  val time: LocalTime,
  val command: String,
)