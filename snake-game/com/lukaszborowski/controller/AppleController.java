package com.lukaszborowski.controller;

import java.util.Random;

import com.lukaszborowski.model.Position;
import com.lukaszborowski.model.Snake;

public class AppleController {

	private Snake snake;
	private int gameLenght, gameHeight;
	
	public AppleController(Snake snake, int gameLenght, int gameHeight){

		this.setSnake(snake);
		this.setGameLenght(gameLenght);
		this.setGameHeight(gameHeight);
		
	}
	/*
	 * metoda generateApple sluzy do generowania nowego jablka 
	 */
	public Position generateApple() {
		Random random = new Random();
		int x, y;
		do {
			 x = random.nextInt(gameLenght);
			 y = random.nextInt(gameHeight);
		} while (isOnBorderOrSnake(x, y));

		Position apple = new Position(x,y);
		apple.setTimeOfCreate(System.currentTimeMillis());
		return apple;
	}
	/*
	 * metoda obsluguje sytuacje gdy minie odpowiedni czas od utworzenia jablka a waz go nie zje
	 */
	public Position appleLost(long currentMillis, Position apple) {
		//petla obslugujaca znikanie jablka po okreslonym czasie 
		if(( currentMillis - apple.getTimeOfCreate()) > (snake.getPauseTime()*gameLenght + 6000)) {
			 System.out.println(Long.toString(currentMillis - apple.getTimeOfCreate()));
			snake.setMissed(true);
			snake.setAppleMissed();
			return generateApple();
		}
		return apple;
	}

	/*
	 * metoda obsluguje akcje gdy waz zje jablko
	 */
	public Position eatApple(int frequency_speed_change, int mode, Position apple) {
		Position newApple = apple;
		// obsluga zdarzenia gdy głowa weza najedzie na jablko
		if (snake.getSnakeBody().getFirst().equals(apple)) {
			snake.setEat(true);
			//zwiekszenie predkosci weza zgodnie z ustawieniami
			
			if(snake.getPauseTime() != 60 && snake.getAppleEated()%frequency_speed_change == 0) {
				snake.setPauseTime(snake.getPauseTime()- mode); 
				System.out.println(snake.getPauseTime());
			}
			
			newApple = generateApple();
			
		}
		return newApple;
	}
	/*
	 * metoda sprawdza czy w przekazane wspolrzedne nie wskazuja na granice planszy lub na weza
	 */
	public boolean isOnBorderOrSnake(int x, int y) {
		Position position = new Position(x, y);
				if (position.getCol() == 0 || position.getRow() == 0 || position.getCol() == (gameLenght - 1)
				|| position.getRow() == (gameHeight - 1)) {
			return true;
		}
		if (snake.getSnakeBody().contains(position)) {
			return true;
		}

		return false;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public int getGameLenght() {
		return gameLenght;
	}
	public void setGameLenght(int gameLenght) {
		this.gameLenght = gameLenght;
	}
	public int getGameHeight() {
		return gameHeight;
	}
	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}


}
