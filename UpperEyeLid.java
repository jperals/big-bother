public class UpperEyeLid extends EyeLid {
  public UpperEyeLid() {
    super();
    this.type = "upper";
  }
  public void open(PVector[] pupilBoundingBox) {
    PVector leftEdgeControlPoint, middleLeftControlPoint, middlePoint, middleRightControlPoint, rightEdgeControlPoint;
    float extraOpenness = 20, minimumOpenness = 20;
    int firstRow, lastRow;
    float y0 = height/2;
    firstRow = 1;
    lastRow = numberOfPartitions;
    float peak = max(min(pupilBoundingBox[0].y - extraOpenness, height/2 - minimumOpenness), 0);
    middleLeftControlPoint = new PVector(min(pupilBoundingBox[0].x, width/3), peak);
    middlePoint = new PVector(width/2, peak);
    middleRightControlPoint = new PVector(max(pupilBoundingBox[1].x, width*2/3), peak);
    leftEdgeControlPoint = new PVector(min(pupilBoundingBox[0].x, width/3)/3, y0);
    rightEdgeControlPoint = new PVector(width - (width - max(pupilBoundingBox[1].x, width*2/3))/3, y0);
    for(int j = 1; j < numberOfPartitions; j++) {
      float x0 = width * j / numberOfPartitions;
      float x1, y1, x, y;
      float t =  j / numberOfPartitions;
      if(t < 0.5) {
        x1 = bezierPoint(0, leftEdgeControlPoint.x, middleLeftControlPoint.x, middlePoint.x, 2 * t);
        y1 = bezierPoint(y0, leftEdgeControlPoint.y, middleLeftControlPoint.y, middlePoint.y, 2 * t) - height/2;
      }
      else {
        x1 = bezierPoint(middlePoint.x, middleRightControlPoint.x, rightEdgeControlPoint.x, width, 2 * (t - 0.5));
        y1 = bezierPoint(middlePoint.y, middleRightControlPoint.y, rightEdgeControlPoint.y, y0, 2 * (t - 0.5)) - height/2;
      }
      for(int i = firstRow; i <= lastRow; i++) {
        float factor = i / numberOfPartitions;
        x = x0 + (x1 - x0) * factor;
        y = mesh[i][j].originalPosition.y + y1 * factor;
        mesh[i][j].setTargetPosition(x, y);
      }
    }
  }
}
