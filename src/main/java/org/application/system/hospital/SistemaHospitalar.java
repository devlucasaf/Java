package org.application.system.hospital;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SistemaHospitalar {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Hospital hospital = new Hospital("Hospital Central", "Av. Paulista, 1000");

        // Criando médicos
        Medico drJoao = new Medico(
                "João Silva", "12345678900", LocalDate.parse("15/03/1980", formatter),
                "(11) 99999-1111", "Rua A, 123", "M001", 12000.0,
                LocalDate.parse("10/01/2010", formatter), "Médico",
                "CRM-12345", "Cardiologia", 250.0
        );

        Medico draMaria = new Medico(
                "Maria Souza", "98765432100", LocalDate.parse("22/07/1975", formatter),
                "(11) 98888-2222", "Rua B, 456", "M002", 15000.0,
                LocalDate.parse("05/03/2012", formatter), "Médica",
                "CRM-54321", "Neurologia", 300.0
        );

        // Criando enfermeiros
        Enfermeiro enfermeiroPedro = new Enfermeiro(
                "Pedro Santos", "11122233344", LocalDate.parse("10/12/1990", formatter),
                "(11) 97777-3333", "Rua C, 789", "E001", 5500.0,
                LocalDate.parse("15/06/2015", formatter), "Enfermeiro",
                "COREN-001", "UTI", Turno.NOITE
        );

        Enfermeiro enfermeiraAna = new Enfermeiro(
                "Ana Costa", "44455566677", LocalDate.parse("05/05/1988", formatter),
                "(11) 96666-4444", "Rua D, 101", "E002", 5800.0,
                LocalDate.parse("20/08/2014", formatter), "Enfermeira",
                "COREN-002", "Emergência", Turno.TARDE
        );

        // Criando administrativo
        Administrativo admCarlos = new Administrativo(
                "Carlos Lima", "77788899900", LocalDate.parse("25/01/1985", formatter),
                "(11) 95555-5555", "Rua E, 202", "A001", 4800.0,
                LocalDate.parse("01/12/2018", formatter), "Administrativo",
                "Recepção", 2
        );

        // Criando pacientes
        Paciente paciente1 = new Paciente(
                "Roberto Alves", "12312312300", LocalDate.parse("12/09/1965", formatter),
                "(11) 94444-6666", "Rua F, 303", "P1001", "Unimed", TipoSangue.O_POSITIVO
        );

        Paciente paciente2 = new Paciente(
                "Juliana Mendes", "45645645611", LocalDate.parse("30/04/1990", formatter),
                "(11) 93333-7777", "Rua G, 404", "P1002", "Bradesco Saúde", TipoSangue.A_POSITIVO
        );

        Paciente paciente3 = new Paciente(
                "Fernando Rocha", "78978978922", LocalDate.parse("18/11/1972", formatter),
                "(11) 92222-8888", "Rua H, 505", "P1003", "SUS", TipoSangue.B_NEGATIVO
        );

        // Adicionando ao hospital
        hospital.adicionarFuncionario(drJoao);
        hospital.adicionarFuncionario(draMaria);
        hospital.adicionarFuncionario(enfermeiroPedro);
        hospital.adicionarFuncionario(enfermeiraAna);
        hospital.adicionarFuncionario(admCarlos);

        hospital.adicionarPaciente(paciente1);
        hospital.adicionarPaciente(paciente2);
        hospital.adicionarPaciente(paciente3);

        // Associando médicos responsáveis
        paciente1.associarMedico(drJoao);
        paciente2.associarMedico(draMaria);
        paciente3.associarMedico(drJoao);

        // Adicionando doenças
        paciente1.adicionarDoenca("Hipertensão");
        paciente1.adicionarDoenca("Diabetes tipo 2");
        paciente2.adicionarDoenca("Enxaqueca crônica");
        paciente3.adicionarDoenca("Arritmia cardíaca");

        // Internações
        paciente1.internar();
        paciente3.internar();

        // Atendimentos médicos
        drJoao.atenderPaciente(paciente1);
        drJoao.atenderPaciente(paciente3);
        draMaria.atenderPaciente(paciente2);

        drJoao.realizarCirurgia();
        drJoao.emitirReceita(paciente1, "Losartana 50mg");

        // Ações de enfermagem
        enfermeiroPedro.administrarMedicamento(paciente1, "Insulina");
        enfermeiraAna.verificarSinaisVitais(paciente2);

        // Ações administrativas
        admCarlos.agendarConsulta(paciente2, draMaria, LocalDate.now().plusDays(3));
        admCarlos.processarDocumento("Autorização de internação - " + paciente1.getNumeroProntuario());

        // Aumento salarial
        drJoao.aplicarAumento(5.0);
        System.out.println("Novo salário do Dr. João: R$" + drJoao.calcularSalario());

        // Listagens
        hospital.listarTodosPacientes();
        hospital.listarFuncionariosPorTipo();
        hospital.listarMedicosPorEspecialidade("Cardiologia");
        hospital.calcularFolhaPagamento();

        // Detalhes de um paciente
        System.out.println("\n===== DETALHES DO PACIENTE =====");
        paciente1.exibirInformacoes();

        // Polimorfismo
        System.out.println("\n===== POLIMORFISMO DE SALÁRIO =====");
        for (Funcionario f : hospital.getFuncionarios()) {
            System.out.println(f.getNome() + " (salário calculado): R$" + String.format("%.2f", f.calcularSalario()));
        }
    }
}
