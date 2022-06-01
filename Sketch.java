import processing.core.PApplet;

public class Sketch extends PApplet {
	
  int gameScreen = 0;
  
  // ball variables
  float ballX;
  float ballY;
  int gravity = 1;
  float ballXSpeed = (float) 5.5;
  float ballYSpeed = (float) 5.5;
  float airfriction = (float) 0.0001;
  int ballXDirection = 1;
  int ballYDirection = 1;
  int lives = 3;
  
  
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

    drawLives ();

  }

  void gameOverScreen () {
    background(255);
    textSize(50);
    text("GAME OVER :(", 300, 300 );
    textSize(30);
    text("click to play again", 300, 500);
    if (mousePressed) {
      gameScreen = 1;
      lives = 3;
    }
  }

  public void mousePressed () {
    if (gameScreen == 0) {
      startGame();
    }
  }

  void startGame () {
    gameScreen = 1;
  }

  void gravity () {
    ballXSpeed += gravity;
    ballYSpeed += gravity;
    ballY += ballYSpeed;
  }

  void drawBall () {
    ballXSpeed -= ballXSpeed * airfriction;
    ballYSpeed -= ballYSpeed * airfriction;
    ballX = ballX + (ballXSpeed * ballXDirection);
    ballY = ballY + (ballYSpeed * ballYDirection);
    
    fill(0);
    ellipse(ballX, ballY, 20, 20);

    //ballY += ballFallSpeed;

    /*if (ballY + 10 > height) {
      ballY = (height-(20/2));
      ballFallSpeed*=-1;
    }
    if (ballY - 10 < 50) {
      ballY = 50+(20/2);
      ballFallSpeed*=-1;
    }*/

    if (ballX > width - 10 || ballX < 10) {
      ballXDirection *= -1;
    }
    if (ballY > height - 10 || ballY < 10) {
      ballYDirection *= -1;
    }
    
  }

  void drawBar () {
    //float overhead = mouseY - pmouseY;
    fill(0);
    rectMode(CENTER);
    rect(mouseX, mouseY, barWidth, barHeight);

    // if statement checks if the ball is in between the left and right sides of the rectangle
    if ((ballX > + mouseX - (barWidth/2)) && (ballX < mouseX + (barWidth/2))){
      if (dist(ballX, ballY, ballX, mouseY) <= 10) {
      ballYDirection *= -1;
      /*if (overhead < 0) {
        ballY += overhead;
        ballYSpeed += overhead;
      }*/
      
      }
    }
  }

  void drawLives () {
    for (int i = 0; i < lives; i++) {
      fill(0);
      rect(i*50 + 400, 40, 30, 30);

      if (ballY > height - 10) {
        lives--;
      }

      if (lives == 0) {
        gameScreen = 2;
      }
    }
  }
}

