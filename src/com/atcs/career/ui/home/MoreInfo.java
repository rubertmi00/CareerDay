//Thomas Varano
//Dec 10, 2018
package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public abstract class MoreInfo {

	public static SideInfoPanel getInfoPanel(GuiListable g, CareerDayGUI master) {
		if (g instanceof Student) return new StudentPanel((Student) g, master);
		if (g instanceof Room) return new RoomPanel((Room) g, master);
		if (g instanceof Session) return new SessionPanel((Session) g, master);
		return null;
	}
	
	static ArrayList<String> getNames(ArrayList<Student> students) {
		ArrayList<String> ret = new ArrayList<String>();
		for (Student s : students)
			ret.add(s.getFullName());
		return ret;
	}

	public static abstract class SideInfoPanel extends JPanel {
		public static final int PREF_W = 230;
		public static final int PREF_H = 600;
		private static final long serialVersionUID = 1L;
		protected CareerDayGUI master;

		public SideInfoPanel(CareerDayGUI master) {
			this.master = master;
			generalSetup();
		}

		public void generalSetup() {
			setFocusable(true);
			this.setBackground(Color.WHITE);
		}
		
		protected byte getPeriod() {
//			return 0;
			return master.getSelectedPeriod();
		}
		
		protected CareerDayGUI getMaster() {
			return master;
		}
		
		public abstract void refresh();
		
		public void setPeriod(byte period) {
			refresh();
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}

	}

	/*
	 * TODO be able to move students directly from this panel. have a popup idc
	 * when selecting a student, have a button like "move student" or "remove student" and then do those popups
	 * make sure to log all changes just in case they want to undo
	 * 	make it a human-readable list 
	 */
	public static class RoomPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Room instance variables
		private JList<Student> studentList;
		private JLabel sessionName;
		private Room room;

		public RoomPanel(Room room, CareerDayGUI master) {
			super(master);
			this.room = room;

			initializeUI();
			refresh();
		}
		
		private void initializeUI() {
			JTextField roomNumberField = new JTextField(room.getRoomNumber());
			roomNumberField.setBorder(BorderFactory.createTitledBorder("Room Name"));
			
			roomNumberField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					room.setRoomNumber(roomNumberField.getText());
				}
			});
			
			JTextField roomCapacityField = new JTextField(room.getMaxCapacity() + "");
			roomCapacityField.setBorder(BorderFactory.createTitledBorder("Room Capacity"));
			
			sessionName = new JLabel();
			sessionName.setBorder(BorderFactory.createTitledBorder("Resident Session"));
			
			roomCapacityField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						room.setMaxCapacity(Integer.parseInt(roomCapacityField.getText()));
					} catch (NumberFormatException er) {
						roomCapacityField.setText(room.getMaxCapacity() + "");
					}
				}
			});

			this.setLayout(new BorderLayout());

			JPanel center = new JPanel(new GridLayout(0, 1));
			JPanel north = new JPanel(new GridLayout(0, 1));

			this.add(center, BorderLayout.CENTER);

			studentList =  new JList<Student>();
			studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(studentList);
			scrollPane.setBorder(BorderFactory.createTitledBorder("Resident Students"));

			center.add(scrollPane);
			north.add(roomNumberField);
			north.add(roomCapacityField);
			north.add(sessionName);
			

			this.add(north, BorderLayout.NORTH);
		}

		public void refresh() {
			System.out.println("refresh");
			sessionName.setText(room.getResidentSessions() == null || room.getResidentSessions().length == 0 ? 
					"No Resident Session" :
						// only one resident session per room, so no need for an array.
								room.getResidentSessions()[0].getIdentifier());
			studentList.setListData(room.getResidentSessions() == null || room.getResidentSessions().length == 0 ?
					new Student[0] : 
					
					//get the current session
					room.getResidentSessions()[0]
							// get that session's students for this period
							.getStudents().get(getPeriod())
							// turn to standard array
									.toArray(new Student[room.getResidentSessions()[0].getStudents()
													.get(getPeriod()).size()]));
			
		}
		
	}

	/* TODO
	 * add resident sessions to use and add / remove
	 */
	
	public static class StudentPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		private Student student;
		private JList<Session> memberSessions;
		private JPanel requestPanel;
		public StudentPanel(Student s, CareerDayGUI master) {
			super(master);
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			this.student = s;
			initializeUI();
		}
		
		private void initializeUI() {
			JPanel north = new JPanel(new GridLayout(0, 1));
			north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
			north.add(createInfoField("First Name", student.getfName(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setfName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			
			north.add(createInfoField("Last Name", student.getlName(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setlName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			north.add(createInfoField("Grade", student.getGrade(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					 student.setGrade(Integer.parseInt(((JTextComponent) e.getSource()).getText()));
					} catch (NumberFormatException er) {
						((JTextComponent) e.getSource()).setText(student.getGrade() + "");
					}
				}
			}));
			north.add(createInfoField("Email", student.getEmail(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setEmail(((JTextComponent) e.getSource()).getText());
				}
			}));
			add(north, BorderLayout.NORTH);
						
			requestPanel = new JPanel(new GridLayout(0, 1));
			requestPanel.setBorder(BorderFactory.createTitledBorder("Requests"));
			for (int i = 0; i < student.getRequests().size(); i++)
				requestPanel.add(createRequestSlot(i + 1, student.getRequests().get(i)));
			if (student.getRequests().size() < Student.MAX_REQUESTS) {
				JButton addRequest = new JButton("Add Request");
				addRequest.addActionListener(e -> {
					addRequest(addRequest);
				});
				requestPanel.add(addRequest);	
			}
			add(requestPanel);
			
			
			JPanel south = new JPanel(new GridLayout(0, 1));
			south.setBorder(BorderFactory.createTitledBorder("Assigned Sessions"));
			JButton addAssignment = new JButton("Add Session");
			JButton removeAssignment = new JButton("Remove");
			
			memberSessions = new JList<Session>();
			memberSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			memberSessions.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {

				}
			});
//			student.
			south.add(memberSessions);
			south.add(addAssignment);
			south.add(removeAssignment);
			removeAssignment.setEnabled(false);
			add(south, BorderLayout.SOUTH);
			refresh();
		}
		
		private JPanel createRequestSlot(int choice, Session request) {
			JPanel ret = new JPanel(new BorderLayout());
			ret.add(new JLabel(choice + ":"), BorderLayout.WEST);
			JTextField title = sessionSearchField();
			title.setText(request == null ? "" : request.getTitle());
			ret.add(title);
			JButton remove = new JButton("Remove");
			remove.addActionListener(e -> {
				removeRequest(ret);
				if (request != null)
					student.getRequests().remove(request);
			});
			ret.add(remove, BorderLayout.EAST);
			return ret;
		}
		
		private void removeRequest(JPanel requestSlot) {
			int amountSlots = 0;
			for (Component c : requestPanel.getComponents())
				if (c instanceof JPanel)
					amountSlots++;
				else 
					break;
			System.out.println("count="+requestPanel.getComponentCount());
			boolean alreadyUnder = amountSlots < Student.MAX_REQUESTS;
			requestPanel.remove(requestSlot);
			for (int i = 0; i < amountSlots - 1; i++)
				((JLabel) ((JPanel)requestPanel.getComponent(i)).getComponent(0)).setText((i + 1) + ": ");
			if (!alreadyUnder) {
				JButton addButton = new JButton("Add Request");
				addButton.addActionListener(e -> {
					addRequest(addButton);
				});
				requestPanel.add(addButton);
			}
			requestPanel.revalidate();
		}
		
		private void addRequest(JButton target) {
			requestPanel.add(createRequestSlot(requestPanel.getComponentCount(), null),
					requestPanel.getComponentCount() - 1);
			if (requestPanel.getComponents().length - 1 >= Student.MAX_REQUESTS) 
				requestPanel.remove(target);
			requestPanel.revalidate();
		}
		
		private JTextField sessionSearchField() {
			JTextField searchBar = new JTextField();
			searchBar.addActionListener(e -> {
				// check if the session is valid. if not, tell them
				for (Session s : master.getEvent().getSessions())
					if (s.getTitle().equals(searchBar.getText())) {
						submitNewRequest(s);
						return;
					}
				//notify not found and reset text?
			});
			//auto-complete
			searchBar.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {}
				@Override
				public void keyPressed(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {
					final int maxAmtResults = 5;
					ArrayList<Session> resultData = new ArrayList<Session>();
					for (Session s : master.getEvent().getSessions()) 
						if (s.getIdentifier().toLowerCase().contains(searchBar.getText().toLowerCase()))
							resultData.add(s);
					if (resultData.isEmpty()) return;
					JPopupMenu results = new JPopupMenu();
					for (int i = 0; i < Math.min(maxAmtResults, resultData.size()); i++) {
						JMenuItem addition = new JMenuItem(resultData.get(i).getTitle());
						final int index = i;
						addition.addActionListener(e1 -> {
							searchBar.setText(resultData.get(index).getTitle());
							submitNewRequest(resultData.get(index));
						});
						results.add(addition);
					}
					results.show(searchBar, 0, searchBar.getHeight() - 3);
					searchBar.requestFocus();
				}
			});
			return searchBar;
		}
		
		private void submitNewRequest(Session s) {
			
		}
		
		private JPanel createInfoField(String title, Object data, ActionListener action) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(title + ": "), BorderLayout.WEST);
			JTextField editField = new JTextField(data.toString());
			editField.addActionListener(action);
			panel.add(editField, BorderLayout.CENTER);
			return panel;
		}
		
		
		public void refresh() {
			System.out.println("refresh");
			memberSessions.setListData(student.getAssignments().toArray(new Session[student.getAssignments().size()]));
		}
	}

	public static class SessionPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Session instance variables
		private JButton editStudent, addStudent, removeStudent;
		private JTextField speakerName, classroomNumber;
		private Session session;
		private JList<Student> listStudents;

		public SessionPanel(Session session, CareerDayGUI master) {
			super(master);
			this.session = session;
			addStudent = new JButton("Add Student");
			editStudent = new JButton("Edit Student");
			removeStudent = new JButton("Remove Student");

			speakerName = new JTextField(session.getSpeaker());

			classroomNumber = new JTextField(session.getRoom() == null ? "" : session.getRoom().getRoomNumber());

			

			setLayout(new BorderLayout());

			JPanel north = new JPanel(new GridLayout(2, 0));
			JPanel center = new JPanel(new BorderLayout());
			JPanel south = new JPanel(new BorderLayout());

			add(center, BorderLayout.CENTER);


			listStudents = new JList<Student>();
			listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(listStudents);
			populateList(getPeriod());
			String title = session.getTitle();
			setBorder(BorderFactory.createTitledBorder(null, title,
					TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new Font("Arial", Font.PLAIN, 20), Color.BLACK));

			center.add(scrollPane);

			add(north, BorderLayout.NORTH);
			north.setLayout(new GridLayout(3, 0));
			north.add(speakerName);
			north.add(classroomNumber);

			editStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});

			add(south, BorderLayout.SOUTH);
			south.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			south.setLayout(new GridLayout(5, 1));
			south.add(editStudent);
			south.add(addStudent);
			south.add(removeStudent);

			removeStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					System.out.println("test");
					if (listStudents.getSelectedIndex() != -1) {
						scrollPane.revalidate();
						scrollPane.repaint();
					}
				}
			});

		}

		public void populateList(int period) {
			// model = new DefaultListModel<String>();
			listStudents.setListData(
					session.getStudents().get(period).toArray(new Student[session.getStudents().get(period).size()]));
			listStudents.revalidate();
		}

		public void refresh() {
			System.out.println("refresh");
			System.out.println("getting period "+getPeriod());
			populateList(getPeriod());
			classroomNumber.setText(session.getRoom() == null ? "" : session.getRoom().getRoomNumber());
		}
	}

	private static void show(SideInfoPanel p) {
		JFrame f = new JFrame("test info panel");
		f.getContentPane().add(p);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		Event e = Event.testEvent();
		
		
//		MoreInfo.SessionPanel s = new MoreInfo.SessionPanel(e,
//				e.getSessions().get(0), null);

//		SideInfoPanel s = MoreInfo.getInfoPanel(e.getRooms().get(0), null);
		SideInfoPanel s = MoreInfo.getInfoPanel(e.getStudents().get(0), null);
//		SideInfoPanel s = MoreInfo.getInfoPanel(e.getSessions().get(0), null);
		 show(s);
	}
}