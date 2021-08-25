package dto

data class ProcessDTO(
  val pid: Int,
  val user: String,
  val cpu: String,
  val mem: String,
  val time: String,
  val command: String,
)