package br.univel.model;

public class Disciplina {

	private Long id;
	private int version;
	
	private String descricao;
	private Integer numeroAulas;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getNumeroAulas() {
		return numeroAulas;
	}
	
	public void setNumeroAulas(Integer numeroAulas) {
		this.numeroAulas = numeroAulas;
	}
	
}
