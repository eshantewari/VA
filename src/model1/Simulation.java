package model1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import model1.Doctor;
import model1.Patient;
import model1.Room;

public class Simulation {



	public static void main(String[] args) throws IOException {

		int[] lengths = new int[14];
		BufferedReader in = new BufferedReader(new FileReader("inputs.in"));

		in.readLine();
		StringTokenizer lengthsLine = new StringTokenizer(in.readLine());
		lengthsLine.nextToken();
		for(int i = 0; i < 14; i++){
			lengths[i] = Integer.parseInt(lengthsLine.nextToken());
		}


		in.readLine();

		//Number of Rooms
		StringTokenizer triageRooms = new StringTokenizer(in.readLine());
		triageRooms.nextToken();
		int roomTriage = Integer.parseInt(triageRooms.nextToken());

		StringTokenizer generalRooms = new StringTokenizer(in.readLine());
		generalRooms.nextToken();
		int roomGM = Integer.parseInt(generalRooms.nextToken());

		StringTokenizer psychRooms = new StringTokenizer(in.readLine());
		psychRooms.nextToken();
		int roomP = Integer.parseInt(psychRooms.nextToken());

		StringTokenizer audioRooms = new StringTokenizer(in.readLine());
		audioRooms.nextToken();
		int roomA = Integer.parseInt(audioRooms.nextToken());


		in.readLine();
		in.readLine();

		//Doctors
		int[] rangeMA = new int[2];
		int[] rangeNP = new int[2];
		int[] rangeA = new int[2];
		int[] rangeN = new int[2];
		int[] rangeP = new int[2];
		int[] costs = new int[5];


		StringTokenizer MAs = new StringTokenizer(in.readLine());
		MAs.nextToken();
		rangeMA[0] = Integer.parseInt(MAs.nextToken());
		rangeMA[1] = Integer.parseInt(MAs.nextToken());
		costs[0] = Integer.parseInt(MAs.nextToken());

		StringTokenizer NPs = new StringTokenizer(in.readLine());
		NPs.nextToken();
		rangeNP[0] = Integer.parseInt(NPs.nextToken());
		rangeNP[1] = Integer.parseInt(NPs.nextToken());
		costs[1] = Integer.parseInt(NPs.nextToken());


		StringTokenizer As = new StringTokenizer(in.readLine());
		As.nextToken();
		rangeA[0] = Integer.parseInt(As.nextToken());
		rangeA[1] = Integer.parseInt(As.nextToken());
		costs[2] = Integer.parseInt(As.nextToken());

		StringTokenizer Ns = new StringTokenizer(in.readLine());
		Ns.nextToken();
		rangeN[0] = Integer.parseInt(Ns.nextToken());
		rangeN[1] = Integer.parseInt(Ns.nextToken());
		costs[3] = Integer.parseInt(Ns.nextToken());


		StringTokenizer Ps = new StringTokenizer(in.readLine());
		Ps.nextToken();
		rangeP[0] = Integer.parseInt(Ps.nextToken());
		rangeP[1] = Integer.parseInt(Ps.nextToken());
		costs[4] = Integer.parseInt(Ps.nextToken());

		in.readLine();

		//Cases
		StringTokenizer sGenMed = new StringTokenizer(in.readLine());
		sGenMed.nextToken();
		double genMed = Double.parseDouble(sGenMed.nextToken());

		StringTokenizer sA = new StringTokenizer(in.readLine());
		sA.nextToken();
		double A = Double.parseDouble(sA.nextToken());

		StringTokenizer sPN = new StringTokenizer(in.readLine());
		sPN.nextToken();
		double PN = Double.parseDouble(sPN.nextToken());

		//Sub-Cases
		in.readLine();
		in.readLine();

		//Case 1
		StringTokenizer sCase1 = new StringTokenizer(in.readLine());
		sCase1.nextToken();
		double GMcase1 = Double.parseDouble(sCase1.nextToken());
		double Acase1 = Double.parseDouble(sCase1.nextToken());
		double PNcase1 = Double.parseDouble(sCase1.nextToken());

		//Case 2
		StringTokenizer sCase2 = new StringTokenizer(in.readLine());
		sCase2.nextToken();
		double GMcase2 = Double.parseDouble(sCase2.nextToken());
		double Acase2 = Double.parseDouble(sCase2.nextToken());
		double PNcase2 = Double.parseDouble(sCase2.nextToken());


		//Case 3
		StringTokenizer sCase3 = new StringTokenizer(in.readLine());
		sCase3.nextToken();
		double GMcase3 = Double.parseDouble(sCase3.nextToken());
		double PNcase3 = Double.parseDouble(sCase3.nextToken());
		double Acase3 = Double.parseDouble(sCase3.nextToken());


		//Remaining Variables
		in.readLine();

		StringTokenizer st = new StringTokenizer(in.readLine());
		st.nextToken();
		int dayLength = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(in.readLine());
		st.nextToken(); 
		int apptInterval = Integer.parseInt(st.nextToken());


		st = new StringTokenizer(in.readLine());
		st.nextToken(); 
		int cases = Integer.parseInt(st.nextToken());
		cases+=3;
		in.close();


		int iteration = 1;

		double minCost = Double.MAX_VALUE;
		Patient[] optPatients = null;
		Room[] optRooms = null;
		Doctor[] optDoctors = null;

		for(int numMA = rangeMA[0]; numMA <= rangeMA[1]; numMA++){
			for(int numNP = rangeNP[0]; numNP <= rangeNP[1]; numNP++){
				for(int numA = rangeA[0]; numA <= rangeA[1]; numA++){
					for(int numN = rangeN[0]; numN <= rangeN[1]; numN++){
						for(int numP = rangeP[0]; numP <= rangeP[1]; numP++){
							for(int a = 0; a < 100; a++){
								Patient[] patients = null;
								Room[] rooms = null;
								Doctor[] doctors = null;
								ArrayList<Patient> tempPatients = new ArrayList<Patient>();
								for(int i = 0; i < (int)(cases*PN*PNcase1); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.PN1, lengths));
								}
								for(int i = 0; i < (int)(cases*PN*PNcase2); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.PN2, lengths));
								}
								for(int i = 0; i < (int)(cases*genMed*GMcase1); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM1, lengths));
								}
								for(int i = 0; i < (int)(cases*genMed*GMcase2); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM2, lengths));
								}
								for(int i = 0; i < (int)(cases*genMed*GMcase3); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM3, lengths));
								}

								for(int i = 0; i < (int)(cases*A*Acase1); i++){
									tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.A1, lengths));
								}


								patients = tempPatients.toArray(new Patient[tempPatients.size()]);

								ArrayList<Room> tempRooms = new ArrayList<Room>();
								for(int i = 0; i < roomTriage; i++){
									tempRooms.add(new Room(dayLength, Room.RoomType.TRIAGE, lengths));
								}		
								for(int i = 0; i < roomGM; i++){
									tempRooms.add(new Room(dayLength, Room.RoomType.GM, lengths));
								}
								for(int i = 0; i < roomA; i++){
									tempRooms.add(new Room(dayLength, Room.RoomType.A, lengths));
								}
								for(int i = 0; i < roomP; i++){
									tempRooms.add(new Room(dayLength, Room.RoomType.P, lengths));
								}

								rooms = tempRooms.toArray(new Room[tempRooms.size()]);

								ArrayList<Doctor> tempDoctors = new ArrayList<Doctor>();	
								for(int i = 0; i < numMA; i++){
									tempDoctors.add(new Doctor(costs[0], dayLength, Doctor.DocType.MA, lengths));
								}
								for(int i = 0; i < numNP; i++){
									tempDoctors.add(new Doctor(costs[1], dayLength, Doctor.DocType.NP, lengths));
								}
								for(int i = 0; i < numA; i++){
									tempDoctors.add(new Doctor(costs[2], dayLength, Doctor.DocType.A, lengths));
								}	
								for(int i = 0; i < numN; i++){
									tempDoctors.add(new Doctor(costs[3], dayLength, Doctor.DocType.N, lengths));
								}
								for(int i = 0; i < numP; i++){
									tempDoctors.add(new Doctor(costs[4], dayLength, Doctor.DocType.P, lengths));
								}


								doctors = tempDoctors.toArray(new Doctor[tempDoctors.size()]);
								//print(patients);
								//print(doctors);
								//print(rooms);

								RandomizeArray(patients);

								//book the neurologist
								for(int time = 0; time < dayLength; time+=iteration){
									int doneIndex = donePatients(patients);
									sortPatients(patients, doneIndex);
									for(int i = 0; i < patients.length; i++){
										Patient patient = patients[i];
										int[] needs = patient.getNeeds();
										if(patient.isDone()) continue;
										for(int j = 10; j <= 11; j++){
											if(needs[j] != 0){
												boolean made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
												if(made){
													break;
												}
											}
										}
									}
								}

								//book the audiologist
								for(int time = 0; time < dayLength; time+=iteration){
									int doneIndex = donePatients(patients);
									sortPatients(patients, doneIndex);
									for(int i = 0; i < patients.length; i++){
										Patient patient = patients[i];
										int[] needs = patient.getNeeds();
										if(patient.isDone()) continue;
										for(int j = 8; j <= 9; j++){
											if(needs[j] != 0){
												boolean made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
												if(made){
													break;
												}
											}

										}
									}
								}

								//book the psychiatrist
								for(int time = 0; time < dayLength; time+=iteration){
									int doneIndex = donePatients(patients);
									sortPatients(patients, doneIndex);
									for(int i = 0; i < patients.length; i++){
										Patient patient = patients[i];
										int[] needs = patient.getNeeds();
										if(patient.isDone()) continue;
										for(int j = 12; j <= 13; j++){
											if(needs[j] != 0){
												boolean made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
												if(made){
													break;
												}
											}

										}
									}
								}


								//deal with the rest of the cases
								int doneIndex = 0;
								for(int time = 0; time < dayLength; time+=iteration){ //for loop (Minutes)
									RandomizeArray(patients);
									doneIndex = donePatients(patients);
									sortPatients(patients, doneIndex);
									int max = 0;
									if(doneIndex+10 >= patients.length) max = patients.length;
									else max = doneIndex+10;
									//RandomizeArray(doctors);
									//RandomizeArray(rooms);
									for(int i = 0; i < max; i++){ //for loop (Patients)

										Patient patient = patients[i];
										if(patient.isDone()) continue;
										int[] needs = patient.getNeeds();
										for(int j = 0; j < 8; j++){ //for loop (Needs)
											if(needs[j] != 0){
												boolean made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
												if(made) break; //cannot address two needs at the same time
											}
										}	
									}

									update(patients, rooms, doctors);
								}

								finish(patients, doctors, rooms);
								if(doneIndex == patients.length && Doctor.totalCost(doctors) < minCost){
									optDoctors = doctors;
									optPatients = patients;
									optRooms = rooms;
									minCost = Doctor.totalCost(doctors);
								}

							}
						}

					}

				}
			}
		}

		if(optDoctors==null){
			System.out.println("Cannot Get Through All Patients");
			System.exit(0);
		}
		///**
		for(Doctor d:optDoctors){
			System.out.println(d.getType()+":"+d.wastedTime());
		}
		//*/
		System.out.printf("Average Patient Wait Time: %.2f",Patient.totalStartWaitTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Maximum Patient Wait Time: "+Patient.maxStartWaitTime(optPatients));
		System.out.printf("Average Patient In-Out Time: %.2f",Patient.totalInOutTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Maximum Patient In-Out Time: "+Patient.maxInOutTime(optPatients));
		System.out.printf("Average Patient Lobby Time: %.2f",Patient.totalLobbyTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Total Doctor Wasted Time: "+Doctor.totalWastedTime(optDoctors));

		PrintWriter patientsOut = new PrintWriter(new BufferedWriter(new FileWriter("patients.out")));
		PrintWriter doctorsOut = new PrintWriter(new BufferedWriter(new FileWriter("doctors.out")));
		PrintWriter roomsOut = new PrintWriter(new BufferedWriter(new FileWriter("rooms.out")));

		patientsOut.print("\t");
		for(int j = 0; j < optPatients.length; j++){
			patientsOut.print(optPatients[j].getCaseType()+ "\t");
		}
		patientsOut.println();
		for(int j = 0; j < dayLength; j++){
			patientsOut.print(j+"\t");
			for(int i = 0; i < optPatients.length; i++){
				Patient p = optPatients[i];

				int[] occupied = p.getOccupied();
				if(occupied[j] != 0)
					patientsOut.print((i+1)*occupied[j]+"\t");
				else
					patientsOut.print(" \t");
			}
			patientsOut.println();
		}


		doctorsOut.print("\t");
		for(int j = 0; j < optDoctors.length; j++){
			doctorsOut.print(optDoctors[j].getType()+ "\t");
		}
		doctorsOut.println();
		for(int j = 0; j < dayLength; j++){
			doctorsOut.print(j+"\t");
			for(int i = 0; i < optDoctors.length; i++){
				Doctor d = optDoctors[i];
				int[] occupied = d.getOccupied();
				if(occupied[j] != 0)
					doctorsOut.print((i+1)*occupied[j]+"\t");
				else
					doctorsOut.print(" \t");
			}
			doctorsOut.println();
		}

		roomsOut.print("\t");
		for(int j = 0; j < optRooms.length; j++){
			roomsOut.print(optRooms[j].getType()+ "\t");
		}
		roomsOut.println();
		
		
		for(int j = 0; j < dayLength; j++){
			roomsOut.print(j+"\t");
			for(int i = 0; i < optRooms.length; i++){
				Room r = optRooms[i];

				int[] occupied = r.getOccupied();
				if(occupied[j] != 0)
					roomsOut.print((i+1)*occupied[j]+"\t");
				else
					roomsOut.print(" \t");
			}
			roomsOut.println();
		}


		patientsOut.close();
		doctorsOut.close();
		roomsOut.close();

	}

	private static void finish(Patient[] patients, Doctor[] doctors, Room[] rooms) {
		for(Patient p: patients){
			p.finish();
		}
		for(Doctor d: doctors){
			d.finish();
		}
		for(Room r: rooms){
			r.finish();
		}
		
	}

	private static Object[] RandomizeArray(Object[] array){
		Random rgen = new Random();  // Random number generator			

		for (int i=0; i<array.length; i++) {
			int randomPosition = rgen.nextInt(array.length);
			Object temp = array[i];
			array[i] = array[randomPosition];
			array[randomPosition] = temp;
		}

		return array;
	}

	private static int donePatients(Patient[] patients) {
		int swapIndex = 0;
		for(int i = 0; i < patients.length; i++){
			if(patients[i].isDone()) {
				Patient p = patients[i];
				patients[i] = patients[swapIndex];
				patients[swapIndex] = p;
				swapIndex++;
			}
		}

		return swapIndex;
	}

	private static void sortPatients(Patient[] patients, int doneIndex) {
		int swapIndex = doneIndex;
		for(int i = doneIndex; i < patients.length; i++){
			if(patients[i].hasStarted()) {
				Patient p = patients[i];
				patients[i] = patients[swapIndex];
				patients[swapIndex] = p;
				swapIndex++;
			}
		}
	}

	private static void setUp(Patient patient, Doctor doctor, Room room, int time, int category) {
		patient.addTime(time, category, doctor, room);
		doctor.addTime(time, category, room);
		room.addTime(time, category);

	}

	private static void update(Patient[] patients, Room[] rooms, Doctor[] doctors) {

		for(Patient p:patients){
			p.update();
		}
		for(Doctor d:doctors){
			d.update();
		}
		for(Room r:rooms){
			r.update();
		}


	}

	private static boolean makeMatch(Patient patient, Doctor[] doctors, Room[] rooms, int[] lengths, int time, int j, int dayLength){
		int doc = -1;
		int room = -1;
		for(int t = time; t < time+lengths[j]; t++){
			if(t >= dayLength) break;
			if(patient.isBusy(t)){
				return false;
			}
		}
		for(int k = 0; k < doctors.length; k++){ //for loop (Doctors)
			Doctor d = doctors[k];
			if(!d.getTypes()[j]) continue;
			boolean isDocBusy = false;
			for(int t = time; t < time+lengths[j]; t++){
				if(t >= dayLength) break;
				if(d.isBusy(t)){
					isDocBusy = true;
					break;
				}
			}
			if(!isDocBusy){
				doc = k;
				break;
			}
		}
		if(doc != -1){
			for(int k = 0; k < rooms.length; k++){ //for loop (Rooms)
				Room r = rooms[k];
				if(!r.getTypes()[j]) continue;
				boolean isRoomBusy = false;
				for(int t = time; t < time+lengths[j]; t++){
					if(t >= dayLength) break;
					if(r.isBusy(t)){
						isRoomBusy = true;
						break;
					}
				}
				if(!isRoomBusy){
					room = k;
					break;
				}
			}
			if(room != -1){
				//room and doc are available
				if(j % 2 == 1){ //non-MA
					if(lengths[j-1] != 0 && patient.hasSeenMA(j)){ //MA Seen
						setUp(patient, doctors[doc], rooms[room], time, j);
						return true;
					}
					else if(lengths[j-1] == 0){ //no-MA required
						setUp(patient, doctors[doc], rooms[room], time, j);
						return true;
					}
					//Otherwise, can't help you
				}
				else{ //MA
					setUp(patient, doctors[doc], rooms[room], time, j);
					return true;
				}
			}
		}

		return false;
	}

}
