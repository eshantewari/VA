package model1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import model1.Doctor;
import model1.Patient;
import model1.Room;

/**
 * 
 * @author Eshan Tewari, for Infinite Computer Solutions
 *
 */

public class Simulation {


	public static void main(String[] args) throws IOException {
		FileInputStream file = new FileInputStream(new File("Modeling_Sheet.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet inputs = workbook.getSheetAt(1);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		Iterator<Row> rowIterator = inputs.iterator();
		Iterator<Cell> cellIterator = null;
		Row row = null;

		int[] lengths = new int[21];


		rowIterator.next();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		for(int i = 0; i < lengths.length; i++){
			lengths[i] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		}


		rowIterator.next();

		row = rowIterator.next();
		cellIterator = row.cellIterator();

		cellIterator.next();

		int checkInLength = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		row = rowIterator.next();
		cellIterator = row.cellIterator();

		cellIterator.next();

		int checkOutLength = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		//Number of Rooms

		row = rowIterator.next();
		cellIterator = row.cellIterator();

		cellIterator.next();

		int roomTriage = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int roomGM = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int roomP = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int roomA = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		//Doctors
		int[] rangeMA = new int[2];
		int[] rangeNP = new int[2];
		int[] rangeA = new int[2];
		int[] rangeN = new int[2];
		int[] rangeP = new int[2];
		int[] costs = new int[5];

		rowIterator.next();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		rangeMA[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		rangeMA[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		costs[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		rangeNP[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		rangeNP[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		costs[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		rangeA[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		rangeA[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		costs[2] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		rangeN[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		rangeN[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		costs[3] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		rangeP[0] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		rangeP[1] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		costs[4] = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		//Cases
		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		double genMed = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();
		double A = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();
		double PN = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		//Sub-Cases
		rowIterator.next();
		rowIterator.next();

		//Case 1
		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();
		double GMcase1 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double Acase1 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double PNcase1 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		//Case 2
		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();
		double GMcase2 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double Acase2 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double PNcase2 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		//Case 3
		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();
		double GMcase3 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double Acase3 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();
		double PNcase3 = evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		//Remaining Variables
		row = rowIterator.next();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int dayLength = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int apptInterval = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();


		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int cases = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		row = rowIterator.next();
		cellIterator = row.cellIterator();
		cellIterator.next();

		int sanitation = (int) evaluator.evaluateInCell(cellIterator.next()).getNumericCellValue();

		cases+=3;
		for(int i = 0; i < lengths.length; i++){
			if(i % 3 == 0) continue;
			else if(lengths[i] != 0) lengths[i]+=sanitation;
		}

		int iteration = 1;

		int minCasesLeft = Integer.MAX_VALUE;
		double minCost = Double.MAX_VALUE;
		Patient[] optPatients = null;
		Room[] optRooms = null;
		Doctor[] optDoctors = null;
		int[] optLobbyOccupancy = null;
		boolean success = false;
		boolean flag = true;
		for(int numMA = rangeMA[0]; numMA <= rangeMA[1]; numMA++){
			for(int numNP = rangeNP[0]; numNP <= rangeNP[1]; numNP++){
				for(int numA = rangeA[0]; numA <= rangeA[1]; numA++){
					for(int numN = rangeN[0]; numN <= rangeN[1]; numN++){
						for(int numP = rangeP[0]; numP <= rangeP[1]; numP++){
							for(int maxWaiting = 0; maxWaiting < cases; maxWaiting++){
								for(int a = 0; a < 50; a++){
									Patient[] patients = null;
									Room[] rooms = null;
									Doctor[] doctors = null;
									int[] lobbyOccupancy = new int[dayLength];
									ArrayList<Patient> tempPatients = new ArrayList<Patient>();
									int index = 0;
									for(int i = 0; i < (int)(cases*PN*PNcase1); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.PN1, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}
									for(int i = 0; i < (int)(cases*PN*PNcase2); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.PN2, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}
									for(int i = 0; i < (int)(cases*genMed*GMcase1); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM1, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}
									for(int i = 0; i < (int)(cases*genMed*GMcase2); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM2, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}
									for(int i = 0; i < (int)(cases*genMed*GMcase3); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.GM3, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}

									for(int i = 0; i < (int)(cases*A*Acase1); i++){
										tempPatients.add(new Patient(dayLength, apptInterval, Patient.CaseType.A1, lengths, checkInLength, checkOutLength, i+1, index));
										index++;
									}


									patients = tempPatients.toArray(new Patient[tempPatients.size()]);

									if(flag){
										System.out.println("Number of DBQs: "+Patient.numDBQs(patients));
										System.out.println("Total Diagnostic Time: "+Patient.totalDBQtime(patients));
										flag = false;
									}

									ArrayList<Room> tempRooms = new ArrayList<Room>();
									tempRooms.add(new Room(dayLength, Room.RoomType.PLANNING, lengths, 0));
									for(int i = 0; i < roomTriage; i++){
										tempRooms.add(new Room(dayLength, Room.RoomType.TRIAGE, lengths, i+1));
									}		
									for(int i = 0; i < roomGM; i++){
										tempRooms.add(new Room(dayLength, Room.RoomType.GM, lengths, i+1));
									}
									for(int i = 0; i < roomA; i++){
										tempRooms.add(new Room(dayLength, Room.RoomType.A, lengths, i+1));
									}
									for(int i = 0; i < roomP; i++){
										tempRooms.add(new Room(dayLength, Room.RoomType.P, lengths, i+1));
									}

									rooms = tempRooms.toArray(new Room[tempRooms.size()]);

									ArrayList<Doctor> tempDoctors = new ArrayList<Doctor>();	
									for(int i = 0; i < numMA; i++){
										tempDoctors.add(new Doctor(costs[0], dayLength, Doctor.DocType.MA, lengths, checkInLength, checkOutLength, i+1));
									}
									for(int i = 0; i < numNP; i++){
										tempDoctors.add(new Doctor(costs[1], dayLength, Doctor.DocType.NP, lengths, checkInLength, checkOutLength, i+1));
									}
									for(int i = 0; i < numA; i++){
										tempDoctors.add(new Doctor(costs[2], dayLength, Doctor.DocType.A, lengths, checkInLength, checkOutLength, i+1));
									}	
									for(int i = 0; i < numN; i++){
										tempDoctors.add(new Doctor(costs[3], dayLength, Doctor.DocType.N, lengths, checkInLength, checkOutLength, i+1));
									}
									for(int i = 0; i < numP; i++){
										tempDoctors.add(new Doctor(costs[4], dayLength, Doctor.DocType.P, lengths, checkInLength, checkOutLength, i+1));
									}


									doctors = tempDoctors.toArray(new Doctor[tempDoctors.size()]);

									int doneIndex = 0;
									sortPatients(patients, doneIndex);

									//book the audiologist
									for(int time = 0; time < dayLength; time+=iteration){
										for(int i = 0; i < patients.length; i++){
											doneIndex = donePatients(patients);
											sortPatients(patients, doneIndex);
											Patient patient = patients[i];
											if(Patient.numWaiting(time, patients) >= maxWaiting && !patient.hasStarted()) continue; //too many people waiting: get them done first
											int[] needs = patient.getNeeds();
											if(patient.isDone()) continue;
											for(int j =13; j <= 14; j++){
												if(j%3==0) continue; //Pre-MA
												if(needs[j] != 0){
													boolean made = false;
													if(j%3==2){
														made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
													}
													else{
														if(!patient.isMAdone())
															made = makeMAMatch(patient, doctors, rooms, lengths, time, dayLength);
													}
													if(made) break; //cannot address two needs at the same time
												}


											}
											update(patients, rooms, doctors, time);
										}

									}

									//book the neurologist and psychiatrist
									for(int time = 0; time < dayLength; time+=iteration){
										for(int i = 0; i < patients.length; i++){
											doneIndex = donePatients(patients);
											sortPatients(patients, doneIndex);

											Patient patient = patients[i];
											if(Patient.numWaiting(time, patients) >= maxWaiting && !patient.hasStarted()) continue; //too many people waiting: get them done first
											int[] needs = patient.getNeeds();
											if(patient.isDone()) continue;
											for(int j = 16; j <= 20; j++){
												if(j%3==0) continue; //Pre-MA
												if(needs[j] != 0){
													boolean made = false;
													if(j%3==2){
														made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
													}
													else{
														if(!patient.isMAdone())
															made = makeMAMatch(patient, doctors, rooms, lengths, time, dayLength);
													}
													if(made) break; //cannot address two needs at the same time
												}
											}
											update(patients, rooms, doctors, time);
										}

									}

									//book everything else
									//at this point, we want patients to get done; hence, the outer patient loop
									for(int time = 0; time < dayLength; time+=iteration){ //for loop (Minutes)
										for(int i = 0; i < patients.length; i++){ //for loop (Patients)
											doneIndex = donePatients(patients);
											sortPatients(patients, doneIndex);
											Patient patient = patients[i];
											if(Patient.numWaiting(time, patients) >= maxWaiting && !patient.hasStarted()) continue; //too many people waiting: get them done first
											if(patient.isDone()) continue;
											int[] needs = patient.getNeeds();
											for(int j = 0; j < needs.length; j++){ //for loop (Needs)
												if(j%3==0) continue; //Pre-MA
												if(needs[j] != 0){
													boolean made = false;
													if(j%3==2){
														made = makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
													}
													else{
														if(!patient.isMAdone())
															made = makeMAMatch(patient, doctors, rooms, lengths, time, dayLength);
													}
													if(made) break; //cannot address two needs at the same time
												}
											}	
											update(patients, rooms, doctors, time);
										}


									}

									//book the PMA (Pre-MA)
									for(int time = 0; time < dayLength; time+=iteration){
										for(int i = 0; i < patients.length; i++){
											Patient patient = patients[i];
											sortPatients(patients, doneIndex);
											int[] needs = patient.getNeeds();
											for(int j = 0; j < lengths.length; j+=3){
												if(needs[j] != 0){
													makeMatch(patient, doctors, rooms, lengths, time, j, dayLength);
													//if multiple MA's are available, they can take care of multiple Pre-MAs of the same patient
												}
											}
										}
										update(patients, rooms, doctors, time);
									}

									finish(patients, doctors, rooms);
									boolean completelyDone = completelyDone(patients);

									for(int time = 0; time < dayLength; time++){
										for(Patient p:patients){
											if(p.getDoneTime() >= time && p.getStartTime() <= time && !p.isBusy(time)){
												lobbyOccupancy[time] ++;
											}
										}
									}

									if(completelyDone && Doctor.totalWastedTime(doctors)*Patient.totalLobbyTime(patients) < minCost){
										ArrayList<Doctor> temp = new ArrayList<Doctor>();
										for(Doctor d: doctors){
											if(d.getStartTime() >= 0) temp.add(d);
										}
										optDoctors = temp.toArray(new Doctor[temp.size()]);
										optPatients = patients;
										optRooms = rooms;
										optLobbyOccupancy = lobbyOccupancy;
										minCost = Doctor.totalCost(doctors);
										success = true;
									}

									else if(Patient.remainingCases(patients) < minCasesLeft){
										optDoctors = doctors;
										optPatients = patients;
										optRooms = rooms;
										optLobbyOccupancy = lobbyOccupancy;
									}

								}
							}

						}

					}
				}
			}
		}

		//re-order patients to original ordering
		for(int i = 0; i < optPatients.length; i++){
			int minIndex = i;
			int min = optPatients[i].getPlace();
			for(int j = i; j < optPatients.length; j++){
				if(optPatients[j].getPlace() < min){
					min = optPatients[j].getPlace();
					minIndex = j;
				}
			}

			Patient temp = optPatients[minIndex];
			optPatients[minIndex] = optPatients[i];
			optPatients[i] = temp;


		}

		/**
		System.out.printf("Average Patient Wait Time: %.2f",Patient.totalStartWaitTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Maximum Patient Wait Time: "+Patient.maxStartWaitTime(optPatients));
		System.out.printf("Average Patient In-Out Time: %.2f",Patient.totalInOutTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Maximum Patient In-Out Time: "+Patient.maxInOutTime(optPatients));
		System.out.printf("Average Patient Lobby Time: %.2f",Patient.totalLobbyTime(optPatients)*1.0/optPatients.length);
		System.out.println();
		System.out.println("Total Doctor Wasted Time: "+Doctor.totalWastedTime(optDoctors));
		 */

		if(!success){
			System.out.println("Could not get through all patients.");
		}

		if(workbook.getSheet("RemainingDBQs") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("RemainingDBQs")));
		}

		if(workbook.getSheet("Doctors") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("Doctors")));
		}

		if(workbook.getSheet("Patients") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("Patients")));
		}

		if(workbook.getSheet("Rooms") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("Rooms")));
		}

		if(workbook.getSheet("Lobby") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("Lobby")));
		}

		if(workbook.getSheet("InOutTimes") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("InOutTimes")));
		}

		if(workbook.getSheet("StartWaitTimes") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("StartWaitTimes")));
		}

		if(workbook.getSheet("LobbyTimes") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("LobbyTimes")));
		}

		if(workbook.getSheet("DoctorWastage") != null){
			workbook.removeSheetAt(workbook.getSheetIndex(workbook.getSheet("DoctorWastage")));
		}


		XSSFSheet remDBQs = workbook.createSheet("RemainingDBQs");
		ArrayList<Object[]> data = new ArrayList<Object[]>();

		if(!success){
			data.add(new Object[] {"Unsuccessful Run","PMA1","MA1","NP1","PMA2","MA2","NP2","PMA3","MA3","NP3","PMA4","MA4","NP4","PAMA","AMA","A","PNMA","NMA","N","PPMA","PMA","P"});
		}
		else{
			data.add(new Object[] {"Successful Run","PMA1","MA1","NP1","PMA2","MA2","NP2","PMA3","MA3","NP3","PMA4","MA4","NP4","PAMA","AMA","A","PNMA","NMA","N","PPMA","PMA","P"});
		}

		for(int i = 0; i < optPatients.length; i++){
			int[] needs = optPatients[i].getNeeds();
			Object[] o = new Object[needs.length+1];
			o[0] = optPatients[i].getCaseType().toString();
			for(int j = 0; j < needs.length; j++){
				o[j+1] = new Integer(needs[j]);
			}
			data.add(o);
		}
		writeToSheet(remDBQs, "RemDQBs", optPatients[0].getNeeds().length+1, data);




		XSSFSheet lobby = workbook.createSheet("Lobby");
		data = new ArrayList<Object[]>();
		data.add(new Object[] {"Time", "Lobby Occupancy"});
		for(int time = 0; time < dayLength; time++){
			data.add(new Object[] {time, optLobbyOccupancy[time]});
		}

		writeToSheet(lobby, "LobbyOccupancy", 2, data);

		XSSFSheet patients = workbook.createSheet("Patients");
		data = new ArrayList<Object[]>();
		Object[] o = new Object[optPatients.length+1];
		o[0] = "Times";
		for(int j = 0; j < optPatients.length; j++){
			o[j+1] = optPatients[j].toString();
		}
		data.add(o);

		for(int j = 0; j < dayLength; j++){
			o = new Object[optPatients.length+1];
			o[0] = j;
			for(int i = 0; i < optPatients.length; i++){
				Patient p = optPatients[i];

				int[] occupied = p.getOccupied();
				if(occupied[j] != 0)
					o[i+1] = (i+1)*occupied[j];
				else
					o[i+1] = "";
			}
			data.add(o);
		}

		writeToSheet(patients, "Patients", optPatients.length+1, data);

		XSSFSheet doctors = workbook.createSheet("Doctors");
		data = new ArrayList<Object[]>();
		o = new Object[optDoctors.length+1];
		o[0] = "Times";
		for(int j = 0; j < optDoctors.length; j++){
			o[j+1] = optDoctors[j].toString();
		}
		data.add(o);
		for(int j = 0; j < dayLength; j++){
			o = new Object[optDoctors.length+1];
			o[0] = j;
			for(int i = 0; i < optDoctors.length; i++){
				Doctor d = optDoctors[i];

				int[] occupied = d.getOccupied();
				if(occupied[j] != 0)
					o[i+1] = (i+1)*occupied[j];
				else
					o[i+1] = "";
			}
			data.add(o);
		}

		writeToSheet(doctors, "Doctors", optDoctors.length+1, data);

		XSSFSheet rooms = workbook.createSheet("Rooms");
		data = new ArrayList<Object[]>();
		o = new Object[optRooms.length+1];
		o[0] = "Times";
		for(int j = 0; j < optRooms.length; j++){
			o[j+1] = optRooms[j].toString();
		}
		data.add(o);
		for(int j = 0; j < dayLength; j++){
			o = new Object[optRooms.length+1];
			o[0] = j;
			for(int i = 0; i < optRooms.length; i++){
				Room r = optRooms[i];

				int[] occupied = r.getOccupied();
				if(occupied[j] != 0)
					o[i+1] = (i+1)*occupied[j];
				else
					o[i+1] = "";
			}
			data.add(o);
		}

		writeToSheet(rooms, "Rooms", optRooms.length+1, data);


		ArrayList<Integer> startWaitBins = new ArrayList<Integer>();
		ArrayList<Integer> startWaitFreq = new ArrayList<Integer>();


		ArrayList<Integer> lobbyTimeBins = new ArrayList<Integer>();
		ArrayList<Integer> lobbyTimeFreq = new ArrayList<Integer>();

		ArrayList<Integer> lobbyTimePerCaseBins = new ArrayList<Integer>();
		ArrayList<Integer> lobbyTimePerCaseFreq = new ArrayList<Integer>();

		ArrayList<Integer> inOutTimesBins = new ArrayList<Integer>();
		ArrayList<Integer> inOutTimesFreq = new ArrayList<Integer>();

		ArrayList<Integer> inOutTimesPerCaseBins = new ArrayList<Integer>();
		ArrayList<Integer> inOutTimesPerCaseFreq = new ArrayList<Integer>();


		ArrayList<Integer> doctorWasteBins = new ArrayList<Integer>();
		ArrayList<Integer> doctorWasteFreq = new ArrayList<Integer>();

		int minStartWait = Integer.MAX_VALUE;
		int minLobbyTime = Integer.MAX_VALUE;
		int minInOutTime = Integer.MAX_VALUE;
		int minLobbyTimePerCase = Integer.MAX_VALUE;
		int minInOutTimePerCase = Integer.MAX_VALUE;

		for(Patient p:optPatients){
			if(p.startWaitTime() < minStartWait){
				minStartWait = p.startWaitTime();
			}
			if(p.lobbyTime() < minLobbyTime){
				minLobbyTime = p.lobbyTime();
			}
			if(p.inOutTime() < minInOutTime){
				minInOutTime = p.inOutTime();
			}
			if(p.lobbyTimePerCase() < minLobbyTimePerCase){
				minLobbyTimePerCase = p.lobbyTimePerCase();
			}
			if(p.inOutTimePerCase() < minInOutTimePerCase){
				minInOutTimePerCase = p.inOutTimePerCase();
			}
		}

		int minDoctorWaste = Integer.MAX_VALUE;
		for(Doctor d:optDoctors){
			if(d.wastedTime() < minDoctorWaste){
				minDoctorWaste = d.wastedTime();
			}
		}


		startWaitBins.add(minStartWait/5*5);
		startWaitFreq.add(0);
		lobbyTimeBins.add(minLobbyTime/5*5);
		lobbyTimeFreq.add(0);
		inOutTimesBins.add(minInOutTime/5*5);
		inOutTimesFreq.add(0);
		inOutTimesPerCaseBins.add(minInOutTimePerCase/5*5);
		inOutTimesPerCaseFreq.add(0);
		lobbyTimePerCaseBins.add(minLobbyTimePerCase/5*5);
		lobbyTimePerCaseFreq.add(0);


		doctorWasteBins.add(minDoctorWaste/5*5);
		doctorWasteFreq.add(0);

		for(Patient p:optPatients){
			int startWaitTime = p.startWaitTime();
			while(startWaitTime > startWaitBins.get(startWaitBins.size()-1)){
				startWaitBins.add(startWaitBins.get(startWaitBins.size()-1)+5);
				startWaitFreq.add(0);
			}

			for(int i = 1; i < startWaitBins.size(); i++){
				if(startWaitTime <= startWaitBins.get(i)){
					startWaitFreq.set(i-1, startWaitFreq.get(i-1)+1);
					break;
				}
			}

			int lobbyTime = p.lobbyTime();
			while(lobbyTime > lobbyTimeBins.get(lobbyTimeBins.size()-1)){
				lobbyTimeBins.add(lobbyTimeBins.get(lobbyTimeBins.size()-1)+5);
				lobbyTimeFreq.add(0);
			}
			for(int i = 1; i < lobbyTimeBins.size(); i++){
				if(lobbyTime <= lobbyTimeBins.get(i)){
					lobbyTimeFreq.set(i-1, lobbyTimeFreq.get(i-1)+1);
					break;
				}
			}

			int lobbyTimePerCase = p.lobbyTimePerCase();
			while(lobbyTimePerCase > lobbyTimePerCaseBins.get(lobbyTimePerCaseBins.size()-1)){
				lobbyTimePerCaseBins.add(lobbyTimePerCaseBins.get(lobbyTimePerCaseBins.size()-1)+5);
				lobbyTimePerCaseFreq.add(0);
			}
			for(int i = 1; i < lobbyTimePerCaseBins.size(); i++){
				if(lobbyTimePerCase <= lobbyTimePerCaseBins.get(i)){
					lobbyTimePerCaseFreq.set(i-1, lobbyTimePerCaseFreq.get(i-1)+1);
					break;
				}
			}



			int inOutTime = p.inOutTime();
			while(inOutTime > inOutTimesBins.get(inOutTimesBins.size()-1)){
				inOutTimesBins.add(inOutTimesBins.get(inOutTimesBins.size()-1)+20);
				inOutTimesFreq.add(0);
			}
			for(int i = 1; i < inOutTimesBins.size(); i++){
				if(inOutTime <= inOutTimesBins.get(i)){
					inOutTimesFreq.set(i-1, inOutTimesFreq.get(i-1)+1);
					break;
				}
			}

			int inOutTimePerCase = p.inOutTimePerCase();
			while(inOutTimePerCase > inOutTimesPerCaseBins.get(inOutTimesPerCaseBins.size()-1)){
				inOutTimesPerCaseBins.add(inOutTimesPerCaseBins.get(inOutTimesPerCaseBins.size()-1)+10);
				inOutTimesPerCaseFreq.add(0);
			}
			for(int i = 1; i < inOutTimesPerCaseBins.size(); i++){
				if(inOutTimePerCase <= inOutTimesPerCaseBins.get(i)){
					inOutTimesPerCaseFreq.set(i-1, inOutTimesPerCaseFreq.get(i-1)+1);
					break;
				}
			}



		}

		for(Doctor d:optDoctors){
			int wastedTime = d.wastedTime();
			while(wastedTime > doctorWasteBins.get(doctorWasteBins.size()-1)){
				doctorWasteBins.add(doctorWasteBins.get(doctorWasteBins.size()-1)+5);
				doctorWasteFreq.add(0);
			}
			for(int i = 1; i < doctorWasteBins.size(); i++){
				if(wastedTime <= doctorWasteBins.get(i)){
					doctorWasteFreq.set(i-1, doctorWasteFreq.get(i-1)+1);
					break;
				}
			}
		}


		XSSFSheet inOutTimes = workbook.createSheet("InOutTimes");
		data = new ArrayList<Object[]>();
		data.add(new Object[] {"In Out Time Bins", "In Out Time Frequencies"});
		for(int i = 0; i < inOutTimesBins.size(); i++){
			data.add(new Object[] {inOutTimesBins.get(i), inOutTimesFreq.get(i)});
		}

		writeToSheet(inOutTimes, "InOutTimes", 2, data);

		XSSFSheet lobbyTimes = workbook.createSheet("LobbyTimes");
		data = new ArrayList<Object[]>();
		data.add(new Object[] {"Total Lobby Wait Time Bins", "Total Lobby Wait Time Frequencies"});
		for(int i = 0; i < lobbyTimeBins.size(); i++){
			data.add(new Object[] {lobbyTimeBins.get(i), lobbyTimeFreq.get(i)});
		}

		writeToSheet(lobbyTimes, "LobbyTimes", 2, data);

		XSSFSheet startWaitTimes = workbook.createSheet("StartWaitTimes");
		data = new ArrayList<Object[]>();
		data.add(new Object[] {"Start Wait Time Bins", "Start Wait Time Frequencies"});
		for(int i = 0; i < startWaitBins.size(); i++){
			data.add(new Object[] {startWaitBins.get(i), startWaitFreq.get(i)});
		}

		writeToSheet(startWaitTimes, "StartWaitTimes", 2, data);

		XSSFSheet doctorWastage = workbook.createSheet("DoctorWastage");
		data = new ArrayList<Object[]>();
		data.add(new Object[] {"Doctor Wastage Bins", "Doctor Wastage Frequencies"});
		for(int i = 0; i < doctorWasteBins.size(); i++){
			data.add(new Object[] {doctorWasteBins.get(i), doctorWasteFreq.get(i)});
		}

		writeToSheet(doctorWastage, "DoctorWastage", 2, data);


		FileOutputStream fileOut = new FileOutputStream("Modeling_Sheet.xlsx");
		workbook.write(fileOut);
		workbook.close();
		fileOut.close();


	}

	/**
	 * Determines whether or not all patients have gotten each of their diagnostic needs addressed.
	 * @param patients
	 * @return
	 */
	private static boolean completelyDone(Patient[] patients) {
		for(Patient p:patients){
			if(!p.isCompletelyDone()) return false;
		}
		return true;
	}

	/**
	 * Called when iterations through the day are completed.  Sets final state variables.
	 * @param patients
	 * @param doctors
	 * @param rooms
	 */
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

	/**
	 * Randomize the order of elements within an array.
	 * @param array
	 * @return
	 */
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

	/**
	 * Determine patients which are done and place them in a location that will be skipped in iteration
	 * @param patients
	 * @return
	 */

	private static int donePatients(Patient[] patients) {
		RandomizeArray(patients);
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

		//put all patients who have started at the top of the queue
		for(int i = doneIndex; i < patients.length; i++){
			if(patients[i].hasStarted()) {
				Patient p = patients[i];
				patients[i] = patients[swapIndex];
				patients[swapIndex] = p;
				swapIndex++;
			}
		}

		//sort the patients who have started by the time since their last exam
		for(int i = doneIndex; i < swapIndex; i++){
			int mostTime = patients[i].getTimeSinceExam();
			int mostTimeIndex = i;
			for(int j = i+1; j < swapIndex; j++){
				if(patients[j].getTimeSinceExam() > mostTime){
					mostTime = patients[j].getTimeSinceExam();
					mostTimeIndex = j;
				}
			}
			Patient temp = patients[mostTimeIndex];
			patients[mostTimeIndex] = patients[i];
			patients[i] = temp;

		}

		//sort the remaining patients by total number of cases left
		for(int i = swapIndex; i < patients.length; i++){
			int mostCases = patients[i].getNumCases();
			int mostCasesIndex = i;
			for(int j = i+1; j < patients.length; j++){
				if(patients[j].getNumCases() > mostCases){
					mostCases = patients[j].getNumCases();
					mostCasesIndex = j;
				}
			}
			Patient temp = patients[mostCasesIndex];
			patients[mostCasesIndex] = patients[i];
			patients[i] = temp;

		}
	}

	/**
	 * Once a match has been found, this method is called to carry out the matching process
	 * @param patient
	 * @param doctor
	 * @param room
	 * @param time
	 * @param category
	 * @param lengths
	 */
	private static void setUp(Patient patient, Doctor doctor, Room room, int time, int category, int[] lengths) {

		if(patient.isCheckedIn(time) == 1){
			return; //the patient has started to check in and will start the exam as soon as the check in is finished
		}

		int checkIn = 0;
		if(patient.isCheckedIn(time) == 0){ //not checked in
			checkIn = patient.getCheckInLength();
			for(int i = time; i < checkIn; i++){
				if(patient.isBusy(i) || doctor.isBusy(i)) return;
			}
			patient.checkIn(time);
			doctor.checkIn(time);
		}


		patient.addTime(time+checkIn, category, doctor, room);
		doctor.addTime(time+checkIn, category, room);
		room.addTime(time+checkIn, category);

		if(patient.isDone() && !patient.isCheckedOut()){
			patient.checkOut(time+checkIn+lengths[category]);
			doctor.checkOut(time+checkIn+lengths[category]);
		}

	}

	/**
	 * Update the variables that need to updated on every iteration of time
	 * @param patients
	 * @param rooms
	 * @param doctors
	 * @param time
	 */

	private static void update(Patient[] patients, Room[] rooms, Doctor[] doctors, int time) {

		for(Patient p:patients){
			p.update(time);
		}
		for(Doctor d:doctors){
			d.update();
		}
		for(Room r:rooms){
			r.update();
		}


	}

	/**
	 * Determine whether or not there is a suitable room and doctor for a patient at a given time.  
	 * This method applies to specialists and medical assistant work done in the absence of a patient (the day before).
	 * @param patient
	 * @param doctors
	 * @param rooms
	 * @param lengths
	 * @param time
	 * @param j (the category)
	 * @param dayLength
	 * @return
	 */

	private static boolean makeMatch(Patient patient, Doctor[] doctors, Room[] rooms, int[] lengths, int time, int j, int dayLength){
		int doc = -1;
		int room = -1;
		for(int t = time; t < time+lengths[j]; t++){
			if(t >= dayLength){ 
				return false;
			}
			if(patient.isBusy(t)){
				return false;
			}
		}

		for(int k = 0; k < doctors.length; k++){ //for loop (Doctors)
			Doctor d = doctors[k];
			if(!d.getTypes()[j]) continue;
			boolean isDocBusy = false;
			for(int t = time; t < time+lengths[j]; t++){
				if(t >= dayLength) return false;
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
					if(t >= dayLength) return false;
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
				if(j % 3 == 2){ //specialist
					if(lengths[j-1] != 0 && patient.hasSeenMA(j)){ //MA Seen
						setUp(patient, doctors[doc], rooms[room], time, j, lengths);
						return true;
					}
					else if(lengths[j-1] == 0){ //no-MA required
						setUp(patient, doctors[doc], rooms[room], time, j, lengths);
						return true;
					}
					//Otherwise, can't help you
				}
				else{ //MA or Pre-MA
					setUp(patient, doctors[doc], rooms[room], time, j, lengths);
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * Determine whether or not there is a suitable room and doctor for a patient at a given time.  
	 * This method applies to medical assistants. 
	 * Once a patient begins his medical assistant examinations, that patient will finish all MA exams before proceeding.
	 * @param patient
	 * @param doctors
	 * @param rooms
	 * @param lengths
	 * @param time
	 * @param dayLength
	 * @return
	 */
	private static boolean makeMAMatch(Patient patient, Doctor[] doctors, Room[] rooms, int[] lengths, int time, int dayLength) {
		int[] occupied = patient.getOccupied();
		int startTime = time;
		int endTime = 0;
		int[] needs = patient.getNeeds();
		boolean MADone = false;
		for(int t = time; t < dayLength; t++){
			boolean made = false;
			for(int i = 1; i < needs.length; i+=3){ //All MA's
				if(needs[i] != 0){
					made = makeMatch(patient, doctors, rooms, lengths, t, i, dayLength);
					if(made) break;
				}
			}
			if(!made && t == time){
				return false; //can't do now, try again later. Have not started, not a big deal.
			}

			//We've started, so we have to keep going
			//Check if we're done
			MADone = true;
			for(int i = 1; i < needs.length; i+=3){ //All MA's
				if(needs[i] != 0){
					MADone = false;
				}
			}
			if(MADone){
				endTime = time;
				break;
			}
		}

		if(MADone == false){
			return false;
		}


		patient.setMAdone(true);
		for(int t = startTime; t < endTime; t++){
			occupied[t] = 1;
		}
		patient.setOccupied(occupied);




		return true;
	}


	private static void writeToSheet(XSSFSheet sheet, String title, int numCols, ArrayList<Object[]> data) {
		/* Create an object of type XSSFTable */
		XSSFTable my_table = sheet.createTable();

		/* get CTTable object*/
		CTTable cttable = my_table.getCTTable();

		/* Let us define the required Style for the table */    
		CTTableStyleInfo table_style = cttable.addNewTableStyleInfo();
		table_style.setName("TableStyleMedium9");   

		/* Set Table Style Options */
		table_style.setShowColumnStripes(false); //showColumnStripes=0
		table_style.setShowRowStripes(true); //showRowStripes=1

		/* Define the data range including headers */
		AreaReference my_data_range = new AreaReference(new CellReference(0, 0), new CellReference(data.size()-1, numCols-1));

		/* Set Range to the Table */
		cttable.setRef(my_data_range.formatAsString());
		cttable.setDisplayName(title);      /* this is the display name of the table */
		cttable.setName(title);    /* This maps to "displayName" attribute in <table>, OOXML */            
		cttable.setId(1L); //id attribute against table as long value

		CTTableColumns columns = cttable.addNewTableColumns();
		columns.setCount(numCols); //define number of columns

		/* Define Header Information for the Table */
		Object[] headers = data.get(0);
		for (int i = 0; i < numCols; i++)
		{
			CTTableColumn column = columns.addNewTableColumn();
			column.setName(headers[i].toString());   

			column.setId(i+1);

		}

		int rownum = 0;
		/* Add Table Data */
		for (Object[] objArr : data){ //populate all the rows
			/* Create a Row */
			XSSFRow row = sheet.createRow(rownum);
			int cellnum = 0;
			for (Object obj : objArr){
				XSSFCell localXSSFCell = row.createCell(cellnum);
				if(obj instanceof String){
					localXSSFCell.setCellValue((String)obj);
				}
				else if(obj instanceof Integer){
					localXSSFCell.setCellValue(new Double(obj.toString()));	
				}


				cellnum++;
			}

			rownum++;
		}
	}
}
