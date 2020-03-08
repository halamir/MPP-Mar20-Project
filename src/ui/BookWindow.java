package ui;

import business.Book;
import business.SystemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BookWindow extends Stage implements LibWindow {
	public static final BookWindow INSTANCE = new BookWindow();
	
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
	
	static Book selectedBook; 

	/* This class is a singleton */
    private BookWindow () {}
    
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
		Label labelTitulo = new Label("Books ");
		labelTitulo.setId("window-title");
//		Button buttonOverdue = new Button("Search Overdue");
		Button buttonAdd = new Button("Add");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		System.out.println("On development");
        		//AddBookWindow.run();        	   
        	}
        });
		HBox buttonContainer = new HBox();
		buttonContainer.setPrefWidth(500);

		//buttonContainer.getChildren().add(buttonAdd);
		//buttonContainer.getChildren().add(buttonOverdue);
		//newStack.getChildren().add(labelUsuario);
		topContainer.getChildren().add(basicStack);
		generalContainer.getChildren().add(labelTitulo);
		generalContainer.getChildren().add(buttonContainer);
		generalContainer.getChildren().add(createTable());
		topContainer.getChildren().add(generalContainer);
		
		Scene scene = new Scene(topContainer, 800,500);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }
    static TableView<Book> tableMember;
    public static TableView<Book> createTable() {
    	//Members
    	TableColumn<Book,String> bookISBN = new TableColumn<>("ISBN");
    	bookISBN.setMinWidth(180);
    	bookISBN.setCellValueFactory(new PropertyValueFactory("isbn") ) ;

    	TableColumn<Book,String> bookTitle = new TableColumn<>("Title");
    	bookTitle.setMinWidth(180);
    	bookTitle.setCellValueFactory(new PropertyValueFactory("title") ) ;
    	
    	TableColumn<Book,String> bookMaxLength = new TableColumn<>("Available Copies");
    	bookMaxLength.setMinWidth(180);

    	bookMaxLength.setCellValueFactory(new Callback<CellDataFeatures<Book, String>,
                ObservableValue<String>>() {  
			@Override  
			public ObservableValue<String> call(CellDataFeatures<Book, String> data){  
			return new SimpleStringProperty( data.getValue().getNumAvailabeCopies()+"" );  
			}
		});
    	tableMember = new TableView();
    	tableMember.setItems( getMembersTest());
    	

    	tableMember.getColumns().addAll(bookISBN,bookTitle,bookMaxLength);
    	addButtonToTable();addButtonPlusToTable();
    	return tableMember;
    }
	private static ObservableList<Book> getMembersTest() {
		// TODO Auto-generated method stub
		ObservableList<Book> asd = FXCollections.observableArrayList();
		SystemController sc = new SystemController();
		asd.addAll(sc.allBooks());
		return asd;
	}

    private static void addButtonPlusToTable() {
        TableColumn<Book, Void> colBtn = new TableColumn("Add more copies");

        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button("More");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            selectedBook = tableMember.getItems().get(getIndex());
                        	AddMoreCopiesBookWindow.run();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                    	super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableMember.getColumns().add(colBtn);

    }
    private static void addButtonToTable() {
        TableColumn<Book, Void> colBtn = new TableColumn("Add copies");

        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button("+");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book book = tableMember.getItems().get(getIndex());
                            SystemController sc = new SystemController();
                            sc.addBookCopies(book, 1);
                            tableMember.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                    	super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableMember.getColumns().add(colBtn);

    }
    
	public static void run() {
		// TODO Auto-generated method stub
		Start.hideAllWindows();
			if(!BookWindow.INSTANCE.isInitialized()) {
				BookWindow.INSTANCE.init();
   			}
			BookWindow.INSTANCE.clear();
			BookWindow.INSTANCE.show();
	}
	
	
}
