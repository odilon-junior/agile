package br.com.agile.gateway.web.authentication;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by odilon on 30/03/17.
 */
@Entity
public class Role implements GrantedAuthority {

    @Id
    private String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
