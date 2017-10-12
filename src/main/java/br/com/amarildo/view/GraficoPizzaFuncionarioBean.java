package br.com.amarildo.view;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.amarildo.repository.PessoaRepository;
 
@Named
@RequestScoped
public class GraficoPizzaFuncionarioBean {
 
	@Inject
	private PessoaRepository pessoaRepository;
 
	private PieChartModel pieChartModel;
 
	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}
 
	@PostConstruct
	public  void init(){
  		this.pieChartModel = new PieChartModel();
 		this.MontaGrafico();
	}
 
    // Monta Grafico na Pagina    
	private void MontaGrafico(){

		//Consulta dados para Montar Grafico
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();
 
		//Informa os Valores para Montar o Grafico
		hashtableRegistros.forEach((chave,valor) -> {
			pieChartModel.set(chave, valor);
 
		});
 
		pieChartModel.setTitle("Total de Funcionários cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");
 
	}
}
