package extrator_caracteristicas;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicas {
	
	
	public static ArrayList<Double> extraiCaracteristicas(File f) {
		
		ArrayList<Double> caracteristicas = new ArrayList<Double>();
		
		double cinzaCabeloSmithers = 0;
		double roxoGravataSmithers = 0;
		double verdeTernoSmithers = 0;
		double vermelhoCabeloBob = 0;
//		double marromBocaHomer = 0;
//		double cinzaSapatoHomer = 0; 
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				if(i > (h/3) && isCabeloSmithers(r, g, b)) {
					cinzaCabeloSmithers++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i > (h/3) && isGravataSmithers(r, g, b)) {
					roxoGravataSmithers++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if (isTernoSmithers(r, g, b)) {
					verdeTernoSmithers++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i>(h/2) && isCabeloBob(r, g, b)) {
					vermelhoCabeloBob++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
//				if(i < (h/2 + h/3) && isBocaHomer(r, g, b)) {
//					marromBocaHomer++;
//					imagemProcessada.put(i, j, new double[]{0, 255, 255});
//				}
//				if (i > (h/2 + h/3) && isSapatoHomer(r, g, b)) {
//					cinzaSapatoHomer++;
//					imagemProcessada.put(i, j, new double[]{0, 255, 255});
//				}
				
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
        cinzaCabeloSmithers = (cinzaCabeloSmithers / (w * h)) * 100;
        roxoGravataSmithers = (roxoGravataSmithers / (w * h)) * 100;
        verdeTernoSmithers = (verdeTernoSmithers / (w * h)) * 100;
        vermelhoCabeloBob = (vermelhoCabeloBob / (w * h)) * 100;
//        marromBocaHomer = (marromBocaHomer / (w * h)) * 100;
//        cinzaSapatoHomer = (cinzaSapatoHomer / (w * h)) * 100;
    	
        caracteristicas.add(cinzaCabeloSmithers);
        caracteristicas.add(roxoGravataSmithers);
        caracteristicas.add(verdeTernoSmithers);
        caracteristicas.add(vermelhoCabeloBob);
//        caracteristicas.add(marromBocaHomer);
//        caracteristicas.add(cinzaSapatoHomer);
        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas.add(f.getName().charAt(0)=='s'?(double)0:1);
		
		//HighGui.imshow("Imagem original", imagemOriginal);
        //HighGui.imshow("Imagem processada", imagemProcessada);
        
        //HighGui.waitKey(0);
		
		return caracteristicas;
	}
	
	
	public static boolean isCabeloSmithers(double r, double g, double b) {
		 if (b >= 80 && b <= 100 &&  g >= 105 && g <= 125 &&  r >= 120 && r <= 140) {                       
         	return true;
         }
		 return false;
	}
	public static boolean isGravataSmithers(double r, double g, double b) {
		if (b >= 140 && b <= 190 &&  g >= 60 && g <= 90 &&  r >= 120 && r <= 160) {                       
			return true;
		}
		return false;
	}
	public static boolean isTernoSmithers(double r, double g, double b) {
		if (b >= 20 && b <= 35 &&  g >= 55 && g <= 75 &&  r >= 55 && r <= 75) {                       
			return true;
		}
		return false;
	}
	public static boolean isCabeloBob(double r, double g, double b) {
		if (b >= 20 && b <= 60 &&  g >= 0 && g <= 25 &&  r >= 100 && r <= 255) {                       
			return true;
		}
		return false;
	}
//	public static boolean isBocaHomer(double r, double g, double b) {
//		if (b >= 95 && b <= 140 &&  g >= 160 && g <= 185 &&  r >= 175 && r <= 200) {                       
//			return true;
//		}
//		return false;
//	}
//	public static boolean isSapatoHomer(double r, double g, double b) {
//		if (b >= 25 && b <= 45 &&  g >= 25 && g <= 45 &&  r >= 25 && r <= 45) {                       
//			return true;
//		}
//		return false;
//	}

	
	public static void extrair() {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute cinza_cabelo_smithers real\n";
		exportacao += "@attribute roxo_gravata_smithers real\n";
		exportacao += "@attribute verde_terno_smithers real\n";
		exportacao += "@attribute vermelho_cabelo_bob real\n";
//		exportacao += "@attribute azul_calca_homer real\n";
//		exportacao += "@attribute cinza_sapato_homer real\n";
		exportacao += "@attribute classe {Smithers, Bob}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\imagens");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
	    ArrayList<ArrayList<Double>> caracteristicas = new ArrayList<ArrayList<Double>>();
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas.add(extraiCaracteristicas(imagem));
        	
        	String classe = caracteristicas.get(cont).get(caracteristicas.get(cont).size()-1) == 0 ?"Smithers":"Bob";
        	
        	
        	System.out.println("Cinza cabelo Smithers: " + caracteristicas.get(cont).get(0) 
            		+ " - Roxo gravata Smithers: " + caracteristicas.get(cont).get(1) 
            		+ " - Verde terno Smithers: " + caracteristicas.get(cont).get(2) 
            		+ " - Vermelho cabelo Bob: " + caracteristicas.get(cont).get(3) 
//            		+ " - Marrom boca Homer: " + caracteristicas.get(cont).get(4)
//            		+ " - Preto sapato Homer: " + caracteristicas.get(cont).get(5)
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas.get(cont).get(0) + "," 
                    + caracteristicas.get(cont).get(1) + "," 
        		    + caracteristicas.get(cont).get(2) + "," 
                    + caracteristicas.get(cont).get(3) + "," 
//        		    + caracteristicas.get(cont).get(4) + "," 
//                    + caracteristicas.get(cont).get(5) + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
