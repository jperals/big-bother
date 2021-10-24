class MeshPoint {
  public float x, y;
  float noiseOffset = 0;
  float step = 0.2;
  PVector originalPosition, targetPosition;
  public MeshPoint(float x, float y) {
    this.x = x;
    this.y = y;
    originalPosition = new PVector(x, y);
    this.targetPosition = new PVector(x, y);
  }
  public void setTargetPosition(float x, float y) {
    targetPosition.x = x;
    targetPosition.y = y;
  }
  public void takeStep(float step) {
    x += (targetPosition.x - x)*step;
    y += (targetPosition.y - y)*step;
  }
  public void shake(noiseOffset) {
    x += 10 * (noise(x, y, noiseOffset) - 0.5);
    y += 10 * (noise(x, y, noiseOffset*2) - 0.5);
  }
  public void update() {
    takeStep(step);
  }
}
