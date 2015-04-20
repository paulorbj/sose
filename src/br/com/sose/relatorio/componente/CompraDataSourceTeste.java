package br.com.sose.relatorio.componente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sose.entity.admistrativo.Componente;

@Service(value="compraDataSourceTeste")
public class CompraDataSourceTeste {
	
	public static List<RelatorioCompraVO> createBeanCollection(){
		List<RelatorioCompraVO> lista = new ArrayList<RelatorioCompraVO>();
		RelatorioCompraVO rc = new RelatorioCompraVO();
		Componente c = new Componente();
		c.setId(new Long(211));
		c.setNome("Nome componente");
		c.setPinos("12p");
		
		c.setPosicaoEstoque("L1093");
		rc.setComponente(c);
		rc.setData(new Date());
		lista.add(rc);
		
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		lista.add(new RelatorioCompraVO());
		
		
		return lista;
	}
}
