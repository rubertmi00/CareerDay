//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.io.importexport.CSVReader;
import com.atcs.career.logic.Algorithms;

public class Event implements Serializable {

	private static final long serialVersionUID = -7463051683970561540L;
	private static final int minSessionSize = 10; //COME BACK AND CHANGE TO PROPER VALUE
//	public static int startYear = Calendar.getInstance().YEAR;
//	public static int startDay = Calendar.getInstance().DAY_OF_YEAR;
	public static int startDay = 0;
	public static int startYear = 0;
	private int amountOfSessions;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Student> masterStudents = new ArrayList<Student>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String eventName;

	// TESTING
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Event e = new Event("career");
//		Event e = new Event();
		FileHandler.save(e);
//		e = FileHandler.load(CSVReader.getFileLocation(".event"));
//		System.out.println(e.getStudents());
//		System.out.println(e.getRooms());
//		System.out.println(e.getSessions());
		
		
		ArrayList<Student> master = new ArrayList<Student>();
		Algorithms.myBigFatGreekWethod(e.students, master, e.rooms, e.sessions);
		
		
//		for(int i = 0; i < e.sessions.size(); i++){
//		   System.out.println(e.sessions.toString());
//		}		
		
	}

	/**
	 * Creates a new Event from scratch
	 */
	public Event(String name) {
		eventName = name;
		 students = IOUtilities.loadStudentArray(CSVReader.getFileLocation(".csv"));
		 masterStudents = IOUtilities.loadMasterStudentArray(CSVReader.getFileLocation(".csv"));
		 rooms = IOUtilities.loadRoomArray(CSVReader.getFileLocation(".csv"));
		 sessions = IOUtilities.loadSessionArray(CSVReader.getFileLocation(".csv"));
		 amountOfSessions = sessions.size();
		 
		 startYear = students.get(0).getTimeEntered()/1000;
		 startDay = students.get(0).getTimeEntered()%1000;
	}
	
	public Event()
   {
       super();
       sessions = new ArrayList<Session>();
       sessions.add(new Session("Business", "Donald Trump"));
       sessions.add(new Session("Investment", "Warren Buffet"));
       sessions.add(new Session("Military", "James Mattis"));
       sessions.add(new Session("Electrical Engineering", "Elon Musk"));
       sessions.add(new Session("Astronomy", "Albert Einstein"));
       sessions.add( new Session("Criminal Defense", "Robert Shapiro"));
       sessions.add(new Session("Intelligence", "James Comey"));
       sessions.add(new Session("Software Development", "Johnny Ive"));
       
       students = new ArrayList<Student>();
       {
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(this.sessions.get(0));
        sessions.add(this.sessions.get(1));
        sessions.add(this.sessions.get(2));
       
       
       students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
       
       students.add(new Student("Jack", "Black", "ppeter20@pascack.org",sessions, 0));
       students.add(new Student("Eric", "Wang", "ppeter20@pascack.org",sessions, 0));
       students.add(new Student("Jarret", "Bierman", "ppeter20@pascack.org",sessions, 0));
       students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
       students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
       
       rooms = new ArrayList<Room>();
       for(int i = 121; i< 140; i++)
       rooms.add(new Room(i + "", 30));
       
       
       
       }
       
       eventName = "TEST";
       
   }

	public void selectStudentFile() {
		students = IOUtilities.loadStudentArray(CSVReader.getFileLocation(".csv"));
		amountOfSessions = sessions.size();
	}

	public void selectRoomFile() {
		rooms = IOUtilities.loadRoomArray(CSVReader.getFileLocation(".csv"));
	}
	

	public void selectSessionFile() {
		sessions = IOUtilities.loadSessionArray(CSVReader.getFileLocation(".csv"));
		amountOfSessions = sessions.size();
	}

	public int getAmountOfSessions() {
		return amountOfSessions;
	}

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

   public ArrayList<Student> getMasterStudents() {
      return masterStudents;
   }

   public void setMasterStudents(ArrayList<Student> masterStudents) {
      this.masterStudents = masterStudents;
   }

   public static long getSerialversionuid() {
      return serialVersionUID;
   }

   public static int getMinsessionsize() {
      return minSessionSize;
   }

   public void setAmountOfSessions(int amountOfSessions) {
      this.amountOfSessions = amountOfSessions;
   }

   public void setSessions(ArrayList<Session> sessions) {
      this.sessions = sessions;
   }

   public void setStudents(ArrayList<Student> students) {
      this.students = students;
   }

   public void setRooms(ArrayList<Room> rooms) {
      this.rooms = rooms;
   }
}
