package com.julper.springbootbackendchat.controllers;

import com.julper.springbootbackendchat.models.documents.Mensaje;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Random;

@Controller
public class ChatController {

    private String[] colores={"red", "green", "yellow", "orange", "purple", "blue", "magenta"};

    @MessageMapping("/mensaje")//El destino sería /app/mensaje, el /app ya lo hemos definido en WebSocketConfig
    @SendTo("/chat/mensaje")//Nos subscribiremos desde Angular a este evento
    public Mensaje recibirMensaje(Mensaje mensaje){
        mensaje.setFecha(new Date().getTime());

        if(mensaje.getTipo().equals("Nuevo_usuario")){
            mensaje.setColor(colores[new Random().nextInt(colores.length)]);
            mensaje.setTexto("Nuevo usuario conectado");
        }

        return mensaje;
    }

    //Este método es para notificar cuando un usuario está escribiendo. Devolvemos un string, por ej: 'Menganito está escribiendo'
    @MessageMapping("/escribiendo")
    @SendTo("/chat/escribiendo")
    public String estaEscribiendo(String username){
        return username.concat(" está escribiendo");
    }

}
