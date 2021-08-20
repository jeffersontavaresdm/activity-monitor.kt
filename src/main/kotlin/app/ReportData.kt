package app

interface ReportData {

  /**
   * data of the system
   */
  fun reportSystemData()

  /**
   * data of the running processes
   */
  fun reportProcessData()
}