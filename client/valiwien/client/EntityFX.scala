package valiwien.client

import javafx.scene.Group
import valiwien.common.Entity

trait EntityFX {
  
  val node = new Group()
  val entity: Entity
  // May only be called in the FX thread.
  def updateUI(): Unit
  
}