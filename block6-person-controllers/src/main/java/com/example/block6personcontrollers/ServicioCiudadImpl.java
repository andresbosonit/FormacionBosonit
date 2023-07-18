package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioCiudadImpl implements ServicioCiudad{
    private List<Ciudad> ciudades = new ArrayList<>();
    @Override
    public void agregarCiudad(Ciudad ciudad) {
        ciudades.add(ciudad);
    }

    @Override
    public List<Ciudad> obtenerCiudades() {
        return ciudades;
    }
}
