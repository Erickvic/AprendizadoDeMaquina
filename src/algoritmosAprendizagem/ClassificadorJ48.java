package algoritmosAprendizagem;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.ArrayList;

public class ClassificadorJ48 {

	public static double[] j48(ArrayList<Double> caracteristicas) {
		double[] retorno = {0,0};
		try {
			//*******carregar arquivo de caracter�sticas
			DataSource ds = new DataSource("caracteristicas.arff");
			Instances instancias = ds.getDataSet();
			instancias.setClassIndex(instancias.numAttributes()-1);
			
			//cria a �rvore
			J48 arvore = new J48();
			arvore.buildClassifier(instancias);
			//arvore.setUnpruned(true);//sem podas
			
			//Novo registro
			Instance novo = new DenseInstance(instancias.numAttributes());
			novo.setDataset(instancias);
			novo.setValue(0, caracteristicas.get(0));
			novo.setValue(1, caracteristicas.get(1));
			novo.setValue(2, caracteristicas.get(2));
			novo.setValue(3, caracteristicas.get(3));
//			novo.setValue(4, caracteristicas.get(4));
//			novo.setValue(5, caracteristicas.get(5));
			
			retorno = arvore.distributionForInstance(novo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
}
