package com.lukaszborowski.view;


import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.Component.Alignment;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.TextArea;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.Terminal.Color;
/**
 * Widok - widok nie ma pojęcia o istnieniu modelu, całą komunikacją między widokiem a modelem zrządza kontroler
 * @author Lukasz
 *
 */
public class TerminalGameView implements CurrentView{


	//----------------------------------------------------------------------------------------------
	//private fields
	//------------------------------------------------------------------------------------------------

	private Color snakeColor = Terminal.Color.GREEN;
	String SnakeName;
	private String snakeBodyLook = "O";
	private String snakeHeadLook = "☺";	
	private String appleLook = "ó";
	private String border = " ";
	private Color borderColor = Terminal.Color.RED;
	private Color appleColor = Terminal.Color.MAGENTA;
	private Color backgroundColor = Terminal.Color.BLACK;
	private Key key = null;
	boolean isStarted = false;
	int choice = 0;
	private Terminal terminal;
	private Screen gameScreen;
	final GUIScreen guiScreen;
	

	//----------------------------------------------------------------------------------------------
	//constructor
	//------------------------------------------------------------------------------------------------

 	public TerminalGameView() {
		terminal = TerminalFacade.createTerminal();
		guiScreen = TerminalFacade.createGUIScreen(terminal);
		gameScreen =guiScreen.getScreen();
		gameScreen.startScreen();
		gameScreen.setCursorPosition(null);
		gameScreen.refresh();
	}

	//----------------------------------------------------------------------------------------------
	//methods
	//------------------------------------------------------------------------------------------------
	
	public void drawSnakeElement(int column, int row) {
		gameScreen.putString(column, row, snakeBodyLook, snakeColor, backgroundColor);	
	}
	
	public void drawSnakeHead(int headColumn, int HeadRow) {
		gameScreen.putString(headColumn, HeadRow, snakeHeadLook, snakeColor, backgroundColor);	
	}
	

	/**
	 * Metoda zmienia kolor tła poszczególnych elementów w konsoli które tworzą
	 * granicę
	 */
	public void drawBorders() {
		gameScreen.clear();
		// ustawienie krawędzi
		for (int column = 0; column < getAreaLenght(); column++) {
			// krawedz gorna
			gameScreen.putString(column, 0, border, borderColor, borderColor);
			// krawedz dolna
			gameScreen.putString(column, getAreaHeight() - 1, border, borderColor, borderColor);
			// krawedz lewa
			for (int row = 0; row < getAreaHeight(); row++) {
				gameScreen.putString(0, row, border, borderColor, borderColor);
				// krawedz prawa
				gameScreen.putString(getAreaLenght() - 1, row, border, borderColor, borderColor);
			}
		}
	}

	
	/*
	 * metoda rysuje jablko w konsoli
	 */
	public void makeAppleVisible(int column, int row) {
		gameScreen.putString(column, row, appleLook, appleColor, backgroundColor);
		gameScreen.refresh();
	}

	/**
	 * 
	 * @return wysokosc planszy
	 */
	public int getAreaHeight() {
		return gameScreen.getTerminalSize().getRows();
	}
	
/**
 * 
 * @return dlugosc planszy 
 */
	public int getAreaLenght() {
		return gameScreen.getTerminalSize().getColumns();
	}
	public String keyToString() {
		if(getKey() != null) {
		return getKey().toString();
		}else {
			return "";
		}
	}

	public void refreshScreen() {
		this.gameScreen.refresh();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//metody obslugujace wyswietlanie okien
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	public int showFirstScreen() {
		choice = 0;
		final Window window = new Window("Snake");
		window.setWindowSizeOverride(new TerminalSize(100, 50));
		window.setSoloWindow(true);
		window.addComponent(new Label("                              #######  ##     #  #######  #   #  ######"));
		window.addComponent(new Label("                              #        # #    #  #     #  #  #   #"));
		window.addComponent(new Label("                              #        #  #   #  #     #  # #    #"));
		window.addComponent(new Label("                              #######  #   #  #  #######  ##     ####"));
		window.addComponent(new Label("                                    #  #    # #  #     #  # #    #"));
		window.addComponent(new Label("                                    #  #     ##  #     #  #  #   #"));
		window.addComponent(new Label("                              #######  #      #  #     #  #   #  ######"));
		window.addComponent(new EmptySpace());
		window.addComponent(new EmptySpace());
		Button newGameBtn = new Button("Nowa Gra", new Action() {

			public void doAction() {
				choice = 1;
				window.close();
			}
		});
		Button changeNameBtn = new Button("Zmień imię", new Action() {

			public void doAction() {
				choice = 2;
				window.close();

			}
		});
		Button showScoreTableBtn = new Button("Pokaż tabelę wyników", new Action() {

			public void doAction() {
				choice = 3;
				window.close();
			}
		});
		Button settingsBtn = new Button("Ustawienia", new Action() {

			public void doAction() {
				choice = 4;
				window.close();
				System.exit(0);
			}
		});
		Button exitBtn = new Button("Wyjście", new Action() {

			public void doAction() {
				choice = 5;
				window.close();
				System.exit(0);
			}
		});
		newGameBtn.setAlignment(Alignment.TOP_CENTER);
		window.addComponent(newGameBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(changeNameBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(showScoreTableBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(settingsBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(exitBtn, LinearLayout.GROWS_HORIZONTALLY);
		guiScreen.showWindow(window);
		
		return choice;
	}
	
	public String changeSnakeNameScreen(String currentName) {
		SnakeName = currentName;
				
		final Window window = new Window("Zmień nazwę użytkownika");
		window.setWindowSizeOverride(new TerminalSize(100, 50));
		window.setSoloWindow(true);
		Label currentNameLabel = new Label("Twoja aktualna nazwa użytkownika to : " + SnakeName);
		TextBox newName = new TextBox(SnakeName);
		Button applyBtn = new Button("Zatwierdż", new Action() {

			public void doAction() {
				SnakeName = newName.getText();
				window.close();
				showFirstScreen();
			}
		});
		currentNameLabel.setAlignment(Alignment.TOP_CENTER);
		
		window.addComponent(currentNameLabel, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(new EmptySpace());
		window.addComponent(new EmptySpace());
		window.addComponent(new EmptySpace());
		window.addComponent(newName, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(new EmptySpace());
		window.addComponent(applyBtn, LinearLayout.GROWS_HORIZONTALLY);
		
		return SnakeName;
	}
	
	/**
	 * Metoda włącza nowe okno w ktorym jest wyswietlony wynik 
	 */
	public int showGameResult(int appleEated, int appleMissed, String snakeName) {
		choice = 0;
		int score =(appleEated*100 - appleMissed*50);
		Label l1 = new Label("Brawo "+ snakeName + ". Twój wynik to: ");
		Label l2 = new Label(Integer.toString(score));
		l1.setAlignment(Alignment.TOP_CENTER);
		l2.setAlignment(Alignment.TOP_CENTER);
		final Window window = new Window("Koniec Gry!");
		window.setWindowSizeOverride(new TerminalSize(50, 25));
		//window.setSoloWindow(true);
		
		window.addComponent(new EmptySpace());
		window.addComponent(l1, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(l2, LinearLayout.GROWS_HORIZONTALLY);
		Button exitBtn = new Button("Exit", new Action() {

			public void doAction() {
				window.close();
				System.exit(0);
			}
		});
		Button menuBtn = new Button("Menu Główne", new Action() {

			public void doAction() {
				window.close();
				showFirstScreen();
			}
		});
		Button restartBtn = new Button("Restart game", new Action() {
			public void doAction() {
				choice = 1;
				window.close();
				
			}
		});
		restartBtn.setAlignment(Alignment.BOTTON_CENTER);
		window.addComponent(restartBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(menuBtn, LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(exitBtn, LinearLayout.GROWS_HORIZONTALLY);
		guiScreen.showWindow(window);
		return choice;

	}
	//----------------------------------------------------------------------------------------------
	//getters and setters
	//------------------------------------------------------------------------------------------------

	public Screen getScreen() {
		return gameScreen;
	}

	public void setScreen(Screen screen) {
		this.gameScreen = screen;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Color getSnakeColor() {
		return snakeColor;
	}

	public void setSnakeColor(Color snakeColor) {
		this.snakeColor = snakeColor;
	}

	public String getSnakeLookBody() {
		return snakeBodyLook;
	}

	public void setSnakeLookBody(String snakeLookBody) {
		this.snakeBodyLook = snakeLookBody;
	}

	public String getSnakeHeadLook() {
		return snakeHeadLook;
	}

	public void setSnakeHeadLook(String snakeHead) {
		this.snakeHeadLook = snakeHead;
	}

	public String getAppleLook() {
		return appleLook;
	}

	public void setAppleLook(String appleLook) {
		this.appleLook = appleLook;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getAppleColor() {
		return appleColor;
	}

	public void setAppleColor(Color appleColor) {
		this.appleColor = appleColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Key getKey() {
		return key;
	}

	public void setKey() {
		this.key = guiScreen.getScreen().readInput();
	}
}