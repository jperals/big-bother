public class LowerEyeLid extends EyeLid {
  public LowerEyeLid() {
    super();
    this.type = "lower";
  }
  public void open(PVector[] pupilBoundingBox) {
    PVector leftEdgeControlPoint, middleLeftControlPoint, middlePoint, middleRightControlPoint, rightEdgeControlPoint;
    float extraOpenness = 20, minimumOpenness = 20;
    int firstRow, lastRow;
    float y0 = 0;
    firstRow = 0;
    lastRow = numberOfPartitions - 1;
    float peak = min(max(pupilBoundingBox[1].y - height/2 + extraOpenness, minimumOpenness), height);
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
        y1 = bezierPoint(y0, leftEdgeControlPoint.y, middleLeftControlPoint.y, middlePoint.y, 2 * t);
      }
      else {
        x1 = bezierPoint(middlePoint.x, middleRightControlPoint.x, rightEdgeControlPoint.x, width, 2 * (t - 0.5));
        y1 = bezierPoint(middlePoint.y, middleRightControlPoint.y, rightEdgeControlPoint.y, y0, 2 * (t - 0.5));
      }
      for(int i = firstRow; i <= lastRow; i++) {
        float factor = 1 - i / numberOfPartitions;
        x = x0 + (x1 - x0) * factor;
        y = mesh[i][j].originalPosition.y + y1 * factor;
        mesh[i][j].setTargetPosition(x, y);
      }
    }
  }
}
