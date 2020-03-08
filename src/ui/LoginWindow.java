package ui;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow extends Stage implements LibWindow {
	public static final LoginWindow INSTANCE = new LoginWindow();
	
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
    private LoginWindow () {}
    
    public void tryLogin(String user,String passs) {
    	ControllerInterface c = new SystemController();
		try {
			c.login(user, passs);
//			c.login("103", "111");
			
			Start.hideAllWindows();
			String role = SystemController.currentAuth.toString();
			switch(role) {
			case "LIBRARIAN":
				CheckoutWindow.run();
				break;
			case "ADMIN":
				BookWindow.run();
				break;
			case "BOTH":
				BookWindow.run();
				break;
			}
		} catch (LoginException e) {
			messageBar.setFill(Start.Colors.red);
			messageBar.setText("Error! " + e.getMessage());
		}
    	
    }
    
    public void init() {  

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();
		topContainer.setAlignment(Pos.CENTER);
		VBox loginHolder = new VBox();
		 loginHolder.setMaxHeight(100);
		 loginHolder.setMaxWidth(350);
		loginHolder.setPadding(new Insets(10,20,10,20));
		loginHolder.setAlignment(Pos.CENTER);
		loginHolder.setId("login-container"); 
		topContainer.getChildren().add(loginHolder); 
		
        Text scenetitle = new Text("Welcome");
        scenetitle.setId("title-login");
        scenetitle.setFill(Color.WHITE);

        loginHolder.getChildren().add(scenetitle);

        Text sceneSubtitle = new Text("'The key to knowledge is in every book' ");
        sceneSubtitle.setId("sub-title-login");
        sceneSubtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 15));  
        sceneSubtitle.setFill(Color.WHITE);
        loginHolder.getChildren().add(sceneSubtitle ); 

        TextField userTextField = new TextField();
        userTextField.setPromptText("User Name");
        userTextField.setId("inputUser");
        loginHolder.getChildren().add(userTextField);

        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Password");
        pwBox.setId("inputPass");
        loginHolder.getChildren().add(pwBox );

        VBox.setMargin(scenetitle, new Insets(0, 0, 20, 0));
        VBox.setMargin(sceneSubtitle, new Insets(0, 0, 20, 0));
        VBox.setMargin(userTextField, new Insets(0, 0, 10, 0));
        VBox.setMargin(pwBox, new Insets(0, 0, 20, 0));
        
        Button loginBtn = new Button("Log in");
        loginBtn.setId("login-button");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(loginBtn);
        loginHolder.getChildren().add(hbBtn );

        HBox messageBox = new HBox(10);
        messageBox.setId("error-text");
        messageBox.setAlignment(Pos.BOTTOM_CENTER);
        messageBox.getChildren().add(messageBar);;
        loginHolder.getChildren().add(messageBox );
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                	tryLogin(userTextField.getText().trim(),pwBox.getText().trim());                	
                }
            }
        });
        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                	tryLogin(userTextField.getText().trim(),pwBox.getText().trim());                	
                }
            }
        });
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		 tryLogin(userTextField.getText().trim(),pwBox.getText().trim());
        	   
        	}
        });

         
		Scene scene = new Scene(topContainer, 700,400);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);

        scenetitle.requestFocus();
    }
	
	
}
