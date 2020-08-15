package com.julper.springbootbackendchat.models.service;

import com.julper.springbootbackendchat.models.dao.ChatRepository;
import com.julper.springbootbackendchat.models.documents.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository dao;

    @Override
    public List<Mensaje> obtenerUltimos10Mensajes() {
        return dao.findFirst10ByOrderByFechaDesc();
    }

    @Override
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return dao.save(mensaje);
    }
}
