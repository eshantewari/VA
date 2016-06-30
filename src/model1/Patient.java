package model1;
import model1.Doctor;

import java.util.ArrayList;
import java.util.Collections;

public class Patient {
	
	
	//Categories - PMA1, MA1, NP1, PMA2, MA2, NP2, PMA3, MA3, NP3, PMA4, MA4, NP4, PAMA, AMA, A, PNMA, NMA, N, PPMA, PMA, P (Type of Exam)
	//What the patient needs
	public static final int[] GM1 = {0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0}; //Case Type 1
	public static final int[] GM2 = {0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1}; //Case Type 2
	public static final int[] GM3 = {1,1,1,4,4,4,0,0,0,1,1,1,0,0,0,0,0,0,1,1,1}; //Case Type 3
	public static final int[] A1 =  {0,0,0,2,2,2,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1}; //Case Type 4
	public static final int[] PN1 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2}; //Case Type 5
	public static final int[] PN2 = {0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2}; //Case Type 6
	
	
	private String name;
	private int checkInLength;
	private int checkOutLength;
	private boolean checkedIn;
	private boolean checkedOut;
	private int checkInTime;
	private int checkOutTime;
	private int numCases;
	private int timeSinceExam;
	private boolean completelyDone;
	private int[] occupied;
	private int appt;
	private int apptInterval;
	private ArrayList<Integer> startTimes;
	private ArrayList<Integer> endTimes;
	private ArrayList<Room> rooms;
	private ArrayList<Doctor> doctors;
	private int place;
	
	private boolean MAdone;
	private boolean done;
	private int[] needs;
	private int current; //current appointment
	private int[] lengths;
	
	private int dayLength;
	
	private boolean started;
	
	private int doneTime;

	private int[] seenMA; //need to see MA before NP, Audio, Psych
	
	public enum CaseType{
		GM1, GM2, GM3, A1, PN1, PN2;
	}
	
	private CaseType caseType;
	
	public Patient(int dayLength, int apptInterval, CaseType type, int[] lengths, int checkInLength, int checkOutLength, int index, int place){
		this.checkInLength = checkInLength;
		this.checkOutLength = checkOutLength;
		checkInTime = -1;
		checkOutTime = -1;
		timeSinceExam = 0;
		this.dayLength = dayLength;
		done = false;
		setCompletelyDone(false);
		occupied = new int[dayLength];
		caseType = type;
		appt = 0;
		MAdone = false;
		this.apptInterval = apptInterval;
		startTimes = new ArrayList<Integer>();
		endTimes = new ArrayList<Integer>();
		rooms = new ArrayList<Room>();
		doctors = new ArrayList<Doctor>();
		started = false;
		this.lengths = lengths;
		needs = new int[lengths.length];
		seenMA = new int[lengths.length];
		setCheckedIn(false);
		setCheckedOut(false);
		setDoneTime(-1);
		
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
		
		setNumCases(0);
		for(int i = 0; i < needs.length; i+=3){
			setNumCases(getNumCases() + needs[i]);
		}
		
		name = type.toString()+" "+index;
		this.place = place;
	}
	
	public static int numWaiting(int time, Patient[] patients){
		int count = 0;
		for(Patient p:patients){
			if(!p.isDone() && !p.isBusy(time) && p.hasStarted()) count++;
		}
		return count;
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
			return time;
		}
		return 0;
	}
	
	public int lobbyTimePerCase(){
		return lobbyTime()/numCases;
	}
	
	public int inOutTimePerCase(){
		return inOutTime()/numCases;
	}
	
	public static int remainingCases(Patient[] patients) {
		int cases = 0;
		for(Patient p:patients){
			int[] needs = p.getNeeds();
			for(int i:needs){
				cases += i;
			}
		}
		return cases;
	}
	
	public static int numDBQs(Patient[] patients){
		int dbqs = 0;
		for(Patient p:patients){
			int[] needs = p.getNeeds();
			for(int i = 2; i < needs.length; i+=3){
				dbqs+=needs[i];
			}
		}
		return dbqs;
	}
	
	public static int totalDBQtime(Patient[] patients){
		int dbqs = 0;
		for(Patient p:patients){
			int[] needs = p.getNeeds();
			for(int i = 2; i < needs.length; i+=3){
				int mult = p.lengths[i-2]+p.lengths[i-1]+p.lengths[i];
				dbqs+=needs[i]*mult;
			}
		}
		return dbqs;
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
			appt = ((startTimes.get(0)-checkInLength)/apptInterval)*apptInterval;

	}
	
	public void addTime(int time, int category, Doctor doctor, Room room){
		timeSinceExam = 0;
		if(room.getType() == Room.RoomType.PLANNING){
			needs[category]--;
			return;
		}
		
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
		else if(category % 3 == 2)
			seenMA[category-1] --;

	}
	
	public void finish(){
		Collections.sort(startTimes);
		Collections.sort(endTimes);
		optimalAppt();
	}
	
	public void update(int time){
		if(hasStarted() && !isBusy(time)){
			timeSinceExam++;
		}
		if(!done){
			for(int i = 0; i < needs.length; i++){
				if(i%3 == 0) continue; //PMA
				if(needs[i] != 0) return;
			}
			setDoneTime(time);
			done = true;
		}
		//PMA
		if(!completelyDone){
			for(int i = 0; i < needs.length; i+=3){
				if(needs[i] != 0) return;
			}
			completelyDone = true;
		}
		
	}
	
	public void checkIn(int time){
		for(int i = time; i < time+checkInLength; i++){
			if(occupied[i]==1) return;

		}
		
		for(int i = time; i < time+checkInLength; i++){
			occupied[i] = 1;
		}
		
		checkInTime = time+checkInLength;
		checkedIn = true;
	}
	
	public void checkOut(int time){
		for(int i = time; i < time+checkOutLength; i++){
			if(i >= dayLength) break;
			occupied[i] = 1;
		}
		checkedOut = true;
		return;
	}
	
	public int getStartTime(){
		return startTimes.get(0) - checkInLength;
	}
	
	
	public String toString(){
		return name;
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

	/**
	 * @return the mAdone
	 */
	public boolean isMAdone() {
		return MAdone;
	}

	/**
	 * @param mAdone the mAdone to set
	 */
	public void setMAdone(boolean mAdone) {
		MAdone = mAdone;
	}

	/**
	 * @return the numCases
	 */
	public int getNumCases() {
		return numCases;
	}

	/**
	 * @param numCases the numCases to set
	 */
	public void setNumCases(int numCases) {
		this.numCases = numCases;
	}

	/**
	 * @return the completelyDone
	 */
	public boolean isCompletelyDone() {
		return completelyDone;
	}

	/**
	 * @param completelyDone the completelyDone to set
	 */
	public void setCompletelyDone(boolean completelyDone) {
		this.completelyDone = completelyDone;
	}

	public int[] getSeenMA() {
		return seenMA;
	}

	public void setSeenMA(int[] seenMA) {
		this.seenMA = seenMA;
	}

	/**
	 * @return the timeSinceExam
	 */
	public int getTimeSinceExam() {
		return timeSinceExam;
	}

	/**
	 * @param timeSinceExam the timeSinceExam to set
	 */
	public void setTimeSinceExam(int timeSinceExam) {
		this.timeSinceExam = timeSinceExam;
	}

	/**
	 * @return the checkedOut
	 */
	public boolean isCheckedOut() {
		return checkedOut;
	}

	/**
	 * @param checkedOut the checkedOut to set
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * @return the checkedIn
	 */
	public int isCheckedIn(int time) {
		if(!checkedIn) return 0;
		if(checkedIn && time < checkInTime) return 1;
		else return 2;
		
	}

	/**
	 * @param checkedIn the checkedIn to set
	 */
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	/**
	 * @return the checkOutLength
	 */
	public int getCheckOutLength() {
		return checkOutLength;
	}

	/**
	 * @param checkOutLength the checkOutLength to set
	 */
	public void setCheckOutLength(int checkOutLength) {
		this.checkOutLength = checkOutLength;
	}

	public int getCheckInLength() {
		return checkInLength;
	}

	public void setCheckInLength(int checkInLength) {
		this.checkInLength = checkInLength;
	}

	public int getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	public int getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(int checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	/**
	 * @return the doneTime
	 */
	public int getDoneTime() {
		return doneTime;
	}

	/**
	 * @param doneTime the doneTime to set
	 */
	public void setDoneTime(int doneTime) {
		this.doneTime = doneTime;
	}

	/**
	 * @return the place
	 */
	public int getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(int place) {
		this.place = place;
	}

	
}
