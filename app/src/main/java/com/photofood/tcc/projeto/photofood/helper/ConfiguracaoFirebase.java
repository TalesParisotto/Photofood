package com.photofood.tcc.projeto.photofood.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by jamiltondamasceno
 */

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference storage;

    //retorna a referencia do database
    public static DatabaseReference getFirebase(){
        if( referenciaFirebase == null ){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if( referenciaAutenticacao == null ){
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }

    //Retorna instancia do FirebaseStorage
    public static StorageReference getFirebaseStorage(){
        if( storage == null ){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }

}
