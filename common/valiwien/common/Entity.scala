package valiwien.common

trait Entity {
  
  def x: Double
  def y: Double
  def dir: Double // in radians
  
  def step(): Unit
  
}