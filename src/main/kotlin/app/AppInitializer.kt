package app

object AppInitializer : Runnable {

  private fun initSystemInfo() {
    while (true) {
      SystemManager.printSystemInfo()
      Thread.sleep(1000)
    }
  }

  private fun initSystemProc() {
    while (true) {
      SystemManager.printSystemProc()
      Thread.sleep(3000)
    }
  }

  override fun run() {
    Thread { initSystemInfo() }.start()
    Thread { initSystemProc() }.start()
  }
}