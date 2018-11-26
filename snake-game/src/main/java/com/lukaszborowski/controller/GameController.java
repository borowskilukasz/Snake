package com.lukaszborowski.controller;

import java.util.LinkedList;

import com.lukaszborowski.model.Direction;
import com.lukaszborowski.model.Position;
import com.lukaszborowski.model.Snake;
import com.lukaszborowski.view.CurrentView;
import com.lukaszborowski.view.TerminalGameView;

/*
 *  trzeba zrobic jakis interface dla kontrolera widoku zeby były tam wszystkie konieczne metody potrzebne do prawidłowego obsluzenia widoku
 *  ale przed tym trzeba wydzielic jeden kontroler dla widoku wlasnie ktory bedzie posiadal wylacznie metody ktore zajmuja sie widokiem
 *  
 *  dodatkowo musisz zmienic kolory w terminalview bo pobierasz kolory z lanterny a musza byc uniwercalne i w currentview ustaw wszystkie getery i setery tych kolorow zeby kazdy widok takie mial
 *  bo bedzie zmieniac te kolory w swoim programie 
 *  
 */

public class GameController {

	//----------------------------------------------------------------------------------------------
	//public fields
	//------------------------------------------------------------------------------------------------

	// zmienne ustawiajace opcje gry
	public static int HARD_MODE_ACCELERATE = 10;
	public static int MEDIUM_MODE_ACCELERATE = 5;
	public static int EASY_MODE_ACCELERATE = 3;
	public static int HARD_MODE_FREQUENCY_SPEED_CHANGE = 3;
	public static int MEDIUM_MODE_FREQUENCY_SPEED_CHANGE = 5;
	public static int EASY_MODE_FREQUENCY_SPEED_CHANGE = 10;

	//----------------------------------------------------------------------------------------------
	//private fields
	//------------------------------------------------------------------------------------------------

	private boolean isStarted = false;
	private CurrentView view;
	private DirectionController directionController = new DirectionController();
	private int gameHeight;
	private int gameLenght;
	private String keey;
	private Snake snake; 
	private String snakeName = "Gość";
	Position apple;
	AppleController appleController;
	
	//----------------------------------------------------------------------------------------------
	//constructor
	//------------------------------------------------------------------------------------------------

	public GameController(TerminalGameView terminalView, int gameLenght, int gameHeight) {
		this.view = terminalView;
		this.gameLenght = gameLenght;
		this.gameHeight = gameHeight;
	}
	
	//----------------------------------------------------------------------------------------------
	//methods
	//------------------------------------------------------------------------------------------------

	
	/**
	 * Metoda aktualizuje widok
	 * @param snakeBody cialo weza przekazane do narysowania 
	 * @param appleColumn wspolzedna polozenia jablka
	 * @param appleRow wspolrzedna polozenia jablka
	 */
	public void gameViewActualization(LinkedList<Position> snakeBody, int appleColumn, int appleRow) {
		view.drawBorders();
		view.makeAppleVisible(appleColumn, appleRow);

		for (int i = 1; i < snakeBody.size(); i++) {
			view.drawSnakeElement(snakeBody.get(i).getCol(), snakeBody.get(i).getRow());
		}
		view.drawSnakeHead(snakeBody.get(0).getCol(), snakeBody.get(0).getRow());
		view.refreshScreen();
	}
	
	public void firstScreen() {
		int choice = view.showFirstScreen();
		switch(choice) {
		case 1:
			game();
			break;
		case 2:
			//zmiana nazwy weza, randomowa nazwa to Gosc 
			/*
			 * wywolujemy metode pobierajaca nazwe weza z terminala 
			 * snake.setName(METODA WIDOKU ZWRACAJACA IMIE W POSTACI STRINGA);
			 */
			view.changeSnakeNameScreen("");
			System.out.println("TODO");
			break;
		case 3:
			System.out.println("TODO");
			break;
		case 4:
			System.out.println("TODO");
			break;
		case 5:
			System.exit(0); 
		}
		
	}
	
	private void startNewGame() {
		this.snake = new Snake(30, 10, 7, Direction.RIGHT);	
		this.snake.setName(snakeName);
		appleController = new AppleController(snake, gameLenght, gameHeight);
		this.apple = appleController.generateApple();
		isStarted = true;
	}
	/*
	 * metoda obslugujaca gre
	 */
	public void game() {
		
		if(!isStarted) {
		startNewGame();
		}
		int appleColumn = apple.getCol();
		int appleRow = apple.getRow();

		//inicjalizujemy pierwszy ekran do gry 
			//generujemy piersze jablko na ekranie 
			//appleController.generateApple(terminalView, snake);
		//aktualizujemy ekran z powstalym jablkiem i wezem ktorego stworzylismy
		gameViewActualization(snake.getSnakeBody(), appleColumn, appleRow);
		
			// kolejka przyciskow do obsluzenia
			LinkedList<Direction> keyQueue = new LinkedList<Direction>();
			// zmienna przechowujaca czas od postawienia kroku weza
			long stepStartMillis = 0;

			// petla programu
			while (true) {				
				view.setKey();					
				keey = view.keyToString();					
				//pobieramy przycisk z klawiatury i dodajemy go do kolejki przyciskow do wykonania
				getKey(keyQueue, keey);					
				// zmienna zapisująca aktualny czas
				long currentMillis = System.currentTimeMillis();
				// jeśli od zrobienia kroku do teraz minęło więcej niż czas ustalony w zmiennej pauseTime
				if (currentMillis - stepStartMillis > snake.getPauseTime()) {
					// przypisujemy czas zrobienia kolejnego kroku jako czas aktualny
					stepStartMillis = currentMillis;
				//sprawdzzenie czy glowa weza nie znajduje sie na jablku 
				 apple = appleController.eatApple(HARD_MODE_FREQUENCY_SPEED_CHANGE, HARD_MODE_ACCELERATE, apple);
				 appleColumn = apple.getCol();
				 appleRow = apple.getRow();				 
				 gameViewActualization(snake.getSnakeBody(), appleColumn, appleRow);
				 apple = appleController.appleLost(currentMillis, apple);
				 // jeżeli w kolejce są jakieś kierunki to wykonujemy kolejny krok w kierunku z kolejki
					getMove(keyQueue, appleColumn, appleRow);					
					// spradzenie czy waz nie wpadl na granice planszy oraz czy się nie zjadł 
					if(crashCheck(snake.getSnakeBody().getFirst().getCol(), snake.getSnakeBody().getFirst().getRow(), gameLenght, gameHeight) ||
						snakeEatHimself(snake.getSnakeBody())) {
					snake.setCrashed(true);
					}					
					//obsluga zdarzenia zakonczenia gry
					if (snake.isCrashed()) {
						if( view.showGameResult(snake.getAppleEated(), snake.getAppleMissed(), snake.getName() ) == 1) {
							startNewGame();	
						}else {
								break;					
						}
					}
				}					
			}
	}
	/*
	 * metoda pobiera przycisk od gracza i odpowiednio zmienia kierunek weza 
	 */
	public void getKey(LinkedList<Direction> keyQueue, String key) {
		// zmiennna przechowujaca nacisniety przycisk
		Direction direction = null;

		if (!(key.equals(""))) {
			// jesli jest nacisniety jakis przycisk funkcja getDirectionFromKey sprawdza czy
			// jest to odpowiedni przycisk
			// i jesli tak to zwraca odpowiedni kierunek
			direction = directionController.getDirectionFromKey(key);
			// jesli przycisk zgadza sie z sterowaniem
			if (direction != null) {
				// jesli w kolejce nie ma zadnego innego przycisku
				if (keyQueue.size() == 0) {
					// i jesli podany kierunek przez uzytkownika nie jest kierunkiem przeciwnym do
					// aktualnego
					if (snake.getDirection() != directionController.getOppositeDirection(direction)) {
						// to dodajemy kierunek do kolejki wykonania
						keyQueue.add(direction);
					}
					// w przypadku rowniez gdy w kolejce jest jakis kierunek dodajemy do kolejki
					// aktualny kierunek pod warunkiem ze nie jest to kierunek przeciwny do
					// ostaniego kierunku w kolejce
				} else if (keyQueue.getLast() != directionController.getOppositeDirection(direction)) {
					keyQueue.add(direction);
				}
			}
			// tutaj zostaja obsluzone inne przyciski od tych sterujacych
		}
	}
	/*
	 * metoda obslugujaca jeden krok weza jesli kierunek sie zmienil
	 */
	public void makeStep(Direction direct) {
        if (snake.isCrashed()) {
            System.out.println("CRASHED");
            return;
        }
        snake.setDirection(direct);

        Position newBodyPart = null;

        if (snake.isEat()) {
            newBodyPart = new Position(snake.getSnakeBody().getLast().getCol(), snake.getSnakeBody().getLast().getRow());
            snake.setAppleEated();
            System.out.println("EATED");
        }

        for (int i = snake.getSnakeBody().size() - 1; i > 0; i--) {
        	snake.getSnakeBody().get(i).setCol(snake.getSnakeBody().get(i - 1).getCol());
        	snake.getSnakeBody().get(i).setRow(snake.getSnakeBody().get(i - 1).getRow());
        }
        if (snake.getDirection()== Direction.LEFT) {
        	snake.getSnakeBody().get(0).setCol(snake.getSnakeBody().get(0).getCol() - 1);
        }
        if (snake.getDirection() == Direction.RIGHT) {
        	snake.getSnakeBody().get(0).setCol(snake.getSnakeBody().get(0).getCol() + 1);
        }
        if (snake.getDirection() == Direction.UP) {
        	snake.getSnakeBody().get(0).setRow(snake.getSnakeBody().get(0).getRow() - 1);
        }
        if (snake.getDirection() == Direction.DOWN) {
        	snake.getSnakeBody().get(0).setRow(snake.getSnakeBody().get(0).getRow() + 1);
        }
        
        if (snake.isEat()) {
        	snake.getSnakeBody().add(newBodyPart);
            snake.setEat(false);
        }
    }
	/*
	 * metoda obslugujaca jeden krok weza w tym samym kierunku
	 */
	public void makeStep() {
		makeStep(snake.getDirection());
	}
	/*
	 * metoda obslugujaca w ktora strone powinien byc wykonany krok
	 */
	public void getMove(LinkedList<Direction> keyQueue, int appleColumn, int appleRow) {
		if (keyQueue.size() > 0) {
			// wykonujemy krok w kierunku z początku kolejki i wykonujemy krok
			makeStep(keyQueue.removeFirst());

			gameViewActualization(snake.getSnakeBody(), appleColumn, appleRow);

		} else {
			// jeśli nie było żadnego nowego kierunku w kolejce waz robi krok w tym kierunku
			// ktory byl zapisany jako ostatni
			makeStep();
			gameViewActualization(snake.getSnakeBody(), appleColumn, appleRow);
			
		}
	}
	
	/**
	 * Metoda sprawdza czy waz nie najechał głową na pole gdzie znajduje się aktualnie jego ciało
	 * @param snake aktualny wąż
	 */
	public boolean snakeEatHimself(LinkedList<Position> snakeBody) {
		for(int i =1; i < snakeBody.size() ; i++) {
			if(snakeBody.get(i).equals(snakeBody.getFirst())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metod sprawdza czy waz nie wpadl na krawedz pola gry
	 * @param snakeHead
	 * @param gameLenght
	 * @param gameHeight
	 * @return
	 */
	public boolean crashCheck(int snakeHeadColumn, int snakeHeadRow, int gameLenght, int gameHeight) {
		if (snakeHeadColumn == 0 || 
				snakeHeadRow == 0 || 
					snakeHeadColumn == (gameLenght-1) || 
							snakeHeadRow == (gameHeight-1)) {
			return true;
		}
		return false;
	}
	
	//----------------------------------------------------------------------------------------------
	//getters and setters
	//------------------------------------------------------------------------------------------------

	public String getSnakeName() {
		return snakeName;
	}

	public void setSnakeName(String snakeName) {
		this.snakeName = snakeName;
	}
	
	public int getGameHeight() {
		return gameHeight;
	}
	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}
	public int getGameLenght() {
		return gameLenght;
	}
	public void setGameLenght(int gameLenght) {
		this.gameLenght = gameLenght;
	}
}
