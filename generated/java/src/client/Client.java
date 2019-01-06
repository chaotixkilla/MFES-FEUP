package client;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import org.overture.codegen.runtime.VDMSet;

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
		int option = -1;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| 1. Log in an existant account.       ||");
		System.out.println("|| 2. Register a new account.           ||");
		System.out.println("||                                      ||");
		System.out.println("|| 0. Exit.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > 2) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 0:
				System.out.println("Program exited with success!");
				break;
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
		int option = -1;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| 1. Create a new repository.          ||");
		System.out.println("|| 2. Manage your repositories.         ||");
		System.out.println("|| 3. Explore available repositories.   ||");
		System.out.println("|| 4. Delete this account.              ||");
		System.out.println("|| 5. Log out.                          ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 1 || option > 5) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1: 
				this.createRepositoryMenu();
				break;
			case 2:
				this.manageRepositoryMenu();
				break;
			case 3:
				this.exploreRepositoryMenu();
				break;
			case 4:
				this.deleteUserMenu();
				break;
			case 5:
				this.github.logout();
				this.printStartMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void createRepositoryMenu() {
		String repName;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Insert the repository name           ||");
		System.out.print("||  ");
		repName = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		if(!this.github.getAllOwnedRepositoriesName(this.github.getUser(this.github.getLoggedInUsername())).contains(repName)) {
			this.github.createRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
			System.out.println(this.github.getAllAvailableRepositoriesName(this.github.getUser(this.github.getLoggedInUsername())));
			System.out.println("Repository " + repName + " created!");
			this.printMainMenu();
		} else {
			System.out.println("You already own repository " + repName + " !");
			this.printMainMenu();
		}
	}
	
	private void manageRepositoryMenu() {
		int option = -1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| 1. Navigate your owned repositories. ||");
		System.out.println("|| 2. Navigate repositories you         ||");
		System.out.println("||    collaborate to.                   ||");
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > 2) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1:
				this.ownedRepositoriesMenu();
				break;
			case 2:
				this.collabRepositoriesMenu();
				break;
			case 0:
				this.printMainMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void ownedRepositoriesMenu() {
		VDMSet ownedRepositories = this.github.getAllOwnedRepositoriesName(this.github.getUser(this.github.getLoggedInUsername()));
		HashMap<Integer, String> repMapping = new HashMap<Integer, String>();
		int index = 0;
		int option = -1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Repositories you own:                ||");
		System.out.println("||                                      ||");

		if(ownedRepositories.size() > 0) {
			index = 1;
			for(Iterator<String> i = ownedRepositories.iterator(); i.hasNext();) {
				String repName = i.next();
				Repository rep = this.github.getSpecificOwnedRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
				System.out.println("|| " + index + ". " + rep.getName() + " " + rep.getVisibility().toString());
				
				repMapping.put(index, rep.getName());
				index++;
			}
		} else {
			System.out.println("|| You do not own any repositories :(   ||");
		}
		
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > index) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		if(option == 0) {
			this.manageRepositoryMenu();
		} else {
			System.out.println("Repository " + repMapping.get(option) + " selected.");
			this.manageOwnedRepositoryMenu(repMapping.get(option));
		}
	}
	
	private void manageOwnedRepositoryMenu(String repName) {
		Repository rep = this.github.getSpecificOwnedRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		int option = -1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Repository " + repName + " " + rep.getVisibility().toString());
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                      ||");
		System.out.println("|| 1. Add a new collaborator.           ||");
		System.out.println("|| 2. Remove a collaborator.            ||");
		System.out.println("|| 3. Make repository public.           ||");
		System.out.println("|| 4. Make repository private.          ||");
		System.out.println("|| 5. Delete repository.                ||");
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > 5) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1:
				this.addCollaboratorMenu(repName);
				break;
			case 2:
				this.removeCollaboratorMenu(repName);
				break;
			case 3:
				this.github.makeRepositoryPublic(this.github.getUser(this.github.getLoggedInUsername()), repName);
				System.out.println("Repository " + repName + " is now public!");
				this.ownedRepositoriesMenu();
				break;
			case 4:
				this.github.makeRepositoryPrivate(this.github.getUser(this.github.getLoggedInUsername()), repName);
				System.out.println("Repository " + repName + " is now private!");
				this.ownedRepositoriesMenu();
				break;
			case 5:
				this.deleteRepositoryMenu(repName);
				break;
			case 0:
				this.ownedRepositoriesMenu();
				break;
			default:
				break;
		}
	}
	
	private void addCollaboratorMenu(String repName) {
		Repository rep = this.github.getSpecificOwnedRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		String newCollabName = "-1";
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Current collaborators:               ||");
		
		if(rep.getCollaborators().size() > 0) {
			for(Iterator<User> i = rep.getCollaborators().iterator(); i.hasNext();) {
				User user = i.next();
				System.out.println("||  " + user.getUsername());
			}
		} else {
			System.out.println("||  There are no collaborators :(       ||");
		}
		
		while(this.github.getUser(newCollabName).getUsername() == null || newCollabName.equals(this.github.getLoggedInUsername())) {
			System.out.println("||                                      ||");
			System.out.println("|| Insert the new collaborator username ||");
			System.out.print("||  ");
			newCollabName = this.scanner.nextLine();
			
			if(this.github.getUser(newCollabName).getUsername() == null) {
				System.out.println("The user " + newCollabName + " does not exist!");
			}
			
			if(newCollabName.equals(this.github.getLoggedInUsername())) {
				System.out.println("You cannot add yourself as collaborator!");
			}
		}
		
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		this.github.addCollaborator(this.github.getUser(this.github.getLoggedInUsername()), repName, this.github.getUser(newCollabName));
		System.out.println("User " + newCollabName + " is now a " + repName + " collaborator!");
		this.manageOwnedRepositoryMenu(repName);
	}
	
	private void removeCollaboratorMenu(String repName) {
		Repository rep = this.github.getSpecificOwnedRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		int index = 0;
		int option = -1;
		HashMap<Integer, String> collabMapping = new HashMap<Integer, String>();
		
		if(rep.getCollaborators().size() > 0) {
			this.printGithubLogo();
			System.out.println("||                                      ||");
			System.out.println("|| Current collaborators:               ||");
			
			index = 1;
			for(Iterator<User> i = rep.getCollaborators().iterator(); i.hasNext();) {
				User user = i.next();
				System.out.println("||  " + index + ". " + user.getUsername());
				
				collabMapping.put(index, user.getUsername());
				index++;
			}
			
			System.out.println("||                                      ||");
			System.out.println("|| 0. Back.                             ||");
			System.out.println("||                                      ||");
			System.out.println("|| Choose an option.                    ||");
			System.out.println("||                                      ||");
			System.out.println("||||||||||||||||||||||||||||||||||||||||||");
			
			while(option < 0 || option > index) {
				String optionString = this.scanner.nextLine();
				try {
					option = Integer.parseInt(optionString);
				} catch(NumberFormatException e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			
			if(option == 0) {
				this.manageOwnedRepositoryMenu(repName);
			} else {
				rep.removeCollaborator(this.github.getUser(this.github.getLoggedInUsername()), this.github.getUser(collabMapping.get(option)));
				System.out.println("User " + collabMapping.get(option) + " is no longer a collaborator of " + repName + "!");
				this.manageOwnedRepositoryMenu(repName);
			}
			
		} else {
			System.out.println("You cannot remove collaborators as this repository has none!");
			this.manageOwnedRepositoryMenu(repName);
		}
	}
	
	private void deleteRepositoryMenu(String repName) {
		
		int option = -1;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Are you sure you want to delete      ||");
		System.out.println("|| this repository? This operation is   ||");
		System.out.println("|| permanent!                           ||");
		System.out.println("||                                      ||");
		System.out.println("|| 1. YES                               ||");
		System.out.println("|| 2. NO                                ||");
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
				this.github.deleteRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
				System.out.println("Repository " + repName + " was successfully deleted!");
				this.ownedRepositoriesMenu();
				break;
			case 2:
				this.manageOwnedRepositoryMenu(repName);
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void collabRepositoriesMenu() {
		VDMSet availableRepositories = this.github.getAllAvailableRepositoriesName(this.github.getUser(this.github.getLoggedInUsername()));
		HashMap<Integer, String> repMapping = new HashMap<Integer, String>();
		int index = 0;
		int option = -1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Repositories you collaborate to:     ||");
		System.out.println("||                                      ||");

		if(availableRepositories.size() > 0) {
			index = 1;
			for(Iterator<String> i = availableRepositories.iterator(); i.hasNext();) {
				String repName = i.next();
				Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
				System.out.println("|| " + index + ". " + rep.getName() + " " + rep.getVisibility().toString());
				
				repMapping.put(index, rep.getName());
				index++;
			}
		} else {
			System.out.println("|| You do not collaborate to any        ||");
			System.out.println("|| repositories :(                      ||");
		}
		
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > index) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		if(option == 0) {
			this.manageRepositoryMenu();
		} else {
			System.out.println("Repository " + repMapping.get(option) + " selected.");
			this.manageCollabRepositoryMenu(repMapping.get(option));
		}
	}
	
	private void manageCollabRepositoryMenu(String repName) {
		Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		int option = -1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Repository \'" + repName + "\' Information");
		System.out.println("||                                      ||");
		
		for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
			Branch branch = i.next();
			System.out.println("||  " + branch.getName());
			
			if(branch.getCommits().size() > 0) {
				for(Iterator<Commit> i1 = branch.getCommits().iterator(); i1.hasNext();) {
					Commit commit = i1.next();
					System.out.println("||   " + commit.getIdentifier() + " \'" + commit.getMessage() + "\'");
				}
			} else {
				System.out.println("||   No commits done to this branch.    ||");
			}
			
			System.out.println("||                                      ||");
		}
		
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                      ||");
		System.out.println("|| 1. Create new commit.                ||");
		System.out.println("|| 2. Create new branch.                ||");
		System.out.println("|| 3. Delete existent branch.           ||");
		System.out.println("|| 4. Merge two existent branches.      ||");
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > 4) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		switch(option) {
			case 1:
				this.pickBranchMenu(repName);
				break;
			case 2:
				this.createBranchMenu(repName);
				break;
			case 3:
				this.deleteBranchMenu(repName);
				break;
			case 4:
				this.mergeBranchMenu(repName);
				break;
			case 0:
				this.collabRepositoriesMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void mergeBranchMenu(String repName) {
		Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		HashMap<Integer, String> branchMapping = new HashMap<Integer, String>();
		int index = 1;
		int option1 = -1;
		int option2 = -1;
		
		if(rep.getBranches().size() > 1) {
			this.printGithubLogo();
			System.out.println("||                                      ||");
			System.out.println("|| Pick first branch to merge (this     ||");
			System.out.println("|| branch will still exist after the    ||");
			System.out.println("|| merge occurs):                       ||");
			System.out.println("||                                      ||");
			
			for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
				Branch branch = i.next();
				System.out.println("|| " + index + ". " + branch.getName());
				
				branchMapping.put(index, branch.getName());
				index++;
			}
			
			System.out.println("||                                      ||");
			System.out.println("|| 0. Back.                             ||");
			System.out.println("||                                      ||");
			System.out.println("|| Choose an option.                    ||");
			System.out.println("||                                      ||");
			System.out.println("||||||||||||||||||||||||||||||||||||||||||");
			
			while(option1 < 0 || option1 > index) {
				String optionString = this.scanner.nextLine();
				try {
					option1 = Integer.parseInt(optionString);
				} catch(NumberFormatException e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			
			this.printGithubLogo();
			System.out.println("||                                      ||");
			System.out.println("|| Pick second branch to merge (this    ||");
			System.out.println("|| branch will be deleted after the     ||");
			System.out.println("|| merge occurs):                       ||");
			System.out.println("||                                      ||");
			
			index = 1;
			for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
				Branch branch = i.next();
				System.out.println("|| " + index + ". " + branch.getName());
				
				branchMapping.put(index, branch.getName());
				index++;
			}
			
			System.out.println("||                                      ||");
			System.out.println("|| 0. Back.                             ||");
			System.out.println("||                                      ||");
			System.out.println("|| Choose an option.                    ||");
			System.out.println("||                                      ||");
			System.out.println("||||||||||||||||||||||||||||||||||||||||||");
			
			while(option2 < 0 || option2 > index) {
				String optionString = this.scanner.nextLine();
				try {
					option2 = Integer.parseInt(optionString);
				} catch(NumberFormatException e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			
			if(option1 == 0 || option2 == 0) {
				this.manageCollabRepositoryMenu(repName);
			} else {
				this.github.mergeBranches(this.github.getUser(this.github.getLoggedInUsername()), repName, branchMapping.get(option1), branchMapping.get(option2));
				System.out.println("Branch " + branchMapping.get(option2) + " was successfully merged into branch " + branchMapping.get(option1) + "!");
				this.manageCollabRepositoryMenu(repName);
				//this.deleteBranchMenuConfirmation(repName, branchMapping.get(option));
			}
			
		} else {
			System.out.println("Not enough branches to merge in this repository!");
			this.manageCollabRepositoryMenu(repName);
		}
	}
	
	private void deleteBranchMenu(String repName) {
		Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		HashMap<Integer, String> branchMapping = new HashMap<Integer, String>();
		int index = 1;
		int option = -1;
		
		if(rep.getBranches().size() > 1) {
			this.printGithubLogo();
			System.out.println("||                                      ||");
			System.out.println("|| Branches in this repository:         ||");
			System.out.println("||                                      ||");
			
			for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
				Branch branch = i.next();
				System.out.println("|| " + index + ". " + branch.getName());
				
				branchMapping.put(index, branch.getName());
				index++;
			}
			
			System.out.println("||                                      ||");
			System.out.println("|| 0. Back.                             ||");
			System.out.println("||                                      ||");
			System.out.println("|| Choose an option.                    ||");
			System.out.println("||                                      ||");
			System.out.println("||||||||||||||||||||||||||||||||||||||||||");
			
			while(option < 0 || option > index) {
				String optionString = this.scanner.nextLine();
				try {
					option = Integer.parseInt(optionString);
				} catch(NumberFormatException e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			
			if(option == 0) {
				this.manageCollabRepositoryMenu(repName);
			} else {
				this.deleteBranchMenuConfirmation(repName, branchMapping.get(option));
			}
			
		} else {
			System.out.println("Cannot delete the last branch of a repository!");
			this.manageCollabRepositoryMenu(repName);
		}
		
	}
	
	private void deleteBranchMenuConfirmation(String repName, String branchName) {
		int option = -1;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Are you sure you want to delete      ||");
		System.out.println("|| this branch? This operation is       ||");
		System.out.println("|| permanent and all commits will be    ||");
		System.out.println("|| lost!                                ||");
		System.out.println("||                                      ||");
		System.out.println("|| 1. YES                               ||");
		System.out.println("|| 2. NO                                ||");
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
				this.github.deleteBranch(this.github.getUser(this.github.getLoggedInUsername()), repName, branchName);
				System.out.println("Branch " + branchName + " was successfully deleted!");
				this.manageCollabRepositoryMenu(repName);
				break;
			case 2:
				this.manageCollabRepositoryMenu(repName);
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void createBranchMenu(String repName) {
		Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		String branchName = "";
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Branches in this repository:         ||");
		System.out.println("||                                      ||");
		
		for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
			Branch branch = i.next();
			System.out.println("|| " + branch.getName());
		}
		
		while(branchName.equals("") || rep.getBranchesNames().contains(branchName)) {
			System.out.println("||                                      ||");
			System.out.println("|| Insert a branch name                 ||");
			System.out.print("||  ");
			branchName = this.scanner.nextLine();
			
			if(branchName.equals("")) {
				System.out.println("New branch must have a name!");
			}
			
			if(rep.getBranchesNames().contains(branchName)) {
				System.out.println("New branch must have a different name!");
			}
		}
		
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		this.github.createBranch(this.github.getUser(this.github.getLoggedInUsername()), repName, branchName);
		this.manageCollabRepositoryMenu(repName);
		
	}
	
	private void pickBranchMenu(String repName) {
		Repository rep = this.github.getSpecificAvailableRepository(this.github.getUser(this.github.getLoggedInUsername()), repName);
		HashMap<Integer, String> branchMapper = new HashMap<Integer, String>();
		int option = -1;
		int index = 1;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Branches in this repository:         ||");
		System.out.println("||                                      ||");
		
		for(Iterator<Branch> i = rep.getBranches().iterator(); i.hasNext();) {
			Branch branch = i.next();
			System.out.println("|| " + index + ". " + branch.getName());
			
			branchMapper.put(index, branch.getName());
			index++;
		}
		
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back.                             ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > index) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		if(option == 0) {
			this.manageCollabRepositoryMenu(repName);
		} else {
			System.out.println("Branch " + branchMapper.get(option) + " selected for commit.");
			this.createCommitMenu(repName, branchMapper.get(option));
		}
	}
	
	private void createCommitMenu(String repName, String branchName) {
		String commitID, commitMessage;
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Insert a commit message              ||");
		System.out.print("||  ");
		commitMessage = this.scanner.nextLine();
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		//UUID generator
		final String AB = "0123456789abcdef";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(6);
	    for(int i = 0; i < 6; i++) {
	    	sb.append(AB.charAt(rnd.nextInt(AB.length())));
	    }
	    commitID = sb.toString();
	    
	    this.github.createCommit(this.github.getUser(this.github.getLoggedInUsername()), repName, branchName, commitID, commitMessage);
	    System.out.println("Commit " + commitID + " \'" + commitMessage + "\' successfully created!");
	    this.manageCollabRepositoryMenu(repName);
	}
	
	private void deleteUserMenu() {
		int option = -1;
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Are you sure you want to delete      ||");
		System.out.println("|| this user? This operation is         ||");
		System.out.println("|| permanent!                           ||");
		System.out.println("||                                      ||");
		System.out.println("|| 1. YES                               ||");
		System.out.println("|| 2. NO                                ||");
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
				this.github.deleteUser(this.github.getLoggedInUsername());
				System.out.println("User " + this.github.getLoggedInUsername() + " was successfully deleted!");
				this.printStartMenu();
				break;
			case 2:
				this.printMainMenu();
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
		}
	}
	
	private void exploreRepositoryMenu() {
		VDMSet clonableReps = this.github.getClonableRepositories(this.github.getUser(this.github.getLoggedInUsername()));
		int index = 1;
		int option = -1;
		HashMap<Integer, String> repMapping = new HashMap<Integer, String>();
		
		this.printGithubLogo();
		System.out.println("||                                      ||");
		System.out.println("|| Choose a repository to clone:        ||");
		System.out.println("||                                      ||");
		
		for(Iterator<Repository> i = clonableReps.iterator(); i.hasNext();) {
			Repository rep = i.next();
			System.out.println("|| " + index + ". " + rep.getName());
			
			repMapping.put(index, rep.getName());
			index++;
		}
		
		System.out.println("||                                      ||");
		System.out.println("|| 0. Back                              ||");
		System.out.println("||                                      ||");
		System.out.println("|| Choose an option.                    ||");
		System.out.println("||                                      ||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||");
		
		while(option < 0 || option > index) {
			String optionString = this.scanner.nextLine();
			try {
				option = Integer.parseInt(optionString);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		if(option == 0) {
			this.printMainMenu();
		} else {
			System.out.println("Cloned repository " + repMapping.get(option) + " successfully!");
		}
	}
}