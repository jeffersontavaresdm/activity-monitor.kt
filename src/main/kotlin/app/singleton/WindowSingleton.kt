package app.singleton

import app.Window

object WindowSingleton {
  private var window: Window? = null

  val instance = window ?: run { window = Window(); window!! }
}