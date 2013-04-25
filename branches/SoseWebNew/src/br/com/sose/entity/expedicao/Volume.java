package br.com.sose.entity.expedicao;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Volume implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "total_itens")
	private Integer totalItens;

	@Column(name = "peso_bruto")
	private Float pesoBruto;

	@Column(name = "tipo_embalagem")
	private String tipoEmbalagem;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "nota_fiscal_saida_id", referencedColumnName = "id")
	private NotaFiscalRemessa notaFiscalSaida;
	
	private Long idNotaFiscalSaida;
	
	public Volume() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Volume(Long id, Integer totalItens, Float pesoBruto,
			String tipoEmbalagem, Long idNotaFiscalSaida) {
		super();
		this.id = id;
		this.totalItens = totalItens;
		this.pesoBruto = pesoBruto;
		this.tipoEmbalagem = tipoEmbalagem;
		this.idNotaFiscalSaida = idNotaFiscalSaida;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(Integer totalItens) {
		this.totalItens = totalItens;
	}

	public Float getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Float pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getTipoEmbalagem() {
		return tipoEmbalagem;
	}

	public void setTipoEmbalagem(String tipoEmbalagem) {
		this.tipoEmbalagem = tipoEmbalagem;
	}

	public NotaFiscalRemessa getNotaFiscalSaida() {
		if(!Hibernate.isInitialized(notaFiscalSaida)){
			notaFiscalSaida = null;	
		}
		return notaFiscalSaida;
	}

	public void setNotaFiscalSaida(NotaFiscalRemessa notaFiscalSaida) {
		this.notaFiscalSaida = notaFiscalSaida;
	}



	public Long getIdNotaFiscalSaida() {
		return idNotaFiscalSaida;
	}



	public void setIdNotaFiscalSaida(Long idNotaFiscalSaida) {
		this.idNotaFiscalSaida = idNotaFiscalSaida;
	}

	
}
