package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private TextField tfAuthor = new TextField();
	private TextField tfTitle = new TextField();
	private TextField tfFirstName = new TextField();
	private TextField tfLastName = new TextField();
	
	private TextArea taReport = new TextArea();
	
	private TextField ISBNTextField = new TextField();
	
	private Button checkOutButton = new Button("Check-Out Book");
	private Button returnButton = new Button("Return Book");
	private Button printBooksButton = new Button("Display Books");
	private Button checkedOutBooksButton = new Button("Checked Out Books");
	private Button listPatronsButton = new Button("List Patrons");
	private Button whoHasBookButton = new Button("Who Has This Book?");
	private Button instructionsButton = new Button("Instructions");
	
	private Label ISBNLabel = new Label("ISBN Number");
	private Label authorLabel = new Label("Author");
	private Label titleLabel = new Label("Title");
	private Label firstNameLabel = new Label("First Name");
	private Label lastNameLabel = new Label("Last Name");
		
	@Override
	public void start(Stage primaryStage) throws SQLException{
		try {
			
			String url = "jdbc:mysql://localhost/javabook";
		  	String user = "scott";
		  	String password = "tiger";
		    Connection myConn = DriverManager.getConnection(url, user, password);
		    Statement myStmt = myConn.createStatement();
		    
		    // Book Table //////////////////////////////////////////////////////////////////
		    String sqlDropBookTable = "drop table if exists Book;";
		    myStmt.executeUpdate(sqlDropBookTable);
			String sqlCreateBookTable = 
					  "create table Book (" +  
					  "id int not null auto_increment," +
					  "ISBN varchar(50)," +
					  "title varchar(35)," +
					  "author varchar(45)," +
					  "copies int," +
					  "primary key (id));";
			myStmt.executeUpdate(sqlCreateBookTable);
			
			// Patron Table ////////////////////////////////////////////////////////////////
		    String sqlDropPatronTable = "drop table if exists Patron;";
		    myStmt.executeUpdate(sqlDropPatronTable);
		    String sqlCreatePatronTable = 
		    		  "create table Patron (" +  
					  "id int not null auto_increment," +
					  "firstname varchar(30)," +
					  "lastname varchar(30)," +
					  "street varchar(20)," +
					  "city char(20)," +
					  "state char(20)," +
					  "primary key (id));";
		    myStmt.executeUpdate(sqlCreatePatronTable);
		    
		    // Checked-Out Table ////////////////////////////////////////////////////////////////
		    String sqlDropCheckedOutTable = "drop table if exists CheckedOut;";
		    myStmt.executeUpdate(sqlDropCheckedOutTable);
		    String sqlCreateCheckedOutTable = 
		    		"create table CheckedOut( " +
		            "bookID int not null, " + 
				    "patronID int not null, " +
				    "primary key (bookID, patronID));";
		    myStmt.executeUpdate(sqlCreateCheckedOutTable);

		    // Checked-In Table ////////////////////////////////////////////////////////////////
		    String sqlDropCheckedInTable = "drop table if exists CheckedIn;";
		    myStmt.executeUpdate(sqlDropCheckedInTable);
		    String sqlCreateCheckedInTable = 
		    		"create table CheckedIn (" +
		    		"bookID int not null, " +
		    		"primary key (bookID))";
		    myStmt.executeUpdate(sqlCreateCheckedInTable);
		    
		    // Populate Database //////////////////////////////////////////////////////////////
			String sql1 = "insert into Book (ISBN, title, author, copies) values ('341','Hamlet', 'William Shakespear', '4');";
			String sql2 = "insert into Book (ISBN, title, author, copies) values ('234','Catcher In the Rye', 'JD Salanger', '2');";
			String sql3 = "insert into Book (ISBN, title, author, copies) values ('283','Driving for Dummies', 'Bill Crane', '7');";
			String sql4 = "insert into Book (ISBN, title, author, copies) values ('839','Night Crawlers', 'Mary Shadely', '2');";
			String sql5 = "insert into Book (ISBN, title, author, copies) values ('928','Northeastern Plants', 'Jane Smith', '9');";
			
			String sql6 = "insert into Patron (firstname, lastname, street, city, state) values ('Travis', 'Fox', 'Main Street', 'Milford', 'PA');";
			String sql7 = "insert into Patron (firstname, lastname, street, city, state) values ('Jason', 'Smith', 'East Street', 'Glendale', 'AZ');";
			String sql8 = "insert into Patron (firstname, lastname, street, city, state) values ('Lindsay', 'Meadow', 'Rintintin Ave', 'Spaceville', 'NY');";
			String sql9 = "insert into Patron (firstname, lastname, street, city, state) values ('Bill', 'Gates', 'Weston Junction', 'Murrytown', 'IL');";
			String sql10 = "insert into Patron (firstname, lastname, street, city, state) values ('Steve', 'Warren', 'Hobbit Street', 'Multon', 'WI');";


			myStmt.addBatch(sql1); 
			myStmt.addBatch(sql2);  
			myStmt.addBatch(sql3);  
			myStmt.addBatch(sql4);  
			myStmt.addBatch(sql5);  
			myStmt.addBatch(sql6); 
			myStmt.addBatch(sql7);  
			myStmt.addBatch(sql8);  
			myStmt.addBatch(sql9);  
			myStmt.addBatch(sql10);  


		    int count[] = myStmt.executeBatch();
		    
		    ////////////////////////////////////////////////////////////////////////////////
		    
		    //String sqlGetStudents = "select * from Patron";
		    //ResultSet rs = myStmt.executeQuery(sqlGetStudents);
		    taReport.setText("To check out a book, enter ISBN and FULL name, click 'Check Out'.\n"
		    				+ "To return a book, enter ISBN and FULL name, click 'Return Book'.\n"
		    				+ "To see all available books, click 'Display Books'.\n"
		    				+ "To see who has a specific book, enter ISBN, click 'Who Has This Book?'.\n"
		    				+ "To see which books a Patron has, enter FULL name, 'Checked Out Books'.\n"
		    				+ "To see all patrons, hit 'List Patrons' \n");
		    /*
		    while(rs.next()){
		    	taReport.appendText(rs.getString(2) + " ");
		    	taReport.appendText(rs.getString(3) + " \n");
		    }
		    */		    		
		    		
			// GUI Construction ////////////////////////////////////////////////////////////
			HBox bookBox = new HBox(5);
			bookBox.getChildren().addAll(titleLabel, tfTitle, authorLabel, tfAuthor);
			bookBox.setAlignment(Pos.CENTER);
			
			HBox ISBNBox = new HBox(5);
			ISBNBox.getChildren().addAll(ISBNLabel, ISBNTextField);
			ISBNBox.setAlignment(Pos.CENTER);
			
			HBox patronBox = new HBox(5);
			patronBox.getChildren().addAll(firstNameLabel, tfFirstName, lastNameLabel, tfLastName);
			patronBox.setAlignment(Pos.CENTER);
			
			HBox reportBox = new HBox(5);
			reportBox.getChildren().add(taReport);
			reportBox.setAlignment(Pos.CENTER);
			
			HBox instructionsBox = new HBox(5);
			instructionsBox.getChildren().add(instructionsButton);
			instructionsBox.setAlignment(Pos.CENTER);
			
			HBox buttonBoxTop = new HBox(5);
			buttonBoxTop.getChildren().addAll(checkOutButton, returnButton);
			buttonBoxTop.setAlignment(Pos.CENTER);
						
			HBox buttonBoxBottom = new HBox(5);
			buttonBoxBottom.getChildren().addAll(printBooksButton, listPatronsButton, whoHasBookButton, checkedOutBooksButton);
			buttonBoxBottom.setAlignment(Pos.CENTER);
			
			VBox mainBox = new VBox(1);
			mainBox.getChildren().addAll(bookBox, ISBNBox, patronBox, reportBox, instructionsBox, buttonBoxTop, buttonBoxBottom);
			
			Scene scene = new Scene(mainBox,580,380);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			printBooksButton.setOnAction(e -> displayBooks());
			checkOutButton.setOnAction(e -> checkOutBook());
			checkedOutBooksButton.setOnAction(e -> checkedOutBooks());
			listPatronsButton.setOnAction(e -> listPatrons());
			instructionsButton.setOnAction(e -> instructions());
			whoHasBookButton.setOnAction(e -> whoHasBook());
			returnButton.setOnAction(e -> returnBook());

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void returnBook() {
		try{
			String url = "jdbc:mysql://localhost/javabook";
		  	String user = "scott";
		  	String password = "tiger";
		    Connection myConn = DriverManager.getConnection(url, user, password);
			
	        // Get the book
	        String sqlCheckOut = "SELECT * FROM Book WHERE ISBN='"+ ISBNTextField.getText() +"';";
	        PreparedStatement getCopies = myConn.prepareStatement(sqlCheckOut);
		    ResultSet rsfind = getCopies.executeQuery();
		    int copies = 0;
	        int bookID = 0;
	        while(rsfind.next()) {
	        	bookID = rsfind.getInt(1);
	        	copies = rsfind.getInt(5);
	        }
	        
	        // Subtract a copy of the book
	        copies++;
	        taReport.setText("Returned " + ISBNTextField.getText());
	        
	        // Update copies in the database
	        String sqlUpdateCopies = "UPDATE Book SET copies = "+copies+" WHERE ISBN = '"+ ISBNTextField.getText() +"';";
	        System.out.println(sqlUpdateCopies);
	        PreparedStatement myStmt3 = myConn.prepareStatement(sqlUpdateCopies);
	        myStmt3.executeUpdate();
	        
	        // Delete record to CheckedOut Table
	        //int bookID2 = 0;
	        String sqlGetPatronID = "SELECT javabook.CheckedOut.bookID FROM javabook.CheckedOut JOIN javabook.Patron ON javabook.Patron.id = javabook.CheckedOut.patronID JOIN javabook.Book ON javabook.Book.id = javabook.Patron.id WHERE javabook.Patron.firstname = '"+ tfFirstName.getText() +"' AND javabook.Patron.lastname = '"+ tfLastName.getText() +"' AND javabook.CheckedOut.bookID = '"+ bookID +"';";
	        System.out.println(sqlGetPatronID);
	        PreparedStatement getPatronID = myConn.prepareStatement(sqlGetPatronID);

	        ResultSet rsPatron = getPatronID.executeQuery();
	        while(rsPatron.next()) {
	        	bookID = rsPatron.getInt(1);
	        }
	        System.out.println(bookID);
	        String sqlDeleteCheckedOutBook = "delete from javabook.CheckedOut where javabook.CheckedOut.bookID = '"+ bookID +"'";
	        System.out.println(sqlDeleteCheckedOutBook);
	        PreparedStatement myStmt = myConn.prepareStatement(sqlDeleteCheckedOutBook);
	        //myStmt.setInt(1, bookID);
	        //myStmt.setInt(2, bookID2);
	      	myStmt.executeUpdate();
	      	

		}catch(Exception e){
			
		}
	}
	
	public void whoHasBook() {
		try{
		String url = "jdbc:mysql://localhost/javabook";
	  	String user = "scott";
	  	String password = "tiger";
	    Connection myConn = DriverManager.getConnection(url, user, password);
		String sqlGetRenter = "SELECT javabook.Patron.firstname, javabook.Patron.lastname, javabook.Book.title FROM javabook.Patron JOIN javabook.CheckedOut ON javabook.Patron.id = javabook.CheckedOut.patronID JOIN javabook.Book ON javabook.Book.id = javabook.CheckedOut.bookID WHERE javabook.Book.ISBN = '"+ ISBNTextField.getText() +"';";
		PreparedStatement findStmt = myConn.prepareStatement(sqlGetRenter);   
        ResultSet rs = findStmt.executeQuery(sqlGetRenter);
        taReport.clear();
        while(rs.next()){
        	//Retrieve by column name
        	taReport.appendText(rs.getString(1) + " ");
        	taReport.appendText(rs.getString(2) + " ");
        	taReport.appendText(rs.getString(3) + " ");
        	taReport.appendText("\n");
        }
		
		}catch(Exception e) {
			
		}
	}
	
	public void instructions() {
		taReport.clear();
		taReport.setText("To check out a book, enter ISBN and FULL name, click 'Check Out'.\n"
				+ "To return a book, enter ISBN and FULL name, click 'Return Book'.\n"
				+ "To see all available books, click 'Display Books'.\n"
				+ "To see who has a specific book, enter ISBN, click 'Who Has This Book?'.\n"
				+ "To see which books a Patron has, enter FULL name, 'Checked Out Books'.\n"
				+ "To see all patrons, hit 'List Patrons' \n");
	}
	
	public void listPatrons() {
		try{
		String url = "jdbc:mysql://localhost/javabook";
	  	String user = "scott";
	  	String password = "tiger";
	    Connection myConn = DriverManager.getConnection(url, user, password);
	    Statement myStmt = myConn.createStatement();
		String sqlGetStudents = "select * from Patron";
	    ResultSet rs = myStmt.executeQuery(sqlGetStudents);
	    taReport.clear();
	    while(rs.next()){
	    	taReport.appendText(rs.getString(2) + " ");
	    	taReport.appendText(rs.getString(3) + " \n");
	    }
		}catch(Exception e) {
			
		}
	   		 
	}
	
	public void displayBooks() {
		try{
			String url = "jdbc:mysql://localhost/javabook";
		  	String user = "scott";
		  	String password = "tiger";
		    Connection myConn = DriverManager.getConnection(url, user, password);
			String sqlGetBooks = "select * from Book;";
			PreparedStatement findStmt = myConn.prepareStatement(sqlGetBooks);   
	        ResultSet rs = findStmt.executeQuery(sqlGetBooks);
	        taReport.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taReport.appendText("ISBN#: ");
	        	taReport.appendText(rs.getString(2) + " ");
	        	taReport.appendText(rs.getString(3) + " ");
	        	taReport.appendText(" by ");
	        	taReport.appendText(rs.getString(4) + " ");
	        	taReport.appendText(" Number of Copies: ");
	        	taReport.appendText(rs.getString(5) + " ");
	        	taReport.appendText("\n");
	        }
	        taReport.appendText("\nTo checkout a book, enter its ISBN# and Patron's FULL name.");
		}catch(Exception e){
			
		}
	}
	
	public void checkOutBook() {
		try{
			String url = "jdbc:mysql://localhost/javabook";
		  	String user = "scott";
		  	String password = "tiger";
		    Connection myConn = DriverManager.getConnection(url, user, password);
			String sqlGetBooks = "select * from Book;";
			PreparedStatement findStmt = myConn.prepareStatement(sqlGetBooks);   
	        ResultSet rs = findStmt.executeQuery(sqlGetBooks);
	        taReport.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taReport.appendText("ISBN#: ");
	        	taReport.appendText(rs.getString(2) + " ");
	        	taReport.appendText(rs.getString(3) + " ");
	        	taReport.appendText(" by ");
	        	taReport.appendText(rs.getString(4) + " ");
	        	taReport.appendText(" Number of Copies: ");
	        	taReport.appendText(rs.getString(5) + " ");
	        	taReport.appendText("\n");
	        }
	        // Get the book
	        String sqlCheckOut = "SELECT * FROM Book WHERE ISBN='"+ ISBNTextField.getText() +"';";
	        PreparedStatement getCopies = myConn.prepareStatement(sqlCheckOut);
		    ResultSet rsfind = getCopies.executeQuery();
		    int copies = 0;
	        int bookID = 0;
	        while(rsfind.next()) {
	        	bookID = rsfind.getInt(1);
	        	copies = rsfind.getInt(5);
	        }
	        
	        // Subtract a copy of the book
	        copies--;
	        taReport.setText("Checked Out " + ISBNTextField.getText());
	        
	        // Update copies in the database
	        String sqlUpdateCopies = "UPDATE Book SET copies="+copies+" WHERE ISBN='"+ ISBNTextField.getText() +"';";
	        System.out.println(sqlUpdateCopies);
	        PreparedStatement myStmt3 = myConn.prepareStatement(sqlUpdateCopies);
	        myStmt3.executeUpdate();
	        
	        // Add record to CheckedOut Table
	        int patronID = 0;
	        String sqlGetPatronID = "select * from Patron WHERE firstname = '"+ tfFirstName.getText() +"' AND lastname = '"+ tfLastName.getText() +"'";
	        System.out.println(sqlGetPatronID);
	        PreparedStatement getPatronID = myConn.prepareStatement(sqlGetPatronID);

	        ResultSet rsPatron = getPatronID.executeQuery();
	        while(rsPatron.next()) {
	        	patronID = rsPatron.getInt(1);
	        }
	        System.out.println(patronID);
	        String sqlAddCheckedOutBook = "insert into CheckedOut (bookID, patronID) values (?,?)";
	        PreparedStatement myStmt = myConn.prepareStatement(sqlAddCheckedOutBook);
	        myStmt.setInt(1, bookID);
	        myStmt.setInt(2, patronID);
	      	myStmt.executeUpdate();
	      	

		}catch(Exception e){
			
		}
	}
	
	public void checkedOutBooks() {
		try{
			String url = "jdbc:mysql://localhost/javabook";
		  	String user = "scott";
		  	String password = "tiger";
		    Connection myConn = DriverManager.getConnection(url, user, password);
			String sqlGetISBN = "SELECT javabook.Patron.firstname, javabook.Patron.lastname, javabook.Book.title FROM javabook.Patron JOIN javabook.CheckedOut ON javabook.Patron.id = javabook.CheckedOut.patronID JOIN javabook.Book ON javabook.Book.id = javabook.CheckedOut.bookID WHERE javabook.Patron.firstname = '"+ tfFirstName.getText() +"' AND javabook.Patron.lastname = '"+ tfLastName.getText() +"';";                              
			PreparedStatement findStmt = myConn.prepareStatement(sqlGetISBN);   
	        ResultSet rs = findStmt.executeQuery(sqlGetISBN);
	        taReport.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taReport.appendText(rs.getString(1) + " ");
	        	taReport.appendText(rs.getString(2) + " ");
	        	taReport.appendText(rs.getString(3) + " ");
	        	taReport.appendText("\n");
	        }
			
		}catch(Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
