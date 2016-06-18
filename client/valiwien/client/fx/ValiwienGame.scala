package valiwien.client.fx

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.PerspectiveCamera
import javafx.scene.transform.Transform
import javafx.scene.transform.Translate
import javafx.scene.transform.Rotate
import javafx.scene.shape.Box
import javafx.scene.paint.PhongMaterial
import javafx.scene.SceneAntialiasing

class ValiwienGame(width: Int, height: Int) extends FXSlide {
  
  def open(nav: FXNav) = {
    val root = new Group()
    val scene = new Scene(root, width, height, true, SceneAntialiasing.BALANCED)
    scene.setFill(Color.BLACK)
    val cam = new PerspectiveCamera(true)
    val camNode = new Group()
    camNode.getChildren.add(cam); root.getChildren.add(camNode)
    val camPos = new Translate(0, 0)
    val camDir = new Rotate(0, Rotate.Z_AXIS)
    val camYaw = new Rotate(45, Rotate.X_AXIS)
    val camZoom = new Translate(0, 0, -25)
    camNode.getTransforms.addAll(camPos, camDir, camYaw, camZoom)
    scene.setCamera(cam)
    val testMaterial = new PhongMaterial()
    testMaterial.setDiffuseColor(Color.DARKGRAY)
    testMaterial.setSpecularColor(Color.GRAY)
    for (x <- -10 until 10; y <- -10 until 10; z <- 0 until 1; if (x+y+z) % 2 == 0) {
      val cube = new Box()
      cube.setMaterial(testMaterial)
      cube.setTranslateX(x * 2)
      cube.setTranslateY(y * 2)
      cube.setTranslateZ(z * 2)
      root.getChildren.add(cube)
    }
    scene.onMouseMovedProperty().setValue(ValiwienFX.eventHandler { ev => 
      //camPos.setX(ev.getSceneX / 100)
      //camPos.setY(ev.getSceneY / 100)
      camDir.setAngle(ev.getSceneX / 3)
      camYaw.setAngle(ev.getSceneY / 3)
    })
    scene.setOnScroll(ValiwienFX.eventHandler { ev =>
      val newZ = camZoom.getZ + ev.getDeltaY/10
      if (newZ <= -10 && newZ >= -50)
        camZoom.setZ(newZ)
    })
    scene
  }
  def close() = {
    
  }
  
}