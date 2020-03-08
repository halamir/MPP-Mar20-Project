package ui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class AutoCompleteBox<T> implements EventHandler {
	private ComboBox<T> comboBox;
	final private ObservableList<T> data;
	private Integer sid;
	private T currentValue = null;
	public AutoCompleteBox(final ComboBox<T> comboBox) {
		this.comboBox = comboBox;
		this.data = comboBox.getItems();

		this.doAutoCompleteBox();
	}

	public AutoCompleteBox(final ComboBox<T> comboBox, Integer sid) {
		this.comboBox = comboBox;
		this.data = comboBox.getItems();
		this.sid = sid;

		this.doAutoCompleteBox();
	}

	private void doAutoCompleteBox() {
		this.comboBox.setEditable(true);
		this.comboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {// mean onfocus
				this.comboBox.show();
			}
		});

		this.comboBox.getEditor().setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				if (event.getClickCount() == 2) {
					return;
				}
			}
			this.comboBox.show();
		});

		this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int currentIndex = (int) newValue;
			if(currentIndex > -1) {
				setCurrentValue(this.comboBox.getItems().get(currentIndex));
			}
			moveCaret(this.comboBox.getEditor().getText().length());
		});

		this.comboBox.setOnKeyPressed(t -> comboBox.hide());

		this.comboBox.setOnKeyReleased(AutoCompleteBox.this);

		if (this.sid != null)
			this.comboBox.getSelectionModel().select(this.sid);
	}

	private void setItems() {
		ObservableList<T> list = FXCollections.observableArrayList();

		for (Object datum : this.data) {
			String s = this.comboBox.getEditor().getText().toLowerCase();
			if (datum.toString().toLowerCase().contains(s.toLowerCase())) {
				list.add((T) datum);
			}
		}

		if (list.isEmpty())
			this.comboBox.hide();

		this.comboBox.setItems(list);
		this.comboBox.show();
	}

	private void moveCaret(int textLength) {
		this.comboBox.getEditor().positionCaret(textLength);
	}

	@Override
	public void handle(Event arg) {
		KeyEvent event = (KeyEvent) arg;
		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.RIGHT
				|| event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
				|| event.getCode() == KeyCode.TAB) {
			return;
		}

		if (event.getCode() == KeyCode.BACK_SPACE) {
			String str = this.comboBox.getEditor().getText();
			if (str != null && str.length() > 0) {
				str = str.substring(0, str.length() - 1);
			}
			if (str != null) {
				this.comboBox.getEditor().setText(str);
				moveCaret(str.length());
			}
			this.comboBox.getSelectionModel().clearSelection();
		}

		if (event.getCode() == KeyCode.ENTER && comboBox.getSelectionModel().getSelectedIndex() > -1)
			return;

		setItems();
	}

	public T getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(T currentValue) {
		this.currentValue = currentValue;
	}
}