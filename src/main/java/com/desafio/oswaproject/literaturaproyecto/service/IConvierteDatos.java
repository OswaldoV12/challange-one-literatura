package com.desafio.oswaproject.literaturaproyecto.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
