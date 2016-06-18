package valiwien.client

import valiwien.common.Figure
import javafx.scene.shape.Cylinder
import javafx.scene.Scene
import javafx.scene.paint.PhongMaterial
import javafx.scene.paint.Color

object Player {
  def makePlayerModel() = {
    val material = new PhongMaterial()
    material.setDiffuseColor(Color.DARKBLUE)
    material.setSpecularColor(Color.BLUE)
    val body = new Cylinder(0.6, 1.8, 32)
    body.setTranslateZ(0.9)
    body.setMaterial(material)
    body
  }
}
class Player extends FigureFX {
  val entity = new Figure
  val body = Player.makePlayerModel()
  model.getChildren.add(body)
}

class LocalPlayer extends Player {
  
}