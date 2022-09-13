
package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	txtResult.clear();
    	double x;
    	double max=model.getPesoMax();
    	double min=model.getPesoMin(); 
    try {
    	x= Double.parseDouble(this.txtSoglia.getText());
    	if(x>min && x<max) {
    		txtResult.appendText(model.MaxMin(x));
    	}
    	
    }catch(NumberFormatException e) {
    	txtResult.appendText("\nInserisci soglia" );
    }
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	int n;
    	try {
    		n=Integer.parseInt(this.txtSoglia.getText());
    		List<Integer> rico= model.calcolaPercorso(n);
    		if(rico.isEmpty())
    			txtResult.appendText("Lista vuota\n");
    		for(Integer i: rico) {
    			txtResult.appendText(i+"\n");
    		}
    		
    	}catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.appendText("Inserire una soglia valida\n");
    		return;
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
		txtResult.appendText(model.creaGrafo());
		txtResult.appendText("\n"+model.pesoMaxMin());
		
	}
}