package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import posjavamavenhibernate.HibernateUtil;

public class DaoGeneric<E> {

	private static final String UNCHECKED = "unchecked";
	private EntityManager entityManager = HibernateUtil.getEntityManager();

	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	public E updateMerge(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();

		return entidadeSalva;
	}

	@SuppressWarnings(UNCHECKED)
	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);

		E e = (E) entityManager.find(entidade.getClass(), id);

		return e;
	}

	public E procurar(Long id, Class<E> entidade) {
		entityManager.clear();
		@SuppressWarnings(UNCHECKED)
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();
		return e;
	}

	public E buscar(Long id, Class<E> entidade) {
		@SuppressWarnings(UNCHECKED)
		E e = (E) entityManager.find(entidade, id);

		return e;
	}

	public void deletarPorId(E entidade) throws Exception {
		Object id = HibernateUtil.getPrimaryKey(entidade); // Obtem o ID do objeto PK

		EntityTransaction transaction = entityManager.getTransaction(); // Objeto de transação
		transaction.begin(); // Inicia uma transação
		entityManager
				.createNativeQuery(
						"DELETE FROM " + entidade.getClass().getSimpleName().toLowerCase() + " where id = " + id) // Monta
																													// a
																													// query
																													// para
																													// o
																													// delete
				.executeUpdate(); // Executa o delete no banco de dados
		transaction.commit(); // Grava as alterações no banco de dados
	}

	public List<E> listar(Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		@SuppressWarnings(UNCHECKED)
		List<E> lista = entityManager.createQuery("FROM " + entidade.getName()).getResultList();

		transaction.commit();

		return lista;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
