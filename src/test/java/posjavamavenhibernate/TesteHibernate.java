package posjavamavenhibernate;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		// HibernateUtil.getEntityManager();
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setIdade(45);
		pessoa.setLogin("teste");
		pessoa.setNome("Roberval");
		pessoa.setSobrenome("Da Costa");
		pessoa.setEmail("rob.costa@gmail.com");
		pessoa.setSenha("abc123");

		daoGeneric.salvar(pessoa);

	}

	@Test
	public void testePesquisar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);
	}

	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.buscar(1L, UsuarioPessoa.class);

		System.out.println(pessoa);
	}

	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.buscar(2L, UsuarioPessoa.class);

		pessoa.setIdade(99);
		pessoa.setNome("Nome Atualizado Hibernate");

		pessoa = daoGeneric.updateMerge(pessoa);

		System.out.println(pessoa);

	}
	
	@Test
	public void testeUpdate2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);
		pessoa = daoGeneric.pesquisar(pessoa);

		pessoa.setIdade(55);
		pessoa.setNome("Marcos Silva");

		pessoa = daoGeneric.updateMerge(pessoa);

		System.out.println("Registro atualizado com sucesso! Nome: " + pessoa.getNome());

	}

	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.buscar(1L, UsuarioPessoa.class);

		try {
			daoGeneric.deletarPorId(pessoa);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Removido com sucesso!");

	}

	@Test
	public void testeConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------");
		}

	}

	@Test
	public void testeQueryList() {
		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = HibernateUtil.getEntityManager()
				.createQuery(" from UsuarioPessoa where nome = 'Roberval' ").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	@Test
	public void testeQueryListMaxResult() {
		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = HibernateUtil.getEntityManager().createQuery(" from UsuarioPessoa order by nome ")
				.setMaxResults(4).getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	@Test
	public void testeQueryListParameter() {
		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = HibernateUtil.getEntityManager()
				.createQuery(" from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome ")
				.setParameter("nome", "Roberval").setParameter("sobrenome", "Da").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	@Test
	public void testeQuerySomaIdade() {
		Long somaIdade = (Long) HibernateUtil.getEntityManager()
				.createQuery(" select sum(u.idade) from UsuarioPessoa u ").getSingleResult();

		System.out.println(somaIdade);
	}

	@Test
	public void testeQueryMediaIdade() {
		Double mediaIdade = (Double) HibernateUtil.getEntityManager()
				.createQuery(" select avg(u.idade) from UsuarioPessoa u ").getSingleResult();

		System.out.println(mediaIdade);
	}

	@Test
	public void testeNameQuery1() {
		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = HibernateUtil.getEntityManager().createNamedQuery("UsuarioPessoa.findAll")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeNameQuery2() {
		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = HibernateUtil.getEntityManager().createNamedQuery("UsuarioPessoa.findName")
				.setParameter("nome", "Roberval")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.buscar(2L, UsuarioPessoa.class);
		
		TelefoneUser telefoneUser = new TelefoneUser();
		
		telefoneUser.setTipo("Casa");
		telefoneUser.setNumero("48 3030-0000");
		telefoneUser.setUsuarioPessoa(pessoa);
		
		daoGeneric.salvar(telefoneUser);
	}
	
	@Test
	public void testeConsultaTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.buscar(2L, UsuarioPessoa.class);
		
		for (TelefoneUser telefoneUser : pessoa.getTelefoneUsers()) {
			System.out.println(telefoneUser.getUsuarioPessoa().getNome());
			System.out.println(telefoneUser.getNumero());
			System.out.println(telefoneUser.getTipo());
			System.out.println("----------------");
		}
	}
}
