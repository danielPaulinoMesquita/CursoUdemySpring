package com.daniel.cursoudemy.domain.enums;

public enum Perfil {
    ADMIN(1,"ROLE_ADMIN"),
    CLIENTE(2,"ROLE_CLIENTE");

    private Integer cod;
    private String descricao;

    Perfil(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod){
        if(cod==null){
            return null;
        }

        for(Perfil estado: Perfil.values()){
            if(cod.equals(estado.cod)){
                return estado;
            }
        }

        throw new IllegalArgumentException("Id inválido: "+cod);

    }
}
