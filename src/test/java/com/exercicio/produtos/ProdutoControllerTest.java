package com.exercicio.produtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void fluxoCompletoDosCincoEndpoints() throws Exception {
        // POST
        mvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teclado\",\"preco\":150.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Teclado"));

        // GET lista
        mvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Teclado"));

        // GET por id
        mvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(150.0));

        // PATCH (só o preço)
        mvc.perform(patch("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"preco\":199.9}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teclado"))
                .andExpect(jsonPath("$.preco").value(199.9));

        // DELETE
        mvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());

        mvc.perform(get("/produtos/1"))
                .andExpect(status().isNotFound());
    }
}
