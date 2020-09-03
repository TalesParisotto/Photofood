package com.photofood.tcc.projeto.photofood.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.photofood.tcc.projeto.photofood.R;
import com.photofood.tcc.projeto.photofood.model.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jamiltondamasceno
 */

public class AdapterPesquisa extends RecyclerView.Adapter<AdapterPesquisa.MyViewHolder> {

    private List<Usuario> listaUsuario;
    private Context context;

    public AdapterPesquisa(List<Usuario> l, Context c) {
        this.listaUsuario = l;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesquisa_usuario, parent, false);
        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Usuario usuario = listaUsuario.get(position);

        holder.nome.setText( usuario.getNome() );

        if( usuario.getCaminhoFoto() != null ){
            Uri uri = Uri.parse( usuario.getCaminhoFoto() );
            Glide.with(context).load(uri).into(holder.foto);
        }else {
            holder.foto.setImageResource(R.drawable.avatar);
        }

    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView foto;
        TextView nome;


        public MyViewHolder(View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageFotoPesquisa);
            nome = itemView.findViewById(R.id.textNomePesquisa);

        }
    }

}
