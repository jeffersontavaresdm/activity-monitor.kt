package app

import util.ColorLayout
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.plaf.metal.MetalLookAndFeel
import kotlin.system.exitProcess

class WindowKeyListener(private val window: Window) : KeyListener {

  override fun keyTyped(e: KeyEvent?) {}

  override fun keyReleased(e: KeyEvent?) {}

  override fun keyPressed(event: KeyEvent) {
    when (event.keyCode) {
      27 -> {
        window.dispose()
        exitProcess(130)
      }
      112 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.darkGray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      113 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.black).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      114 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.blue).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      115 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.gray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      121 -> {
        val tips = """
        |Press Button to select color
        |F1: darkGray (defalt)
        |F2: black
        |F3: blue
        |F4: gray
        |
        |ESC: exit""".trimMargin()

        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.gray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
        JOptionPane.showMessageDialog(window, tips)
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.darkGray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
    }
  }
}