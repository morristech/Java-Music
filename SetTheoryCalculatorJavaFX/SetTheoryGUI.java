import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import java.util.Arrays;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/*
if i enabled the user to submit a forte-number,
this might be tricky when Rahn radio is selected

i could have done the normal form a bit simpler:
after getting the interval array with dupes,
just make a two-dim-array numDupes in length.
this'll hold all the potential candidates.
then just do a for loop like my prime form calculations
*/

public class SetTheoryGUI extends Application {
	GridPane gridPane, algorithmRadioGridPane,
		inputRadioGridPane, calcResetPane, themePane;
	Button calculate_button, copy_nf_button,
		copy_pf_button, copy_icv_button,
		copy_forte_button, copy_transpose_button,
		copy_log_button, reset_button;
	Button[] buttonArray;
	RadioButton forte_radio, rahn_radio, normal_input_radio,
		real_time_radio, rahn_theme_radio, forte_theme_radio;
	ToggleGroup algorithm_group, input_group, theme_group;
	Label input_label, algorithm_label, nf_label,
		pf_label, icv_label, forte_label, log_label,
		input_options_label, theme_label;
	Label[] labelArray;
	Labeled[] labelsAndRadios;
	TextField input_textfield, nf_textfield,
		pf_textfield, icv_textfield, forte_textfield,
		transpose_textfield;
	TextInputControl[] textFieldTextAreaArray;
	Boolean printLog_nf, printLog_pf;
	ComboBox<String> transpose_combobox;
	String tn, tni, integerValueInZeroToTwelve;
	Slider slider;
	Scene scene;
	TextArea log;
	Font log_font;
	int[] normalForm, primeForm, intervalVector,
		sliderTransposition;
	int[][][] forte_set_classes;
	String[][] forte_numbers;
	Clipboard clipboard;
	ClipboardContent content;
	@Override
	public void start(Stage primaryStage) {
		setVariables();
		makeForteNumbers();
		setColors();
		setListeners();
		setNodeAlignments();
		addNodesToPanes();

		scene = new Scene(gridPane);
		scene.getStylesheets().add("styles.css");
		input_textfield.requestFocus(); //this only works after Scene is created
		primaryStage.setTitle("Nick's Pitch Class Set Theory Calculator");
		primaryStage.setScene(scene);
		primaryStage.show();
		transpose_combobox.show(); //had to do this weirdness because the css of
		transpose_combobox.hide(); //the combobox only takes effect the second time i click it.
					//see http://stackoverflow.com/questions/24664701/text-color-on-javafx-combobox-items-change-only-after-first-selection
	}
	public void doEverything() {
		log.setText(null);
		printLog_nf = true;
		int[] setArray = getAndValidateUserInput(input_textfield.getText());
		String formatted = String.valueOf(p(setArray));
		input_textfield.setText(formatted);

		normalForm = getNormalForm(setArray);
		String nf_formatted = String.valueOf(p(normalForm));
		nf_textfield.setText("{" + nf_formatted + "}");
		log.appendText("\nTherefore, the normal form is: " + "{" + String.valueOf(pAlt(normalForm)) + "}");
		printLog_nf = false;

		primeForm = getPrimeForm(normalForm);
		String pf_formatted = String.valueOf(p(primeForm));
		pf_textfield.setText("[" + pf_formatted + "]");

		intervalVector = getIntervalVector(normalForm);
		icv_textfield.setText("<" + p(intervalVector) + ">");

		getForteNumber(primeForm);

		sliderTransposition = getNormalForm(handleSlider(normalForm));
		transpose_textfield.setText("{" + p(sliderTransposition) + "}");
		if (normal_input_radio.isSelected()) {
			input_textfield.requestFocus();
			input_textfield.selectAll();
		}
		log.positionCaret(0);
	}
	public void addNodesToPanes() {
		gridPane.add(input_label, 0, 0);
		gridPane.add(input_textfield, 1, 0);
	//	gridPane.add(calculate_button, 2, 0);
		calcResetPane.add(calculate_button, 0, 0);
		calcResetPane.add(reset_button, 1, 0);
		inputRadioGridPane.add(input_options_label, 0, 0);
		inputRadioGridPane.add(normal_input_radio, 0, 1);
		inputRadioGridPane.add(real_time_radio, 1, 1);
		algorithmRadioGridPane.add(algorithm_label, 0, 0);
		algorithmRadioGridPane.add(forte_radio, 0, 1);
		algorithmRadioGridPane.add(rahn_radio, 1, 1);
		gridPane.add(calcResetPane, 2, 0);
		gridPane.add(inputRadioGridPane, 1, 1);
		gridPane.add(algorithmRadioGridPane, 2, 1);
		gridPane.add(nf_label, 0, 2);
		gridPane.add(nf_textfield, 1, 2);
		gridPane.add(copy_nf_button, 2, 2);
		gridPane.add(pf_label, 0, 3);
		gridPane.add(pf_textfield, 1, 3);
		gridPane.add(copy_pf_button, 2, 3);
		gridPane.add(icv_label, 0, 4);
		gridPane.add(icv_textfield, 1, 4);
		gridPane.add(copy_icv_button, 2, 4);
		gridPane.add(forte_label, 0, 5);
		gridPane.add(forte_textfield, 1, 5);
		gridPane.add(copy_forte_button, 2, 5);
		gridPane.add(transpose_combobox, 0, 6);
		gridPane.add(transpose_textfield, 1, 6);
		gridPane.add(copy_transpose_button, 2, 6);
		gridPane.add(slider, 0, 7);
		gridPane.add(log_label, 0, 8);
		themePane.add(theme_label, 0, 0);
		themePane.add(forte_theme_radio, 0, 1);
		themePane.add(rahn_theme_radio, 1, 1);
		//gridPane.add(themePane, 2, 8);
		gridPane.add(log, 0, 9);
	}
	public void setVariables() {
		gridPane = new GridPane();
		algorithmRadioGridPane = new GridPane();
		inputRadioGridPane = new GridPane();
		calcResetPane = new GridPane();
		themePane = new GridPane();
		algorithmRadioGridPane.setPadding(new Insets(0));
		algorithmRadioGridPane.setVgap(0);
		inputRadioGridPane.setPadding(new Insets(0));
		inputRadioGridPane.setVgap(0);
		gridPane.setPadding(new Insets(15)); //padding like padding in css
		gridPane.setHgap(15);	//like margin: 0 5 0 5; in css (left/right)
		gridPane.setVgap(15);	//like margin: 5 0 5 0; in css (top/bottom)

		clipboard = Clipboard.getSystemClipboard();
		content = new ClipboardContent();
		calculate_button = new Button("Calculate");
		reset_button = new Button("Reset");
		copy_nf_button = new Button("Copy");
		copy_pf_button = new Button("Copy");
		copy_icv_button = new Button("Copy");
		copy_forte_button = new Button("Copy");
		copy_transpose_button = new Button("Copy");
		copy_log_button = new Button("Copy");
		forte_radio = new RadioButton("Forte ");
		rahn_radio = new RadioButton("Rahn");
		normal_input_radio = new RadioButton("Normal  ");
		real_time_radio = new RadioButton("Real-time");
		rahn_theme_radio = new RadioButton("B.A.T.");
		forte_theme_radio = new RadioButton("S.A.M. ");
		theme_group = new ToggleGroup();
		algorithm_group = new ToggleGroup();
		input_group = new ToggleGroup();
		forte_radio.setToggleGroup(algorithm_group);
		forte_radio.setSelected(true);
		rahn_radio.setToggleGroup(algorithm_group);
		normal_input_radio.setToggleGroup(input_group);
		normal_input_radio.setSelected(true);
		real_time_radio.setToggleGroup(input_group);
		rahn_theme_radio.setToggleGroup(theme_group);
		forte_theme_radio.setToggleGroup(theme_group);
		rahn_theme_radio.setSelected(true);
		input_label = new Label("Enter a collection of pitch-classes: " +
			"\n(Use 'A' or 't' and 'B' or 'e' for \nPCs 10 and 11, respectively)");
		theme_label = new Label("Color theme:");
		algorithm_label = new Label("Algorithm:");
		nf_label = new Label("Normal form:");
		pf_label = new Label("Prime form:");
		icv_label = new Label("Interval class vector:");
		forte_label = new Label("Forte number:");
		log_label = new Label("Console log:");
		input_options_label = new Label("Input:");
		input_textfield = new TextField();
		nf_textfield = new TextField();
		pf_textfield = new TextField();
		icv_textfield = new TextField();
		forte_textfield = new TextField();
		transpose_textfield = new TextField();
		transpose_combobox = new ComboBox<String>();
		tn = "Tn";
		tni = "TnI";
		transpose_combobox.getItems().addAll(tn, tni);
		transpose_combobox.setValue(tn);
		slider = new Slider(1, 1, 1);
		log = new TextArea();
		log_font = new Font("Courier New", 15);
		log.setFont(log_font);
		log.setPrefRowCount(13);
		labelArray = new Label[] {nf_label, pf_label,
			icv_label, forte_label, log_label};
		labelsAndRadios = new Labeled[] {forte_radio, rahn_radio,
			normal_input_radio, real_time_radio, input_options_label,
			algorithm_label, input_label, theme_label, forte_theme_radio,
			rahn_theme_radio};
		buttonArray = new Button[] {calculate_button, copy_nf_button,
			copy_pf_button, copy_icv_button,
			copy_forte_button, copy_transpose_button,
			copy_log_button, reset_button};
		textFieldTextAreaArray = new TextInputControl[] {input_textfield, nf_textfield,
		pf_textfield, icv_textfield, forte_textfield,
		transpose_textfield, log};
	}
	public void setNodeAlignments() {
		GridPane.setValignment(calculate_button, VPos.TOP);
	//	calculate_button.setVgap(0);
		GridPane.setValignment(input_textfield, VPos.TOP);
		GridPane.setValignment(input_label, VPos.TOP);
		GridPane.setValignment(algorithm_label, VPos.BOTTOM);
		GridPane.setValignment(algorithmRadioGridPane, VPos.TOP);
	//	input_textfield.setText("(0169AB)");
	//	calculate_button.setMaxWidth(Double.MAX_VALUE);
		transpose_combobox.setMaxWidth(85);
		GridPane.setColumnSpan(log, 3);
		GridPane.setColumnSpan(slider, 2);
		slider.setMin(0);
		slider.setMax(11);
		slider.setValue(0);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		slider.setBlockIncrement(1);
		nf_textfield.setEditable(false);
		pf_textfield .setEditable(false);
		icv_textfield.setEditable(false);
		forte_textfield.setEditable(false);
		transpose_textfield.setEditable(false);
		log.setEditable(false);
		algorithm_label.setPadding(new Insets(0, 0, 5, 0));
		algorithmRadioGridPane.setPadding(new Insets(-43, 0, 0, 0));
		input_options_label.setPadding(new Insets(0, 0, 5, 0));
		theme_label.setPadding(new Insets(0, 0, 5, 0));
		inputRadioGridPane.setPadding(new Insets(-43, 0, 0, 0));
		GridPane.setHalignment(algorithm_label, HPos.RIGHT);
		calcResetPane.setHgap(5);
		//input_textfield.setPromptText("Type here!");
	}
	public void setListeners() {
		copy_nf_button.setOnAction((ActionEvent e) -> {
			if (nf_textfield != null && nf_textfield.getText().length() >= 1) {
				content.putString(nf_textfield.getText());
				clipboard.setContent(content);
			}
		});
		copy_pf_button.setOnAction((ActionEvent e) -> {
			if (pf_textfield != null && pf_textfield.getText().length() >= 1) {
				content.putString(pf_textfield.getText());
				clipboard.setContent(content);
			}
		});
		copy_icv_button.setOnAction((ActionEvent e) -> {
			if (icv_textfield != null && icv_textfield.getText().length() >= 1) {
				content.putString(icv_textfield.getText());
				clipboard.setContent(content);
			}
		});
		copy_forte_button.setOnAction((ActionEvent e) -> {
			if (forte_textfield != null && forte_textfield.getText().length() >= 1) {
				content.putString(forte_textfield.getText());
				clipboard.setContent(content);
			}
		});
		copy_transpose_button.setOnAction((ActionEvent e) -> {
			if (transpose_textfield != null && transpose_textfield.getText().length() >= 1) {
				content.putString(transpose_textfield.getText());
				clipboard.setContent(content);
			}
		});
		calculate_button.setOnAction((ActionEvent e) -> {
			if (input_textfield.getText().length() >= 1) {
				doEverything();
			}
		});
		reset_button.setOnAction((ActionEvent e) -> {
			nullEveryField();
			input_textfield.requestFocus();
		});
		input_textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (input_textfield.getText().length() >= 1 && keyEvent.getCode() == KeyCode.ENTER)  {
					 doEverything();
				}
			}
		});
		input_textfield.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (real_time_radio.isSelected()) {
					if (input_textfield.getText().length() >= 1 &&
						keyEvent.getCode() != KeyCode.UP &&
						keyEvent.getCode() != KeyCode.DOWN &&
						keyEvent.getCode() != KeyCode.LEFT &&
						keyEvent.getCode() != KeyCode.RIGHT &&
						keyEvent.getCode() != KeyCode.CONTROL &&
						keyEvent.getCode() != KeyCode.SHIFT &&
						keyEvent.getCode() != KeyCode.ALT &&
						keyEvent.getCode() != KeyCode.ESCAPE &&
						keyEvent.getCode() != KeyCode.CAPS &&
						keyEvent.getCode() != KeyCode.TAB &&
						keyEvent.getCode() != KeyCode.HOME &&
						!new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN).match(keyEvent))  {
						 doEverything();
						 input_textfield.positionCaret(input_textfield.getText().length()); //puts cursor at end of input
					} else if (input_textfield.getText().length() == 0) {
						nullEveryField(); //if input_textfield is empty, everything else should also be empty
					}
				}
			}
		});
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
				Number oldValue, Number newValue) {
				if (input_textfield.getText().length() >= 1) {
					sliderTransposition = getNormalForm(handleSlider(normalForm));
					transpose_textfield.setText("{" + p(sliderTransposition) + "}");
				}
			}
        });
	   transpose_combobox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				if (input_textfield.getText().length() >= 1) {
					sliderTransposition = getNormalForm(handleSlider(normalForm));
					transpose_textfield.setText("{" + p(sliderTransposition) + "}");
				}
			}
		});
		algorithm_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
			Toggle old_toggle, Toggle new_toggle) {
				if (input_textfield.getText().length() >= 1) {
					doEverything();
				}
				input_textfield.requestFocus();
				input_textfield.positionCaret(input_textfield.getText().length());
			}
		});
		input_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
			Toggle old_toggle, Toggle new_toggle) {
				if (input_textfield.getText().length() >= 1) {
					doEverything();
				}
				input_textfield.requestFocus();
				input_textfield.positionCaret(input_textfield.getText().length());
			}
		});
		theme_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
			Toggle old_toggle, Toggle new_toggle) {
				setColors();
			}
		});
	}
	public int[] getAndValidateUserInput(String s) {
		String userInput = cleanUpUserInput(s.toLowerCase());
		int[] tempArray = convertStringInputToIntArray(userInput);
		int[] tempArray2 = removeDuplicates(tempArray, countDuplicates(tempArray));
		log.appendText("Input: (");
		log.appendText(String.valueOf(pAlt(tempArray2)) + ")");
		log.appendText("\n\nCalculating normal form...");
		return tempArray2;
	}
	public String cleanUpUserInput(String s) {
		s = s.replaceAll("t", "a");
		s = s.replaceAll("e", "b");
		s = s.replaceAll("[^0-9ab]+", "");
		return s;
	}
	public int[] convertStringInputToIntArray(String s) {
		int[] array = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			array[i] = Integer.parseInt(String.valueOf(s.charAt(i)), 12);
		}
		return sortIntArrayAscending(array);
	}
	public int[] sortIntArrayAscending(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[i]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		return array;
	}
	public int countDuplicates(int[] array) {
		int numberOfDuplicates = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] == array[i - 1]) {
				numberOfDuplicates++;
			}
		}
		return numberOfDuplicates;
	}
	public int[] removeDuplicates(int[] array, int numberOfDuplicates) {
		int[] duplicatesRemoved = new int[array.length - numberOfDuplicates];
		duplicatesRemoved[0] = array[0];
		for (int i = 1, j = 1; i < array.length && j < duplicatesRemoved.length; i++) {
			if (array[i] != array[i - 1]) {
				duplicatesRemoved[j] = array[i];
				j++;
			}
		}
		return duplicatesRemoved;
	}
	public int[] getNormalForm(int[] array) {
		/*Create a copy of the set for manipulation, leaving
		the original set in a preserved state.*/
		int[] arrayCopy = new int[array.length];
		copyIntArray(array, arrayCopy);
		int[] intervalArray = getIntervalArray(arrayCopy);
		if (printLog_nf) {
			log.appendText("\n\nRespective intervals from indices 0 to " + (array.length - 1) + " for every rotation: ");
			log.appendText("\n(" + String.valueOf(pAlt(intervalArray)) + ")");
		}
		int smallestElement = getSmallestElement(intervalArray),
			firstSmallestElementIndex = getFirstSmallestElementIndex(intervalArray, smallestElement);
		if (hasUniqueSmallestElement(intervalArray, smallestElement, firstSmallestElementIndex)) {
			//System.out.println("smallest element: " + smallestElement);
			//System.out.println("smallest element index: " + smallestElementIndex);
			rotate(arrayCopy, firstSmallestElementIndex);
		} else {
			/*In the event that the interval array doesn't have an obvious
			normal form candidate, i.e. intervalArray doesn't have a unique
			smallest element, a tiebreaker must be made. This will determine
			the true candidate for normal form (or determine that everything is
			a tie, e.g. in the case of a set like {0, 3, 6, 9}). In cases like
			this tie, the smallestElementIndex will begin at 0 and remain at 0
			after all the testing; this is a good thing.*/
			//System.out.println("smallest element (despite duplicates): " + smallestElement);
			int[] duplicateSmallestElementIndices = getSmallestDupeIndices(intervalArray, smallestElement);
			if (printLog_nf) {
				integerValueInZeroToTwelve = String.valueOf(smallestElement);
				if (smallestElement == 10) {
					integerValueInZeroToTwelve = "A";
				} else if (smallestElement == 11) {
					integerValueInZeroToTwelve = "B";
				}
				log.appendText("\nSmallest element: " + integerValueInZeroToTwelve);
				log.appendText("\nSmallest element indices: (" + String.valueOf(pAlt(duplicateSmallestElementIndices)) + ")");
				log.appendText("\n\nRotating the original set to these above indices...");
			}
			//System.out.println("duplicateSmallestElementIndices from intervalArray: ");
			//p(duplicateSmallestElementIndices);
			int[] customIntervals = new int[getNumberOfDupes(intervalArray, smallestElement)];
			/*The variable "until" must start be initialized as 1, since
			it's used to measure the intervallic distance from any array[0]
			until array[until]. It will keep increasing through the do-while
			loop below until the optimal normal form is found.*/
			int until = 0;
			if (forte_radio.isSelected()) {
				until = 1;
				do {
					getCustomIntervals(arrayCopy, duplicateSmallestElementIndices, customIntervals, until);
					if (printLog_nf) {
						log.appendText("\nIntervals from indices 0 to " + until + " for each candidate:\n");
						log.appendText("(" + String.valueOf(pAlt(customIntervals)) + ")");
					}
					//System.out.println("\nthe neighbour intervals from indices 0 to " + until);
					//p(customIntervals);
					smallestElement = getSmallestElement(customIntervals);
					firstSmallestElementIndex = getFirstSmallestElementIndex(customIntervals, smallestElement);
					until++;
				} while (!hasUniqueSmallestElement(customIntervals, smallestElement, firstSmallestElementIndex) &&
					until < arrayCopy.length - 1);
			} else if (rahn_radio.isSelected()) {
				until = arrayCopy.length - 2;
				do {
					getCustomIntervals(arrayCopy, duplicateSmallestElementIndices, customIntervals, until);
					if (printLog_nf) {
						log.appendText("\nIntervals from indices 0 to " + until + " for each candidate:\n");
						log.appendText("(" + String.valueOf(pAlt(customIntervals)) + ")");
					}
					smallestElement = getSmallestElement(customIntervals);
					firstSmallestElementIndex = getFirstSmallestElementIndex(customIntervals, smallestElement);
					until--;
				} while (!hasUniqueSmallestElement(customIntervals, smallestElement, firstSmallestElementIndex) && until >= 1);
			}
			if ((until == arrayCopy.length || until == arrayCopy.length - 1 || until == 0) && printLog_nf) {
				log.appendText("\n\nThe tiebreaker failed.");
			}
			rotate(arrayCopy, duplicateSmallestElementIndices[firstSmallestElementIndex]);
		}
		return arrayCopy;
	}
	/*This makes an "interval array" of an array. That is,
	if the array consisted of {0, 1, 3} the interval array
	would then be {3, 11, 10}. These latter elements represent
	the gaps between the boundary elements of each rotation
	of the former array. This is useful because it's an
	essential part of getting the normal form of a set.

	There will be as many intervals as there are elements within
	the original set, thus intervalArray.length will be == set.length.

	The set in this method ends up being rotated set.length times;
	the set thus returns to its original rotation after
	the last rotation is executed.*/
	public int[] getIntervalArray(int[] array) {
		int[] intervalArray = new int[array.length];
		for (int i = 0; i < intervalArray.length; i++) {
			intervalArray[i] = subtractEdgeIntervals(array);
			rotate(array);
		}
		return intervalArray;
	}
	/*This gets the intervallic difference between the first
	and last elements of an array, a.k.a. the edge/boundary
	elements. This value is then converted to conform to a
	modulo 12 universe, which tonal music uses.

	A negative number can often appear as a value for
	the integer variable "interval." This is because as a set gets
	rotated (to get an interval array, the set is rotated
	set.length times and each rotation calls subtractEdgeIntervals())
	subtracting the last element by the first
	could mean subtracting a smaller number by a larger number.
	Take for example, the set {0, 1, 6}:
	The interval array would ideally be {6, 11, 7},
	but the set goes through the roations:
	{1, 5, 6}, {5, 6, 1}, {6, 1, 5}.
	Note how calling subtractEdgeIntervals() for the second
	rotation means subtracting 1 - 5 = -4. I can't
	just simply use Math.abs() to get the correct interval
	because I want the distance from 5 to 1, which is 8.
	I can add 12 to negative numbers like -4 to get its
	actual value in modulo 12, and the return value below solves
	that issue.*/
	public int subtractEdgeIntervals(int[] array) {
		int interval = array[array.length - 1] - array[0];
		return interval >= 0 ? interval : interval + 12;
	}
	/*This method returns the difference between two
	elements of an array. Specifically, the difference between
	the index of the argument given to the parameter "n" and the first
	(zeroth) index of the array.

	This is used for breaking ties between candidates for normal form
	which yielded the more than one of the smallest possible edge intervals
	of its intervalArray.*/
	public int subtractCustomIntervals(int[] array, int n) {
		int interval = array[n] - array[0];
		return interval >= 0 ? interval : interval + 12;
	}
	/*This method packs a lot of content into just a few lines. It first
	rotates a given set to an index of the original set which is a
	candidate for its normal form. It can do this because
	duplicateSmallestElementIndices holds those values.
	The neighbourIntervals array is then assigned the intervallic
	distance from set[0] to set[n]. Afterwards, the set is in a
	problematic state: it has been rotated from its original position.
	The last line of this for loop "resets" it back to its original
	numerically ascending position by doing effectively a rotation
	of the inversional amount it was rotated in the first line of
	the for loop.*/
	public void getCustomIntervals(int[] array, int[] duplicateSmallestElementIndices,
		int[] customIntervals, int n) {
		if (printLog_nf) {
			log.appendText("\n\nRotated candidates for normal form:\n");
		}
		for (int i = 0; i < duplicateSmallestElementIndices.length; i++) {
			rotate(array, duplicateSmallestElementIndices[i]);
			customIntervals[i] = subtractCustomIntervals(array, n);
			if (printLog_nf) {
				log.appendText("(" + String.valueOf(pAlt(array)) + ")\n");
			}
			//System.out.println("\nrotation candidates for normal form:");
			//p(array);
			rotate(array, array.length - duplicateSmallestElementIndices[i]);
		}
	}
	/*This rotates a set one time.*/
	public void rotate(int[] array) {
		int firstElement = array[0];
		for (int i = 1; i < array.length; i++) {
			array[i - 1] = array[i];
		}
		array[array.length - 1] = firstElement;
	}
	/*This overloaded version of rotate() (above) rotates the
	set but repeatedly until a specific index of the original
	unrotated set is reached. E.g. for {1, 3, 4} if I called
	rotate(set, 1) it will rotate the set for it to become
	{3, 4, 1}. Basically, it just iterates the rotate() above
	however many times the value of the argument given to
	"rotateThisManyTimes" in the method below.*/
	public void rotate(int[] array, int rotateThisManyTimes) {
		for (int i = 0; i < rotateThisManyTimes; i++) {
			rotate(array);
		}
	}
	public int getSmallestElement(int[] array) {
		int smallestElement = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < smallestElement) {
				smallestElement = array[i];
			}
		}
		return smallestElement;
	}
	public int getFirstSmallestElementIndex(int[] array, int smallestElement) {
		int firstSmallestElementIndex = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == smallestElement) {
				firstSmallestElementIndex = i;
				break;
			}
		}
		return firstSmallestElementIndex;
	}
	public boolean hasUniqueSmallestElement(int[] array, int smallestElement, int firstSmallestElementIndex) {
		boolean hasUniqueSmallestElement = true;
		for (int i = 0; i < array.length; i++) {
			if (i == firstSmallestElementIndex) {
				continue;
			} else if (array[i] == smallestElement) {
				hasUniqueSmallestElement = false;
				break;
			}
		}
		if (printLog_nf) {
			log.appendText("\n\nThe above array has a unique smallest element present: " + hasUniqueSmallestElement);
			integerValueInZeroToTwelve = String.valueOf(smallestElement);
			if (smallestElement == 10) {
				integerValueInZeroToTwelve = "A";
			} else if (smallestElement == 11) {
				integerValueInZeroToTwelve = "B";
			}
			if (hasUniqueSmallestElement) {
				log.appendText("\nUnique smallest element: " + integerValueInZeroToTwelve + "\n");
				log.appendText("Rotating to candidate/index: " + firstSmallestElementIndex + "\n");
			}
		}
		//System.out.println("hasUniqueSmallestElement: " + hasUniqueSmallestElement);
		return hasUniqueSmallestElement;
	}
	/*This checks whether the smallest element in an array is
	unique. If the argument given is intervalArray, and there
	is indeed a unique smallest element, then getting the normal form of the
	original set is simply a matter of starting that original set from the
	same index as the  smallest element index of the interval array. For
	example, {0, 1, 9} has an interval array of {9, 11, 4}. The third index
	of this latter array is the smallest and is unique, therefore rotating
	the original set to start on its own third index gives us the normal form:
	{9, 0, 1}.

	If the smallest element of the interval array is not unique, it can
	get much more complicated to decide which index to start from for
	the original set to be in normal form.*/
	public boolean hasUniqueSmallestElement(int[] array) {
		int smallestElement = getSmallestElement(array),
			firstSmallestElementIndex = getFirstSmallestElementIndex(array, smallestElement);
		boolean hasUniqueSmallestElement = true;
		for (int i = 0; i < array.length; i++) {
			if (i == firstSmallestElementIndex) {
				continue;
			} else if (array[i] == smallestElement) {
				hasUniqueSmallestElement = false;
				break;
			}
		}
		//System.out.println("hasUniqueSmallestElement: " + hasUniqueSmallestElement);
		return hasUniqueSmallestElement;
	}
	/*In the event that an intervalArray has two or more duplicates
	of the smallest element, this method counts the number of
	these duplicates.*/
	public int getNumberOfDupes(int[] array, int smallestElement) {
		int numberOfDupes = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == smallestElement) {
				numberOfDupes++;
			}
		}
		return numberOfDupes;
	}
	/*This method gathers the indices of these smallest elements of
	intervalArray. For example, if the intervalArray were {9, 11, 10, 9, 9}
	(the result of a set {0, 1, 3, 6, 9}, the indicies of the smallest elements
	would be 0, 3, and 4. These are inserted into duplicateSmallestElementIndices,
	resulting in that latter array being simply {0, 3, 4}.

	The (N.B.) values of these elements thus define all the potential rotations'
	for the normal form of the original set. That is, if a value in
	duplicateSmallestElementIndices is 3, that means a rotation of the original
	set starting on index 3 could be the normal form.

	Don't mix up the purpose of the values of this array with other indices!*/
	public int[] getSmallestDupeIndices(int[] array, int smallestElement) {
		int[] tempArray = new int[getNumberOfDupes(array, smallestElement)];
		for (int i = 0, j = 0; i < array.length && j < tempArray.length; i++) {
			if (array[i] == smallestElement) {
				tempArray[j] = i;
				j++;
			}
		}
		return tempArray;
	}
	/*Makes a copy of an array of ints but with each resulting in distinct
	reference addresses.*/
	public void copyIntArray(int[] source, int[] copy) {
		for (int i = 0; i < source.length; i++) {
			copy[i] = source[i];
		}
	}
	/*This method simply prints an array of ints.
	I could have made a toString() method, but typing "p(set);" is a lot
	faster than "System.out.println(set);"!*/
	public StringBuilder p(int[] array) {
		StringBuilder formatted = new StringBuilder();
	//	formatted.append("(");
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 10) {
				formatted.append("A");
			} else if (array[i] == 11) {
				formatted.append("B");
			} else {
				formatted.append(array[i]);
			}
		}
	//	formatted.append(")");
		return formatted;
	}
	public StringBuilder pAlt(int[] array) {
		StringBuilder formatted = new StringBuilder();
	//	formatted.append("(");
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] == 10) {
				formatted.append("A, ");
			} else if (array[i] == 11) {
				formatted.append("B, ");
			} else {
				formatted.append(array[i] + ", ");
			}
		}
		int lastElement = array[array.length - 1];
		if (lastElement == 10) {
			formatted.append("A");
		} else if (lastElement == 11) {
			formatted.append("B");
		} else {
			formatted.append(lastElement);
		}
	//	formatted.append(")");
		return formatted;
	}
	public int[] getPrimeForm(int[] array) {
		int[] potentialPrimeForm1 = new int[array.length],
			  potentialPrimeForm2 = new int[array.length];

		copyIntArray(array, potentialPrimeForm1);
		copyIntArray(array, potentialPrimeForm2);

		transposeToStartWithZero(potentialPrimeForm1);
		TnI(potentialPrimeForm2, 0);
		sortIntArrayAscending(potentialPrimeForm2);
		potentialPrimeForm2 = getNormalForm(potentialPrimeForm2);
		transposeToStartWithZero(potentialPrimeForm2);

		int sum1 = sumSetPitches(potentialPrimeForm1),
			sum2 = sumSetPitches(potentialPrimeForm2);

		log.appendText("\n\nCalculating prime form...\n\n");
		log.appendText("Prime form candidate(s):\n");
		if (Arrays.equals(potentialPrimeForm1, potentialPrimeForm2)) {
			log.appendText("[" + String.valueOf(pAlt(potentialPrimeForm1)) + "]\n\nThere is only one candidate. It is therefore the prime form.\n");
			return potentialPrimeForm1;
		} else {
			log.appendText("[" + String.valueOf(pAlt(potentialPrimeForm1)) + "]\n");
			log.appendText("[" + String.valueOf(pAlt(potentialPrimeForm2)) + "]\n\n");
		}

	//	System.out.println("\npotentialPrimeForm1: ");
	//	p(potentialPrimeForm1);
	//	System.out.println("sum: " + sum1);
	//	System.out.println("potentialPrimeForm1: ");
	//	p(potentialPrimeForm2);
	//	System.out.println("sum: " + sum2);

	//	System.out.println("\nPrime form of the set:");
		return primeFormTiebreaker(potentialPrimeForm1, potentialPrimeForm2);
	}
	public int[] primeFormTiebreaker(int[] candidate1, int[] candidate2) {
		for (int i = 1; i < candidate1.length; i++) { //candidate1.length == candidate2.length
			log.appendText("Interval from indices 0 to " + i + " for each candidate: (" +
				(candidate1[i] - candidate1[0]) + ", " +
				(candidate2[i] - candidate2[0]) + ")\n");
			if (candidate1[i] - candidate1[0] <
				candidate2[i] - candidate2[0]) {
				log.appendText("The first candidate has a smaller interval.\n\n" +
					"The prime form is therefore: [" + pAlt(candidate1) + "]\n");
				return candidate1;
			} else if (candidate1[i] - candidate1[i - 1] >
				candidate2[i] - candidate2[i - 1]) {
				log.appendText("The second candidate has a smaller interval.\n\n" +
					"The prime form is therefore: [" + pAlt(candidate2) + "]\n");
				return candidate2;
			} else {
				log.appendText("It is a tie.\n\n");
			}
		}
		return new int[] {}; //useless. a consequence of the for loop returns
	}
	public void TnI(int[] array, int n) {
		while (n >= 12) {
			n -= 12;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] > 0) {
				array[i] = 12 - array[i]; //11, 7, 3, 2, 1
			}
		}
		if (n > 0) {
			Tn(array, n);
		}
		sortIntArrayAscending(array);
	}
	public void Tn(int[] array, int n) {
		for (int i = 0; i < array.length; i++) {
			array[i] += n;
			if (array[i] >= 12) {
				array[i] -= 12;
			}
		}
		sortIntArrayAscending(array);
	}
	public void transposeToStartWithZero(int[] array) {
		int firstPitchClass = array[0];
		int transposeBy = 12 - firstPitchClass;
		Tn(array, transposeBy);
	}
	public int sumSetPitches(int[] array) {
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}
	public int[] getIntervalVector(int[] array) {
		return getManyIntervals(array);
	}
	public int getOneInterval(int[] set_array, int a, int b) {
		return Math.abs(set_array[a] - set_array[b + 1]);
	}
	public int[] getManyIntervals(int[] set_array) {
		int[] interval_vector = {0, 0, 0, 0, 0, 0};
		int distance = 0;
		for (int i = 0; i < set_array.length - 1; i++) {
			for (int j = i; j < set_array.length - 1; j++) {
				distance = getOneInterval(set_array, i, j);
				if (distance > 6) {
					distance = getInversion(distance);
				}
				switch (distance) {
					case 1: interval_vector[0]++; break;
					case 2: interval_vector[1]++; break;
					case 3: interval_vector[2]++; break;
					case 4: interval_vector[3]++; break;
					case 5: interval_vector[4]++; break;
					case 6: interval_vector[5]++; break;
				}
			}
		}
		return interval_vector;
	}
	public int getInversion(int distance) {
		return 12 - distance;
	}
	public int[] handleSlider(int[] array) {
		if (transpose_combobox.getValue().toString().equals(tn)) {
			return getSliderTn(array);
		} else {
			return getSliderTnI(array);
		}
	}
	public int[] getSliderTn(int[] array) {
		int[] transposed_array = new int[array.length];
		copyIntArray(array, transposed_array);
		Tn(transposed_array, (int)slider.getValue());
		return transposed_array;
	}
	public int[] getSliderTnI(int[] array) {
		int[] transposed_inverted_array = new int[array.length];
		copyIntArray(array, transposed_inverted_array);
		TnI(transposed_inverted_array, (int)slider.getValue());
		return transposed_inverted_array;
	}
	public void nullEveryField() {
		input_textfield.setText(""); //empty strings or else it blows up from with exceptions
		nf_textfield.setText("");
		pf_textfield.setText("");
		icv_textfield.setText("");
		forte_textfield.setText("");
		transpose_textfield.setText("");
		log.setText("");
	}
	public void getForteNumber(int[] array) {
		forte_textfield.setText("N/A");
		if (array.length < 3 || array.length > 9) {
			return;
		}
		int input_len = array.length - 3; //minus 3 because the big set class array starts on trichords, i.e. its [0] is for trichords.
		for (int i = 0; i < forte_set_classes[input_len].length; i++) {
			if (Arrays.equals(array, forte_set_classes[input_len][i])) { //I don't delve into the 3rd dimension because that would just be the individual primitive ints
				forte_textfield.setText(forte_numbers[input_len][i]);
				break;
			}
		}
	}
	public void makeForteNumbers() {
		forte_set_classes = new int[][][] {
			{ //trichords
				{0, 1, 2},
				{0, 1, 3},
				{0, 1, 4},
				{0, 1, 5},
				{0, 1, 6},
				{0, 2, 4},
				{0, 2, 5},
				{0, 2, 6},
				{0, 2, 7},
				{0, 3, 6},
				{0, 3, 7},
				{0, 4, 8}
			},
			{ //tetrachords
				{0, 1, 2, 3},
				{0, 1, 2, 4},
				{0, 1, 3, 4},
				{0, 1, 2, 5},
				{0, 1, 2, 6},
				{0, 1, 2, 7},
				{0, 1, 4, 5},
				{0, 1, 5, 6},
				{0, 1, 6, 7},
				{0, 2, 3, 5},
				{0, 1, 3, 5},
				{0, 2, 3, 6},
				{0, 1, 3, 6},
				{0, 2, 3, 7},
				{0, 1, 4, 6},
				{0, 1, 5, 7},
				{0, 3, 4, 7},
				{0, 1, 4, 7},
				{0, 1, 4, 8},
				{0, 1, 5, 8},
				{0, 2, 4, 6},
				{0, 2, 4, 7},
				{0, 2, 5, 7},
				{0, 2, 4, 8},
				{0, 2, 6, 8},
				{0, 3, 5, 8},
				{0, 2, 5, 8},
				{0, 3, 6, 9},
				{0, 1, 3, 7}
			},
			{ //pentachords
				{0, 1, 2, 3, 4},
				{0, 1, 2, 3, 5},
				{0, 1, 2, 4, 5},
				{0, 1, 2, 3, 6},
				{0, 1, 2, 3, 7},
				{0, 1, 2, 5, 6},
				{0, 1, 2, 6, 7},
				{0, 2, 3, 4, 6},
				{0, 1, 2, 4, 6},
				{0, 1, 3, 4, 6},
				{0, 2, 3, 4, 7},
				{0, 1, 3, 5, 6},
				{0, 1, 2, 4, 8},
				{0, 1, 2, 5, 7},
				{0, 1, 2, 6, 8},
				{0, 1, 3, 4, 7},
				{0, 1, 3, 4, 8},
				{0, 1, 4, 5, 7},
				{0, 1, 3, 6, 7},
				{0, 1, 3, 7, 8}, //forte 5-20
				{0, 1, 5, 6, 8}, //rahn 5-20
				{0, 1, 4, 5, 8},
				{0, 1, 4, 7, 8},
				{0, 2, 3, 5, 7},
				{0, 1, 3, 5, 7},
				{0, 2, 3, 5, 8},
				{0, 2, 4, 5, 8},
				{0, 1, 3, 5, 8},
				{0, 2, 3, 6, 8},
				{0, 1, 3, 6, 8},
				{0, 1, 4, 6, 8},
				{0, 1, 3, 6, 9},
				{0, 1, 4, 6, 9},
				{0, 2, 4, 6, 8},
				{0, 2, 4, 6, 9},
				{0, 2, 4, 7, 9},
				{0, 1, 2, 4, 7},
				{0, 3, 4, 5, 8},
				{0, 1, 2, 5, 8}
			},
			{ //hexachords
				{0, 1, 2, 3, 4, 5},
				{0, 1, 2, 3, 4, 6},
				{0, 1, 2, 3, 5, 6},
				{0, 1, 2, 4, 5, 6},
				{0, 1, 2, 3, 6, 7},
				{0, 1, 2, 5, 6, 7},
				{0, 1, 2, 6, 7, 8},
				{0, 2, 3, 4, 5, 7},
				{0, 1, 2, 3, 5, 7},
				{0, 1, 3, 4, 5, 7},
				{0, 1, 2, 4, 5, 7},
				{0, 1, 2, 4, 6, 7},
				{0, 1, 3, 4, 6, 7},
				{0, 1, 3, 4, 5, 8},
				{0, 1, 2, 4, 5, 8},
				{0, 1, 4, 5, 6, 8},
				{0, 1, 2, 4, 7, 8},
				{0, 1, 2, 5, 7, 8},
				{0, 1, 3, 4, 7, 8},
				{0, 1, 4, 5, 8, 9},
				{0, 2, 3, 4, 6, 8},
				{0, 1, 2, 4, 6, 8},
				{0, 2, 3, 5, 6, 8},
				{0, 1, 3, 4, 6, 8},
				{0, 1, 3, 5, 6, 8},
				{0, 1, 3, 5, 7, 8},
				{0, 1, 3, 4, 6, 9},
				{0, 1, 3, 5, 6, 9},
				{0, 1, 3, 6, 8, 9}, //forte 6-Z29
				{0, 2, 3, 6, 7, 9}, //rahn 6-Z29
				{0, 1, 3, 6, 7, 9},
				{0, 1, 3, 5, 8, 9}, //forte 6-31
				{0, 1, 4, 5, 7, 9}, //rahn 6-31
				{0, 2, 4, 5, 7, 9},
				{0, 2, 3, 5, 7, 9},
				{0, 1, 3, 5, 7, 9},
				{0, 2, 4, 6, 8, 10},
				{0, 1, 2, 3, 4, 7},
				{0, 1, 2, 3, 4, 8},
				{0, 1, 2, 3, 7, 8},
				{0, 2, 3, 4, 5, 8},
				{0, 1, 2, 3, 5, 8},
				{0, 1, 2, 3, 6, 8},
				{0, 1, 2, 3, 6, 9},
				{0, 1, 2, 5, 6, 8},
				{0, 1, 2, 5, 6, 9},
				{0, 2, 3, 4, 6, 9},
				{0, 1, 2, 4, 6, 9},
				{0, 1, 2, 4, 7, 9},
				{0, 1, 2, 5, 7, 9},
				{0, 1, 3, 4, 7, 9},
				{0, 1, 4, 6, 7, 9}
			},
			{ //septachords
				{0, 1, 2, 3, 4, 5, 6},
				{0, 1, 2, 3, 4, 5, 7},
				{0, 1, 2, 3, 4, 5, 8},
				{0, 1, 2, 3, 4, 6, 7},
				{0, 1, 2, 3, 5, 6, 7},
				{0, 1, 2, 3, 4, 7, 8},
				{0, 1, 2, 3, 6, 7, 8},
				{0, 2, 3, 4, 5, 6, 8},
				{0, 1, 2, 3, 4, 6, 8},
				{0, 1, 2, 3, 4, 6, 9},
				{0, 1, 3, 4, 5, 6, 8},
				{0, 1, 2, 3, 4, 7, 9},
				{0, 1, 2, 4, 5, 6, 8},
				{0, 1, 2, 3, 5, 7, 8},
				{0, 1, 2, 4, 6, 7, 8},
				{0, 1, 2, 3, 5, 6, 9},
				{0, 1, 2, 4, 5, 6, 9},
				{0, 1, 2, 3, 5, 8, 9}, //forte 7-Z18
				{0, 1, 4, 5, 6, 7, 9}, //rahn 7-Z18
				{0, 1, 2, 3, 6, 7, 9},
				{0, 1, 2, 4, 7, 8, 9}, //forte 7-20
				{0, 1, 2, 5, 6, 7, 9}, //rahn 7-20
				{0, 1, 2, 4, 5, 8, 9},
				{0, 1, 2, 5, 6, 8, 9},
				{0, 2, 3, 4, 5, 7, 9},
				{0, 1, 2, 3, 5, 7, 9},
				{0, 2, 3, 4, 6, 7, 9},
				{0, 1, 3, 4, 5, 7, 9},
				{0, 1, 2, 4, 5, 7, 9},
				{0, 1, 3, 5, 6, 7, 9},
				{0, 1, 2, 4, 6, 7, 9},
				{0, 1, 2, 4, 6, 8, 9},
				{0, 1, 3, 4, 6, 7, 9},
				{0, 1, 3, 4, 6, 8, 9},
				{0, 1, 2, 4, 6, 8, 10},
				{0, 1, 3, 4, 6, 8, 10},
				{0, 1, 3, 5, 6, 8, 10},
				{0, 1, 2, 3, 5, 6, 8},
				{0, 1, 3, 4, 5, 7, 8},
				{0, 1, 2, 4, 5, 7, 8}
			},
			{ //octachords
				{0, 1, 2, 3, 4, 5, 6, 7},
				{0, 1, 2, 3, 4, 5, 6, 8},
				{0, 1, 2, 3, 4, 5, 6, 9},
				{0, 1, 2, 3, 4, 5, 7, 8},
				{0, 1, 2, 3, 4, 6, 7, 8},
				{0, 1, 2, 3, 5, 6, 7, 8},
				{0, 1, 2, 3, 4, 5, 8, 9},
				{0, 1, 2, 3, 4, 7, 8, 9},
				{0, 1, 2, 3, 6, 7, 8, 9},
				{0, 2, 3, 4, 5, 6, 7, 9},
				{0, 1, 2, 3, 4, 5, 7, 9},
				{0, 1, 3, 4, 5, 6, 7, 9},
				{0, 1, 2, 3, 4, 6, 7, 9},
				{0, 1, 2, 4, 5, 6, 7, 9},
				{0, 1, 2, 3, 4, 6, 8, 9},
				{0, 1, 2, 3, 5, 7, 8, 9},
				{0, 1, 3, 4, 5, 6, 8, 9},
				{0, 1, 2, 3, 5, 6, 8, 9},
				{0, 1, 2, 4, 5, 6, 8, 9},
				{0, 1, 2, 4, 5, 7, 8, 9},
				{0, 1, 2, 3, 4, 6, 8, 10},
				{0, 1, 2, 3, 5, 6, 8, 10},
				{0, 1, 2, 3, 5, 7, 8, 10},
				{0, 1, 2, 4, 5, 6, 8, 10},
				{0, 1, 2, 4, 6, 7, 8, 10},
				{0, 1, 2, 4, 5, 7, 9, 10}, //forte 8-26
				{0, 1, 3, 4, 5, 7, 8, 10}, //rahn 8-26
				{0, 1, 2, 4, 5, 7, 8, 10},
				{0, 1, 3, 4, 6, 7, 9, 10},
				{0, 1, 2, 3, 5, 6, 7, 9}
			},
			{ //nonachords
				{0, 1, 2, 3, 4, 5, 6, 7, 8},
				{0, 1, 2, 3, 4, 5, 6, 7, 9},
				{0, 1, 2, 3, 4, 5, 6, 8, 9},
				{0, 1, 2, 3, 4, 5, 7, 8, 9},
				{0, 1, 2, 3, 4, 6, 7, 8, 9},
				{0, 1, 2, 3, 4, 5, 6, 8, 10},
				{0, 1, 2, 3, 4, 5, 7, 8, 10},
				{0, 1, 2, 3, 4, 6, 7, 8, 10},
				{0, 1, 2, 3, 5, 6, 7, 8, 10},
				{0, 1, 2, 3, 4, 6, 7, 9, 10},
				{0, 1, 2, 3, 5, 6, 7, 9, 10},
				{0, 1, 2, 4, 5, 6, 8, 9, 10}
			}
		};
		forte_numbers = new String[][] {
			{"3-1", "3-2", "3-3", "3-4", "3-5",
				"3-6", "3-7", "3-8", "3-9", "3-10",
				"3-11", "3-12"},
			{"4-1", "4-2", "4-3", "4-4", "4-5",
				"4-6", "4-7", "4-8", "4-9", "4-10",
				"4-11", "4-12", "4-13", "4-14", "4-Z15",
				"4-16", "4-17", "4-18", "4-19", "4-20",
				"4-21", "4-22", "4-23", "4-24", "4-25",
				"4-26", "4-27", "4-28", "4-Z29"},
			{"5-1", "5-2", "5-3", "5-4", "5-5",
				"5-6", "5-7", "5-8", "5-9", "5-10",
				"5-11", "5-Z12", "5-13", "5-14", "5-15",
				"5-16", "5-Z17", "5-Z18", "5-19", "5-20", "5-20", //double 5-20 intentional
				"5-21", "5-22", "5-23", "5-24", "5-25",
				"5-26", "5-27", "5-28", "5-29", "5-30",
				"5-31", "5-32", "5-33", "5-34", "5-35",
				"5-Z36", "5-Z37", "5-Z38"},
			{"6-1", "6-2", "6-Z3", "6-Z4", "6-5",
				"6-Z6", "6-7", "6-8", "6-9", "6-Z10",
				"6-Z11", "6-Z12", "6-Z13", "6-14", "6-15",
				"6-16", "6-Z17", "6-18", "6-Z19", "6-20",
				"6-21", "6-22", "6-Z23", "6-Z24", "6-Z25",
				"6-Z26", "6-27", "6-Z28", "6-Z29", "6-Z29", "6-30", //double 6-Z29 intentional
				"6-31", "6-31", "6-32", "6-33", "6-34", "6-35", //double 6-31 intentional
				"6-Z36", "6-Z37", "6-Z38", "6-Z39", "6-Z40",
				"6-Z41", "6-Z42", "6-Z43", "6-Z44", "6-Z45",
				"6-Z46", "6-Z47", "6-Z48", "6-Z49", "6-Z50"},
			{"7-1", "7-2", "7-3", "7-4", "7-5",
				"7-6", "7-7", "7-8", "7-9", "7-10",
				"7-11", "7-Z12", "7-13", "7-14", "7-15",
				"7-16", "7-Z17", "7-Z18", "7-Z18", "7-19", "7-20", "7-20", //double 7-Z18 and 7-20 intentional
				"7-21", "7-22", "7-23", "7-24", "7-25",
				"7-26", "7-27", "7-28", "7-29", "7-30",
				"7-31", "7-32", "7-33", "7-34", "7-35",
				"7-Z36", "7-Z37", "7-Z38"},
			{"8-1", "8-2", "8-3", "8-4", "8-5",
				"8-6", "8-7", "8-8", "8-9", "8-10",
				"8-11", "8-12", "8-13", "8-14", "8-Z15",
				"8-16", "8-17", "8-18", "8-19", "8-20",
				"8-21", "8-22", "8-23", "8-24", "8-25",
				"8-26", "8-26", "8-27", "8-28", "8-Z29"}, //double 8-26 intentional
			{"9-1", "9-2", "9-3", "9-4", "9-5",
				"9-6", "9-7", "9-8", "9-9", "9-10",
				"9-11", "9-12"},
		};
	}
	public void setColors() {
		/*
		if (forte_theme_radio.isSelected()) {
		//adds Forte colors/styles
			gridPane.setStyle("-fx-background-color: #155c30;");
			//gridPane.setStyle("-fx-font-size: 12; -fx-font-family: SansSerif;");
			Color forte_yellow = Color.web("d4c65b");

			for (int i = 0; i < labelsAndRadios.length; i++) {
				labelsAndRadios[i].setTextFill(Color.WHITESMOKE);
			//	labelsAndRadios[i].setStyle("-fx-font-weight: bold;");
			}
			for (int i = 0; i < labelArray.length; i++) {
				labelArray[i].setTextFill(forte_yellow);
			//	labelArray[i].setStyle("-fx-font-weight: bold;");
			}
		//	slider.setStyle("-fx-text-fill: yellow; -fx-fill: yellow; -fx-stroke: yellow; -fx-base: yellow; -fx-color: #bfc468; -fx-background: yellow;");
		//	slider.setStyle(".slider .axis {-fx-tick-label-fill: #bfc468; -fx-tick-label-font-weight: bold;}");

		//	System.out.println(slider.getStyle());
		} else {
			//rahn theme
			gridPane.setStyle("-fx-background-color: #155c30;");
			gridPane.setStyle("-fx-background-color: #f4f4f4;");
			for (int i = 0; i < labelsAndRadios.length; i++) {
				labelsAndRadios[i].setTextFill(Color.web("323232"));
			}
			for (int i = 0; i < labelArray.length; i++) {
				labelArray[i].setTextFill(Color.web("323232"));
			}
			//Application.setUserAgentStylesheet(null); //didnt work
		}
		*/
		gridPane.setStyle("-fx-background-color: #2b2b2b;");
		for (int i = 0; i < labelsAndRadios.length; i++) {
			labelsAndRadios[i].setTextFill(Color.web("cb772f"));
		}
		for (int i = 0; i < labelArray.length; i++) {
			labelArray[i].setTextFill(Color.web("9876aa"));
		}
		for (int i = 0; i < textFieldTextAreaArray.length; i++) {
			textFieldTextAreaArray[i].setStyle("-fx-background-color: #4d5052; -fx-text-fill: #a9b7c6;");
		}
		input_textfield.setStyle("-fx-background-color: #666666; -fx-text-fill: #f8f8f8;");
	}
}