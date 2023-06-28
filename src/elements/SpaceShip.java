package elements;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lib.Point;

public class SpaceShip extends Entity {
	
	private int ultiCount; // so um ti 
	public SpaceShip(String linkImage, float width, float heigh) {
		super(linkImage, width, heigh,1);
	}
	public SpaceShip() {
		this("/resourses/gamekit/spritesheets/ship/SpaceShip.png", 90, 90);
		setPosition(new Point(650, 600));
		bulletStore = 1000;
		ultiCount = 2; // khoi tao bang 1
		
	}
	private int score = 0;
	private static final double STEP = 5;
	private double shipAngle;
	private int bulletStore;
	private int cachBan = 4;// cách bắn
	public boolean canShoot = true;
	public boolean canDiChuyen = true;
	public boolean canUlti = true;
	public boolean canChange = true;
	public int getUltiCount() {
		return ultiCount;
	}
	public void setUltiCount(int ultiCount) {
		this.ultiCount = ultiCount;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setBulletStore(int bulletCounter) {
		this.bulletStore = bulletCounter;
	}
	public int getCachBan() {
		return this.cachBan;
	}
	public void setCachBan(int cachBan) {
		this.cachBan = cachBan;
	}
	public int getBulletStore() {
		return this.bulletStore;
	}
	
	public void spaceShipMove(boolean isLeftKeyPressed, boolean isRightKeyPressed) {
		this.shipAngle = this.getImageView().getRotate();
		Point position = getPosition();
		
		if (isLeftKeyPressed && !isRightKeyPressed) {
			System.out.println("Movin' Left");
			
			if (position.getX() < 10) position.setLocation(0, position.getY());
			else position.setLocation(position.getX() - STEP, position.getY());
			
			setPosition(position);
			
			if (this.shipAngle > -10) this.shipAngle -= 10;
			getImageView().setRotate(this.shipAngle);
		}
		else if (!isLeftKeyPressed && isRightKeyPressed) {
			System.out.println("Movin' Right");
			
			if (getPosition().getX() > 1250) position.setLocation(1250, position.getY());
			else position.setLocation(position.getX() + STEP, position.getY());
			
			setPosition(position);
			
			if (this.shipAngle < 10) this.shipAngle += 10;
			getImageView().setRotate(this.shipAngle);
		}
		else if ((!isLeftKeyPressed && !isRightKeyPressed)
				|| (isLeftKeyPressed && isRightKeyPressed)) {
			if (this.shipAngle < 0 ) this.shipAngle += 10;
			else if (this.shipAngle > 0) this.shipAngle -= 10;
			
			getImageView().setRotate(this.shipAngle);
		}
	}
	
	public void spaceShipAttack1(AnchorPane pane,ArrayList<Entity> E) {
				System.out.println("Shotin' them");
					UpgradeShoot bullet = new UpgradeShoot(cachBan);
					bullet.Shoot(this, pane, E);
	}
	public void spaceShipAttack2(AnchorPane pane, ArrayList<Entity> E) {
				UltimateBullet ulti = new UltimateBullet("/resourses/gamekit/spritesheets/UltimateBullet/Heaven's Fury VFX_Animation 1_00.png",150,700,new Point(0,1));
				ulti.Shoot(this, pane, E);
	}
	public void dau() {//đau
		FadeTransition fade = new FadeTransition();
		fade.setNode(getImageView());
		fade.setDuration(Duration.millis(100));
		fade.setCycleCount(4);
		fade.setInterpolator(Interpolator.LINEAR);
		fade.setFromValue(1);
		fade.setToValue(0.1);
		fade.setAutoReverse(true);
		fade.play();
		
	}
	public void no() {//nổ
		
		String[] FRAME_PATH = {
				"/resourses/gamekit/spritesheets/explosion1.png",
				"/resourses/gamekit/spritesheets/explosion2.png",
				"/resourses/gamekit/spritesheets/explosion3.png",
				"/resourses/gamekit/spritesheets/explosion4.png",
				"/resourses/gamekit/spritesheets/explosion5.png",
				"/resourses/gamekit/spritesheets/explosion6.png",
				"/resourses/gamekit/spritesheets/explosion7.png",
				"/resourses/gamekit/spritesheets/explosion8.png"
		};
		
		AnimationTimer timer = new AnimationTimer() {
			int currentFrame = 0;
			long lastTime = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(currentFrame == 8) {
					this.stop();
					
				}
				if(now - lastTime>=1e9/10) {
					Image image = new Image(FRAME_PATH[currentFrame]);
					getImageView().setImage(image);
					currentFrame++;
					lastTime = now;
				}

			}
			
		};timer.start();
	}
	public boolean coKhien = false;
}
