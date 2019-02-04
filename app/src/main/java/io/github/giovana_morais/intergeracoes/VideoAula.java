package io.github.giovana_morais.intergeracoes;


public class VideoAula {
    private String grupo;   // grupo (wpp, facebook etc)
    private String nome;    // tema da videoaula em si
//    private URL url;        // link pro youtube
    private String url;

    VideoAula() {

    }

    VideoAula(String grupo, String nome, String url) {
        this.grupo = grupo;
        this.nome = nome;
        this.url = url;
    }

    public String getGrupo(){
        return this.grupo;
    }

    public String getNome(){
        return this.nome;
    }

    public String getUrl(){
        return this.url;
    }

    @Override
    public String toString() {
        return grupo + ": " + nome;
    }
}
