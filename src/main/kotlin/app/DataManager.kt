package app

class DataManager : ReportData {
  private val window = Window()

  override fun reportSystemData() {
    window.updateSystemData()
  }

  override fun reportProcessData() {
    window.updateProcesses()
  }
}