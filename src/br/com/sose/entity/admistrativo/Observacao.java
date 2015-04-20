package br.com.sose.entity.admistrativo;

import java.util.Date;

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

import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Observacao implements Comparable<Observacao> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_nota_fiscal_id")
	private NotaFiscal notaFiscal;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_ordem_servico_id")
	private OrdemServico ordemServico;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_proposta_id")
	private Proposta proposta;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_item_proposta_id")
	private ItemProposta itemProposta;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_orcamento_id")
	private Orcamento orcamento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_laudo_tecnico_id")
	private LaudoTecnico laudoTecnico;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_reparo_id")
	private Reparo reparo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_nf_saida_id")
	private NotaFiscalRemessa notaFiscalSaida;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_faturamento_id")
	private Faturamento faturamento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="ref_componente_id")
	private Componente componente;
	
	private String origem;
	
	@Column(length = 2000)
	private String texto;
	
	@Column(name = "cadastrado_em")
	private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="cadastrado_por")
	private Usuario usuario;
	
	private Integer relevancia;
	
	/**
	 * SISTEMA: LOGA TODOS OS METODOS CRUD
	 * USUARIO: LOGA AS OBSERVACOES INSERIDAS POR UM USUARIO DO SISTEMA
	 * FUNCIONAL: LOGA SOMENTE OS METODOS PRESENTES NOS CONTROLLERS
	 * 1 - SISTEMA + USUARIO + FUNCIONAL
	 * 2 - USUARIO + FUNCIONAL
	 * 3 - USUARIO
	 */
	private Integer escopo;
	
	private Boolean sigiloso;
	
	public Observacao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NotaFiscal getNotaFiscal() {
		if(!Hibernate.isInitialized(notaFiscal)){
			notaFiscal = null;	
		}
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public OrdemServico getOrdemServico() {
		if(!Hibernate.isInitialized(ordemServico)){
			ordemServico = null;	
		}
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		if(!Hibernate.isInitialized(usuario)){
			usuario = null;	
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getRelevancia() {
		return relevancia;
	}

	public void setRelevancia(Integer relevancia) {
		this.relevancia = relevancia;
	}

	@Override
	public int compareTo(Observacao o) {
		return this.data.compareTo(o.data);
	}

	public Integer getEscopo() {
		return escopo;
	}

	public void setEscopo(Integer escopo) {
		this.escopo = escopo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		result = prime * result
				+ ((relevancia == null) ? 0 : relevancia.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observacao other = (Observacao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		if (relevancia == null) {
			if (other.relevancia != null)
				return false;
		} else if (!relevancia.equals(other.relevancia))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}

	public Proposta getProposta() {
		if(!Hibernate.isInitialized(proposta)){
			proposta = null;	
		}
		return proposta;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}

	public Orcamento getOrcamento() {
		if(!Hibernate.isInitialized(orcamento)){
			orcamento = null;	
		}
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public LaudoTecnico getLaudoTecnico() {
		if(!Hibernate.isInitialized(laudoTecnico)){
			laudoTecnico = null;	
		}
		return laudoTecnico;
	}

	public void setLaudoTecnico(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public Reparo getReparo() {
		if(!Hibernate.isInitialized(reparo)){
			reparo = null;	
		}
		return reparo;
	}

	public void setReparo(Reparo reparo) {
		this.reparo = reparo;
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

	public Boolean getSigiloso() {
		return sigiloso;
	}

	public void setSigiloso(Boolean sigiloso) {
		this.sigiloso = sigiloso;
	}

	public Faturamento getFaturamento() {
		if(!Hibernate.isInitialized(faturamento)){
			faturamento = null;	
		}
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

	public ItemProposta getItemProposta() {
		if(!Hibernate.isInitialized(itemProposta)){
			itemProposta = null;	
		}
		return itemProposta;
	}

	public void setItemProposta(ItemProposta itemProposta) {
		this.itemProposta = itemProposta;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}
	
	
	
}
