public class EyeBall {
  var blobs = new Array();
  int numberOfIrisLayers = 3;
  color[] baseEyeBallColors = {color(200, 200, 200), color(210, 210, 210), color(220, 220, 220), color(230, 230, 230), color(255, 255, 255)};
  color baseIrisColor = color(random(255), random(255), random(255));
  color[] basePupilBallColors = {color(63, 63, 63), color(41, 41, 41), color(63, 63, 53), color(101, 101, 101)};
  color[] eyeBallColors = new color[baseEyeBallColors.length + numberOfIrisLayers + basePupilBallColors.length];
  int diameter, radius;
  float basePupilSizeFactor = 1;
  float noiseOffset = 0;
  HashMap pupils;
  int numberOfPartitions = 11;
  public EyeBall() {
    diameter = 2 * max(width, height);
    radius = diameter/2;
    pupils = new HashMap();
    for(int i = 0; i < numberOfIrisLayers; i++) {
      baseEyeBallColors = append(baseEyeBallColors, baseIrisColor);
    }
    for(int i = 0; i < basePupilBallColors.length; i++) {
      baseEyeBallColors = append(baseEyeBallColors, basePupilBallColors[i]);
    }
  }
  public void display() {
    background(eyeBallColors[0]);
    if(blobs.length > 0) {
      for(int i = 1; i < eyeBallColors.length; i++) {
        for(int j = 0; j < blobs.length; j++) {
          String blobId = blobs[j]["id"];
          Pupil pupil = pupils.get(blobId);
          displayEyeBallLayer(pupil.x, pupil.y, i);
        }
      }
    }
  }
  public void update(blobs) {
    this.blobs = blobs;
    if(blobs.length > 0) {
      basePupilSizeFactor += (1 / sqrt(blobs.length) - basePupilSizeFactor)/10;
    }
    for(int i = 0; i < blobs.length; i++) {
      String blobId = blobs[i]["id"];
      if(pupils.containsKey(blobId)) {
        Pupil pupil = pupils.get(blobId);
        pupil.setTargetPosition(blobs[i].x, blobs[i].y);
        pupil.update();
      }
      else {
        pupils.put(blobId, new Pupil(blobs[i].x, blobs[i].y));
      }
    }
    for(int i = 0; i < eyeBallColors.length; i++) {
      eyeBallColors[i] = shakeColor(baseEyeBallColors[i], sq(i)*10);
    }
    noiseOffset += 0.015;
  }
  private void displayEyeBallLayer(float x, float y, int index) {
    pushStyle();
    fill(eyeBallColors[index]);
    stroke(eyeBallColors[index]);
    int circleRadius = basePupilSizeFactor * radius * (1 - sqrt(index/(eyeBallColors.length - 1)));
    shakingCircle(x, y, circleRadius);
    popStyle();
  }
  private void displayEyeBallLayer(PVector position, int index) {
    displayEyeBallLayer(position.x, position.y, index);
  }
  private PVector rectangleToEllipseCoordinates(PVector p) {
    return rectangleToEllipseCoordinates(p.x, p.y);
  }
  private PVector rectangleToEllipseCoordinates(float xRectangle, float yRectangle) {
    float xCircle = xRectangle * sqrt(1 - 0.5 * abs(yRectangle));
    float yCircle = yRectangle * sqrt(1 - 0.5 * abs(xRectangle));
    return new PVector(xCircle, yCircle);
  }
  private PVector absoluteToRelative(float x, float y) {
    x += (diameter - width)/2;
    y += (diameter - height)/2;
    return new PVector((x - radius) / radius, (y - radius) / radius);
  }
  private PVector relativeToAbsolute(float x, float y) {
    PVector p = new PVector((x + 1) * radius, (y + 1) * radius);
    p.x -= (diameter - width)/2;
    p.y -= (diameter - height)/2;
    return p;
  }
  private PVector relativeToAbsolute(PVector p) {
    return relativeToAbsolute(p.x, p.y);
  }
  private PVector toEyeBallCoordinates(float x, float y) {
    PVector positionRelative = absoluteToRelative(x, y);
    PVector positionRelativeInEyeBall = rectangleToEllipseCoordinates(positionRelative);
    return relativeToAbsolute(positionRelativeInEyeBall);
  }
  private float shake(float n) {
    return n + 25*(noise(n/10, noiseOffset) - 0.5);
  }
  private color shakeColor(color c, int i) {
    float r = shakeColorComponent(red(c), i);
    float g = shakeColorComponent(green(c), i*2);
    float b = shakeColorComponent(blue(c), i*3);
    //float a = shakeColorComponent(alpha(c), i*4);
    return color(r, g, b);
  }
  private int shakeColorComponent(colorComponent c, int i) {
    int newValue = c + 255 * (noise(i, noiseOffset) - 0.5);
    newValue = constrain(newValue, 0, 255);
    return newValue;
  }
  private void shakingCircle(float x0, float y0, int circleRadius) {
    float xFirst = x0 + circleRadius;
    float yFirst = y0;
    float xPrevious = xFirst;
    float yPrevious = yFirst;
    for(int i = 1; i < numberOfPartitions; i++) {
      float x1 = x0 + circleRadius * cos(2 * PI * i/numberOfPartitions);
      float y1 = y0 + circleRadius * sin(2 * PI * i/numberOfPartitions);
      shakingTriangle(xPrevious, yPrevious, x1, y1, x0,y0);
      xPrevious = x1;
      yPrevious = y1;
    }
    shakingTriangle(xPrevious, yPrevious, xFirst, yFirst, x0, y0)
  }
  private void shakingTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
    PVector firstPoint = toEyeBallCoordinates(shake(x1), shake(y1));
    PVector secondPoint = toEyeBallCoordinates(shake(x2), shake(y2));
    PVector thirdPoint = toEyeBallCoordinates(shake(x3), shake(y3));
    triangle(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y, thirdPoint.x, thirdPoint.y);
  }
}
