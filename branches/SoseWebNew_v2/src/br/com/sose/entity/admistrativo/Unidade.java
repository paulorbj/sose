package br.com.sose.entity.admistrativo;

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

import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, nullable = false)
	private String nome;

	@Column(length = 1000)
	private String descricao;

	@Column(length = 50)
	private String codigo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "equipamento_id", referencedColumnName = "id")
	private Equipamento equipamento = new Equipamento();

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fabricante_id", referencedColumnName = "id")
	private Fabricante fabricante = new Fabricante();

	/**
	 * O Laboratorio para onde a unidade deve ser encaminhada
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "laboratorio_id", referencedColumnName = "id")
	private Laboratorio laboratorio;

	/**
	 * O laboratorio onde essa unidade pode ser reparada externamente
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Pessoa prestadorServicoExterno;

	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;

	private String nomeFabricante;

	private String nomeEquipamento;

	private String nomeLaboratorio;

	private String nomePrestadorServico;

	public Unidade() {
		// TODO Auto-generated constructor stub
	}


	public Unidade(Long id, String nome, String codigo, String nomeFabricante,
			String nomeEquipamento, String nomeLaboratorio,
			String nomePrestadorServico) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		this.nomeFabricante = nomeFabricante;
		this.nomeEquipamento = nomeEquipamento;
		this.nomeLaboratorio = nomeLaboratorio;
		this.nomePrestadorServico = nomePrestadorServico;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Equipamento getEquipamento() {
		if(!Hibernate.isInitialized(equipamento)){
			equipamento = null;
		}
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
		if(equipamento != null){
			this.nomeEquipamento = equipamento.getNome();
		}
	}

	public Fabricante getFabricante() {
		if(!Hibernate.isInitialized(fabricante)){
			fabricante = null;
		}
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
		if(fabricante != null){
			this.nomeFabricante = fabricante.getNome();
		}
	}

	public Laboratorio getLaboratorio() {
		if(!Hibernate.isInitialized(laboratorio)){
			laboratorio = null;
		}
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
		if(laboratorio != null){
			this.nomeLaboratorio = laboratorio.getNome();
		}
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public Pessoa getPrestadorServicoExterno() {
		if(!Hibernate.isInitialized(prestadorServicoExterno)){
			prestadorServicoExterno = null;	
		}
		return prestadorServicoExterno;
	}

	public void setPrestadorServicoExterno(Pessoa prestadorServicoExterno) {
		this.prestadorServicoExterno = prestadorServicoExterno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Unidade other = (Unidade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}


	public String getNomeFabricante() {
		return nomeFabricante;
	}


	public void setNomeFabricante(String nomeFabricante) {
		if(nomeFabricante != null)
			this.nomeFabricante = nomeFabricante;
	}


	public String getNomeEquipamento() {
		return nomeEquipamento;
	}


	public void setNomeEquipamento(String nomeEquipamento) {
		if(nomeEquipamento != null)
			this.nomeEquipamento = nomeEquipamento;
	}


	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}


	public void setNomeLaboratorio(String nomeLaboratorio) {
		if(nomeLaboratorio != null)
			this.nomeLaboratorio = nomeLaboratorio;
	}


	public String getNomePrestadorServico() {
		return nomePrestadorServico;
	}


	public void setNomePrestadorServico(String nomePrestadorServico) {
		if(nomePrestadorServico != null)
			this.nomePrestadorServico = nomePrestadorServico;
	}	

}
