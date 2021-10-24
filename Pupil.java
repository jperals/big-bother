public class Pupil {
  float x, y;
  PVector targetPosition;
  public Pupil(float x, float y) {
    this.x = x;
    this.y = y;
    targetPosition = new PVector(x, y);
  }
  public void setTargetPosition(float x, float y) {
    targetPosition.x = x;
    targetPosition.y = y;
  }
  public void update() {
    x += (targetPosition.x - x)/10;
    y += (targetPosition.y - y)/10;
  }
}
