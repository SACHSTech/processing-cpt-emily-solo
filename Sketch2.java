import processing.core.PApplet;

public class Sketch2 extends PApplet {
	
	
  int gameScreen = 0;

  public void settings() {
	// put your size call here
    size(400, 400);
  }

  
  public void setup() {
    background(210, 255, 173);
  }

  
  public void draw() {
	  
	// sample code, delete this stuff
    if (gameScreen == 0) {
      homeScreen();
    } else if (gameScreen == 1) {
      gameScreen();
    } else if (gameScreen == 2) {
      gameOverScreen();
    }

    stroke(255);
    line(50, 125, 70, 50);  
  }

  public void homeScreen () {
    fill(132,188,208);
    textSize(30);
    text("Click anywhere to play", 250, 400);
  }
  
  public void gameScreen () {

  }

  public void gameOverScreen () {
    textSize(50);
    text("GAME OVER :(", 20, 30 );
  }
}
