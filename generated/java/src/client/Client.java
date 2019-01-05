package client;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.*;
import model.quotes.*;

public class Client {
	private Scanner scanner;
	private Github github;
	
	Client(){
		this.scanner = new Scanner(System.in);
		this.github = new Github();
		
		this.populateDatabase();
	}
	
	public void start() {
		this.printStartMenu();
	}
	
	private void populateDatabase(){
		User user1 = new User("user1", "user1@gmail.com", "password123");
		User user2 = new User("user2", "user2@gmail.com", "password123");
		User user3 = new User("user3", "user3@gmail.com", "password123");
		
		this.github.addUser(user1);
		this.github.addUser(user2);
		this.github.addUser(user3);
		
		//System.out.println(this.github.toString());
	}
	
	//Menu printing
	private void printGithubLogo() {
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                      ||");
		System.out.println("||                GITHUB                ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
	}
	
	private void printStartMenu() {
		int option = 0;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| 1. Log in an existant account.       ||");
		System.out.println("|| 2. Register a new account.           ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 1 || option > 2) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1: 
				//System.out.println("login");
				this.printLoginMenu();
				break;
			case 2:
				//System.out.println("register");
				this.printRegisterMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void printLoginMenu() {
		String username, password;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Username:                            ||");
		System.out.print("||  ");
		username = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("|| Password:                            ||");
		System.out.print("||  ");
		password = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		//System.out.println("logging with username " + username + " and password " + password);
		
		if(this.github.login(username, password)) {
			System.out.println("Successful login!");
			this.printMainMenu();
		} else {
			System.out.println("Invalid credentials, please try again!");
			this.printLoginMenu();
		}
	}
	
	private void printRegisterMenu() {
		String username, email, password;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Please type your desired username    ||");
		System.out.print("||  ");
		username = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("|| Insert your email                    ||");
		System.out.print("||  ");
		email = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("|| Insert a password                    ||");
		System.out.print("||  ");
		password = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		//System.out.println(this.github.getUsers().toString());
		User newUser = new User(username, email, password);
		this.github.addUser(newUser);
		
		this.github.login(username, password);
		this.printMainMenu();
		//System.out.println(this.github.getUsers().toString());
	}
	
	private void printMainMenu() {
		int option = 0;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| 1. Create a new repository.          ||");
		System.out.println("|| 2. Manage your repositories.         ||");
		System.out.println("|| 3. Delete this account.              ||");
		System.out.println("|| 4. Log out.                          ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 1 || option > 4) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1: 
				//this.createRepositoryMenu();
				break;
			case 2:
				//this.manageRepositoryMenu();
				break;
			case 3:
				//this.deleteUserMenu();
				break;
			case 4:
				this.github.logout();
				this.printStartMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
}
