package com.lukaszborowski.model;

import java.util.LinkedList;

public class Snake {

	//----------------------------------------------------------------------------------------------
	//private fields
	//------------------------------------------------------------------------------------------------
	private String name = "Gość";
	private LinkedList<Position> snakeBody = new LinkedList<Position>();
	private Direction direction;
	private boolean crashed = false;
	private boolean eat = false;
	private boolean missed = false;
	private int appleEated = 0;
	private int appleMissed = 0;
	private long pauseTime = 200;

	//----------------------------------------------------------------------------------------------
	//constructor
	//------------------------------------------------------------------------------------------------
	public Snake(int x, int y, int length, Direction direction) {
        this.direction = direction;

        for (int i = 0; i <= length; i++) {
            int bodyX = x;
            int bodyY = y;

            if (direction == Direction.DOWN) {
                bodyY = bodyY - i;
            }
            if (direction == Direction.UP) {
                bodyY = bodyY + i;
            }
            if (direction == Direction.RIGHT) {
                bodyX = bodyY - i;
            }
            if (direction == Direction.LEFT)  {
                bodyX = bodyX + i;
            }

            snakeBody.add(new Position(bodyX, bodyY));
        }
    }
	

	//----------------------------------------------------------------------------------------------
	//getters and setters
	//------------------------------------------------------------------------------------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LinkedList<Position> getSnakeBody() {
		return snakeBody;
	}

	public void setSnakeBody(LinkedList<Position> snakeBody) {
		this.snakeBody = snakeBody;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isCrashed() {
		return crashed;
	}

	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}

	public boolean isEat() {
		return eat;
	}

	public void setEat(boolean eat) {
		this.eat = eat;
	}

	public int getAppleEated() {
		return appleEated;
	}

	public void setAppleEated() {
		this.appleEated++;
	}

	public int getAppleMissed() {
		return appleMissed;
	}

	public void setAppleMissed() {
		this.appleMissed++;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}



	public boolean isMissed() {
		return missed;
	}



	public void setMissed(boolean missed) {
		this.missed = missed;
	}

	
}
