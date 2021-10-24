public class EyeLids {
  EyeLid upperLid, lowerLid;
  PVector[] pupilBoundingBox = new PVector[2];
  public EyeLids() {
    pupilBoundingBox = {new PVector(width/2, height/2), new PVector(width/2, height/2)};
    upperLid = new UpperEyeLid();
    lowerLid = new LowerEyeLid();
  }
  public void close() {
    upperLid.close();
    lowerLid.close();
  }
  public void display() {
    upperLid.display();
    lowerLid.display();
  }
  public void open() {
    upperLid.open(pupilBoundingBox);
    lowerLid.open(pupilBoundingBox);
  }
  public void update(blobs) {
    if(blobs.length > 0) {
      pupilBoundingBox = {new PVector(width/2, height/2), new PVector(width/2, height/2)};
      for(int i = 0; i < blobs.length; i++) {
        pupilBoundingBox[0].x = min(pupilBoundingBox[0].x, blobs[i].x);
        pupilBoundingBox[1].x = max(pupilBoundingBox[1].x, blobs[i].x);
        pupilBoundingBox[0].y = min(pupilBoundingBox[0].y, blobs[i].y);
        pupilBoundingBox[1].y = max(pupilBoundingBox[1].y, blobs[i].y);
      }
      this.open();
    }
    else {
      this.close();
    }
    upperLid.update();
    lowerLid.update();
  }
}
