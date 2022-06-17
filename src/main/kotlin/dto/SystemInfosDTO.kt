package dto

data class SystemInfosDTO(

  /**
   * process fields
   */
  val runningThreads: Int,
  val totalSystemTime: String,

  /**
   * cpu fields
   */
  val threadsPerCore: Int,
  val availableProcessors: Int,
  val sockets: String,

  /**
   * ram memory fields
   */
  val freeMem: Long,
  val usedMem: Long,
  val totalMem: Long,

  /**
   * swap memory fields
   */
  val freeSwap: Long,
  val usedSwap: Long,
  val totalSwap: Long,
)