package com.exemplo.estoque.livros.demo.controller.sales;

import com.exemplo.estoque.livros.demo.controller.SalesController;
import com.exemplo.estoque.livros.demo.dto.*;
import com.exemplo.estoque.livros.demo.repository.LivroRepository;
import com.exemplo.estoque.livros.demo.repository.SalesRepository;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PostSalesControllerIT {

    @InjectMocks
    private SalesController salesController;

    @Mock
    private static UserRepository userRepository;

    @Mock
    private static LivroRepository livroRepository;

    @Mock
    private SalesRepository salesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void postSalesSucces(){
        var sales = new Sales(new DadosDeCadastroSales(1L, 120L, 10L, 50L));
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(livroRepository.existsById(anyLong())).thenReturn(true);
        when(salesRepository.save(any(Sales.class))).thenReturn(sales);

        ResponseEntity<String> responseEntity = salesController.postSales(new DadosDeCadastroSales(1L, 120L, 10L, 50L));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = "venda feita com sucesso";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(salesRepository).save(any(Sales.class));
    }

    @Test
    public void postSalesFailure() {
        when(userRepository.existsById(anyLong())).thenReturn(false);
        when(livroRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<String> responseEntity = salesController.postSales(new DadosDeCadastroSales(1L, 120L, 10L, 50L));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertEquals("Ocorreu algum erro ao tentar fazer a venda", responseEntity.getBody());
    }
}