package br.com.sose.daoImpl.administrativo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import org.hibernate.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.daoImpl.JpaDao;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Repository("lpuDao")
public class LpuDao extends HibernateDaoGenerico<Lpu, Long> {

	public LpuDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Lpu buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (Lpu)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Lpu> findAllAtivoOrderByNome(){		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.unidade");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Lpu> findAllOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.unidade");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public Lpu buscarPorNomeECliente(final String nome, final Pessoa cliente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome AND h.cliente = :cliente");
		q.setParameter("nome", nome);
		q.setParameter("cliente", cliente);
		return (Lpu)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Lpu> listarPorCliente(final Pessoa pessoa) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.cliente = :cliente");
		q.setParameter("cliente", pessoa);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Lpu> listarPorClienteCombo(final Pessoa pessoa) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.cliente = :cliente");
		q.setParameter("cliente", pessoa);
		return q.list();
	}

	public Boolean remover(final Lpu lpu){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Lpu WHERE id = :id");
		q.setParameter("id", lpu.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	public void atualizarValor(Lpu lpu){
		sessionFactory.getCurrentSession().flush();
		StringBuilder sb = new StringBuilder("update sosedb_migrado.ordemservico os left join sosedb_migrado.lpu l on os.lpu_id = l.id left join sosedb_migrado.orcamento orc on os.orcamento_id = orc.id left join sosedb_migrado.reparo rep on os.reparo_id = rep.id set os.valorsistema = l.valorreparo,os.valorfaturado = l.valorreparo where origemfaturamento = 'LPU' and os.lpu_id = :id and ((os.orcamento_id is not null and orc.condicao = 'Com condição de reparo') or (os.reparo_id is not null and rep.condicao = 'Com condição de reparo')) and os.faturamento_id is null");
		StringBuilder sb2 = new StringBuilder("update sosedb_migrado.ordemservico os left join sosedb_migrado.lpu l on os.lpu_id = l.id left join sosedb_migrado.faturamento f on os.faturamento_id = f.id left join sosedb_migrado.orcamento orc on os.orcamento_id = orc.id left join sosedb_migrado.reparo rep on os.reparo_id = rep.id set os.valorsistema = l.valorreparo,os.valorfaturado = l.valorreparo where origemfaturamento = 'LPU' and os.lpu_id = :id and os.faturamento_id is not null and ((os.orcamento_id is not null and orc.condicao = 'Com condição de reparo') or (os.reparo_id is not null and rep.condicao = 'Com condição de reparo')) and f.dataCriacaoFatura is null");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		Query query2 = sessionFactory.getCurrentSession().createSQLQuery(sb2.toString());
		query.setParameter("id", lpu.getId());
		query2.setParameter("id", lpu.getId());
		int i = query.executeUpdate();
		int i2 = query2.executeUpdate();
		sessionFactory.getCurrentSession().flush();
		System.out.println(i);
	}
}
