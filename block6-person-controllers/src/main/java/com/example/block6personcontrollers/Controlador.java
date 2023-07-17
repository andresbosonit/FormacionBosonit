package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/controlador")
public class Controlador {
    private final Persona personaBean1;
    private final Persona personaBean2;
    private final Persona personaBean3;
    public Controlador(@Qualifier("bean1") Persona personaBean1, @Qualifier("bean2") Persona personaBean2, @Qualifier("bean3") Persona personaBean3){
        this.personaBean1 = personaBean1;
        this.personaBean2 = personaBean2;
        this.personaBean3 = personaBean3;
    }

    @GetMapping(value = "/bean/{bean}")
    public Persona bean(@PathVariable String bean){
        if(bean.equals("bean1")){
            return this.personaBean1;
        }else if(bean.equals("bean2")) {
            return this.personaBean2;
        }else{
            return this.personaBean3;
        }
    }
}
