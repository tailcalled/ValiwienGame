package valiwien.client.fx

import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.layout.BorderPane
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.control.Button
import javafx.geometry.Pos
import javafx.geometry.Insets

object ValiwienMenu extends FXSlide {
  
  def open(nav: FXNav): Scene = {
    val root = new BorderPane()
    root.setPadding(new Insets(30, 30, 30, 30))
    val title = new Label("Elements of Valiwien")
    title.setFont(ValiwienFX.fonts.title)
    title.setMaxWidth(Double.MaxValue)
    title.setAlignment(Pos.CENTER)
    root.setTop(title)
    val menu = new VBox(5)
    menu.setFillWidth(true)
    val startButton = ValiwienFX.button("Start")
    startButton.setOnAction(ValiwienFX.eventHandler { ev =>
      nav.enter(new ValiwienGame(1920, 1080))
    })
    val optionsButton = ValiwienFX.button("Options")
    val exitButton = ValiwienFX.button("Exit")
    exitButton.setOnAction(ValiwienFX.eventHandler { ev =>
      nav.exit()
    })
    menu.getChildren.addAll(startButton, optionsButton, exitButton)
    root.setRight(menu)
    new Scene(root)
  }
  def close() = {}
  
}