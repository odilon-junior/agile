package br.com.agile.business.repository;

import br.com.agile.gateway.web.authentication.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by odilon on 30/03/17.
 */
@Repository
public class UsuarioRepository implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u");
        jpql.append(" WHERE u.login = :login");

        TypedQuery<Usuario> query = em.createQuery(jpql.toString(), Usuario.class);

        query.setParameter("login", login);

        try {
            return query.getSingleResult();
        }catch (NoResultException ex){
            throw  new UsernameNotFoundException(String.format("O usuario %s não foi encontrado", login));
        }
    }
}
