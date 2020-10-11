package algoritmosAprendizagem;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;

public class ClassificadorNaiveBayes {
	
	public static double[] naiveBayes(ArrayList<Double> caracteristicas) {
		double[] retorno = {0,0};
		try {
			//*******carregar arquivo de características
			DataSource ds = new DataSource("caracteristicas.arff");
			Instances instancias = ds.getDataSet();
			//instancias.setClassIndex(6);
			instancias.setClassIndex(instancias.numAttributes()-1);
			
			//Classifica com base nas características da imagem selecionada
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(instancias);//aprendizagem (tabela de probabilidades)
//			ConfusionMatrix cm = new ConfusionMatrix(new String[] {"bart", "homer"});
//			System.out.println(cm.toString());	
			
			Instance novo = new DenseInstance(instancias.numAttributes());
			novo.setDataset(instancias);
			
			for(int i = 0; i < caracteristicas.size() - 1; i++) {
				novo.setValue(i, caracteristicas.get(i));
			}
			
			retorno = nb.distributionForInstance(novo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
