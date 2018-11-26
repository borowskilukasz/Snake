package com.lukaszborowski.controller;

import com.lukaszborowski.model.Direction;

public class DirectionController {

	public DirectionController() {}

	public Direction getDirectionFromKey(String key) {
		if (key.equals("ArrowUp")) {
			return Direction.UP;
		} else if (key.equals("ArrowDown")) {
			return Direction.DOWN;
		} else if (key.equals("ArrowLeft")) {
			return Direction.LEFT;
		} else if (key.equals("ArrowRight")) {
			return Direction.RIGHT;
		} else {
			return null;
		}
	}
	/*
	 * ta klasa sprawdza jaki jest aktualny kierunek i zwraca kierunek przeciwny
	 * wspomaga ona obsluge zdazenia gdy ktos przypadkowo nacisnie przycisk odwrotny do kierunku weza 
	 * bez obslugi tego zdarzenia gracz po wcisnieciu tego przycisku od razu by przegral
	 */
	public Direction getOppositeDirection(Direction direction) {
		if (direction == Direction.LEFT)
			return Direction.RIGHT;
		if (direction == Direction.RIGHT)
			return Direction.LEFT;
		if (direction == Direction.UP)
			return Direction.DOWN;
		if (direction == Direction.DOWN)
			return Direction.UP;
		throw new IllegalArgumentException(
				"Something went terribly wrong in getOppositeDirection function. Passed direction: " + direction);
	}
	
}
