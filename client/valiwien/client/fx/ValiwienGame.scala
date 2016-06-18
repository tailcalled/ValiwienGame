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
import valiwien.client.EntityFX
import valiwien.client.LocalPlayer
import valiwien.client.Player
import javafx.scene.input.KeyCode
import javafx.application.Platform
import java.util.concurrent.CountDownLatch

class ValiwienGame(width: Int, height: Int) extends FXSlide {
  
  def open(nav: FXNav) = {
    val root = new Group()
    val scene = new Scene(root, width, height, true, SceneAntialiasing.BALANCED)
    scene.setFill(Color.BLACK)
    val cam = new PerspectiveCamera(true)
    val camNode = new Group()
    camNode.getChildren.add(cam)
    val camDir = new Rotate(0, Rotate.Z_AXIS)
    val camYaw = new Rotate(45, Rotate.X_AXIS)
    val camZoom = new Translate(0, 0, -25)
    camNode.getTransforms.addAll(camDir, camYaw, camZoom)
    scene.setCamera(cam)
    var entities = Vector[EntityFX]()
    def addEntity(entity: EntityFX) = {
      entities :+= entity
      root.getChildren.add(entity.node)
    }
    for (x <- -10 until 10; y <- -10 until 10; if (x+y) % 2 == 0) {
      val cube = new Box()
      val material = new PhongMaterial()
      material.setDiffuseColor(Color.DARKGREEN)
      material.setSpecularColor(Color.GREEN)
      cube.setMaterial(material)
      cube.setTranslateX(x * 2)
      cube.setTranslateY(y * 2)
      cube.setTranslateZ(-1)
      root.getChildren.add(cube)
    }
    val player = new LocalPlayer()
    player.node.getChildren.add(camNode)
    addEntity(player)
    addEntity(new Player())
    scene.onKeyPressedProperty().setValue(ValiwienFX.eventHandler { ev =>
      ev.getCode match {
        case KeyCode.W =>
          player.entity.x += Math.cos(player.entity.dir)
          player.entity.y += Math.sin(player.entity.dir)
        case KeyCode.A =>
          player.entity.dir -= Math.PI / 16
        case KeyCode.D =>
          player.entity.dir += Math.PI / 16
        case _ =>
      }
    })
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
    thread = new Thread() {
      override def run() = {
        while (true) {
          for (entity <- entities) {
            entity.entity.step()
          }
          val latch = new CountDownLatch(1)
          Platform.runLater(ValiwienFX.runnable {
            for (entity <- entities) {
              entity.updateUI()
            }
            latch.countDown()
          })
          latch.await()
          Thread.sleep(1)
        }
      }
    }
    thread.start()
    scene
  }
  var thread = null: Thread
  def close() = {
    thread.interrupt()
  }
  
}