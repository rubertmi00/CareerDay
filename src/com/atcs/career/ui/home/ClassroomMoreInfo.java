package com.atcs.career.ui.home;
//Screen for when you want more information on the student
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;

public class ClassroomMoreInfo extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 600;
   public static final int PREF_H = 400;
  private String sessionName = "pasta";
  private String classroomNumber;
  private String speakerName = "Pasta man";
  private JButton editRoomNumber;
  private JScrollPane scrollPane;
  private Event event;
  private Session session;
  
  
   
   

   public ClassroomMoreInfo(Event event) 
   {    
	  
      addKeyListener(this);
      addMouseListener(this);
      addMouseMotionListener(this);
      setFocusable(true);
      this.setBackground(Color.WHITE);
      
     this.event = event;
     
     classroomNumber = session.getRoom().getRoomNumber();
    
      
      editRoomNumber = new JButton("Edit Room Number");
      editRoomNumber.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String roomNumber = JOptionPane.showInputDialog("Please input the new room number: ");
			session.getRoom().setRoomNumber(roomNumber);
			classroomNumber = session.getRoom().getRoomNumber();
			updatePanel();
			
		}    	  
      });

      
      
//      createLayout();
      ClassroomMoreInfo allContent = this;
      allContent.setLayout(new BorderLayout());
      JFrame frame = new JFrame("More Info on Classroom");
      JPanel center = new JPanel(new BorderLayout());
      JPanel south = new JPanel(new GridLayout(0,1));
      
      
      allContent.add(center, BorderLayout.CENTER);
      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
      JList<String> listNames = new JList<String>(studentNames);
      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollPane = new JScrollPane(listNames);
      String sessionInfo = "<html>" + sessionName + "  <br><center> <font size=\"7\"> "+ classroomNumber + "</font></center></html>";
      scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
      center.add(scrollPane);
      
      allContent.add(south, BorderLayout.SOUTH);
      south.setPreferredSize(new Dimension(PREF_W, 100));
      south.add(editRoomNumber);
      
      
      
 	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(allContent);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      
      
      
   }
 
   public void updatePanel()
   {      
	   
	  String sessionInfo = "<html>" + sessionName + "  <br><center> <font size=\"7\"> "+ classroomNumber + "</font></center></html>";
	  scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
//      this.revalidate();
   }
  
   /**
    * The paintComponent is a method that is inherited from the JPanel class.
    * Writing code in here overrides the method
    * This is where you draw shapes, pictures, and objects
    */
   @Override
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
     
      
      
   }
   
   
   /**
    * keyPressed does something when you press a key on a keyboard
    * Every key has a unique int value (and can access with e.getKeyCode()
    * you can choose which specific key with an if statement (shown below)
    */
   @Override
   public void keyPressed(KeyEvent e)
   {
      
   }
   
   /**
    * keyReleased is the same as keyPressed, but it does something when you release a key
    */
   @Override
   public void keyReleased(KeyEvent e)
   {
     
         
   }
   @Override
   public void keyTyped(KeyEvent e){}
   
   @Override
   public void mousePressed(MouseEvent e)
   {
      
   }
   
   @Override
   public void mouseReleased(MouseEvent e)
   {
   }
   
   public void mouseClicked(MouseEvent e){}
   
   @Override
   public void mouseEntered(MouseEvent e)
   {
   }
   
   @Override
   public void mouseExited(MouseEvent e)
   {
   }
   
   @Override
   public void mouseDragged(MouseEvent e)
   {
      
   }
   @Override
   public void mouseMoved(MouseEvent e)
   {
   }


   /**
    * This overrides the JPanel's getPreferredSize() method
    * It tells the JPanel to be a certain width and height
    */
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }
   /**
    * The main method runs your entire program
    * It has the method createAndShowGUI() and runs it.
    * This makes your whole program work.
    */
   public static void main(String[] args) {
   	Event e = new Event();
      new ClassroomMoreInfo(e);
   }
}