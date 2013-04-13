package br.com.sose.service.expedicao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.expedicao.VolumeDao;
import br.com.sose.entity.expedicao.Volume;

@Service(value="volumeService")
@RemotingDestination(value="volumeService")
public class VolumeService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public VolumeDao volumeDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Volume> listarVolumes() throws Exception {
		List<Volume> volumes;
		try {
			volumes =(List<Volume>) volumeDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return volumes;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Volume salvarVolume(Volume volume) throws Exception {
		Volume volumeSalva;
		try {
			if(volume.getId() == null || volume.getId().equals(new Long(0)))
				volumeSalva =(Volume) volumeDao.save(volume);	
			else
				volumeSalva =(Volume) volumeDao.update(volume);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return volumeSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirVolume(Volume volume) throws Exception {
		try {
			volumeDao.delete(volume);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
