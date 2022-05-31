import processing.core.PApplet;

public class Sketch extends PApplet {
	
  int gameScreen = 0;
  
  // ball variables
  int ballX;
  int ballY;
  int ballFallSpeed = 8;
  
  // bar variables
  int barWidth = 100;
  int barHeight = 10;
  int barBounce = 20;

  public void settings() {
	// put your size call here
    size(600, 600);
  }

  
  public void setup() {
    ballX = width/6;
    ballY = height/6;
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

  }

  void homeScreen () {
    background(132,188,208);
    textAlign(CENTER);
    textSize(30);
    text("Click anywhere to play", height/2, 400);
  }
  
  void gameScreen () {
    background(255);

    drawBall();

    drawBar ();
  }

  void gameOverScreen () {
    textSize(50);
    text("GAME OVER :(", 20, 30 );
  }

  public void mousePressed () {
    if (gameScreen == 0) {
      startGame();
    }
  }

  void startGame () {
    gameScreen = 1;
  }

  void drawBall () {
    fill(0);
    ellipse(ballX, ballY, 20, 20);

    ballY += ballFallSpeed;

    if (ballY + 10 > height) {
      ballY = (height-(20/2));
    ballFallSpeed*=-1;
    }
    if (ballY - 10 < 50) {
      ballY = 50+(20/2);
      ballFallSpeed*=-1;
    }
    
  }

  void drawBar () {
    fill(0);
    rectMode(CENTER);
    rect(mouseX, mouseY, barWidth, barHeight);

    // if statement checks if the ball is in between the left and right sides of the rectangle
    if ((ballX > + mouseX - (barWidth/2)) && (ballX < mouseX + (barWidth/2))){
      if (dist(ballX, ballY, ballX, mouseY) <= 10) {
      ballFallSpeed *= -1;
      }

    }
  }
  }

