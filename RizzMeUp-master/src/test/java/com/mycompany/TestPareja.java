package com.mycompany;

import parejaideal.ParejaIdeal;
import dto.ResultadoCompatibilidadDTO;
import java.util.List;

public class TestPareja {
    public static void main(String[] args) {
        ParejaIdeal pi = new ParejaIdeal();
        System.out.println("Instanciado PI");
        List<ResultadoCompatibilidadDTO> res = pi.obtenerMejoresOpciones(1L);
        System.out.println("Lista size: " + (res == null ? "null" : res.size()));
        if (res != null) {
            for (ResultadoCompatibilidadDTO r : res) {
                System.out.println("Puntaje: " + r.getPuntajeTotal() + " | Nombre: " + r.getCandidato().getNombre());
            }
        }
    }
}
