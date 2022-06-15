import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  int gameScreen = 0;

  PImage imgLava;
  PImage homeLava;

  // ball variables
  float ballX;
  float ballY;
  float gravity = 1;
  float airfriction = (float) 0.001;
  float friction = (float) 0.1;
  float ballFallSpeed = 0;
  float ballHorizontalSpeed = 2;
  
  // bar variables
  int barWidth = 100;
  int barHeight = 10;
  int barBounce = 20;

  // health bar variables
  int maxHealth = 100;
  float health = 100;
  int healthBarWidth = 60;

  // points variables
  float [] circleX = new float [1];
  float [] circleY = new float [1];
  boolean [] hideCircle = new boolean [1];
  boolean upPressed = false;
  boolean downPressed = false;
  float circleDiameter = 50;
  int points = 0;
  int circleAmount = 1;

  public void settings() {
    size(600, 600);
    ballX = width/2;
    ballY = height/8;
  }

  
  public void setup() {
    // drawing immunity circles
    for (int i = 0; i < circleX.length; i++) {
      circleX[i] = random(width);
    }
    
    for (int i = 0; i < circleY.length; i++) {
      circleY[i] = random(height-100);
    }
  }

  
  public void draw() {
	  
	// controls which game screen is drawn
    if (gameScreen == 0) {
      homeScreen();
    } else if (gameScreen == 1) {
      gameScreen();
    } else if (gameScreen == 2) {
      pauseScreen();
    }
    else if (gameScreen == 3) {
      gameOverScreen();
    }

  }

  /**
   * home screen settings
   */  
  void homeScreen () {
    background(231,76,16);
    textAlign(CENTER);
    fill(255);
    textSize(50);
    text("THE FLOOR IS LAVA", width/2, 200);
    textSize(30);
    text("Click to play", width/2, 300);

    homeLava = loadImage("pngwing.com.png");
    homeLava.resize(homeLava.width*2,homeLava.height*2);
    image(homeLava, -200, -150);
  }
  
  
  /**
   * game screen settings
   */
  void gameScreen () {
    background(255);

    imgLava = loadImage("lava.png");
    image(imgLava, 0, 0); 

    gravity();

    drawBall();

    drawBar ();

    horizontalSpeed();

    drawHealthBar ();

    drawCircle();

    if (keyPressed) {
      if (key == 'p') {
        gameScreen = 2;
      }
    }

  }

    /**
     * pause screen settings
     */
    void pauseScreen () {
      textAlign(CENTER);
      textSize(70);
      text("PAUSED", 300, 300);
      if (mousePressed) {
          gameScreen = 1;
      }
    }

  /**
   * game over screen settings
   */
  void gameOverScreen () {
    background(255);
    textSize(50);
    fill(0);
    text("GAME OVER :(", 300, 200 );
    textSize(30);
    text("click to play again", 300, 400);
    text("score: " + points, 300, 300);
    
    // resets all variables when clicked to restart the game
    if (mousePressed) {
      reset();
    }
  } 

  /**
   * reset variables after they have been changed to start a new game  
   */ 
  void reset () {
      gameScreen = 1;
      ballX = width/2;
      ballY = height/3;
      health = 100;
      gravity = 1;
      airfriction = (float) 0.0001;
      friction = (float) 0.01;
      ballFallSpeed = 0;
      ballHorizontalSpeed = 2;
      points = 0;
    }

  /**
   * starts game if screen is clicked
   */
    public void mousePressed () {
    if (gameScreen == 0) {
      gameScreen = 1;;
    }
  }

  /**
   * implements gravity into game by adding friction when the ball touches a surface and airfriction
   */
  void gravity () {
    ballFallSpeed += gravity;
    ballY += ballFallSpeed;
    ballFallSpeed -= (ballFallSpeed * airfriction);
  }

  /**
   * makes ball bounce when the bottom of the ball hits a surface
   */
  void bounceBottom (float surface) {
    ballY = surface - 10;
    ballFallSpeed *= -1;
    ballFallSpeed -= (ballFallSpeed * friction);
  }

  /**
   * makes ball bounce when the top of the ball hits a surface
   */
  void bounceTop (float surface) {
    ballY = surface + 10;
    ballFallSpeed *= -1;
    ballFallSpeed -= (ballFallSpeed * friction);
  }

  /**
   * draws the ball
   */
  void drawBall () {
    fill(255);
    ellipse(ballX, ballY, 20, 20);
    
    // keeps the ball in the screen when it hits the bottom
    if (ballY + 10 > height) {
      bounceBottom(height);
    }
    // keeps the ball in the screen when it hits the top
    if (ballY - 10 < 0) {
      bounceTop(0);
    }
    // keeps the ball in the screen when it hits the left
    if (ballX - 10 < 0) {
      makeBounceLeft(0);
    }
    // keeps the ball in the screen when it hits the right
    if (ballX + 10 > width) {
      makeBounceRight(width);
    }
 }

 /**
  * draws the bar that controlls the ball 
  */
 void drawBar () {
    fill(255);
    rectMode(CENTER);
    rect(mouseX, mouseY, barWidth, barHeight);

    float overhead = mouseY - pmouseY;

    // if statement checks if the ball is in between the left and right sides of the rectangle
    if ((ballX + 10 > + mouseX - (barWidth/2)) && (ballX - 10 < mouseX + (barWidth/2))){
      // if statement checks if the ball and the bar are touching
      if (dist(ballX, ballY, ballX, mouseY) <= 10 + abs(overhead)) {
      bounceBottom(mouseY);
      ballHorizontalSpeed = (ballX - mouseX)/5;
      // racket moves up
      if (overhead < 0) {
        ballY += overhead;
        ballFallSpeed += overhead;
      }
      
      }
    }
    // adjusts the length of the bar based on user input
    if (upPressed) {
      barWidth += 2;
    }

    if (downPressed) {
      barWidth -= 2;
    }
  }

  /**
   * keyboard input to recognize when keys w,s are pressed to adjust the size of the bar 
   */
  public void keyPressed () {
    if (key == 'w') {
      upPressed = true;
    }
    if (key == 's') {
      downPressed = true;
    }
  }

  /**
   * keyboard input to recognize when keys w,s are released
   */
  public void keyReleased () {
    if (key == 'w') {
      upPressed = false;
    }

    if (key == 's') {
      downPressed = false;
    }
  }

  /**
   * controlls the horizontal speed of the ball
   */
  void horizontalSpeed () {
    ballX += ballHorizontalSpeed;
    ballHorizontalSpeed -= (ballHorizontalSpeed * airfriction);
  }

  /**
   * bounces the ball left
   */
  void makeBounceLeft(float surface){
    ballX = surface+10;
    ballHorizontalSpeed*=-1;
    ballHorizontalSpeed -= (ballHorizontalSpeed * friction);
  }

  /**
   * bounces the ball right
   */
  void makeBounceRight(float surface){
    ballX = surface-10;
    ballHorizontalSpeed*=-1;
    ballHorizontalSpeed -= (ballHorizontalSpeed * friction);
  }

  /**
   * draws the health bar 
   */
  void drawHealthBar () {
    noStroke ();
    // draws grey health bar
    fill(236, 240, 241);
    rectMode(CORNER);
    rect(ballX - (healthBarWidth/2), ballY - 30, healthBarWidth, 5);
    
    // if statements to change the colour of health bar according to how much health left
    if (health > 60) {
      fill (46, 204, 113);
    } else if (health > 30) {
      fill (230, 126, 34);
    } else {
      fill(231, 76, 60);
    }
    // draws coloured-health bar
    rect(ballX - (healthBarWidth/2), ballY - 30, healthBarWidth*(health/maxHealth), 5);

    // checks if ball hits ground and takes away health if true
    if (ballY + 10 >= height) {
      health -= 10;
    }
    
    if (health <= 0) {
      gameScreen = 3;
    }
  }

  /**
   * draws red immunity circles for player to collect
   */
  void drawCircle() {

    for (int i = 0; i < circleY.length; i++) {
      if(hideCircle[i] == false) {
        noStroke();
        fill(200,40,20);
        ellipse(circleX[i], circleY[i], circleDiameter, circleDiameter);
        
        // hides circle if collision detection is true
        if(dist(ballX, ballY, circleX[i], circleY[i]) < circleDiameter) {
          hideCircle[i] = true;
          points++;
          circleAmount -= 1;
          if (circleAmount == 0) {
            hideCircle[i] = false;
            circleAmount += 1;
            setup();
          }
        }
      }
    }
      
      //displays score to screen
      textAlign(CENTER);
      fill(255);
      textSize(60);
      text(points, 550, 70);
  }
}

