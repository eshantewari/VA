package model1;
import model1.Doctor;

import java.util.ArrayList;
import java.util.Collections;

public class Patient {
	
	
	//Categories - MA1, NP1, MA2, NP2, MA3, NP3, MA4, NP4, AMA, A, NMA, N, PMA, P (Type of Exam)
	//What the patient needs
	public static final int[] GM1 = {0,0,2,2,0,0,0,0,0,0,0,1,0,0}; //Case Type 1
	public static final int[] GM2 = {0,0,3,3,0,0,0,0,0,0,0,1,1,1}; //Case Type 2
	public static final int[] GM3 = {1,1,4,4,0,0,1,1,0,0,0,0,1,1}; //Case Type 3
	public static final int[] A1 =  {0,0,2,2,0,0,0,0,1,1,0,0,1,1}; //Case Type 4
	public static final int[] PN1 = {0,0,0,0,0,0,0,0,0,0,0,1,2,2}; //Case Type 5
	public static final int[] PN2 = {0,0,2,2,0,0,0,0,0,0,0,1,2,2}; //Case Type 6
	
	
	private int[] occupied;
	private int appt;
	private int apptInterval;
	private ArrayList<Integer> startTimes;
	private ArrayList<Integer> endTimes;
	private ArrayList<Room> rooms;
	private ArrayList<Doctor> doctors;

	private boolean done;
	private int[] needs;
	private int current; //current appointment
	private int[] lengths;
	
	private int dayLength;
	
	private boolean started;
	

	private int[] seenMA; //need to see MA before NP, Audio, Psych
	
	public enum CaseType{
		GM1, GM2, GM3, A1, PN1, PN2;
	}
	
	private CaseType caseType;
	
	public Patient(int dayLength, int apptInterval, CaseType type, int[] lengths){
		this.dayLength = dayLength;
		done = false;
		occupied = new int[dayLength];
		caseType = type;
		appt = 0;
		this.apptInterval = apptInterval;
		startTimes = new ArrayList<Integer>();
		endTimes = new ArrayList<Integer>();
		rooms = new ArrayList<Room>();
		doctors = new ArrayList<Doctor>();
		started = false;
		this.lengths = lengths;
		needs = new int[14];
		seenMA = new int[14];
		int[] arr = null;
		
		switch(caseType){
			case GM1:
				arr = GM1;
				break;
			case GM2:
				arr = GM2;
				break;
			case GM3:
				arr = GM3;
				break;
			case A1:
				arr = A1;
				break;
			case PN1:
				arr = PN1;
				break;
			case PN2:
				arr = PN2;
				break;
		}
		
		for(int i = 0; i < arr.length; i++){
			needs[i] = arr[i];
		}
		
		
	}
	
	public int startWaitTime(){
		if(startTimes.size() > 0){
			return startTimes.get(0) - appt;
		}
		return 0;
	}
	
	public static int totalStartWaitTime(Patient[] patients){
		int time = 0;
		for(Patient p:patients){
			time+=p.startWaitTime();
		}
		return time;
	}
	
	public static int maxStartWaitTime(Patient[] patients){
		int time = -1;
		for(Patient p:patients){
			if(p.startWaitTime() > time) time = p.startWaitTime();
		}
		return time;
	}
	
	public int inOutTime(){
		if(startTimes.size() > 0){
			return endTimes.get(endTimes.size()-1) - appt;
		}
		return 0;
	}
	
	public static int totalInOutTime(Patient[] patients){
		int time = 0;
		for(Patient p:patients){
			time+=p.inOutTime();
		}
		return time;
	}
	
	public static int maxInOutTime(Patient[] patients){
		int time = -1;
		for(Patient p:patients){
			if(p.inOutTime() > time) time = p.inOutTime();
		}
		return time;
	}
	
	
	public int lobbyTime(){
		if(startTimes.size() > 0){
			int time = startTimes.get(0) - appt;
			for(int i = 1; i < startTimes.size(); i++){
				time+=startTimes.get(i)-endTimes.get(i-1);
			}
			return time-25;
		}
		return 0;
	}
	
	public static int totalLobbyTime(Patient[] patients){
		int time = 0;
		for(Patient p:patients){
			time+=p.lobbyTime();
		}
		return time;
	}
	
	private void optimalAppt(){

		if(startTimes.size()>0)
			appt = ((startTimes.get(0)-25)/apptInterval)*apptInterval;

	}
	
	public void addTime(int time, int category, Doctor doctor, Room room){
		startTimes.add(time);
		endTimes.add(time+lengths[category]);
		rooms.add(room);
		doctors.add(doctor);
		needs[category]--;
		started = true;
		for(int i = time; i < time+lengths[category]; i++){
			if(i >= dayLength) break;
			occupied[i] = 1;
		}

		
		if(doctor.getType() == Doctor.DocType.MA)
			seenMA[category] ++;
		else
			seenMA[category-1]--;

	}
	
	public void finish(){
		Collections.sort(startTimes);
		Collections.sort(endTimes);
		optimalAppt();
	}
	
	public void update(){
		if(!done){
			for(int i = 0; i < needs.length; i++){
				if(needs[i] != 0) return;
			}

			done = true;
		}
	}
	
	public String toString(){
		return caseType.toString();
	}

	public int getAppt() {
		return appt;
	}

	public void setAppt(int appt) {
		this.appt = appt;
	}

	public ArrayList<Integer> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(ArrayList<Integer> startTimes) {
		this.startTimes = startTimes;
	}

	public ArrayList<Integer> getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(ArrayList<Integer> endTimes) {
		this.endTimes = endTimes;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public boolean isBusy(int time) {
		return occupied[time] == 1;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public int[] getNeeds() {
		return needs;
	}

	public void setNeeds(int[] needs) {
		this.needs = needs;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public boolean hasSeenMA(int category) {
		return seenMA[category-1] > 0;
	}


	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}
	
	public boolean hasStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public int getApptInterval() {
		return apptInterval;
	}

	public void setApptInterval(int apptInterval) {
		this.apptInterval = apptInterval;
	}

	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}

	public int[] getLengths() {
		return lengths;
	}

	public void setLengths(int[] lengths) {
		this.lengths = lengths;
	}

	public boolean isStarted() {
		return started;
	}

	public int[] getOccupied() {
		return occupied;
	}

	public void setOccupied(int[] occupied) {
		this.occupied = occupied;
	}
	
}
