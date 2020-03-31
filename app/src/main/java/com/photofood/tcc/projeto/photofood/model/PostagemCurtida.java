package com.photofood.tcc.projeto.photofood.model;

import com.google.firebase.database.DatabaseReference;
import com.photofood.tcc.projeto.photofood.helper.ConfiguracaoFirebase;

import java.util.HashMap;

/**
 * Created by jamiltondamasceno
 */

public class PostagemCurtida {

    public Feed feed;
    public Usuario usuario;
    public int qtdCurtidas = 0;

    public PostagemCurtida() {
    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        //Objeto usuario
        HashMap<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("nomeUsuario", usuario.getNome() );
        dadosUsuario.put("caminhoFoto", usuario.getCaminhoFoto() );

        DatabaseReference pCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child( feed.getId() )//id_postagem
                .child( usuario.getId() );//id_usuario_logado
        pCurtidasRef.setValue( dadosUsuario );

        //atualizar quantidade de curtidas
        atualizarQtd(1);

    }

    public void atualizarQtd(int valor){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        DatabaseReference pCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child( feed.getId() )//id_postagem
                .child("qtdCurtidas");
        setQtdCurtidas( getQtdCurtidas() + valor );
        pCurtidasRef.setValue( getQtdCurtidas() );
    }

    public void remover(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        DatabaseReference pCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child( feed.getId() )//id_postagem
                .child( usuario.getId() );//id_usuario_logado
        pCurtidasRef.removeValue();

        //atualizar quantidade de curtidas
        atualizarQtd(-1);

    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(int qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }
}
