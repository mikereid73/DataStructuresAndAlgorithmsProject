package itcarlow.c00112726.vote.entity;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.datastructures.MinHeap;

import java.awt.image.BufferedImage;

public class Candidate {

    /**
     * All the candidates in this vote.
     */
	private static final LinkedList<Candidate> ALL_CANDIDATES =  new LinkedList<Candidate>();

    /**
     * How many candidates are in this vote.
     * @return How many candidates are in this vote
     */
    public static int numberOfCandidates() {
        return ALL_CANDIDATES.size();
    }

    /**
     * Add a new candidate to this vote.
     * @param candidate The candidate to add
     */
	public static void addCandidate(Candidate candidate) {
        ALL_CANDIDATES.add(candidate);
	}

    public static void deleteCandidate(Candidate candidate) {
        ALL_CANDIDATES.remove(candidate);
    }

    /**
     * Returns a copy of the list containing all candidates in this vote.
     * @return Copy of all candidates in this vote
     */
	public static LinkedList<Candidate> getCandidateList() {
		return ALL_CANDIDATES.getCopy();
	}
	
	private String firstName;
	private String lastName;
	private String party;
    private BufferedImage image;
	
	private MinHeap<Integer, BallotPaper> ballotPapers;
	
	public Candidate(String firstName, String lastName, String party) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.party = party;
		this.ballotPapers = new MinHeap<>();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getParty() {
		return party;
	}

    public BufferedImage getImage() {
        return image;
    }

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setParty(String party) {
		this.party = party;
	}

    public void setImage(BufferedImage image) {
        this.image = image;
    }
	
	public void add(BallotPaper ballot) {
		ballotPapers.add(1, ballot);
	}
	
	public int numberOfVotes() {
		return ballotPapers.size();
	}
	
	public void reassignBallotPapers() {
		while(!ballotPapers.empty()) {
			final BallotPaper current = ballotPapers.remove();
			current.reassignPaper();
		}
		System.out.println("Removed: " + this);
		Candidate.ALL_CANDIDATES.remove(this);
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == this) {
			return true;
		}
		
		if(!(object instanceof Candidate)) {
			return false;
		}
				
		Candidate candidate = (Candidate)object;		
		return  firstName.equalsIgnoreCase(candidate.firstName) &&
				lastName.equalsIgnoreCase(candidate.lastName) &&
				party.equalsIgnoreCase(candidate.party);
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " of " + party;
	}
}
