package itcarlow.c00112726.vote.entity;

import itcarlow.c00112726.vote.datastructures.MinHeap;

public class BallotPaper {
	
	public MinHeap<Integer, Candidate> candidatePreferences;
	
	public BallotPaper() {
		candidatePreferences = new MinHeap<>();
	}

    /**
     * Add a new Candidate to the ballot paper
     * @param preference The candidate preference
     * @param candidate The new candidates
     */
	public void addCandidate(int preference, Candidate candidate) {
		candidatePreferences.add(preference, candidate);
	}

    /**
     * Removes and returns the Candidate with current best preference(1 is best, >1 is worse)
     * @return The candidate with current best preference
     */
	public Candidate removeCurrentWinner() {
		return candidatePreferences.remove();
	}

    /**
     * Reassigns the paper to the candidate with the next best preference.
     */
	public void reassignPaper() {
		Candidate nextPreference = candidatePreferences.remove();
		nextPreference.add(this);
	}
	
}
