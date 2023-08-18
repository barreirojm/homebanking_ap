/*Un repositorio en Java Persistence API (JPA) es una clase que se encarga de
almacenar/obtener/modificar o borrar instancias de clases (objetos)
de una base de datos.*/

package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
        Client findByEmail (String email);

    }
