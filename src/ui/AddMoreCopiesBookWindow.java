package ui;

import java.text.NumberFormat;

import business.Book;
import business.LibraryMember;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.components.AutoCompleteBox;

public class AddMoreCopiesBookWindow extends Stage implements LibWindow {
	public static final AddMoreCopiesBookWindow INSTANCE = new AddMoreCopiesBookWindow();

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
	private AddMoreCopiesBookWindow() {
	}

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
		generalContainer.setPadding(new Insets(10, 10, 10, 10));
		VBox.setMargin(generalContainer, new Insets(20, 20, 20, 20));

		Label labelTitulo = new Label("Add Book copies ");
		labelTitulo.setId("window-title");
		GridPane gridForm = new GridPane();
		gridForm.setPrefHeight(400);
		gridForm.setPrefWidth(500);
		gridForm.setVgap(10);
		gridForm.add(labelTitulo, 0, 1);
		gridForm.setAlignment(Pos.CENTER);
		TextField numbreCopies = new TextField();
		numbreCopies.setPromptText("Number copies");
		numbreCopies.setId("checkoutDate");
		numbreCopies.setText(5 + "");
		numbreCopies.setPrefWidth(300);

		gridForm.add(numbreCopies, 0, 2);

		Button buttonCancel = new Button("Cancel");
		Button buttonSave = new Button("Save");
		HBox buttonContainer = new HBox();
		buttonContainer.setPrefWidth(300);

		buttonCancel.setAlignment(Pos.CENTER_LEFT);
		buttonSave.setAlignment(Pos.CENTER_RIGHT);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SystemController sc = new SystemController();
				try {
					int numCop = Integer.parseInt(numbreCopies.getText());
					if (numCop < 1)
						throw new NumberFormatException();
					sc.addBookCopies(BookWindow.selectedBook, numCop);
				} catch (NumberFormatException nfe) {
					HelpWindow.showAlert("Number of copies must be an integer greater than 0!");
					return;
				}
				BookWindow.run();

			}
		});
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookWindow.run();
			}
		});
		buttonContainer.getChildren().add(buttonCancel);
		buttonContainer.getChildren().add(buttonSave);

		gridForm.add(buttonContainer, 0, 6);

		generalContainer.getChildren().add(gridForm);
		topContainer.getChildren().add(generalContainer);

		Scene scene = new Scene(topContainer, 400, 600);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}

	public static void run() {
		// TODO Auto-generated method stub
		Start.hideAllWindows();
		if (!AddMoreCopiesBookWindow.INSTANCE.isInitialized()) {
			AddMoreCopiesBookWindow.INSTANCE.init();
		}
		AddMoreCopiesBookWindow.INSTANCE.clear();
		AddMoreCopiesBookWindow.INSTANCE.show();
	}

}
