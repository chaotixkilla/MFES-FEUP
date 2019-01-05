package client;

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
		
	}
	
	private void populateDatabase(){
		User user1 = new User("user1", "user1@gmail.com", "password123");
		User user2 = new User("user2", "user2@gmail.com", "password123");
		User user3 = new User("user3", "user3@gmail.com", "password123");
		
		this.github.addUser(user1);
		this.github.addUser(user2);
		this.github.addUser(user3);
		
		System.out.println(this.github.toString());
	}
	
	//Menu printing
	private void printGithubLogo() {
		System.out.println("||||||||||||||||||||||||||");
		System.out.println("||                      ||");
		System.out.println("||        GITHUB        ||");
		System.out.println("||                      ||");
		System.out.println("||||||||||||||||||||||||||");
	}
	
	private void printMainMenu() {
		this.printGithubLogo();
		System.out.println("Choose an option: ");
	}
}
