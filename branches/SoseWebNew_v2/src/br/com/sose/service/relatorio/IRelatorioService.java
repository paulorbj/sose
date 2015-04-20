package br.com.sose.service.relatorio;

import java.util.List;

public interface IRelatorioService {

	public byte[] gerarRelatorio(Object objeto, String nomeRelatorio, List<?> valores);
}
