package com.photofood.tcc.projeto.photofood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.photofood.tcc.projeto.photofood.R;
import com.photofood.tcc.projeto.photofood.activity.ComentariosActivity;
import com.photofood.tcc.projeto.photofood.helper.ConfiguracaoFirebase;
import com.photofood.tcc.projeto.photofood.helper.UsuarioFirebase;
import com.photofood.tcc.projeto.photofood.model.Feed;
import com.photofood.tcc.projeto.photofood.model.PostagemCurtida;
import com.photofood.tcc.projeto.photofood.model.Usuario;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jamiltondamasceno
 */

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {

    private List<Feed> listaFeed;
    private Context context;

    public AdapterFeed(List<Feed> listaFeed, Context context) {
        this.listaFeed = listaFeed;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed, parent, false);
        return new AdapterFeed.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Feed feed = listaFeed.get(position);
        final Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Carrega dados do feed
        Uri uriFotoUsuario = Uri.parse( feed.getFotoUsuario() );
        Uri uriFotoPostagem = Uri.parse( feed.getFotoPostagem() );

        Glide.with( context ).load( uriFotoUsuario ).into(holder.fotoPerfil);
        Glide.with( context ).load( uriFotoPostagem ).into(holder.fotoPostagem);

        holder.descricao.setText( feed.getDescricao() );
        holder.nome.setText( feed.getNomeUsuario() );

        //Adiciona evento de clique nos comentários
        holder.visualizarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComentariosActivity.class);
                i.putExtra("idPostagem", feed.getId() );
                context.startActivity( i );
            }
        });

        /*
        postagens-curtidas
            + id_postagem
                + qtdCurtidas
                + id_usuario
                    nome_usuario
                    caminho_foto
        * */
        //Recuperar dados da postagem curtida
        DatabaseReference curtidasRef = ConfiguracaoFirebase.getFirebase()
                .child("postagens-curtidas")
                .child( feed.getId() );
        curtidasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int qtdCurtidas = 0;
                if( dataSnapshot.hasChild("qtdCurtidas") ){
                    PostagemCurtida postagemCurtida = dataSnapshot.getValue( PostagemCurtida.class );
                    qtdCurtidas = postagemCurtida.getQtdCurtidas();
                }

                //Verifica se já foi clicado
                if( dataSnapshot.hasChild( usuarioLogado.getId() ) ){
                    holder.likeButton.setLiked(true);
                }else {
                    holder.likeButton.setLiked(false);
                }

                //Monta objeto postagem curtida
                final PostagemCurtida curtida = new PostagemCurtida();
                curtida.setFeed( feed );
                curtida.setUsuario( usuarioLogado );
                curtida.setQtdCurtidas( qtdCurtidas );

                //Adiciona eventos para curtir uma foto
                holder.likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        curtida.salvar();
                        holder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        curtida.remover();
                        holder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );
                    }
                });

                holder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return listaFeed.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView fotoPerfil;
        TextView nome, descricao, qtdCurtidas;
        ImageView fotoPostagem, visualizarComentario;
        LikeButton likeButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            fotoPerfil   = itemView.findViewById(R.id.imagePerfilPostagem);
            fotoPostagem = itemView.findViewById(R.id.imagePostagemSelecionada);
            nome         = itemView.findViewById(R.id.textPerfilPostagem);
            qtdCurtidas  = itemView.findViewById(R.id.textQtdCurtidasPostagem);
            descricao    = itemView.findViewById(R.id.textDescricaoPostagem);
            visualizarComentario    = itemView.findViewById(R.id.imageComentarioFeed);
            likeButton = itemView.findViewById(R.id.likeButtonFeed);
        }
    }

}
