package application.system.hospital;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SistemaTriagemHospitalar {

    public static void main(String[] args) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Hospital hospital = new Hospital("Hospital Central", "Av. Paulista, 1000");

        Medico medicoCardiologista = new Medico("João Silva", "12345678900", LocalDate.parse("15/03/1980", formatador), "(11) 99999-1111", "Rua A, 123", "M001", 12000.0, LocalDate.parse("10/01/2010", formatador), "Médico", "CRM-12345", "Cardiologia", 250.0);

        Medico medicaNeurologista = new Medico("Maria Souza", "98765432100", LocalDate.parse("22/07/1975", formatador), "(11) 98888-2222", "Rua B, 456", "M002", 15000.0, LocalDate.parse("05/03/2012", formatador), "Médica", "CRM-54321", "Neurologia", 300.0);

        Paciente paciente1 = new Paciente("Roberto Alves", "12312312300", LocalDate.parse("12/09/1965", formatador), "(11) 94444-6666", "Rua F, 303", "P1001", "Unimed", TipoSangue.O_POSITIVO);

        Paciente paciente2 = new Paciente("Juliana Mendes", "45645645611", LocalDate.parse("30/04/1990", formatador), "(11) 93333-7777", "Rua G, 404", "P1002", "Bradesco Saúde", TipoSangue.A_POSITIVO);

        Paciente paciente3 = new Paciente("Fernando Rocha", "78978978922", LocalDate.parse("18/11/1972", formatador), "(11) 92222-8888", "Rua H, 505", "P1003", "SUS", TipoSangue.B_NEGATIVO);

        hospital.adicionarFuncionario(medicoCardiologista);
        hospital.adicionarFuncionario(medicaNeurologista);

        hospital.adicionarPaciente(paciente1);
        hospital.adicionarPaciente(paciente2);
        hospital.adicionarPaciente(paciente3);

        hospital.registrarTriagem(paciente1, GravidadeTriagem.URGENTE, Arrays.asList("Dor no peito", "Falta de ar"), "Paciente hipertenso.");

        hospital.registrarTriagem(paciente2, GravidadeTriagem.POUCO_URGENTE, Arrays.asList("Dor de cabeça", "Náusea"), "Sintomas iniciados pela manhã.");

        hospital.registrarTriagem(paciente3, GravidadeTriagem.MUITO_URGENTE, Arrays.asList("Desmaio", "Arritmia"), "Paciente chegou acompanhado.");

        hospital.listarFilaTriagem();

        System.out.println("\n===== INÍCIO DOS ATENDIMENTOS =====");

        RegistroTriagem primeiroAtendimento = hospital.chamarProximoPaciente(medicoCardiologista);

        if (primeiroAtendimento != null) {
            System.out.println("Atendimento iniciado: " + primeiroAtendimento.getPaciente().getNome());
        }

        RegistroTriagem segundoAtendimento = hospital.chamarProximoPaciente(medicaNeurologista);

        if (segundoAtendimento != null) {
            System.out.println("Atendimento iniciado: " + segundoAtendimento.getPaciente().getNome());
        }

        hospital.listarFilaTriagem();

        System.out.println("\n===== FINALIZAÇÃO =====");

        if (medicoCardiologista.getAtendimentoAtual() != null) {
            RegistroTriagem finalizado = medicoCardiologista.finalizarAtendimentoTriagem();
            System.out.println("Atendimento finalizado para " + finalizado.getPaciente().getNome() + ".");
        }

        hospital.chamarProximoPaciente(medicoCardiologista);

        System.out.println("\n===== SITUAÇÃO DOS MÉDICOS =====");

        for (Medico medico : hospital.getMedicos()) {
            System.out.println(medico.getNome() + ": " + (medico.isDisponivel() ? "Disponível" : "Em atendimento"));
        }
    }
}

