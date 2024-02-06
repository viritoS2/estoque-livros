package com.exemplo.estoque.livros.demo.controller.sales;


import com.exemplo.estoque.livros.demo.controller.SalesController;
import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroSales;
import com.exemplo.estoque.livros.demo.dto.Sales;
import com.exemplo.estoque.livros.demo.exceptions.GenericError;
import com.exemplo.estoque.livros.demo.repository.SalesRepository;
import com.google.gson.GsonBuilder;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GetSalesControllerIT {

    @InjectMocks
    private SalesController salesController;

    @Mock
    private SalesRepository salesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetSalesSucces() {
        List<Sales> listOfSales = new ArrayList<>();
        listOfSales.add(new Sales(new DadosDeCadastroSales(1L, 800L, 10L, 50L)));
        listOfSales.add(new Sales(new DadosDeCadastroSales(3L, 800L, 10L, 10L)));
        listOfSales.add(new Sales(new DadosDeCadastroSales(2L, 500L, 20L, 250L)));

        when(salesRepository.findAll()).thenReturn(listOfSales);
        ResponseEntity<String> responseEntity = salesController.getSales();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(listOfSales);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(salesRepository).findAll();


    }

    @Test
    public void testGetVoidSales(){
        List<Sales> listOfSales = new ArrayList<>();

        when(salesRepository.findAll()).thenReturn(listOfSales);
        ResponseEntity<String> responseEntity = salesController.getSales();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedJson = new GsonBuilder().setPrettyPrinting().create().toJson(listOfSales);
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(salesRepository).findAll();
    }

    @Test
    public void testGetSalesFails(){
        List<Sales> listOfSales = new ArrayList<>();
        listOfSales.add(new Sales(new DadosDeCadastroSales(1L, 800L, 10L, 50L)));
        listOfSales.add(new Sales(new DadosDeCadastroSales(3L, 800L, 10L, 10L)));
        listOfSales.add(new Sales(new DadosDeCadastroSales(2L, 500L, 20L, 250L)));

        when(salesRepository.findAll()).thenThrow(new GenericError("Internal server error"));

        ResponseEntity<String> responseEntity = salesController.getSales();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        String expectedJson = "Ocorreu um erro inesperado";
        String actualJson = responseEntity.getBody();

        assertEquals(expectedJson, actualJson);
        verify(salesRepository).findAll();

    }

}
