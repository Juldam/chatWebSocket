package com.julper.springbootbackendchat.models.service;

import com.julper.springbootbackendchat.models.documents.Mensaje;

import java.util.List;

public interface ChatService {

    public List<Mensaje> obtenerUltimos10Mensajes();

    public Mensaje guardarMensaje(Mensaje mensaje);
}
