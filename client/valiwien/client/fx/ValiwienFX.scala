package valiwien.client.fx

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.scene.control.Button
import javafx.event.EventHandler
import javafx.event.Event

object ValiwienFX {
  
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[ValiwienFX], args: _*)
  }
  
  object fonts {
    
    val title = new Font("Verdana", 96)
    val button = new Font("Verdana", 24)
    
  }
  
  def button(label: String) = {
    val button = new Button(label)
    button.setFont(ValiwienFX.fonts.button)
    button.setMaxWidth(Double.MaxValue)
    button
  }
  def eventHandler[Ev <: Event](f: Ev => Unit): EventHandler[Ev] = new EventHandler[Ev]() {
    def handle(ev: Ev) = {
      f(ev)
    }
  }
  def runnable(f: => Unit) = new Runnable {
    def run() = { f }
  }
  
}
class ValiwienFX extends Application {
  
  def start(stage: Stage) = {
    lazy val nav: FXNav = new FXNav {
      var stack = List[(FXSlide, Scene)]()
      def enter(target: FXSlide): Unit = {
        val scene = target.open(nav)
        stack = ((target, scene)) :: stack
        stage.setScene(scene)
        stage.setFullScreen(true)
      }
      def exit(): Unit = {
        stack match {
          case (currentScene, _) :: (newStack @ (_, scene) :: _) =>
            currentScene.close()
            stack = newStack
            stage.setScene(scene)
            stage.setFullScreen(true)
          case _ => stage.close()
        }
      }
      def switch(target: FXSlide): Unit = {
        val (currentScene, _) :: tail = stack
        currentScene.close()
        val scene = target.open(nav)
        stack = ((target, scene)) :: stack
        stage.setScene(scene)
        stage.setFullScreen(true)
      }
    }
    stage.show()
    stage.setFullScreen(true)
    nav.enter(ValiwienMenu)
  }
  
}
sealed trait FXNav {
  
  def enter(target: FXSlide): Unit
  def exit(): Unit
  def switch(target: FXSlide): Unit
  
}
trait FXSlide {
  
  def open(nav: FXNav): Scene
  def close(): Unit
  
}