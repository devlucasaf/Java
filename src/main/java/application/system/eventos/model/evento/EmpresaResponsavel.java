package application.system.eventos.model.evento;

import java.util.ArrayList;
import java.util.List;

public class EmpresaResponsavel {
    private String          razaoSocial;
    private String          cnpj;
    private String          endereco;
    private String          telefone;
    private List<Evento>    eventosRealizados;

    public EmpresaResponsavel(String razaoSocial, String cnpj, String endereco, String telefone) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.eventosRealizados = new ArrayList<>();
    }

    public void adicionarEvento(Evento evento) {
        if (evento != null && !eventosRealizados.contains(evento)) {
            eventosRealizados.add(evento);
            System.out.println("Evento '" + evento.getNome() + "' registrado pela empresa " + razaoSocial);
        }
    }

    public void exibirInformacoes() {
        System.out.println("--- Empresa Organizadora ---");
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Eventos realizados: " + eventosRealizados.size());
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Evento> getEventosRealizados() {
        return eventosRealizados;
    }

    public void setEventosRealizados(List<Evento> eventosRealizados) {
        this.eventosRealizados = eventosRealizados;
    }

}
