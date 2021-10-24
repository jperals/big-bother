public class Eye {
  EyeBall eyeBall;
  EyeLids eyeLids;
  public Eye() {
    eyeBall = new EyeBall();
    eyeLids = new EyeLids();
    api.tracking.connect();
  }
  public void display() {
    eyeBall.display();
    eyeLids.display();
  }
  public void watch() {
    api.tracking.getBlobsOldVersion(function(blobs) {
      eyeBall.update(blobs);
      eyeLids.update(blobs);
    });
  }
}
