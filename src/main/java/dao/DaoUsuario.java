package dao;

import model.UsuarioPessoa;
import posjavamavenhibernate.HibernateUtil;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa>{
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		String sqlDeleteTelefone = "DELETE FROM telefoneuser WHERE usuariopessoa_id = " + pessoa.getId();
		getEntityManager().getTransaction().begin();
		getEntityManager().createNativeQuery(sqlDeleteTelefone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
		
	}
}
