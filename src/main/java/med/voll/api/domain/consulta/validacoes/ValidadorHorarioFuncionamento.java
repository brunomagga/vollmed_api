package med.voll.api.domain.consulta.validacoes;


import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamento {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesAberturaCLinica = dataConsulta.getHour() < 7;
        var depoisEncerramentoClinina = dataConsulta.getHour() > 18;

        if( domingo || antesAberturaCLinica || depoisEncerramentoClinina){
            throw new ValidacaoException("Consulta fora do horario permitido");
        }
    }
}
