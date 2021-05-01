package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Livro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;
	private String isbn;
	private double preco;
	private String categoria;
	private String responsavelCadastro;
	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();
	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autor> autores = new ArrayList<Autor>();

	
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro() {
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(System.currentTimeMillis());
		String dataFormatada = formatter.format(date);
		Date dateFinal;
		try {
			dateFinal = new SimpleDateFormat("dd/MM/yyyy").parse(dataFormatada);
			this.dataCadastro = dateFinal;
		} catch (ParseException e) {
			this.dataCadastro = null;
		}
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}
	
	public void excluirAutor(Autor autor) {
		this.autores.remove(autor);
	}

	public Livro() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getResponsavelCadastro() {
		return responsavelCadastro;
	}

	public void setResponsavelCadastro(String responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}

	

}