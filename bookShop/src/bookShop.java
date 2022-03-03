

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class bookShop {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtBookID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bookShop window = new bookShop();
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
	public bookShop() {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bookshop", "root","");
        }
        catch (ClassNotFoundException ex)
        {
          ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
 
    }
	
	public void table_load()
    {
    	try 
    	{
	    pst = con.prepareStatement("select * from book");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } 
    }
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOK SHOP");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 29));
		lblNewLabel.setBounds(319, 11, 222, 54);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(255, 160, 122));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBounds(39, 92, 422, 233);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Segoe Print", Font.BOLD, 19));
		lblNewLabel_1.setBounds(25, 58, 127, 24);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Segoe Print", Font.BOLD, 19));
		lblNewLabel_1_1.setBounds(25, 113, 127, 24);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Segoe Print", Font.BOLD, 19));
		lblNewLabel_1_1_1.setBounds(25, 170, 127, 24);
		panel.add(lblNewLabel_1_1_1);
		
		txtName = new JTextField();
		txtName.setBounds(162, 61, 183, 24);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(162, 113, 183, 24);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(162, 175, 183, 24);
		panel.add(txtPrice);
		
		JButton btnNewButton = new JButton("Save");             /*Save Button*/
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String bname,edition,price;
				bname = txtName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				try {
				pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");    /*book- the table where we store the records*/
				pst.setString(1, bname);
				pst.setString(2, edition);
				pst.setString(3, price);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Added!");
		        table_load();
				          
				txtName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtName.requestFocus();
				   }
				 
				catch (SQLException e1)
				        {
				e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(new Color(255, 160, 122));
		btnNewButton.setBounds(39, 332, 101, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");            /*Exit Button*/
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBackground(new Color(255, 160, 122));
		btnExit.setBounds(197, 332, 101, 35);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");          /*Clear Button*/
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtName.requestFocus();
			}
		});
		btnClear.setBackground(new Color(255, 160, 122));
		btnClear.setBounds(360, 332, 101, 35);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(515, 88, 294, 280);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 160, 122));
		panel_1.setForeground(new Color(0, 0, 0));
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(39, 398, 422, 54);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtBookID = new JTextField();                              /*Search Book by ID*/
		txtBookID.addKeyListener(new KeyAdapter() {
			@Override
	
				public void keyReleased(KeyEvent e) {
					try {
					         String id = txtBookID.getText();
					 
					         pst = con.prepareStatement("select name, edition, price from book where id = ?");
					         pst.setString(1, id);
					         ResultSet rs = pst.executeQuery();
					 
					         if(rs.next()==true)
					            {
					              
					                String name = rs.getString(1);
					                String edition = rs.getString(2);
					                String price = rs.getString(3);
					                
					                txtName.setText(name);
					                txtEdition.setText(edition);
					                txtPrice.setText(price);
					                
					                
					            }  
					         else
					            {
					        	 	txtName.setText("");
					        	 	txtEdition.setText("");
					                txtPrice.setText("");
					                
					            }
					 
					        }
						catch (SQLException ex) {
					          
					        }
					}	
				
		});
		txtBookID.setColumns(10);
		txtBookID.setBounds(172, 19, 183, 24);
		panel_1.add(txtBookID);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Book ID");
		lblNewLabel_1_1_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_2.setFont(new Font("Segoe Print", Font.BOLD, 19));
		lblNewLabel_1_1_2.setBounds(25, 19, 127, 24);
		panel_1.add(lblNewLabel_1_1_2);
		
		JButton btnUupdate = new JButton("Update");                  /*Update Button*/
		btnUupdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {    
					String bname,edition,price,bid;
					bname = txtName.getText();
					edition = txtEdition.getText();
					price = txtPrice.getText();
					bid  = txtBookID.getText();
					try {
						
					pst = con.prepareStatement("update book set name= ?, edition=?, price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
				    pst.setString(3, price);
				    pst.setString(4, bid);
				    pst.executeUpdate();
				    JOptionPane.showMessageDialog(null, "Record Updated!");
					            
				    table_load();
					                
				    txtName.setText("");					           	
				    txtEdition.setText("");					            
				    txtPrice.setText("");					            
				    txtName.requestFocus();
					}
					 
				    catch (SQLException e1) {
					     e1.printStackTrace();
					}
				}
				
		});
		btnUupdate.setBackground(new Color(255, 160, 122));
		btnUupdate.setBounds(518, 397, 101, 35);
		frame.getContentPane().add(btnUupdate);
		
		JButton btnDelete = new JButton("Delete");       /*Delete nButton*/
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				bid  = txtBookID.getText();
				try {
				pst = con.prepareStatement("delete from book where id =?");
				            pst.setString(1, bid);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Record Deleted!");
				            table_load();
				          
				            txtName.setText("");
				            txtEdition.setText("");
				            txtPrice.setText("");
				            txtName.requestFocus();
				}
				 
				            catch (SQLException e1) {
				e1.printStackTrace();
				}
					
			}
		});
		btnDelete.setBackground(new Color(255, 160, 122));
		btnDelete.setBounds(709, 397, 101, 35);
		frame.getContentPane().add(btnDelete);
	}
}
