/*Un repositorio en Java Persistence API (JPA) es una clase que se encarga de
almacenar/obtener/modificar o borrar instancias de clases (objetos)
de una base de datos.*/

package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
@RepositoryRestResource que le indica a Spring que debe generar el código necesario
para que se pueda administrar la data de la aplicación desde el navegador usando JSON,
es decir se crea una API REST automática que expone los recursos
de cada repositorio anotado con @RepositoryRestResource.
 */
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
