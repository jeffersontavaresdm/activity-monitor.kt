package util

import java.awt.Color
import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

class ColorLayout(val color: Color) {


  val theme: DefaultMetalTheme = object : DefaultMetalTheme() {

    /**
     * inactive title color
     */
    override fun getWindowTitleInactiveBackground(): ColorUIResource {
      return ColorUIResource(Color.lightGray)
    }

    /**
     * active title color
     */
    override fun getWindowTitleBackground(): ColorUIResource {
      return ColorUIResource(Color.gray)
    }

    /**
     * dotted bar color
     */
    override fun getPrimaryControlHighlight(): ColorUIResource {
      return ColorUIResource(Color.white)
    }

    override fun getPrimaryControlDarkShadow(): ColorUIResource {
      return ColorUIResource(Color.black)
    }

    /**
     * button colors
     */
    override fun getPrimaryControl(): ColorUIResource {
      return ColorUIResource(Color.white)
    }

    /**
     * window border colors
     */
    override fun getControlHighlight(): ColorUIResource {
      return ColorUIResource(Color.gray)
    }

    override fun getControlDarkShadow(): ColorUIResource {
      return ColorUIResource(Color.gray)
    }

    /**
     * window background color
     */
    override fun getControl(): ColorUIResource {
      return ColorUIResource(color)
    }
  }
}