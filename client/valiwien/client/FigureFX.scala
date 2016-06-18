package valiwien.client

import valiwien.common.Figure
import javafx.scene.Group
import javafx.scene.transform.Rotate

trait FigureFX extends EntityFX {
  
  val model = new Group()
  node.getChildren.add(model)
  model.setRotationAxis(Rotate.Z_AXIS)
  
  val entity: Figure
  def updateUI() = {
    node.setTranslateX(entity.x)
    node.setTranslateY(entity.y)
    model.setRotate(entity.dir / Math.PI * 180)
  }
  
}