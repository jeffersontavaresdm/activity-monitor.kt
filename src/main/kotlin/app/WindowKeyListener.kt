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

class WindowKeyListener(
  private val window: Window,
  private val colorLayout: ColorLayout,
) : KeyListener {

  override fun keyTyped(event: KeyEvent?) {}

  override fun keyReleased(event: KeyEvent?) {}

  override fun keyPressed(event: KeyEvent) {
    when (event.keyCode) {
      27 -> {
        window.dispose()
        exitProcess(130)
      }
      112 -> {
        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.darkGray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      113 -> {
        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.black).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      114 -> {
        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.blue).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
      115 -> {
        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.gray).theme)
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

        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.gray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)

        JOptionPane.showMessageDialog(window, tips)

        colorLayout.color = Color.darkGray
        MetalLookAndFeel.setCurrentTheme(colorLayout.changeColor(Color.darkGray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(window)
      }
    }
  }
}