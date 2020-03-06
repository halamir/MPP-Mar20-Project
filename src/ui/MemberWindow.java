package ui;

import java.util.List;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.LoginException;
import business.SystemController;
import dataaccess.TestData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MemberWindow extends Stage implements LibWindow {
	public static final MemberWindow INSTANCE = new MemberWindow();
	
	private boolean isInitialized = false;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private Text messageBar = new Text();
	public void clear() {
		messageBar.setText("");
	}
	
	/* This class is a singleton */
    private MemberWindow () {}
    
    public void init() {  

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();   


		VBox generalContainer = new VBox();
		generalContainer.setId("general-container");
		generalContainer.setPrefHeight(600);
		generalContainer.setPrefWidth(600);
		generalContainer.setPadding(new Insets(20,30,20,30));
		VBox.setMargin(generalContainer, new Insets(20, 20, 20, 20));
		
		StackPane basicStack = HelpWindow.getBasicTopPane();
		Label labelTitulo = new Label("Members");
		labelTitulo.setId("window-title");
		Button buttonAdd = new Button("Add / Update");
		//newStack.getChildren().add(labelUsuario);
		topContainer.getChildren().add(basicStack);
		generalContainer.getChildren().add(labelTitulo);
		generalContainer.getChildren().add(buttonAdd);
		generalContainer.getChildren().add(createTable());
		topContainer.getChildren().add(generalContainer);

		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		AddMemberWindow.run();        	   
        	}
        });
		Scene scene = new Scene(topContainer, 800,500);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }
    public static TableView<LibraryMember> createTable() {
    	//Members
    	TableColumn<LibraryMember,String> memberIdColumn = new TableColumn<>("Member ID");
    	memberIdColumn.setMinWidth(180);
    	memberIdColumn.setCellValueFactory(new PropertyValueFactory("memberId") ) ;

    	TableColumn<LibraryMember,String> firstNameColumn = new TableColumn<>("First name");
    	firstNameColumn.setMinWidth(180);
    	firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName") ) ;

    	TableColumn<LibraryMember,String> lastNameColumn = new TableColumn<>("Last name");
    	lastNameColumn.setMinWidth(180);
    	lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName") ) ;

    	TableColumn<LibraryMember,String> telefponhoColumn = new TableColumn<>("Telephone");
    	telefponhoColumn.setMinWidth(180);
    	telefponhoColumn.setCellValueFactory(new PropertyValueFactory("telephone") ) ;

    	TableColumn<LibraryMember,String> streetAdresColumn = new TableColumn<>("Street");
    	streetAdresColumn.setMinWidth(180);
    	streetAdresColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>,
                ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> data){  
			return new SimpleStringProperty( data.getValue().getAddress().getStreet() );  
			}
		});
    	TableColumn<LibraryMember,String> cityAdresColumn = new TableColumn<>("Street");
    	cityAdresColumn.setMinWidth(180);
    	cityAdresColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>,
                ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> data){  
			return new SimpleStringProperty( data.getValue().getAddress().getCity() );  
			}
		});
    	TableColumn<LibraryMember,String> stateAdresColumn = new TableColumn<>("Street");
    	stateAdresColumn.setMinWidth(180);
    	stateAdresColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>,
                ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> data){  
			return new SimpleStringProperty( data.getValue().getAddress().getState() );  
			}
		});
    	TableColumn<LibraryMember,String> zipAdresColumn = new TableColumn<>("Street");
    	zipAdresColumn.setMinWidth(180);
    	zipAdresColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>,
                ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> data){  
			return new SimpleStringProperty( data.getValue().getAddress().getZip() );  
			}
		});
    	
    	
    	
    	
    	TableView<LibraryMember> tableMember = new TableView();
    	tableMember.setItems( getMembersTest());
    	tableMember.getColumns().addAll(memberIdColumn,firstNameColumn,lastNameColumn,telefponhoColumn
    			,streetAdresColumn,cityAdresColumn,stateAdresColumn,zipAdresColumn);
    	return tableMember;
    }
	private static  ObservableList<LibraryMember> getMembersTest() {
		// TODO Auto-generated method stub
		ObservableList<LibraryMember> asd = FXCollections.observableArrayList();
		TestData asdasdsa = new TestData();
		asdasdsa.libraryMemberData();
		asd.addAll(asdasdsa.members);
		return asd;
	}
	public static void run() {
		// TODO Auto-generated method stub
		Start.hideAllWindows();
			if(!MemberWindow.INSTANCE.isInitialized()) {
				MemberWindow.INSTANCE.init();
   			}
			MemberWindow.INSTANCE.clear();
			MemberWindow.INSTANCE.show();
	}
	
	
}
