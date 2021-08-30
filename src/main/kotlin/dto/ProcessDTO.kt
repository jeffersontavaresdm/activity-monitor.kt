package dto

data class ProcessDTO(
  val pid: Int,
  val user: String,
  val cpu: Double,
  val mem: Double,
  val time: String,
  val command: String,
)