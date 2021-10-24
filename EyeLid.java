public class EyeLid {
  int noiseOffset = 0;
  int numberOfPartitions = 7;
  MeshPoint[][] mesh = new MeshPoint[numberOfPartitions + 1][numberOfPartitions + 1];
  String type;
  public EyeLid() {
    this.type = type;
    for(int i = 0; i <= numberOfPartitions; i++) {
      float y = (height/2) * i / numberOfPartitions;
      for(int j = 0; j <= numberOfPartitions; j++) {
        float x = width * j / numberOfPartitions;
        mesh[i][j] = new MeshPoint(x, y);
      }
    }
  }
  public void close() {
    for(int i = 0; i <= numberOfPartitions; i++) {
      float y = (height/2)  * i / numberOfPartitions;
      for(int j = 1; j < numberOfPartitions; j++) {
        float x = width * j / numberOfPartitions;
        mesh[i][j].setTargetPosition(x, y);
      }
    }
  }
  public void display() {
    for(int i = 0; i < numberOfPartitions; i++) {
      for(int j = 0; j < numberOfPartitions; j++) {
        pushMatrix();
        if(type == "lower") {
          translate(0, height/2);
        }
        MeshPoint point = mesh[i][j];
        int dr = 0, dg = noiseOffset, db = 2*noiseOffset;
        int r = 255*noise(point.originalPosition.x/300, point.originalPosition.y, dr),
            g = 255*noise(point.originalPosition.x/300, point.originalPosition.y, dg),
            b = 255*noise(point.originalPosition.x/300, point.originalPosition.y, db);
        color baseColor = color(r, g, b);
        fill(baseColor);
        stroke(baseColor);
        beginShape();
        vertex(point.x, point.y);
        point = mesh[i+1][j];
        vertex(point.x, point.y);
        point = mesh[i+1][j];
        vertex(point.x, point.y);
        point = mesh[i+1][j+1];
        vertex(point.x, point.y);
        point = mesh[i][j+1];
        vertex(point.x, point.y);
        endShape();
        popMatrix();
      }      
    }
  }
  public void shake() {
    for(int i = 1; i < numberOfPartitions; i++) {
      for(int j = 1; j < numberOfPartitions; j++) {
        mesh[i][j].shake(noiseOffset);
      }
    }
  }
  public void update() {
    noiseOffset += 0.01;
    for(int i = 0; i <= numberOfPartitions; i++) {
      for(int j = 1; j < numberOfPartitions; j++) {
        mesh[i][j].update();
      }
    }
    shake();
  }
}
