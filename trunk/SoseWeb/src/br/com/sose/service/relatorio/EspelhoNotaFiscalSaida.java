package br.com.sose.service.relatorio;

import br.com.sose.entity.recebimento.ItemNotaFiscal;

public class EspelhoNotaFiscalSaida {
	
	private ItemNotaFiscal itemNotaFiscal;
	
	private String textoEspelho;
	
	private Long idItemNotaFiscal;

	public ItemNotaFiscal getItemNotaFiscal() {
		return itemNotaFiscal;
	}

	public void setItemNotaFiscal(ItemNotaFiscal itemNotaFiscal) {
		this.itemNotaFiscal = itemNotaFiscal;
	}

	public String getTextoEspelho() {
		return textoEspelho;
	}

	public void setTextoEspelho(String textoEspelho) {
		this.textoEspelho = textoEspelho;
	}

	public Long getIdItemNotaFiscal() {
		return idItemNotaFiscal;
	}

	public void setIdItemNotaFiscal(Long idItemNotaFiscal) {
		this.idItemNotaFiscal = idItemNotaFiscal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idItemNotaFiscal == null) ? 0 : idItemNotaFiscal.hashCode());
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
		EspelhoNotaFiscalSaida other = (EspelhoNotaFiscalSaida) obj;
		if (idItemNotaFiscal == null) {
			if (other.idItemNotaFiscal != null)
				return false;
		} else if (!idItemNotaFiscal.equals(other.idItemNotaFiscal))
			return false;
		return true;
	}
	
	
	
}
