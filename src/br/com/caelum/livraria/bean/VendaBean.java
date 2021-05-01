package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendaBean {

	private BarChartModel vendasModel;
	
	
	
	public BarChartModel getVendasModel() {
		return vendasModel;
	}



	public void setVendasModel(BarChartModel vendasModel) {
		this.vendasModel = vendasModel;
	}



	@PostConstruct
    public void init() {
		createVendasModel();
	}
	
	
	
	public void createVendasModel() {
		
			vendasModel = new BarChartModel();
	        ChartData data = new ChartData();

	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("Quantidade de vendas");
	        
	        List<Venda> vendas = getVendas();
	              

	        List<Number> values = new ArrayList<>();
	        
	        List<String> bgColor = new ArrayList<>();
	        List<String> borderColor = new ArrayList<>();
	        List<String> labels = new ArrayList<>();
	        
	        Integer r1 = new Random().nextInt(246) + 10;
	        Integer r2 = new Random().nextInt(246) + 10;
	        Integer r3 = new Random().nextInt(246) + 10;
	       
	        Integer r5 = new Random().nextInt(246) + 10;
	        Integer r6 = new Random().nextInt(246) + 10;
	        Integer r7 = new Random().nextInt(246) + 10;
	        
	        String rs1 = Integer.toString(r1);
	        String rs2 = Integer.toString(r2);
	        String rs3 = Integer.toString(r3);
	        String rs4 = "0.2"; 
	        
	        String rs5 = Integer.toString(r5);
	        String rs6 = Integer.toString(r6);
	        String rs7 = Integer.toString(r7);
	        
	        for (Venda venda : vendas) {
	        	values.add(venda.getQuantidade());
	        	bgColor.add("rgba(" + rs1 + ", " + rs2 + ", " + rs3 + ", " + rs4);
	        	borderColor.add("rgb(" + rs5 + ", " + rs6 + ", " + rs7);
	        	labels.add(venda.getLivro().getTitulo());
	        	
	        	rs1 = Integer.toString(new Random().nextInt(246) + 10);
	 	        rs2 = Integer.toString(new Random().nextInt(246) + 10);
	 	        rs3 = Integer.toString(new Random().nextInt(246) + 10);
	        }
	        
	        barDataSet.setData(values);
	        barDataSet.setBackgroundColor(bgColor);
	        barDataSet.setBorderColor(borderColor);
	        barDataSet.setBorderWidth(1);
	        data.addChartDataSet(barDataSet);
	        data.setLabels(labels);
	        
	        vendasModel.setData(data);

	        //Options
	        BarChartOptions options = new BarChartOptions();
	        CartesianScales cScales = new CartesianScales();
	        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	        linearAxes.setOffset(true);
	        CartesianLinearTicks ticks = new CartesianLinearTicks();
	        ticks.setBeginAtZero(true);
	        linearAxes.setTicks(ticks);
	        cScales.addYAxesData(linearAxes);
	        options.setScales(cScales);

	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Relatório visual de livros x quantidade");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        // disable animation
	        Animation animation = new Animation();
	        animation.setDuration(0);
	        options.setAnimation(animation);
	        
	        vendasModel.setOptions(options);
		
	}
	
	public List<Venda> getVendas(){
		
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas= new ArrayList<Venda>();
		
		for (Livro livro : livros) {
			Random random = new Random();
			Integer quantidade = random.nextInt(200);
			vendas.add(new Venda(livro, quantidade));
		}
		
		return vendas;
	}
	
	
}
