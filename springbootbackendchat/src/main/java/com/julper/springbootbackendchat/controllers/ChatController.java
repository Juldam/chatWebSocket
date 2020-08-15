package com.julper.springbootbackendchat.controllers;

import com.julper.springbootbackendchat.models.documents.Mensaje;
import com.julper.springbootbackendchat.models.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ChatController {

    private String[] colores={"red", "green", "yellow", "orange", "purple", "blue", "magenta"};

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate websocket;

    @MessageMapping("/mensaje")//El destino sería /app/mensaje, el /app ya lo hemos definido en WebSocketConfig
    @SendTo("/chat/mensaje")//Nos subscribiremos desde Angular a este evento
    public Mensaje recibirMensaje(Mensaje mensaje){
        mensaje.setFecha(new Date().getTime());

        if(mensaje.getTipo().equals("Nuevo_usuario")){
            mensaje.setColor(colores[new Random().nextInt(colores.length)]);
            mensaje.setTexto("Nuevo usuario conectado");
        }else{
            chatService.guardarMensaje(mensaje);
        }

        return mensaje;
    }

    //Este método es para notificar cuando un usuario está escribiendo. Devolvemos un string, por ej: 'Menganito está escribiendo'
    @MessageMapping("/escribiendo")
    @SendTo("/chat/escribiendo")
    public String estaEscribiendo(String username){
        return username.concat(" está escribiendo");
    }


    //Este método es para recuperar el historial de conversaciones de MongoDB. Pero Lo hacemos así para enviarlos solo al cliente id que se acaba de conectar y no a todos los que estén en ese momento conectados al chat.
    @MessageMapping("/historial")
    public void historial(String clienteId){
        websocket.convertAndSend("/chat/historial/"+clienteId, chatService.obtenerUltimos10Mensajes());
    }

}
