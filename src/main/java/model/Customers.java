package model;

import java.sql.*;
import connection.CustomersCon;

public class Customers {
	
	//Insert Pending Customer
	public String insertCustomers(String FullName, String Address, String City, String MobileNumber, String Email) { 
		 
		String output = ""; 
 
		try { 
			
			CustomersCon customerCon = new CustomersCon();
			Connection con = customerCon.connect(); 
 
			if (con == null) {			
				return "Error while connecting to the database for inserting."; 	
			} 
 
				// create a prepared statement
				String query = " insert into Customers (`CustomersID`,`FullName`,`Address`,`City`,`MobileNumber`,`Email`)" + " values (?, ?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
 
				// binding values
				preparedStmt.setInt(1, 0); 
 
				preparedStmt.setString(2, FullName); 
 
				preparedStmt.setString(3, Address); 
 
				preparedStmt.setString(4, City); 
 
				preparedStmt.setString(5, MobileNumber); 
				
				preparedStmt.setString(6, Email); 
 
				// execute the statement
				preparedStmt.execute();  
				con.close(); 
 
				output = "{\"status\":\"success\"}"; 
		} 
 
		catch (Exception e) { 
 
			output = "{\"status\":\"Error while inserting the item\"}"; 
 
			System.err.println(e.getMessage());
			System.err.println("----- Insert Error ! -----");
 
		} 
		
 		return output; 
	} 
	
	//View Users Profile
	public String ViewUsersProfile(String Email, String Password) { 
			 
			String output = ""; 
			try { 
	 
				CustomersCon customerCon = new CustomersCon();
				Connection con = customerCon.connect(); 
	 
				if (con == null) {
					System.err.println("----- Connection Error for Read ! -----");
					return "Error while connecting to the database for reading."; 
				} 
	 
				// Prepare the html table to be displayed
	 
				output = "<table id='tab' class='addView'><tr class='viewTr'><th style='width:10%'>User ID</th><th style='width:15%'>Full Name</th><th style='width:15%'>Address</th>" + "<th style='width:10%'>City</th>" + "<th style='width:10%'>Mobile Number</th><th style='width:15%'>Email</th><th style='width:10%'>Power Plant</th>" + "<th style='width:7%'>Update</th><th style='width:7%'>Delete</th></tr>"; 
				// create a prepared statement
				String query = "select * from PowerPlant where Email like '"+Email+"' and Password like '"+Password+"'"; 
				Statement stmt = con.createStatement(); 
				ResultSet rs = stmt.executeQuery(query);
	 
				if (!rs.isBeforeFirst() ) {    
				   return "{\"status\":\"error\", \"data\": \"No Profile and Try Again.\"}";
				} 
					
				// iterate through the rows in the result set
				while (rs.next()) { 
					String UID = rs.getString("UserID"); 
					String FullName = rs.getString("FullName"); 
					String Address = rs.getString("Address"); 
					String City = rs.getString("City"); 
					String MobileNumber = rs.getString("MobileNumber"); 				
					String Em = rs.getString("Email");
					String PowerPlant = rs.getString("PowerPlant");
	 
					// Add into the html table 
					output += "<tr class='viewTd' style='height:60px'><td>" + UID + "</td>"; 
					output += "<td>" + FullName + "</td>"; 
					output += "<td>" + Address + "</td>"; 
					output += "<td>" + City + "</td>";  
					output += "<td>" + MobileNumber + "</td>";
					output += "<td>" + Em + "</td>"; 
					output += "<td>" + PowerPlant + "</td>";
	 
					// buttons
					output += "<td><input id='btnUpdate' name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-outline-primary'></td>"
							+ "<td><input id='btnRemove' name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-outline-danger'></td></tr>"; 
	 
				} 
	 
				con.close(); 
				// Complete the html table
				output += "</table>"; 
				String out = "{\"status\":\"success\", \"data\": \"" + output + "\"}"; 

				return out;
			} 
	 
			catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while View the item.\"}";
				
				System.err.println(e.getMessage()); 
				System.err.println("----- Error for Read ! -----");
				return output;
			} 
		}
	
	//Update Customer Profile
	public String UpdateProfile(String UserID, String FullName, String Address, String City, String MobileNumber, String Email) { 

		String output = ""; 
 
		try { 
 
			CustomersCon customerCon = new CustomersCon();
			Connection con = customerCon.connect(); 
			
			if (con == null) {
				
				return "Error while connecting to the database for updating."; 
			} 
 
			// create a prepared statement
			String query = "UPDATE PowerPlant SET FullName=?, Address=?, City=?, MobileNumber=?, Email=? WHERE UserID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
 
			// binding values
			preparedStmt.setString(1, FullName); 
			preparedStmt.setString(2, Address); 
			preparedStmt.setString(3, City); 
			preparedStmt.setString(4, MobileNumber); 
			preparedStmt.setString(5, Email); 
			preparedStmt.setString(6, UserID);
 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "{\"status\":\"success\"}"; 
		} 
 
		catch (Exception e) { 
			
			output = "{\"status\":\"Error while Updating the item\"}"; 
			System.err.println(e.getMessage()); 
		} 
 
		return output; 
 
	}
	
	//Delete Customer Profile
		public String DeleteProfiles(String UserID) { 
			 
			String output = ""; 
	 
			try { 
	 
				CustomersCon customerCon = new CustomersCon();
				Connection con = customerCon.connect(); 
	 
				if (con == null) {
					
					return "Error while connecting to the database for deleting."; 
				} 
	 
				// create a prepared statement
				String query = "delete from PowerPlant where UserID=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
				// binding values
				preparedStmt.setString(1, UserID); 
	 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				output = "{\"status\":\"success\"}"; 
	 
			} 
	 
			catch (Exception e) { 
				output = "{\"status\":\"Error while Deleting the item\"}"; 
				System.err.println(e.getMessage()); 
	 
			} 
	 
			return output; 
	 
		} 
		
		//View Bill
		public String ViewUsersBill(String UserID) { 
				 
				String output = ""; 
		 
				try { 
		 
					CustomersCon customerCon = new CustomersCon();
					Connection con = customerCon.connect(); 
		 
					if (con == null) {
						System.err.println("----- Connection Error for Read ! -----");
						return "Error while connecting to the database for reading."; 
					} 
		 
					// Prepare the html table to be displayed
					//output = "<table border='1'><tr><th>User ID</th><th>Full Name</th>" + "<th>City</th>" + "<th>Mobile Number</th><th>Unit</th>" + "<th>Price</th><th>Payment</th></tr>"; 

					// create a prepared statement
					String query = "select * from bill where UserID like '"+UserID+"'"; 
					Statement stmt = con.createStatement(); 
					ResultSet rs = stmt.executeQuery(query);
		 
					// iterate through the rows in the result set
					while (rs.next()) { 
						String UID = rs.getString("UserID"); 
						String FullName = rs.getString("FullName"); 
						String City = rs.getString("City"); 
						String MobileNumber = rs.getString("MobileNumber"); 				
						String Unit = rs.getString("Unit");
						String Price = rs.getString("Price");
		 
						/*// Add into the html table 
						output += "<tr><td>" + UID + "</td>"; 
						output += "<td>" + FullName + "</td>"; 
						output += "<td>" + City + "</td>";  
						output += "<td>" + MobileNumber + "</td>";
						output += "<td>" + Unit + "</td>"; 
						output += "<td>" + Price + "</td>";*/
		 
						// buttons
						//output += "<td><form method='post' action='#'>" + "<input name='btnPay' type='submit' value='Payment' class='btn btn-danger'>" + "<input name='CustomersID' type='hidden' value='" + UserID  + "'>" + "</form></td></tr>"; 
		 
						output = "<div class='card'>"
								+ "<h5 id='billtop'><b>Electro Gird Power Unit Customer Bill</b></h5>"
								+ "<form class='form-card' onsubmit='event.preventDefault()'>"
								+ "<div class='row justify-content-between text-left'>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'><label class='form-control-label px-3'>Full Name :<span class='text-danger'></span></label> <input type='text' id='FullName' value='"+FullName+"' disabled></div>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'> <label class='form-control-label px-3'>Mobile Number :<span class='text-danger'></span></label> <input type='text' id='UserId' value='"+UID+"' disabled> </div>"
								+ "</div>"
								+ "<div class='row justify-content-between text-left'>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'> <label class='form-control-label px-3'>Mobile Number :<span class='text-danger'></span></label> <input type='text' id='MobileNumber' value='"+MobileNumber+"' disabled></div>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'> <label class='form-control-label px-3'>City :<span class='text-danger'></span></label> <input type='text' id='City' value='"+City+"' disabled></div>"
								+ "</div>"
								+ "<div class='row justify-content-between text-left'>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'> <label class='form-control-label px-3'>Unit :<span class='text-danger'></span></label> <input type='text' id='Unit' value='"+Unit+"' disabled> </div>"
								+ "<div class='form-group col-sm-6 flex-column d-flex'> <label class='form-control-label px-3'>Price :<span class='text-danger'></span></label> <input type='text' id='Price' value='"+Price+"' disabled> </div>"
								+ "</div>"
								+ "<div class='row justify-content-between text-left'>"
								+ "<div class='form-group col-12 flex-column d-flex'> <input id='paybu' name='paybu' type='button' value='Click Here For Payment' class='paybu btn btn-outline-primary'></div>"
								+ "</div>"
								+ "</form>"
								+ "</div>";
						
					} 
		 
					con.close(); 
					// Complete the html table
					output += "</table>"; 
					
					String out = "{\"status\":\"success\", \"data\": \"" + output + "\"}"; 

					return out;
				} 
		 
				catch (Exception e) { 
					output = "{\"status\":\"error\", \"data\": \"Error while View the Bill.\"}";
					System.err.println(e.getMessage()); 
					System.err.println("----- Error for Read ! -----");
		 
					return output;
				} 
			}

		//Payment bank acc
		public String Payment(String UserID, String FullName, String BankName, String AccName, String AccNo, String BranchName, String BillPrice, String Date) { 

			String output = ""; 
			String uID = "";
			String Amount = "";
			int credit = 0;

			try { 
				
				CustomersCon customerCon = new CustomersCon();
				Connection con = customerCon.connect(); 
	 
				CustomersCon BankCon = new CustomersCon();
				Connection bank = BankCon.bank(); 
				
				if (con == null) {			
					return "Error while connecting to the database for inserting."; 	
				} 
				if (bank == null) {			
					return "Error while connecting to the database for inserting."; 	
				}
	 
					String ckeck = "select * from "+BankName+" where Accno like '"+AccNo+"'and AccName like '"+AccName+"'";
					Statement stmt = bank.createStatement(); 
					ResultSet rs = stmt.executeQuery(ckeck);

					while (rs.next()) {
						uID = rs.getString("id");
						Amount = rs.getString("Amount");
					}

					int id = Integer.parseInt(uID);
					if(id != 0) {
						credit = Integer.parseInt(Amount) - Integer.parseInt(BillPrice);
						
						if (credit > 0) {
							// create a prepared statement
							String pay = " insert into Payment(`id`,`UserID`,`FullName`,`BillPrice`,`AccNo`,`BranchName`)" + " values (?, ?, ?, ?, ?, ?)"; 
							String update = "UPDATE bill SET Payment=?, Date=? WHERE UserID=?"; 
							String bankAcc = "UPDATE "+BankName+" SET Amount='"+credit+"' WHERE AccNo='"+AccNo+"'"; 
							PreparedStatement preparedStmt = con.prepareStatement(pay); 
							PreparedStatement preparedStmtUp = con.prepareStatement(update);
							PreparedStatement preparedStmtBa = bank.prepareStatement(bankAcc); 
							
							// binding values
							preparedStmt.setInt(1, 0); 
							preparedStmt.setString(2, UserID); 
							preparedStmt.setString(3, FullName); 
							preparedStmt.setString(4, BillPrice); 
							preparedStmt.setString(5, AccNo); 
							preparedStmt.setString(6, BranchName); 
							
							preparedStmtUp.setString(1, BillPrice);
							preparedStmtUp.setString(2, Date);
							preparedStmtUp.setString(3, UserID);
							
							/*preparedStmtBa.setString(1, BankName);
							preparedStmtBa.setString(2, String.valueOf(credit));
							preparedStmtBa.setString(3, AccNo);
							System.out.println(bankAcc);*/
							// execute the statement
							preparedStmt.execute();
							preparedStmtUp.execute();
							preparedStmtBa.execute();
							output = "{\"status\":\"success\"}"; 
						}
					}else {
						System.out.println("-------------------");
					}
	 
					output = "{\"status\":\"success\"}"; 
			} 
	 
			catch (Exception e) { 
	 
				output = "{\"status\":\"Error while inserting the item\"}"; 
	 
				System.err.println(e.getMessage());
				System.err.println("----- Insert Error ! -----");
	 
			} 
			
	 		return output; 
		}
}
