package br.com.sose.service.administrativo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.ArquivoUploadDao;
import br.com.sose.daoImpl.administrativo.LpuDao;
import br.com.sose.entity.admistrativo.ItemLpu;
import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.utils.ArquivoUpload;

@Service(value="lpuService")
@RemotingDestination(value="lpuService")
public class LpuService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public LpuDao lpuDao;

	@Autowired
	public ArquivoUploadDao arquivoUploadDao;
	
	@Autowired
	public ItemLpuService itemLpuService;

	/********************** Metodos de listagem *********************/

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarLpu() throws Exception {
		List<Lpu> lpus;
		try {
			lpus =(List<Lpu>) lpuDao.findAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorUnidade(Unidade unidade) throws Exception {
		List<Lpu> lpus = null;
		try {

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return null;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorCliente(Pessoa pessoa) throws Exception {
		List<Lpu> lpus;
		try {
			lpus = lpuDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorClienteCombo(Pessoa pessoa) throws Exception {
		List<Lpu> lpus;
		try {
			lpus = lpuDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}

	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/

	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarId(Long id) throws Exception {
		try {
			return lpuDao.findById(id);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarCompletoId(Long id) throws Exception {
		try {
			return lpuDao.buscarCompletoId(id);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o id: "+id+" no sistema");
		}
		return null;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarPorNome(String nome) throws Exception {
		try {
			return lpuDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o nome: "+nome+" no sistema");
		}
		return null;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarPorNomeECliente(String nome, Pessoa cliente) throws Exception {
		try {
			return lpuDao.buscarPorNomeECliente(nome,cliente);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o nome: "+nome+" no sistema");
		}
		return null;
	}


	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu salvarLpu(Lpu lpu) throws Exception {
		Lpu lpuSalva;
		try {
			if(lpu.getId() == null || lpu.getId().equals(new Long(0))){
				lpu.setUploadEm(new Date());
				lpuSalva =(Lpu) lpuDao.save(lpu);	
			}else{
				lpuSalva =(Lpu) lpuDao.update(lpu);	
			}
		} catch (Exception e) {
			throw e;
		}
		return lpuSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu processarLpu(Lpu lpu) throws Exception {
		Lpu lpuSalva;
		try {
			lpuSalva = buscarId(lpu.getId());
			ArquivoUpload arquivoUploadLpu = arquivoUploadDao.buscarArquivoUploadLpu(lpu.getId());
			String caminhoLpu = "C:\\arquivo_servilogi\\" + "lpu\\" + lpu.getId() + "\\PLANILHA\\" +  arquivoUploadLpu.getNome();
			InputStream inp = new FileInputStream(caminhoLpu);

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			
			Set<ItemLpu> listaItemLpu = new HashSet<ItemLpu>();
			ItemLpu itemLpu = null;
			
			Row rowAtual = null;
			Cell cellAtual = null;
			CellReference cellReference = null;
			System.out.println("Processando LPU...");
			for(int linhaExcel = Integer.parseInt(lpuSalva.getPrimeiraLinhaDados()); linhaExcel <= Integer.parseInt(lpuSalva.getUltimaLinhaDados()); linhaExcel++){
				itemLpu = new ItemLpu();
				
				if(lpuSalva.getCelulaCodigo1() != null && !lpuSalva.getCelulaCodigo1().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaCodigo1() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					itemLpu.setCodigo1(lerCelula(cellAtual));
				}
				
				if(lpuSalva.getCelulaCodigo2() != null && !lpuSalva.getCelulaCodigo2().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaCodigo2() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					itemLpu.setCodigo2(lerCelula(cellAtual));
				}
				
				if(lpuSalva.getCelulaEquipamento() != null && !lpuSalva.getCelulaEquipamento().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaEquipamento() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					itemLpu.setEquipamento(lerCelula(cellAtual));
				}
				
				if(lpuSalva.getCelulaFabricante() != null && !lpuSalva.getCelulaFabricante().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaFabricante() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					itemLpu.setFabricante(lerCelula(cellAtual));
				}
				
				if(lpuSalva.getCelulaUnidade() != null && !lpuSalva.getCelulaUnidade().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaUnidade() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					itemLpu.setUnidade(lerCelula(cellAtual));
				}
				
				if(lpuSalva.getCelulaValor() != null && !lpuSalva.getCelulaValor().equals("")){
					cellReference = new CellReference(lpuSalva.getCelulaValor() + linhaExcel);
					rowAtual = sheet.getRow(cellReference.getRow());
				    cellAtual = rowAtual.getCell(cellReference.getCol());
					//itemLpu.setValor(new BigDecimal(cellAtual.getNumericCellValue()));
				}
				
				itemLpu.setLpu(lpuSalva);
				itemLpu.setReferenciaExcel(Integer.toString(linhaExcel));
				itemLpu = itemLpuService.salvarItemLpu(itemLpu);
				listaItemLpu.add(itemLpu);
			}

			lpuSalva.setListaItemLpu(listaItemLpu);
			System.out.println("Finalizando processamento LPU...");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return lpuSalva;
	}
	
	private String lerCelula(Cell cell){
		Integer cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_BLANK){
			return "";
		}else if(cellType == Cell.CELL_TYPE_BOOLEAN){
			return "";
		}else if(cellType == Cell.CELL_TYPE_ERROR){
			return "";
		}else if(cellType == Cell.CELL_TYPE_FORMULA){
			return "";
		}else if(cellType == Cell.CELL_TYPE_NUMERIC){
			return Double.toString(cell.getNumericCellValue());
		}else if(cellType == Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue();
		}else{
			return "";
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu excluirLpu(Lpu lpu) throws Exception {
		try {
			lpuDao.remover(lpu);	
			//logger.info("Lpu com o nome: "+lpu.getUnidade()+" foi removida do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			//throw new LpuNaoExclusaoDependenciaExistenteException(lpu.getUnidade());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return lpu;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu importarLpu(Lpu lpu) throws Exception {
		try {
			lpuDao.remover(lpu);	
			//logger.info("Lpu com o nome: "+lpu.getUnidade()+" foi removida do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			//throw new LpuNaoExclusaoDependenciaExistenteException(lpu.getUnidade());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return lpu;
	}

}
