import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingApplication {

	private JFrame frame;
	private JTextField txtname;
	private JTextField txtaddress;
	private JTextField txtdob;
	private JTextField lbltxtsearch;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingApplication window = new SwingApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingApplication() {
		initialize();
		Connect();
		table_load(); 
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:/my_data","root","root123");
		}
		catch(ClassNotFoundException ex)
		{
		}
		catch(SQLException ex)
		{
		}
	}
	
	void table_load() 
	{
		try
		{
			pst=con.prepareStatement("select*from applictiondata");
			rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 710, 604);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 45, 610, 499);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(54, 47, 56, 16);
		panel.add(lblName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(54, 72, 56, 16);
		panel.add(lblAddress);
		
		JLabel lblDob = new JLabel("DOB:");
		lblDob.setBounds(54, 101, 56, 16);
		panel.add(lblDob);
		
		txtname = new JTextField();
		txtname.setBounds(184, 44, 164, 22);
		panel.add(txtname);
		txtname.setColumns(10);
		
		txtaddress = new JTextField();
		txtaddress.setBounds(184, 72, 164, 22);
		txtaddress.setColumns(10);
		panel.add(txtaddress);
		
		txtdob = new JTextField();
		txtdob.setBounds(184, 98, 164, 22);
		txtdob.setColumns(10);
		panel.add(txtdob);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,address;
				String dob;
				
				
				
				
				
				name=txtname.getText();
				address=txtaddress.getText();
				dob=txtdob.getText();
				
				
				
				try {
					pst=con.prepareStatement("insert into applictiondata(name,address,dob)values(?,?,?)");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setString(3, dob);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "record addeddddd!!!!!!");
					table_load();
					txtname.setText("");
					txtaddress.setText("");
					txtdob.setText("");
					txtname.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton.setBounds(443, 43, 97, 25);
		btnNewButton.setBackground(Color.BLUE);
		panel.add(btnNewButton);
		
		JButton btnDisplay = new JButton("Display");
		btnDisplay.setBounds(443, 81, 97, 25);
		btnDisplay.setBackground(Color.BLUE);
		panel.add(btnDisplay);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 242, 506, 244);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Enter name only for searching", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(54, 129, 506, 100);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblName_1 = new JLabel("Name:");
		lblName_1.setBounds(12, 42, 38, 16);
		panel_1.add(lblName_1);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
				String nam=lbltxtsearch.getText();
				
				pst=con.prepareStatement("select name,address,dob from applictiondata where name like'%nam%'");
				
				pst.setString(1,nam);
				ResultSet rs=pst.executeQuery();
				
				if(rs.next()==true)
				{
					String name=rs.getString(1);
					String address=rs.getString(2);
					String dob=rs.getString(3);
					
					txtname.setText(name);
					txtaddress.setText(address);
					txtdob.setText(dob);
					
				}
		          else
		          {
		        	  txtname.setText("");
		        	  txtaddress.setText("");
		        	  txtdob.setText("");
		          }
			  }
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(397, 38, 97, 25);
		panel_1.add(btnSearch);
		btnSearch.setBackground(Color.BLUE);
		
		lbltxtsearch = new JTextField();
		lbltxtsearch.setBounds(128, 39, 164, 22);
		panel_1.add(lbltxtsearch);
		lbltxtsearch.setColumns(10);
	}
}
