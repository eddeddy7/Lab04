/**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;


import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	Model model;
	List <Corso> corsi;

  
	public void setModel(Model model) {
		this.model = model;
		this.doInizializeCorsi();
		
	}

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="congoCorsi"
    private ComboBox <Corso> congoCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscritti"
    private Button btnCercaIscritti; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="btnCompleta"
    private Button btnCompleta; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void doCercaCorsi(ActionEvent event) {
    
    	int temp=0;
      	try {
      temp=Integer.parseInt(txtMatricola.getText());
      Studente t=model.completaStudente(temp);
      if(t==null) {
  		if(temp!=0)
  		txtResult.appendText("Matricola: "+ temp +" non esistente\n");
  	}
     
      if(congoCorsi.getValue()!=null) {
			String setta=model.verificaIscrizione(congoCorsi.getValue().getCodice(), Integer.parseInt(txtMatricola.getText()));
			if(setta==null)
			txtResult.setText("Studenet non Iscritto\n");
			else
				txtResult.setText(setta);
				
				return;
				
			}
      List<Corso> corsi = model.cercaCorsi(temp);
      for (Corso corso : corsi) {

			txtResult.appendText(corso +"\n");


		}
      
    	}
    
      	catch(NumberFormatException ne) {
        	System.out.println("errore conversione");
        	
    		txtResult.appendText("Matricola non trovata, errore di battitura\n");
    	
        	}
	 catch (RuntimeException e) {

		txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");

	}

    }

    @FXML
    void doCercaIscritti(ActionEvent event) {


		try {

          	Corso corso = congoCorsi.getValue();

			if (corso == null) {

				txtResult.setText("Selezionare un corso.\n");

				return;

			}

			
			
			
			List<Studente> studenti = model.studentiIscrittiAlCorso(corso);

			for (Studente studente : studenti) {

				txtResult.appendText(studente +"\n");


			}

		} catch (RuntimeException e) {

			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");

		}
  
    }

    @FXML
    void doCompleta(ActionEvent event) {
    	txtNome.clear();
		txtCognome.clear();
      
		int temp=0;
      	try {
      temp=Integer.parseInt(txtMatricola.getText());
      Studente t=model.completaStudente(temp);
      if(t==null) {
  		if(temp!=0)
  		txtResult.appendText("Matricola: "+ temp +" non esistente\n");
  	}
      txtNome.appendText(t.getNome());
  	txtCognome.setText(t.getCognome());
    	}
    
      	catch(NumberFormatException ne) {
        	System.out.println("errore conversione");
        	
    		txtResult.appendText("Matricola non trovata, errore di battitura\n");
        	
      	}
      	
	 catch (RuntimeException e) {

		txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");

	}
    	    
    	
    }

    @FXML
    void doInizializeCorsi() {
    	corsi = model.aggiungiCorsi();
    	congoCorsi.getItems().addAll(corsi);

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	
    	txtResult.clear();



		try {



			if (txtMatricola.getText().isEmpty()) {

				txtResult.setText("Inserire una matricola.");

				return;

			}



			if (congoCorsi.getValue() == null) {

				txtResult.setText("Selezionare un corso.");

				return;

			}



			// Prendo la matricola in input

			int matricola = Integer.parseInt(txtMatricola.getText());

			Studente studente = model.completaStudente(matricola);

			if (studente == null) {

				txtResult.appendText("Nessun risultato: matricola inesistente");

				return;

			}



			txtNome.setText(studente.getNome());

			txtCognome.setText(studente.getCognome());



			// Ottengo il nome del corso

			Corso corso = congoCorsi.getValue();



			// Controllo se lo studente è già iscritto al corso

			if (model.isStudenteIscrittoACorso(studente, corso)) {

				txtResult.appendText("Studente già iscritto a questo corso");

				return;

			}



			// Iscrivo lo studente al corso.

			// Controllo che l'inserimento vada a buon fine

			if (!model.inscriviStudenteACorso(studente, corso)) {

				txtResult.appendText("Errore durante l'iscrizione al corso");

				return;

			} else {

				txtResult.appendText("Studente iscritto al corso!");

			}



		} catch (NumberFormatException e) {

			txtResult.setText("Inserire una matricola nel formato corretto.");

		} catch (RuntimeException e) {

			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");

		}
			
    }
    
    

    @FXML
    void doReset(ActionEvent event) {
    	txtResult.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert congoCorsi != null : "fx:id=\"congoCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCompleta != null : "fx:id=\"btnCompleta\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
      }

    
}

