package com.lukaszborowski.view;


public interface CurrentView {

	
	/**
	 * Metoda odpowiada za pojawienie sie pojedynczego elementu weza na planszy
	 * @param column zmienna wskazuje na odleglosc elementu weza od lewej krawedzi pola po ktorym sie porusza
	 * @param row zmienna wskazuje na odleglosc elementu weza od gornej krawedzi pola po ktorym sie porusza
	 */
	public void drawSnakeElement(int column, int row);
	/**
	 * Metoda odpowiada za pojawienie sie glowy weza na polu gry
	 * @param headColumn zmienna wskazuje na odleglosc glowy weza od lewej krawedzi pola po ktorym sie porusza
	 * @param HeadRow zmienna wskazuje na odleglosc glowy weza od gornej krawedzi pola po ktorym sie porusza
	 */
	public void drawSnakeHead(int headColumn, int HeadRow);
	/**
	 * Metoda odpowiada za pojawienie sie wszystkich linii granicznych pola do gry
	 */
	public void drawBorders();
	/**
	 * Metoda odpowiada za pojawienie sie jablka w miescu wyznaczonym przez przekazane parametry
	 * @param column olumn zmienna wskazuje na odleglosc jablka od lewej krawedzi pola
	 * @param row  zmienna wskazuje na odleglosc jablka od gornej krawedzi pola
	 */
	public void makeAppleVisible(int column, int row);

	/**
	 * 
	 * @return metoda zwraca wysokosc planszy do gry
	 */
	public int getAreaHeight();
	
	/**
	 * 
	 * @return metoda zwraca dlugosc planszy 
	 */
	public int getAreaLenght();
	
	/**
	 * Metoda pobiera od uzytkownika przycisk jaki nacisnal 
	 */
	public void setKey();
	
	/**
	 * Metoda sprawdza czy jest wcisniety jakis przycisk i jesli jest to zwraca odpowiedni string
	 * @return w przypadku nacisniecia strzalek "ArrayUp", "ArrayDown", "ArrayLeft", "ArrayRight", jesli zaden przycisk nie zostal wcisniety metoda zwraca pusty string
	 */
	public String keyToString();
	
	/**
	 * Metoda odswierzajaca ekran
	 */
	public void refreshScreen();
	
	/**
	 * Metoda pokazuje pierwszy ekran z opcjami do wyboru
	 * @return 1-start gry 2-zmiana nazwy gracza 3-ekran z tabela wynikow 4-ustawienia 5-wyjscie
	 */
	public int showFirstScreen();

	/**
	 * Metoda pokazuje wynik po zakonczeniu gry w oddzielnym okienku
	 * wynik jest obliczany na podstawie wzoru: appleEated*100 - appleMissed*50
	 */
	public int showGameResult(int appleEated, int appleMissed, String snakeName);
	
	/**
	 * 
	 * @param currentName aktualna nazwa gracza
	 * @return metoda zwraca zmieniana nazwe uzytkownika 
	 */
	public String changeSnakeNameScreen(String currentName);
}

	