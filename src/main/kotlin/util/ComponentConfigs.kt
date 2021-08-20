@file:Suppress("SpellCheckingInspection")

package util

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel

object ComponentConfigs {
  fun setFontConfigs(label: JLabel) {
    label.font = Font("Fira Code Medium", Font.BOLD, 16)
    label.foreground = Color.white
    label.background = Color.black
  }

  fun setSizes(panel: JPanel, pids: Int) {
    val dimension = Dimension(200, pids * 20)
    panel.minimumSize = Dimension(960, 540)
    panel.maximumSize = dimension
    panel.preferredSize = dimension
  }
}