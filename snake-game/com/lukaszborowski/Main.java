package com.lukaszborowski;

import java.util.Scanner;

import com.lukaszborowski.controller.GameController;
import com.lukaszborowski.view.TerminalGameView;

public class Main {

	public static void main(String[] args) {
		

		
		int gameHeight;
		int gameLenght;
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Wybierz opcję uruchomienia gry: ");
//		System.out.println("1. Terminal");
//		System.out.println("2. Okienko");
//        if(sc.nextInt() == 1) {
//        	sc.close();
        	TerminalGameView terminalView = new TerminalGameView();
			gameHeight = terminalView.getAreaHeight();
			gameLenght = terminalView.getAreaLenght();
			
			
			
			
			GameController gameController = new GameController(terminalView, gameLenght, gameHeight);
			gameController.firstScreen();
//        }else if(sc.nextInt() == 2)   { 
//        	sc.close();
//        	System.out.println("Jeszcze nie ma");
//        }else {
//        	System.out.println("Nie prawidłowe dane\n\n");
//        }
		
	}
	
}