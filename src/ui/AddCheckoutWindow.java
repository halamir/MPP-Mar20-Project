package ui;

import java.time.LocalDate;

import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.components.AutoCompleteBox;

public class AddCheckoutWindow extends Stage implements LibWindow {
	public static final AddCheckoutWindow INSTANCE = new AddCheckoutWindow();
	
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
    private AddCheckoutWindow () {}

    ComboBox<LibraryMember> comboBoxMember = new ComboBox<LibraryMember>();
	ComboBox<Book> comboboxBook = new ComboBox<Book>();
	AutoCompleteBox<LibraryMember> lll = null;
	AutoCompleteBox<Book> bbb = null;
    public void init() {  

    	VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();   


		VBox generalContainer = new VBox();
		generalContainer.setId("general-container");
		generalContainer.setPrefHeight(500);
		generalContainer.setPrefWidth(500);
		generalContainer.setPadding(new Insets(10,10,10,10));
		VBox.setMargin(generalContainer, new Insets(20, 20, 20, 20));
		
		Label labelTitulo = new Label("Add Checkout Record ");
		labelTitulo.setId("window-title");
		GridPane gridForm = new GridPane();
		gridForm.setPrefHeight(400);
		gridForm.setPrefWidth(500);
		gridForm.setVgap(10);
		gridForm.add(labelTitulo, 0, 1);
		gridForm.setAlignment(Pos.CENTER);
		DatePicker checkoutDate = new DatePicker(); 
		checkoutDate.setPromptText("Checkout Date");
		checkoutDate.setId("checkoutDate");
		checkoutDate.setValue(LocalDate.now());
		checkoutDate.setPrefWidth(300);
		DatePicker dueDate = new DatePicker(); 
		dueDate.setPromptText("Date due");
		dueDate.setId("dueDate");
		dueDate.setPrefWidth(300);
		dueDate.setValue(LocalDate.now().plusDays(21));

		ObservableList<LibraryMember> memebers = FXCollections.observableArrayList();
		ObservableList<Book> booksObservable = FXCollections.observableArrayList();
		SystemController sc = new SystemController();
		memebers.addAll(sc.allMembers());
		booksObservable.addAll(sc.allBooks());
		comboboxBook = new ComboBox<Book>(booksObservable);
		bbb = new AutoCompleteBox<Book>(comboboxBook);
		comboboxBook.setPrefWidth(300);

		comboBoxMember = new ComboBox<LibraryMember>(memebers);
		lll=new AutoCompleteBox<LibraryMember>(comboBoxMember);
		comboBoxMember.setPrefWidth(300);
		gridForm.add(checkoutDate, 0, 2);
		gridForm.add(dueDate, 0, 3);
		gridForm.add(comboBoxMember, 0, 4);
		gridForm.add(comboboxBook, 0, 5);
		
		Button buttonCancel = new Button("Cancel");
		Button buttonSave = new Button("Save");
		HBox buttonContainer = new HBox();
		buttonContainer.setPrefWidth(300);
		
		buttonCancel.setAlignment(Pos.CENTER_LEFT);
		buttonSave.setAlignment(Pos.CENTER_RIGHT);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Book selectedBook = bbb.getCurrentValue();
        		LibraryMember selectedMember = lll.getCurrentValue();
        		if(null == checkoutDate.getValue() || null == dueDate.getValue() || null == selectedBook || null == selectedMember) {
        			HelpWindow.showAlert("Pleaes enter all checkout information!");
        			return;
        		}
        		SystemController sc = new SystemController();
        		try {
					sc.checkoutABook(selectedBook, selectedMember.getMemberId(), java.sql.Date.valueOf(checkoutDate.getValue()), java.sql.Date.valueOf(dueDate.getValue()));
				} catch (LibrarySystemException e1) {
					HelpWindow.showAlert(e1.getMessage());
					return;
				}
        		CheckoutWindow.run(); 
        	   
        	}
        });
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		CheckoutWindow.run();	   
        	}
        });
		buttonContainer.getChildren().add(buttonCancel);
		buttonContainer.getChildren().add(buttonSave);
		
		gridForm.add(buttonContainer,0,6);
		
		generalContainer.getChildren().add(gridForm);
		topContainer.getChildren().add(generalContainer);
		
		Scene scene = new Scene(topContainer, 400,600);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }

	public static void run() {
		// TODO Auto-generated method stub
		Start.hideAllWindows();
			if(!AddCheckoutWindow.INSTANCE.isInitialized()) {
				AddCheckoutWindow.INSTANCE.init();
   			}
			AddCheckoutWindow.INSTANCE.clear();
			AddCheckoutWindow.INSTANCE.show();
	}
	
	
}
