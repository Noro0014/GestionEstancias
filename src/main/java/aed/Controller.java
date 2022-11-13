package aed;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

	// model

	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty fechaI = new SimpleStringProperty();
	private StringProperty fechaF = new SimpleStringProperty();

	private String codHotel;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet resultado;

	// view

	@FXML
	private VBox VboxButtons;

	@FXML
	private TableView<Estancias> dataTableView;
	
	@FXML
	private TableColumn<Estancias, String> nameColumn;
	
	@FXML
	private TableColumn<Estancias, Date> arrivalDateColumn;
	
	@FXML
	private TableColumn<Estancias, Date> departureDateColumn;

	@FXML
	private ComboBox<String> comboBoxHotel;

	@FXML
	private Button deleteButton;

	@FXML
	private Button insertButton;

	@FXML
	private Button updateButton;

	public HBox getView() {
		return view;
	}

	public void setView(HBox view) {
		this.view = view;
	}
	
	ObservableList<Estancias> lista = FXCollections.observableArrayList();

	@FXML
	private HBox view;

	public Controller() throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		nameColumn.textProperty().bindBidirectional(nombre);
		arrivalDateColumn.textProperty().bind(fechaI);
		departureDateColumn.textProperty().bind(fechaF);

		// load data

		try {
			Properties datos = new Properties();
			datos.load(getClass().getResourceAsStream("/sql/configuracion.props"));

			Class.forName(datos.getProperty("driver"));

			conn = DriverManager.getConnection(datos.getProperty("url"), datos.getProperty("username"),
					datos.getProperty("password"));

			try {
				stmt = conn.createStatement();

				// consultas sql
				resultado = stmt.executeQuery("select distinct codHotel from Estancias");

				while (resultado.next()) {
					comboBoxHotel.getItems().add(resultado.getString("codHotel"));
				}

			} catch (SQLException e) {

			}
		} catch (Exception exception) {
			
		}
		
		
		
		
		// Collections
		
		
	}
	

	@FXML
	void onActualizarAction(ActionEvent event) throws SQLException {
		try {
			
			 pstmt = conn.prepareStatement("Select nombre, fechaInicio, fechaFin from estancias order by fechaInicio");
			 resultado = pstmt.executeQuery();
			 
			 	while(resultado.next()) {
				 lista.add(new Estancias(resultado.getString("nombre"), resultado.getDate("fechaInicio"), resultado.getDate("fechaFin")));
				 
			 	}
			 } catch (Exception e) {
			 }

	}

	@FXML
	void onEliminarAction(ActionEvent event) {

	}

	@FXML
	void onInsertarAction(ActionEvent event) {

	}
}
