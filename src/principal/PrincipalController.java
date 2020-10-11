package principal;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import algoritmosAprendizagem.ClassificadorNaiveBayes;
import algoritmosAprendizagem.ClassificadorJ48;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {
	
	@FXML private ImageView imageView;
	@FXML private Label char1Carac1;
	@FXML private Label char1Carac2;
	@FXML private Label char1Carac3;
	@FXML private Label char2Carac1;
	@FXML private Label char1J48;
	@FXML private Label char2J48;
	//@FXML private Label char2Carac2;
	//@FXML private Label char2Carac3;
	
	@FXML private Label char1NaiveBayes;
	@FXML private Label char2NaiveBayes;
	
	private DecimalFormat df = new DecimalFormat("##0.0000");
	private ArrayList<Double> caracteristicasImgSel = new ArrayList<Double>(); //{0,0,0,0,0,0};
	
	@FXML
	public void classificarNaiveBayes() {
		double[] nb = ClassificadorNaiveBayes.naiveBayes(caracteristicasImgSel);
		char1NaiveBayes.setText(df.format(nb[0]) + "%");
		char2NaiveBayes.setText(df.format(nb[1]) + "%");
	}
	
	@FXML
	public void classificadorJ48() {
		double[] nb = ClassificadorJ48.j48(caracteristicasImgSel);
		char1J48.setText(df.format(nb[0]) + "%");
		char2J48.setText(df.format(nb[1]) + "%");
	}
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}

	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.fitWidthProperty().bind(img.widthProperty());
			imageView.fitHeightProperty().bind(img.heightProperty());
			
//			imageView.setFitWidth(img.getWidth());
//			imageView.setFitHeight(img.getHeight());
			caracteristicasImgSel = ExtractCaracteristicas.extraiCaracteristicas(f);
			char1Carac1.setText(caracteristicasImgSel.get(0)+"");
			char1Carac2.setText(caracteristicasImgSel.get(1)+"");
			char1Carac3.setText(caracteristicasImgSel.get(2)+"");
			char2Carac1.setText(caracteristicasImgSel.get(3)+"");
			//char2Carac2.setText(caracteristicas.get(4)+"");
			//char2Carac3.setText(caracteristicas.get(5)+"");
			
			char1NaiveBayes.setText("");
			char2NaiveBayes.setText("");
			
		}
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
}
