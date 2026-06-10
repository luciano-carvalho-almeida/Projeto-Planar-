package com.unifan.planar.service;

import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private VeiculoService veiculoService;

    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo(1L, "ABC-1234", "Civic", 2020, 50000.0);
    }

    // --- listarTodos ---

    @Test
    void deveRetornarListaDeVeiculos() {
        when(repository.findAll()).thenReturn(List.of(veiculo));

        List<Veiculo> resultado = veiculoService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPlaca()).isEqualTo("ABC-1234");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaVeiculos() {
        when(repository.findAll()).thenReturn(List.of());

        List<Veiculo> resultado = veiculoService.listarTodos();

        assertThat(resultado).isEmpty();
    }

    // --- salvar ---

    @Test
    void deveSalvarVeiculoERetornarSalvo() {
        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo resultado = veiculoService.salvar(veiculo);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getModelo()).isEqualTo("Civic");
        verify(repository, times(1)).save(veiculo);
    }

    // --- buscarPorId ---

    @Test
    void deveRetornarVeiculoQuandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(veiculo));

        Optional<Veiculo> resultado = veiculoService.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getPlaca()).isEqualTo("ABC-1234");
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Veiculo> resultado = veiculoService.buscarPorId(99L);

        assertThat(resultado).isEmpty();
    }

    // --- deletar ---

    @Test
    void deveDeletarVeiculoPorId() {
        doNothing().when(repository).deleteById(1L);

        veiculoService.deletar(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    // --- atualizarQuilometragem ---

    @Test
    void deveAtualizarQuilometragemComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo resultado = veiculoService.atualizarQuilometragem(1L, 75000.0);

        assertThat(resultado.getQuilometragemAtual()).isEqualTo(75000.0);
        verify(repository, times(1)).save(veiculo);
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoEncontradoAoAtualizar() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> veiculoService.atualizarQuilometragem(99L, 75000.0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Veículo não encontrado");

        verify(repository, never()).save(any());
    }
}
