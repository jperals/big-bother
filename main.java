/*
* Big bother
* http://programalaplaza.medialab-prado.es/sketch/edit/5356d67bd33b4b59d64ed8ae
* @joanperals
*/

Eye eye;

void setup()
{
  size(192, 157);
  frameRate(25);
  eye = new Eye();
}

void draw()
{
  eye.watch();
  eye.display();
}
