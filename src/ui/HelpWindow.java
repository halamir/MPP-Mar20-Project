package ui;

import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpWindow extends Stage implements LibWindow {
	public static final HelpWindow INSTANCE = new HelpWindow();
	
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
    private HelpWindow () {}
    
    public void init() {  

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();   
 
		topContainer.getChildren().add(getBasicTopPane());
		
		Scene scene = new Scene(topContainer, 800,500);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }
	public static StackPane getBasicTopPane() {
		// TODO Auto-generated method stub
		StackPane sgta = new StackPane();
		Menu optionsMenu = new Menu("Modules");
		MenuItem members = new MenuItem("Members");
		MenuItem checkOutRecord = new MenuItem("Checkout records");
		MenuItem books = new MenuItem("Books");
		

		members.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				Start.hideAllWindows();
				MemberWindow.run();
            }
		});	
		checkOutRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				Start.hideAllWindows();
				CheckoutWindow.run();
            }
		});	
		books.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				Start.hideAllWindows();
				BookWindow.run();
            }
		});	
		
		
		String role = SystemController.currentAuth.toString();
		switch(role) {
		case "LIBRARIAN":
			optionsMenu.getItems().addAll(members);
			break;
		case "ADMIN":
			optionsMenu.getItems().addAll( checkOutRecord, books);
			break;
		case "BOTH":
			optionsMenu.getItems().addAll(members, checkOutRecord, books);
			break;
		}
			MenuBar mainMenu = new MenuBar();
			mainMenu.getMenus().addAll(optionsMenu);

			Label labelSystem = new Label("Library System");
			labelSystem.setAlignment(Pos.CENTER);
			Label labelUsuario = new Label(role);
			labelUsuario.setAlignment(Pos.TOP_RIGHT);
			sgta.getChildren().add(mainMenu);
			sgta.getChildren().add(labelSystem);
		return sgta;
	}
	
	
}
