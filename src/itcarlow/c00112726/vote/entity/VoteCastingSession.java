package itcarlow.c00112726.vote.entity;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.gui.AddNewCandidateDialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.Scanner;

public class VoteCastingSession {
	
	private static VoteCastingSession instance = null;
	
	private final Scanner scanner = new Scanner(System.in);
	private VoteCastingSession() {	
		start();
	}
	
	public static VoteCastingSession getInstance() {
		if(instance == null) {
			instance = new VoteCastingSession();
		}
		return instance;
	}
	
	private void start() {
		int choice = 0;
		while(choice != -1) {
			System.out.println("*** VOTE COUNT APPLICATION ***");
			printMenu();
			System.out.print("Please enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
			processChoice(choice);
		}
	}
	
	private void printMenu() {
		System.out.println("Please select and option from below");
		System.out.println("1. Add Candidate");
		System.out.println("2. Remove Candidate");
		System.out.println("3. View All Candidates");
		System.out.println("4. Begin Voting");
		System.out.println("5. Calculate Results");
		System.out.println("6. Knockout Round");
	}
	
	private void processChoice(int choice) {
		switch (choice) {
		case 1:
			addCandidate();
			break;
			
		case 2:
			deleteCandidate();	
			break;
					
		case 3:
			viewAllCandidates();
			break;
			
		case 4:
			addBallotPaper();
			break;
			
		case 5:
			calculateResults();
			break;
			
		case 6:
			distributeLowest();
			break;

		default:
			break;
		}
	}
	
	private void addCandidate() {
		/*System.out.println("*** ENTER CANDIDATE DETAILS ***");
		
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		
		System.out.print("Party name: ");
		String party = scanner.nextLine();
		
		Candidate candidate = new Candidate(firstName, lastName, party);
		Candidate.addCandidate(candidate);
		
		System.out.println(firstName + " " + lastName + " of " + party + " has been added.");
		System.out.println();
		System.out.println();
		System.out.println();*/
		JFrame frame = new JFrame();
		frame.setVisible(true);
		AddNewCandidateDialog dialog = new AddNewCandidateDialog(frame);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private void deleteCandidate() {
		if(Candidate.getCandidateList().size() <= 0) {
			return;
		}
		System.out.println("*** ENTER CANDIDATE DETAILS ***");
		for(int i = 0; i < Candidate.getCandidateList().size(); i++) {
			Candidate candidate = Candidate.getCandidateList().get(i);
			System.out.println((i + 1) + ". " + candidate);
		}
		System.out.println("* * * * * * * * * * *");
		int choice = 0;
		
		while(choice < 1 || choice > Candidate.getCandidateList().size()) {
			System.out.print("Please select a candidates to remove: ");
			choice = scanner.nextInt();
			scanner.nextLine();
		}
		
		Candidate.getCandidateList().remove(choice - 1);
		
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void viewAllCandidates() {
		System.out.println("*** ALL CANDIDATES ***");
		for(Candidate candidate : Candidate.getCandidateList()) {
			System.out.println(candidate);
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void addBallotPaper() {
		final LinkedList<Candidate> availableCandidates = Candidate.getCandidateList().getCopy();
		final BallotPaper paper = new BallotPaper();
		
		int counter = 1;
		while(availableCandidates.size() > 0) {
			System.out.println("*** AVAILABLE CANDIDATES ***");	
			for (int i = 0; i < availableCandidates.size(); i++) {		
				System.out.println((i + 1) + ". " + availableCandidates.get(i));
			}
			
			System.out.print("Please select preference " + counter + ": ");
			int preference = 0;
			while(preference < 1 || preference > availableCandidates.size()) {
				preference = scanner.nextInt();
				scanner.nextLine();
			}
			paper.addCandidate(counter, availableCandidates.remove(preference - 1));
			counter++;
		}
		System.out.println();
		
		Candidate winner = paper.removeCurrentWinner();
		winner.add(paper);
		System.out.println();
	}
	
	private void calculateResults() {
		for(Candidate c : Candidate.getCandidateList()) {
			System.out.println(c + "... Results: " + c.numberOfVotes());
		}
	}
	
	private void distributeLowest() {
		if(Candidate.getCandidateList().size() == 1) {
			System.out.println("*******************************");
			System.out.println("********WINNER*********");
			System.out.println(Candidate.getCandidateList().peek());
			return;
		}
		Candidate lowest = Candidate.getCandidateList().peek();
		for(Candidate candidate : Candidate.getCandidateList()) {
			if(candidate.numberOfVotes() < lowest.numberOfVotes()) {
				lowest = candidate;
			}
		}
		lowest.reassignBallotPapers();
	}
	
	public static void main(String[] args) {
		VoteCastingSession vcs = VoteCastingSession.getInstance();
		vcs.start();
	}

}
