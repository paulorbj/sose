package br.com.sose.status.estoque.avaya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;


@Service(value="estoqueAvayaController")
@RemotingDestination(value="estoqueAvayaController")
public class EstoqueAvayaController {

	@Autowired
	private DisponivelEstoque disponivelEstoqueEstoqueAvayaStatus;
	@Autowired
	private Estocado estocadoEstoqueAvayaStatus;
	@Autowired
	private Substituido substituidoEstoqueAvayaStatus;	
	@Autowired
	private Reposto repostoEstoqueAvayaStatus;
	@Autowired
	private Retirado retiradoEstoqueAvayaStatus;

	private ItemEstoqueAvaya getStatus(ItemEstoqueAvaya itemEstoqueAvaya){
		if (itemEstoqueAvaya.getStatusString() == null || itemEstoqueAvaya.getStatusString().equals("") || itemEstoqueAvaya.getStatusString().equals(DisponivelEstoque.nome)){
			disponivelEstoqueEstoqueAvayaStatus.setItemEstoqueAvaya(itemEstoqueAvaya);
			itemEstoqueAvaya.setStatus(disponivelEstoqueEstoqueAvayaStatus);
		}else if(itemEstoqueAvaya.getStatusString().equals(Estocado.nome)){
			estocadoEstoqueAvayaStatus.setItemEstoqueAvaya(itemEstoqueAvaya);
			itemEstoqueAvaya.setStatus(estocadoEstoqueAvayaStatus);
		}else if(itemEstoqueAvaya.getStatusString().equals(Substituido.nome)){
			substituidoEstoqueAvayaStatus.setItemEstoqueAvaya(itemEstoqueAvaya);
			itemEstoqueAvaya.setStatus(substituidoEstoqueAvayaStatus);
		}else if(itemEstoqueAvaya.getStatusString().equals(Reposto.nome)){
			repostoEstoqueAvayaStatus.setItemEstoqueAvaya(itemEstoqueAvaya);
			itemEstoqueAvaya.setStatus(repostoEstoqueAvayaStatus);
		}else if(itemEstoqueAvaya.getStatusString().equals(Retirado.nome)){
			retiradoEstoqueAvayaStatus.setItemEstoqueAvaya(itemEstoqueAvaya);
			itemEstoqueAvaya.setStatus(retiradoEstoqueAvayaStatus);
		}
		return itemEstoqueAvaya;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya estocar(ItemEstoqueAvaya itemEstoqueAvaya,Usuario usuario) throws Exception{
		itemEstoqueAvaya = getStatus(itemEstoqueAvaya);
		return itemEstoqueAvaya.getStatus().estocar(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya substituir(ItemEstoqueAvaya itemEstoqueAvaya,String idOs,Usuario usuario) throws Exception{
		itemEstoqueAvaya = getStatus(itemEstoqueAvaya);
		return itemEstoqueAvaya.getStatus().substituir(idOs,usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya repor(ItemEstoqueAvaya itemEstoqueAvaya, String idOs,Usuario usuario) throws Exception{
		itemEstoqueAvaya = getStatus(itemEstoqueAvaya);
		return itemEstoqueAvaya.getStatus().repor(idOs,usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya retirar(ItemEstoqueAvaya itemEstoqueAvaya,Usuario usuario) throws Exception{
		itemEstoqueAvaya = getStatus(itemEstoqueAvaya);
		return itemEstoqueAvaya.getStatus().retirar(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemEstoqueAvaya> retirarTodos(List<ItemEstoqueAvaya> itensEstoqueAvaya,Usuario usuario) throws Exception{
		List<ItemEstoqueAvaya> itensEstoqueAvayaRetorno = new ArrayList<ItemEstoqueAvaya>();
		for (ItemEstoqueAvaya itemEstoqueAvaya : itensEstoqueAvaya){
			itemEstoqueAvaya = getStatus(itemEstoqueAvaya);
			itemEstoqueAvaya =  itemEstoqueAvaya.getStatus().retirar(usuario);
			itensEstoqueAvayaRetorno.add(itemEstoqueAvaya);
		}
		return itensEstoqueAvayaRetorno;
	}


}
