package br.com.sose.status.estoque.avaya;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;
import br.com.sose.service.estoque.avaya.ItemEstoqueAvayaService;

@Service("disponivelEstoqueStatusEstoqueAvaya")
public class DisponivelEstoque extends StatusEstoqueAvaya {

	public static final String nome = "Disponível para estoque"; 
	
	@Autowired
	private ItemEstoqueAvayaService itemEstoqueAvayaService;
	
	public DisponivelEstoque(ItemEstoqueAvaya iea) {
		super.itemEstoqueAvaya = iea;
	}
	
	public DisponivelEstoque() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya estocar(Usuario usuario) throws Exception {
		String posicao = itemEstoqueAvaya.getPosicao();
		itemEstoqueAvaya = itemEstoqueAvayaService.buscarPorId(itemEstoqueAvaya.getId());
		itemEstoqueAvaya.setPosicao(posicao);
		itemEstoqueAvaya.setDataEntrada(new Date());
		itemEstoqueAvaya.setEntradaRealizadaPor(usuario);
		itemEstoqueAvaya.setStatusString(Estocado.nome);
		itemEstoqueAvaya = itemEstoqueAvayaService.salvarItemEstoqueAvaya(itemEstoqueAvaya);
		
		itemEstoqueAvaya.setOrdemServicoOriginal(itemEstoqueAvaya.getOrdemServicoOriginal());
		return itemEstoqueAvaya;
	}
	
	
	
}
