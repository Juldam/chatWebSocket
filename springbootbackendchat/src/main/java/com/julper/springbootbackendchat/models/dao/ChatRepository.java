package com.julper.springbootbackendchat.models.dao;

import com.julper.springbootbackendchat.models.documents.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Mensaje, String> {

    List<Mensaje> findFirst10ByOrderByFechaDesc(); //Con este método obtenemos los primeros 10 mensajes en order descendente de fecha

}
